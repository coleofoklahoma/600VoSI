package edu.uco.shvosi;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import java.util.ArrayList;
import java.util.List;

public class LevelZero extends ApplicationAdapter {

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Protagonist bernard;
    private Antagonist wanderer;
    private ItemHeart health;
    private ItemHeart health2;
    private TrapType1 trap;
    private TrapType2 trap2;
    private TrapType1 trap3;
    private TrapType2 trap4;
    private Map map;
    private EntityGrid entityGrid;
    private TextureLoader tl; // Creating this variable so that I can get some animations made and then they can be disposed of properly. --Cody
    private List<Entity> entityList;
    private Stage stage;
    private String healthpoints;
    BitmapFont healthDisplay;
    private int gameState = 1;
    private Sprite splash;
    private Texture splashT;
    private Sprite startBut;
    private Texture startT;
    private Sprite quitBut;
    private Texture quitT;
    private Skin skin;
    private Label healthLabel;
    //Ignore this variable
    int toggle = 1;
    
     public void LevelZero ()
    {
        create();
    }

    @Override
    public void create() {
        Gdx.input.setInputProcessor(stage);
        entityList = new ArrayList<Entity>();
        tl = new TextureLoader();
        batch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        
        // Create and add entities to the list
        bernard = new Protagonist(TextureLoader.BERNARDTEXTURE, 1, 1);
        wanderer = new Antagonist(TextureLoader.WANDERTEXTURE, 2, 4);
        entityList.add(bernard);
        entityList.add(bernard);
        bernard.setHealth(bernard.getHealth());
        healthDisplay = new BitmapFont();
        splashT = new Texture(Gdx.files.internal("splash.png"));
        splash = new Sprite(splashT, 800, 450);
        splash.setPosition(0, 0);
        startT = new Texture(Gdx.files.internal("startButtonS.png"));
        startBut = new Sprite(startT, 100, 50);
        startBut.setPosition(550, 15);
        quitT = new Texture(Gdx.files.internal("quitButton.png"));
        quitBut = new Sprite(quitT, 100, 50);
        quitBut.setPosition(650, 15);

        //Some Random Enemies for testing, currently bernard texture
        entityList.add(new Antagonist(TextureLoader.WANDERTEXTURE, 6, 6));
        entityList.add(new Antagonist(TextureLoader.WANDERTEXTURE, 6, 7));
        entityList.add(new Antagonist(TextureLoader.WANDERTEXTURE, 6, 8));
        entityList.add(new Antagonist(TextureLoader.WANDERTEXTURE, 6, 9));
        entityList.add(new Antagonist(TextureLoader.WANDERTEXTURE, 6, 10));
        entityList.add(new Antagonist(TextureLoader.WANDERTEXTURE, 6, 11));
        entityList.add(new Antagonist(TextureLoader.WANDERTEXTURE, 6, 12));
        entityList.add(new Antagonist(TextureLoader.WANDERTEXTURE, 6, 13));
        entityList.add(new Antagonist(TextureLoader.WANDERTEXTURE, 6, 14));

        health = new ItemHeart(TextureLoader.HEALTHTEXTURE, 5, 3);
        entityList.add(health);

        health2 = new ItemHeart(TextureLoader.HEALTHTEXTURE, 6, 3);
        entityList.add(health2);

        trap = new TrapType1(TextureLoader.TRAPTEXTURE, 2, 2);
        entityList.add(trap);

        trap2 = new TrapType2(TextureLoader.TRAPTEXTURE2, 3, 2);
        entityList.add(trap2);

        trap3 = new TrapType1(TextureLoader.TRAPTEXTURE, 2, 4);
        entityList.add(trap3);

        trap4 = new TrapType2(TextureLoader.TRAPTEXTURE2, 3, 3);
        entityList.add(trap4);

        bernard.addObserver(trap);
        bernard.addObserver(trap2);
        bernard.addObserver(trap3);
        bernard.addObserver(trap4);

        //Initialize Camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.SCREENWIDTH, Constants.SCREENHEIGHT);
        FitViewport fv = new FitViewport(Constants.SCREENWIDTH, Constants.SCREENHEIGHT, camera);
        stage = new Stage(fv, batch);
        centerCameraOn(bernard);

        // Add the entities to the stage
        for (int i = 0; i < entityList.size(); i++) {
            stage.addActor(entityList.get(i));
            switch (entityList.get(i).getEntityType()) {
                case EntityGridCode.PLAYER:
                    stage.getActors().get(i).setZIndex(3);
                    break;
                case EntityGridCode.ENEMY:
                    stage.getActors().get(i).setZIndex(2);
                    break;
                case EntityGridCode.TRAP:
                case EntityGridCode.ITEM:
                default:
                    stage.getActors().get(i).setZIndex(1);
                    break;
            }
        }

        //Test Level Grid Creation
        map = new Map(bernard, "maps/testmap.tmx");
        entityGrid = new EntityGrid(map.getMapGrid(), entityList);
        
        //Health Display
        healthLabel = new Label("HP: ", skin);
        stage.addActor(healthLabel);
    }

    @Override
    public void render() {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        map.render(camera);
        batch.end();
        
        //temporary health display
        healthpoints = "HP: " + bernard.getHealth();
        healthLabel.setX(bernard.getX() + 25);
        healthLabel.setY(bernard.getY() + 250);
        healthLabel.setText(healthpoints);
        
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        centerCameraOn(bernard);
        camera.update();

        //Bernard Controls
            //Movement
        if (Gdx.input.isKeyJustPressed(Keys.W) && entityGrid.canMove(bernard, Direction.UP)) {
            bernard.setPlayTurn(true);
            bernard.notifyObservers();
            bernard.setDirection(Direction.UP);
            Gdx.app.log("MOVING", "UP");
        } else if (Gdx.input.isKeyJustPressed(Keys.S) && entityGrid.canMove(bernard, Direction.DOWN)) {
            bernard.setPlayTurn(true);
            bernard.notifyObservers();
            bernard.setDirection(Direction.DOWN);
            Gdx.app.log("MOVING", "DOWN");
        } else if (Gdx.input.isKeyJustPressed(Keys.A) && entityGrid.canMove(bernard, Direction.LEFT)) {
            bernard.setPlayTurn(true);
            bernard.notifyObservers();
            if (bernard.getDirection() != Direction.LEFT) {
                bernard.flipTexture(Direction.LEFT);
            }
            bernard.setDirection(Direction.LEFT);
            Gdx.app.log("MOVING", "LEFT");
        } else if (Gdx.input.isKeyJustPressed(Keys.D) && entityGrid.canMove(bernard, Direction.RIGHT)) {
            bernard.setPlayTurn(true);
            bernard.notifyObservers();
            if (bernard.getDirection() != Direction.RIGHT) {
                bernard.flipTexture(Direction.RIGHT);
            }
            bernard.setDirection(Direction.RIGHT);
            Gdx.app.log("MOVING", "RIGHT");
        }
        
            //Skills
        if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
            bernard.setPlayTurn(true);
            bernard.setFiring(true);
        }

        if (Gdx.input.isKeyJustPressed(Keys.NUM_1)) {
            bernard.setPlayTurn(true);
            bernard.setExecuteSkillOne(true);
        }

        if (Gdx.input.isKeyJustPressed(Keys.NUM_2)) {
            bernard.setPlayTurn(true);
            bernard.setExecuteSkillTwo(true);
        }

        if (Gdx.input.isKeyJustPressed(Keys.NUM_3)) {
            bernard.setPlayTurn(true);
            bernard.setExecuteDetection(true);
            bernard.notifyObservers();
        }
        
        //Remove dead entities, temporary handler?
        for (int i = 0; i < entityList.size(); i++) {
            Entity entity = entityList.get(i);
            if (!entityGrid.isAlive(entity)) {
                entityList.remove(i);
                entity.remove();
            }
        }
        
        //Complete the turn
        if (bernard.getPlayTurn()) {
            for (int i = 0; i < entityList.size(); i++) {
                Entity entity = entityList.get(i);
                entityGrid.calculateAITurn(entity);
                entityGrid.moveEntity(entity);
                entityGrid.collision(entity);
                entity.update();
            }
            bernard.setPlayTurn(false);
        }

    }

    @Override
    public void dispose() {
        batch.dispose();
        tl.dispose();
    }

    public void centerCameraOn(Entity entity) {
        camera.position.x = entity.getX() + entity.getWidth() / 2;
        camera.position.y = entity.getY() + entity.getHeight() / 2;
    }

    
}
