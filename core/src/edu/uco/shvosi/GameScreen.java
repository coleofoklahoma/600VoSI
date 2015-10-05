package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameScreen implements Screen {

    private MyGdxGame game;

    private SpriteBatch batch;
    private OrthographicCamera camera;
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
        if (Gdx.input.isKeyJustPressed(Keys.W) && map.bernardCanMove(Constants.Direction.UP)) {
            map.bernard.setPlayTurn(true);
            map.bernard.notifyObservers();
            map.bernard.setDirection(Constants.Direction.UP);
            map.bernard.setTurnAction(Constants.TurnAction.MOVE);
            Gdx.app.log("MOVING", "UP");
        } else if (Gdx.input.isKeyJustPressed(Keys.S) && map.bernardCanMove(Constants.Direction.DOWN)) {
            map.bernard.setPlayTurn(true);
            map.bernard.notifyObservers();
            map.bernard.setDirection(Constants.Direction.DOWN);
            map.bernard.setTurnAction(Constants.TurnAction.MOVE);
            Gdx.app.log("MOVING", "DOWN");
        } else if (Gdx.input.isKeyJustPressed(Keys.A)) {
            if (map.bernard.getDirection() != Constants.Direction.LEFT) {
                map.bernard.flipTexture(Constants.Direction.LEFT);
                map.bernard.setDirection(Constants.Direction.LEFT);
            }
            if (map.bernardCanMove(Constants.Direction.LEFT)) {
                map.bernard.setPlayTurn(true);
                map.bernard.notifyObservers();
                map.bernard.setTurnAction(Constants.TurnAction.MOVE);
                Gdx.app.log("MOVING", "LEFT");
            }
        } else if (Gdx.input.isKeyJustPressed(Keys.D)) {
            if (map.bernard.getDirection() != Constants.Direction.RIGHT) {
                map.bernard.flipTexture(Constants.Direction.RIGHT);
                map.bernard.setDirection(Constants.Direction.RIGHT);
            }

            if (map.bernardCanMove(Constants.Direction.RIGHT)) {
                map.bernard.setPlayTurn(true);
                map.bernard.notifyObservers();
                map.bernard.setTurnAction(Constants.TurnAction.MOVE);
                Gdx.app.log("MOVING", "RIGHT");
            }
        }

        //Use Item
        if (Gdx.input.isKeyJustPressed(Keys.Q)) {
            map.bernard.useItem();
        }

        //Skills
        if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
            map.bernard.setPlayTurn(true);
            map.bernard.setFiring(true);
            map.bernard.setTurnAction(Constants.TurnAction.ATTACK);
        }

        if (Gdx.input.isKeyJustPressed(Keys.NUM_1)) {
            map.bernard.setPlayTurn(true);
            map.bernard.setExecuteSkillOne(true);
            map.bernard.setTurnAction(Constants.TurnAction.ATTACK);
        }

        if (Gdx.input.isKeyJustPressed(Keys.NUM_2)) {
            map.bernard.setPlayTurn(true);
            map.bernard.setExecuteSkillTwo(true);
            map.bernard.setTurnAction(Constants.TurnAction.ATTACK);
        }

        if (Gdx.input.isKeyJustPressed(Keys.NUM_3)) {
            map.bernard.setPlayTurn(true);
            map.bernard.setExecuteDetection(true);
            map.bernard.notifyObservers();
            map.bernard.setTurnAction(Constants.TurnAction.ATTACK);
        }

        if (Gdx.input.isKeyJustPressed(Keys.NUM_4)) {
            map.bernard.setPlayTurn(true);
            map.bernard.setExecuteBarrier(true);
            map.bernard.setBarrierLimit(2);
            map.bernard.notifyObservers();
            map.bernard.setTurnAction(Constants.TurnAction.ATTACK);
        }
        if (map.bernard.getBarrierLimit() == 0) {
            map.bernard.setHeal(true);
        }

        //temporary health display
        healthpoints = "HP: " + map.bernard.getHealth();
        healthLabel.setX(map.bernard.getX() + 25);
        healthLabel.setY(map.bernard.getY() + 250);
        healthLabel.setText(healthpoints);

        //temporary inventory display
        invent.setX(map.bernard.getX() - 325);
        invent.setY(map.bernard.getY() + 175);

        //Remove dead entities, temporary handler?
        for (int i = 0; i < map.getEntityList().size(); i++) {
            Entity entity = map.getEntityList().get(i);
            if (!map.isAlive(entity)) {
                map.getEntityList().remove(i);
                entity.remove();
                if (entity.getGridCode() == Constants.EntityGridCode.ITEM || entity.getGridCode() == Constants.EntityGridCode.TRAP) {
                    map.getEntityGrid()[entity.getCX()][entity.getCY()] = Constants.EntityGridCode.PLAYER;
                } else {
                    map.getEntityGrid()[entity.getCX()][entity.getCY()] = Constants.EntityGridCode.NONE;
                }
            }
        }

        //Complete the turn
        if (map.bernard.getPlayTurn()) {
            if(map.bernard.getHealth() <= 0)
                game.setScreen(game.startScreen);
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
            map.bernard.setPlayTurn(false);
            if (map.exitReached()) {
                //Load the next level
                map.bernard.setPlayTurn(true);
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

        centerCameraOn(map.bernard);
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
            map = new Map("maps/testmap.tmx");
            map.bernard.removeAllObservers();
            level = 1;
        } else if (level == 1) {
            map = new Map("maps/testmap2.tmx");
            map.bernard.removeAllObservers();
            level = 0;
        }

        initStage();
    }

    public void initStage() {
        // Add the entities to the stage
        for (int i = map.getEntityList().size() - 1; i > -1; i--) {
            stage.addActor(map.getEntityList().get(i));
            switch (map.getEntityList().get(i).getGridCode()) {
                case PLAYER:
                    //bernard = (Protagonist)map.getEntityList().get(i);
                    //     stage.getActors().get(i).setZIndex(3);
                    break;
                case ENEMY:
                    //     stage.getActors().get(i).setZIndex(2);
                    for (Skill s : map.bernard.getSkills().values()) {
                        s.addObserver(map.getEntityList().get(i));
                    }
                    // bernard.addObserver(map.getEntityList().get(i));
                    break;
                case TRAP:
                    map.bernard.addObserver(map.getEntityList().get(i));
                    break;
                case ITEM:
                    map.bernard.addObserver(map.getEntityList().get(i));
                    break;
                default:
                    //      stage.getActors().get(i).setZIndex(1);
                    break;
            }
        }

        //Health Display
        stage.addActor(healthLabel);
        stage.addActor(invent);
        //bernard.clearActions();
    }
}
