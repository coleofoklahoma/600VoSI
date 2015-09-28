package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameScreen implements Screen {

    private MyGdxGame game;

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Protagonist bernard;
    private Map map;
    private Stage stage;
    private String healthpoints;
    private Skin skin;
    private Label healthLabel;
    public static Inventory invent;
    private TextureLoader textureLoader = new TextureLoader();

    private int level = 0;

    public GameScreen(MyGdxGame game) {
        this.game = game;
    }

    @Override
    public void render(float delta) {

        // UPDATE
        //Bernard Controls
        //Movement
        if (Gdx.input.isKeyJustPressed(Keys.W) && map.bernardCanMove(Direction.UP)) {
            bernard.setPlayTurn(true);
            bernard.notifyObservers();
            bernard.setDirection(Direction.UP);
            bernard.setTurnAction(TurnAction.MOVE);
            Gdx.app.log("MOVING", "UP");
        } else if (Gdx.input.isKeyJustPressed(Keys.S) && map.bernardCanMove(Direction.DOWN)) {
            bernard.setPlayTurn(true);
            bernard.notifyObservers();
            bernard.setDirection(Direction.DOWN);
            bernard.setTurnAction(TurnAction.MOVE);
            Gdx.app.log("MOVING", "DOWN");
        } else if (Gdx.input.isKeyJustPressed(Keys.A) && map.bernardCanMove(Direction.LEFT)) {
            bernard.setPlayTurn(true);
            bernard.notifyObservers();
            if (bernard.getDirection() != Direction.LEFT) {
                bernard.flipTexture(Direction.LEFT);
            }
            bernard.setDirection(Direction.LEFT);
            bernard.setTurnAction(TurnAction.MOVE);
            Gdx.app.log("MOVING", "LEFT");
        } else if (Gdx.input.isKeyJustPressed(Keys.D) && map.bernardCanMove(Direction.RIGHT)) {
            bernard.setPlayTurn(true);
            bernard.notifyObservers();
            if (bernard.getDirection() != Direction.RIGHT) {
                bernard.flipTexture(Direction.RIGHT);
            }
            bernard.setDirection(Direction.RIGHT);
            bernard.setTurnAction(TurnAction.MOVE);
            Gdx.app.log("MOVING", "RIGHT");
        }

        //Skills
        if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
            bernard.setPlayTurn(true);
            bernard.setFiring(true);
            bernard.setTurnAction(TurnAction.ATTACK);
        }

        if (Gdx.input.isKeyJustPressed(Keys.NUM_1)) {
            bernard.setPlayTurn(true);
            bernard.setExecuteSkillOne(true);
            bernard.setTurnAction(TurnAction.ATTACK);
        }

        if (Gdx.input.isKeyJustPressed(Keys.NUM_2)) {
            bernard.setPlayTurn(true);
            bernard.setExecuteSkillTwo(true);
            bernard.setTurnAction(TurnAction.ATTACK);
        }

        if (Gdx.input.isKeyJustPressed(Keys.NUM_3)) {
            bernard.setPlayTurn(true);
            bernard.setExecuteDetection(true);
            bernard.notifyObservers();
            bernard.setTurnAction(TurnAction.ATTACK);
        }

        //temporary health display
        healthpoints = "HP: " + bernard.getHealth();
        healthLabel.setX(bernard.getX() + 25);
        healthLabel.setY(bernard.getY() + 250);
        healthLabel.setText(healthpoints);

        //temporary inventory display
        invent.setX(bernard.getX() - 325);
        invent.setY(bernard.getY() + 175);

        //Remove dead entities, temporary handler?
        for (int i = 0; i < map.getEntityList().size(); i++) {
            Entity entity = map.getEntityList().get(i);
            if (!map.isAlive(entity)) {
                map.getEntityList().remove(i);
                entity.remove();
                map.getEntityGrid()[entity.getCX()][entity.getCY()] = EntityGridCode.EMPTY;
            }
        }

        //Complete the turn
        if (bernard.getPlayTurn()) {
            for (int i = 0; i < map.getEntityList().size(); i++) {
                Entity aggressor = map.getEntityList().get(i);

                for (int j = 0; j < map.getEntityList().size(); j++) {
                    if (i != j) {

                        map.collision(aggressor, map.getEntityList().get(j));

                    }
                }
                map.playTurn(aggressor);
                aggressor.update();
            }
            bernard.setPlayTurn(false);
            if (map.exitReached()) {
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

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        // called when this screen is set as the screen with game.setScreen();
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        //Initialize Camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.SCREENWIDTH, Constants.SCREENHEIGHT);
        FitViewport fv = new FitViewport(Constants.SCREENWIDTH, Constants.SCREENHEIGHT, camera);
        stage = new Stage(fv, batch);
        bernard = new Protagonist(TextureLoader.BERNARDTEXTURE, 1, 1);
        invent = new Inventory(TextureLoader.INVENTORYTEXTURE, 5, 0);
        healthLabel = new Label("HP: ", skin);

        initNewLevel();
    }

    @Override
    public void hide() {
        // called when current screen changes from this to a different screen
        dispose();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        // never called automatically
        //batch.dispose();
        stage.dispose();
        skin.dispose();
    }

    public void centerCameraOn(Entity entity) {
        camera.position.x = entity.getX() + entity.getWidth() / 2;
        camera.position.y = entity.getY() + entity.getHeight() / 2;
    }

    public void initNewLevel() {
        //Test Level
        if (level == 0) {
            map = new Map(bernard, "maps/testmap.tmx");
            level = 1;
        } else if (level == 1) {
            map = new Map(bernard, "maps/testmap2.tmx");
            level = 0;
        }

        initStage();
    }

    public void initStage() {
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
                    for(Skill s : bernard.getSkills().values()) {
                        s.addObserver(map.getEntityList().get(i));
                    }
                    break;
                case EntityGridCode.TRAP:
                    bernard.addObserver(map.getEntityList().get(i));
                    break;
                case EntityGridCode.ITEM:
                    break;
                default:
                    stage.getActors().get(i).setZIndex(1);
                    break;
            }
        }

        //Health Display
        stage.addActor(healthLabel);
        stage.addActor(invent);
        bernard.clearActions();
    }
}
