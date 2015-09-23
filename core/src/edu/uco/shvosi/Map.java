package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import java.util.ArrayList;
import java.util.List;

public class Map {

    private TiledMap tiledMap;
    private TiledMapRenderer renderer;
    private int[][] mapGrid;
    private int[][] entityGrid;
    private List<Entity> entityList;
    private int tileDimension;
    private int width;
    private int height;
    private Protagonist bernard;

    public Map(Protagonist bernard, String tmxFileName) {
        this.bernard = bernard;
        this.tiledMap = new TmxMapLoader().load(tmxFileName);
        this.renderer = new OrthogonalTiledMapRenderer(this.tiledMap);
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);

        //Setup mapGrid
        mapGrid = new int[layer.getWidth()][layer.getHeight()];
        for (int x = 0; x < layer.getWidth(); x++) {
            for (int y = 0; y < layer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                MapProperties properties = cell.getTile().getProperties();
                if (properties.get("WALL") != null) {
                    mapGrid[x][y] = MapGridCode.WALL;
                } else if (properties.get("STRUCTURE") != null) {
                    mapGrid[x][y] = MapGridCode.STRUCTURE;
                } else if (properties.get("EXIT") != null) {
                    mapGrid[x][y] = MapGridCode.EXIT;
                } else {
                    mapGrid[x][y] = MapGridCode.FLOOR;
                }

                //To animate a cell tile, see below
                //cell.setTile(new AnimatedTiledMapTile(0.5f,waterTiles));
            }
        }

        //Setup entityGrid
        entityList = new ArrayList<Entity>();
        this.entityGrid = new int[mapGrid.length][mapGrid[0].length];

        // Make all cells empty
        for (int i = 0; i < entityGrid.length; i++) {
            for (int j = 0; j < entityGrid[i].length; j++) {
                entityGrid[i][j] = EntityGridCode.EMPTY;
            }
        }

        // Populate Entity Grid
        populateMapForTesting();

        this.tileDimension = Constants.TILEDIMENSION;
        this.width = mapGrid.length * tileDimension;
        this.height = mapGrid[0].length * tileDimension;
    }

    public void render(OrthographicCamera camera) {
        renderer.setView(camera);
        renderer.render();
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int[][] getMapGrid() {
        return this.mapGrid;
    }

    public int[][] getEntityGrid() {
        return this.entityGrid;
    }

    public List<Entity> getEntityList() {
        return this.entityList;
    }

    private void populateMapForTesting() {
        //Initialize Bernard's position and add him
        bernard.setCX(1);
        bernard.setDCX(1);
        bernard.setCY(1);
        bernard.setDCY(1);
        bernard.setX(bernard.getCX() * Constants.TILEDIMENSION);
        bernard.setY(bernard.getCY() * Constants.TILEDIMENSION);
        entityList.add(bernard);

        //Add Traps
        entityList.add(new TrapType1(TextureLoader.TRAPTEXTURE, 2, 2));
        entityList.add(new TrapType2(TextureLoader.TRAPTEXTURE2, 3, 2));
        entityList.add(new TrapType1(TextureLoader.TRAPTEXTURE, 2, 4));
        entityList.add(new TrapType2(TextureLoader.TRAPTEXTURE2, 3, 3));

        //Add Items
        entityList.add(new ItemHeart(TextureLoader.HEALTHTEXTURE, 5, 3));
        entityList.add(new ItemHeart(TextureLoader.HEALTHTEXTURE, 6, 3));

        //Some Random Enemies for testing, currently bernard texture
        entityList.add(new Wanderer(TextureLoader.WANDERTEXTURE, 6, 6));
        entityList.add(new Wanderer(TextureLoader.WANDERTEXTURE, 6, 7));
        entityList.add(new Wanderer(TextureLoader.WANDERTEXTURE, 6, 8));
        entityList.add(new Wanderer(TextureLoader.WANDERTEXTURE, 7, 6));
        entityList.add(new Wanderer(TextureLoader.WANDERTEXTURE, 7, 7));
        entityList.add(new Wanderer(TextureLoader.WANDERTEXTURE, 7, 8));
        entityList.add(new Wanderer(TextureLoader.WANDERTEXTURE, 8, 6));
        entityList.add(new Wanderer(TextureLoader.WANDERTEXTURE, 8, 7));
        entityList.add(new Wanderer(TextureLoader.WANDERTEXTURE, 8, 8));

        // Populate the cells from the entity list
        for (int i = 0; i < this.entityList.size(); i++) {
            Entity entity = this.entityList.get(i);
            this.entityGrid[entity.getCX()][entity.getCY()] = entity.getEntityType();
        }
    }

    public boolean bernardCanMove(Direction direction) {
        if (direction == Direction.UP) {
            if (bernard.getCY() == mapGrid[0].length - 1) {
                return false;
            }
            if (mapGrid[bernard.getCX()][bernard.getCY() + 1] == MapGridCode.EXIT || (mapGrid[bernard.getCX()][bernard.getCY() + 1] == MapGridCode.FLOOR
                    && entityGrid[bernard.getCX()][bernard.getCY() + 1] != EntityGridCode.ENEMY)) {
                bernard.setDCY(bernard.getCY() + 1);
                return true;
            }
        } else if (direction == Direction.DOWN) {
            if (bernard.getCY() == 0) {
                return false;
            }
            if (mapGrid[bernard.getCX()][bernard.getCY() - 1] == MapGridCode.EXIT || (mapGrid[bernard.getCX()][bernard.getCY() - 1] == MapGridCode.FLOOR
                    && entityGrid[bernard.getCX()][bernard.getCY() - 1] != EntityGridCode.ENEMY)) {
                bernard.setDCY(bernard.getCY() - 1);
                return true;
            }
        } else if (direction == Direction.LEFT) {
            if (bernard.getCX() == 0) {
                return false;
            }
            if (mapGrid[bernard.getCX() - 1][bernard.getCY()] == MapGridCode.EXIT || (mapGrid[bernard.getCX() - 1][bernard.getCY()] == MapGridCode.FLOOR
                    && entityGrid[bernard.getCX() - 1][bernard.getCY()] != EntityGridCode.ENEMY)) {
                bernard.setDCX(bernard.getCX() - 1);
                return true;
            }
        } else if (direction == Direction.RIGHT) {
            if (bernard.getCX() == mapGrid.length - 1) {
                return false;
            }
            if (mapGrid[bernard.getCX() + 1][bernard.getCY()] == MapGridCode.EXIT || (mapGrid[bernard.getCX() + 1][bernard.getCY()] == MapGridCode.FLOOR
                    && entityGrid[bernard.getCX() + 1][bernard.getCY()] != EntityGridCode.ENEMY)) {
                bernard.setDCX(bernard.getCX() + 1);
                return true;
            }
        } else {
            // No direction
        }
        return false;
    }

    public boolean enemyCanMove(Entity entity, Direction direction) {
        if (direction == Direction.UP) {
            if (entity.getCY() == mapGrid[0].length - 1) {
                return false;
            }
            if (mapGrid[entity.getCX()][entity.getCY() + 1] == MapGridCode.FLOOR
                    && entityGrid[entity.getCX()][entity.getCY() + 1] == EntityGridCode.EMPTY) {
                entity.setDCY(entity.getCY() + 1);
                return true;
            }
        } else if (direction == Direction.DOWN) {
            if (entity.getCY() == 0) {
                return false;
            }
            if (mapGrid[entity.getCX()][entity.getCY() - 1] == MapGridCode.FLOOR
                    && entityGrid[entity.getCX()][entity.getCY() - 1] == EntityGridCode.EMPTY) {
                entity.setDCY(entity.getCY() - 1);
                return true;
            }
        } else if (direction == Direction.LEFT) {
            if (entity.getCX() == 0) {
                return false;
            }
            if (mapGrid[entity.getCX() - 1][entity.getCY()] == MapGridCode.FLOOR
                    && entityGrid[entity.getCX() - 1][entity.getCY()] == EntityGridCode.EMPTY) {
                entity.setDCX(entity.getCX() - 1);
                return true;
            }
        } else if (direction == Direction.RIGHT) {
            if (entity.getCX() == mapGrid.length - 1) {
                return false;
            }
            if (mapGrid[entity.getCX() + 1][entity.getCY()] == MapGridCode.FLOOR
                    && entityGrid[entity.getCX() + 1][entity.getCY()] == EntityGridCode.EMPTY) {
                entity.setDCX(entity.getCX() + 1);
                return true;
            }
        } else {
            // No direction
        }
        return false;
    }

    public void moveEntity(Entity entity) {
        entityGrid[entity.getCX()][entity.getCY()] = EntityGridCode.EMPTY;
        entityGrid[entity.getDCX()][entity.getDCY()] = entity.getEntityType();
        entity.setCX(entity.getDCX());
        entity.setCY(entity.getDCY());
    }

    public boolean collision(Entity aggressor, Entity receiver) {
        if (aggressor.getCX() == receiver.getCX()
                && aggressor.getCY() == receiver.getCY()) {
            aggressor.collision(receiver);
            return true;
        }
        return false;
    }

    public boolean isAlive(Entity entity) {
        if (entity.getHealth() <= 0) {
            //entityGrid[entity.getCX()][entity.getCY()] = EntityGridCode.EMPTY;
            Gdx.app.log("ENTITY", "DEAD");
            return false;
        }
        return true;
    }

    public boolean exitReached() {
        if (mapGrid[bernard.getCX()][bernard.getCY()] == MapGridCode.EXIT) {
            return true;
        }
        return false;
    }

    public void calculateAITurn(Entity entity) {
        if (entity.getEntityType() == EntityGridCode.ENEMY) {
            Antagonist enemy = (Antagonist) entity;

            //Change this method to however you want enemies to behave
            enemy.calculateTurn(mapGrid, entityGrid);
        }
    }

    public void playTurn(Entity entity) {
        TurnAction action = entity.getTurnAction();
        switch (entity.getEntityType()) {
            case EntityGridCode.PLAYER:
                //Do Bernard Stuff
                if (action == TurnAction.MOVE) {
                    moveEntity(entity);
                }
                else if (action == TurnAction.ATTACK)
                {
                    //Attack stuff?
                }
                break;
            case EntityGridCode.ENEMY:
                calculateAITurn(entity);
                if (action == TurnAction.MOVE) {
                    moveEntity(entity);
                }
                else if (action == TurnAction.ATTACK)
                {
                    //Attack stuff?
                }
                break;
            default:
                //Other stuff
                break;
        }
    }

    public void dispose() {
        tiledMap.dispose();
    }
}
