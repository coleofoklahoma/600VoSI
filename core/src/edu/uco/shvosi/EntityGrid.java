package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import java.util.List;

public class EntityGrid {
    private int[][] mapGrid;
    private int[][] entityGrid;
    private List<Entity> entityList;
    
    public EntityGrid(int[][] map, List<Entity> entityList){
        this.mapGrid = map;
        this.entityGrid = new int[mapGrid.length][mapGrid[0].length];
        this.entityList = entityList;
		
        // Make all cells empty
        for(int i = 0; i < entityGrid.length; i++){
            for(int j = 0; j < entityGrid[i].length; j++){
                entityGrid[i][j] = EntityGridCode.EMPTY;
            }
        }
		
        // Populate the cells from the entity list
        for(int i = 0; i < this.entityList.size(); i++){
            Entity entity = this.entityList.get(i);
            this.entityGrid[entity.getCX()][entity.getCY()] = entity.getEntityType();
        }
    }
    
    public boolean canMove(Entity entity, Direction direction){
        if(direction == Direction.UP){
            if(entity.getCY() == mapGrid[0].length - 1){
                return false;
            }
            if(mapGrid[entity.getCX()][entity.getCY() + 1] == MapGridCode.FLOOR &&
                (entityGrid[entity.getCX()][entity.getCY() + 1] != EntityGridCode.ENEMY &&
                 entityGrid[entity.getCX()][entity.getCY() + 1] != EntityGridCode.PLAYER)){
                entity.setDCY(entity.getCY() + 1);
                return true;
            }
        }
        else if(direction == Direction.DOWN){
            if(entity.getCY() == 0){
                return false;
            }
            if(mapGrid[entity.getCX()][entity.getCY() - 1] == MapGridCode.FLOOR &&
                (entityGrid[entity.getCX()][entity.getCY() - 1] != EntityGridCode.ENEMY &&
                 entityGrid[entity.getCX()][entity.getCY() - 1] != EntityGridCode.PLAYER)){
                entity.setDCY(entity.getCY() - 1);
                return true;
            }
        }
        else if(direction == Direction.LEFT){
            if(entity.getCX() == 0){
                return false;
            }
            if(mapGrid[entity.getCX() - 1][entity.getCY()] == MapGridCode.FLOOR &&
                (entityGrid[entity.getCX() - 1][entity.getCY()] != EntityGridCode.ENEMY &&
                 entityGrid[entity.getCX() - 1][entity.getCY()] != EntityGridCode.PLAYER)){
                entity.setDCX(entity.getCX() - 1);
                return true;
            }
        }
        else if(direction == Direction.RIGHT){
            if(entity.getCX() == mapGrid.length - 1){
                return false;
            }
            if(mapGrid[entity.getCX() + 1][entity.getCY()] == MapGridCode.FLOOR &&
                (entityGrid[entity.getCX() + 1][entity.getCY()] != EntityGridCode.ENEMY &&
                 entityGrid[entity.getCX() + 1][entity.getCY()] != EntityGridCode.PLAYER)){
                entity.setDCX(entity.getCX() + 1);
                return true;
            }
        }
        else {
                // No direction
        }
        return false;
    }
	
    public void moveEntity(Entity entity){
        entityGrid[entity.getCX()][entity.getCY()] = EntityGridCode.EMPTY;
        entityGrid[entity.getDCX()][entity.getDCY()] = entity.getEntityType();
        entity.setCX(entity.getDCX());
        entity.setCY(entity.getDCY());
    }
    
    public boolean collision(Entity entity){
        Entity bernard = entityList.get(0);
        if(entity.getCX() == bernard.getCX() &&
           entity.getCY() == bernard.getCY()){
            entity.collision(bernard);
            return true;
        }
        return false;
    }
    
    public boolean isAlive(Entity entity){
        if(entity.getHealth() <= 0){
            entityGrid[entity.getCX()][entity.getCY()] = EntityGridCode.EMPTY;
            Gdx.app.log("ENTITY", "DEAD");
            return false;
        }
        return true;
    }

    public void calculateAITurn(Entity entity){
        if(entity.getEntityType() == EntityGridCode.ENEMY){
            int random = 0;
            Direction d = Direction.NONE;

            while(!canMove(entity, d)){
                random = (int)(Math.random() * entityGrid.length);
                switch(random % 4){
                    case 1:
                        d = Direction.UP;
                        break;
                    case 2:
                        d = Direction.DOWN;
                        break;
                    case 3:
                        d = Direction.LEFT;
                        break;
                    default:
                        d = Direction.RIGHT;
                        break;
                }
            }
        }
    }
}
