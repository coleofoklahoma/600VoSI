package edu.uco.shvosi;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import java.util.ArrayList;
import java.util.List;

public class MyGdxGame extends ApplicationAdapter {

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
    public static int gameState = -1;
    private Skin skin;
    private Label healthLabel;
    private boolean init;

    @Override
    public void create() {
        init = false;
        Gdx.input.setInputProcessor(stage);
        entityList = new ArrayList<Entity>();
        tl = new TextureLoader();
        batch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        
        //Initialize Camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.SCREENWIDTH, Constants.SCREENHEIGHT);
        FitViewport fv = new FitViewport(Constants.SCREENWIDTH, Constants.SCREENHEIGHT, camera);
        stage = new Stage(fv, batch);
        bernard = new Protagonist(TextureLoader.BERNARDTEXTURE, 1, 1);
        healthLabel = new Label("HP: ", skin);
    }

    @Override
    public void render() {

        switch (gameState) {
                   
            case -1:
                StartScreen sc = new StartScreen();
                sc.create();
                break;
            case 0:
                if(!init)
                    initNewLevel();
                GamePlay();
                break;        
                
        
            case 1:
                LevelOne l1 = new LevelOne();
                System.out.println("l1");
                l1.create();
                break;
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        tl.dispose();
        stage.dispose();
        skin.dispose();
    }

    public void centerCameraOn(Entity entity) {
        camera.position.x = entity.getX() + entity.getWidth() / 2;
        camera.position.y = entity.getY() + entity.getHeight() / 2;
    }

    public void GamePlay() {

        // UPDATE
		
        //Bernard Controls
            //Movement
        if (Gdx.input.isKeyJustPressed(Keys.W) && map.bernardCanMove(Direction.UP)) {
            bernard.setPlayTurn(true);
            bernard.notifyObservers();
            bernard.setDirection(Direction.UP);
            Gdx.app.log("MOVING", "UP");
        } else if (Gdx.input.isKeyJustPressed(Keys.S) && map.bernardCanMove(Direction.DOWN)) {
            bernard.setPlayTurn(true);
            bernard.notifyObservers();
            bernard.setDirection(Direction.DOWN);
            Gdx.app.log("MOVING", "DOWN");
        } else if (Gdx.input.isKeyJustPressed(Keys.A) && map.bernardCanMove(Direction.LEFT)) {
            bernard.setPlayTurn(true);
            bernard.notifyObservers();
            if (bernard.getDirection() != Direction.LEFT) {
                bernard.flipTexture(Direction.LEFT);
            }
            bernard.setDirection(Direction.LEFT);
            Gdx.app.log("MOVING", "LEFT");
        } else if (Gdx.input.isKeyJustPressed(Keys.D) && map.bernardCanMove(Direction.RIGHT)) {
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
		
		//temporary health display
        healthpoints = "HP: " + bernard.getHealth();
        healthLabel.setX(bernard.getX() + 25);
        healthLabel.setY(bernard.getY() + 250);
        healthLabel.setText(healthpoints);
        
        //Remove dead entities, temporary handler?
        for (int i = 0; i < map.getEntityList().size(); i++) {
            Entity entity = map.getEntityList().get(i);
            if (!map.isAlive(entity)) {
                map.getEntityList().remove(i);
                entity.remove();
            }
        }
        
        //Complete the turn
        if (bernard.getPlayTurn()) {
            for (int i = 0; i < map.getEntityList().size(); i++) {
                Entity aggressor = map.getEntityList().get(i);

                for(int j = 0; j < map.getEntityList().size(); j++) {
                        if(i != j){
                            Entity receiver = map.getEntityList().get(j);

                            map.collision(aggressor, receiver);
                        }
                }
                map.calculateAITurn(aggressor);
                map.moveEntity(aggressor);
                aggressor.update();
            }
            bernard.setPlayTurn(false);
            if(map.exitReached()){
                    //Load the next level
                    stage.clear();
                    map.dispose();
                    initNewLevel();
            }
        }
		
		// RENDER

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        map.render(camera);
        batch.end();
        
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        centerCameraOn(bernard);
        camera.update();

    }
    
    public void initNewLevel(){
        //Test Level
        map = new Map(bernard, "maps/testmap.tmx");
		
        initStage();
		
        init = true;
    }
	
    public void initStage(){
        // Add the entities to the stage
        for (int i = 0; i < map.getEntityList().size(); i++) {
            stage.addActor(map.getEntityList().get(i));
            switch (map.getEntityList().get(i).getEntityType()) {
                case EntityGridCode.PLAYER:
					//bernard = (Protagonist)map.getEntityList().get(i);
                    stage.getActors().get(i).setZIndex(3);
                    break;
                case EntityGridCode.ENEMY:
                    stage.getActors().get(i).setZIndex(2);
                    break;
                case EntityGridCode.TRAP:
                    bernard.addObserver(map.getEntityList().get(i));
                case EntityGridCode.ITEM:
                default:
                    stage.getActors().get(i).setZIndex(1);
                    break;
            }
        }
        
        //Health Display
        stage.addActor(healthLabel);
    }
}