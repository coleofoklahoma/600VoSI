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
        private Map map;
        private EntityGrid entityGrid;
        private int tileDimension;
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
        //Ignore this variable
        int toggle = 1;
        
        
        //Temporary Level Grid Creation Var
        private int[][] levelGrid;
	
	@Override
	public void create () {
            Gdx.input.setInputProcessor(stage);
            entityList = new ArrayList<Entity>();
            tl = new TextureLoader();
            tileDimension = Constants.TILEDIMENSION;
            batch = new SpriteBatch();
            
            // Create and add entities to the list
            bernard = new Protagonist(TextureLoader.BERNARDTEXTURE, 1, 1);
            wanderer = new Antagonist (TextureLoader.WANDERTEXTURE,2,4);
            entityList.add(bernard);
            entityList.add(wanderer);
            bernard.setHealth(bernard.getHealth());
            healthDisplay = new BitmapFont();
            splashT = new Texture (Gdx.files.internal("splash.png"));
            splash = new Sprite(splashT,800,450);
            splash.setPosition(0, 0);
            startT = new Texture (Gdx.files.internal("startButtonS.png"));
            startBut = new Sprite (startT,100,50);
            startBut.setPosition(550,15);
            quitT = new Texture (Gdx.files.internal("quitButton.png"));
            quitBut = new Sprite(quitT,100, 50);
            quitBut.setPosition(650, 15);
            
            //Some Random Enemies for testing, currently bernard texture
            entityList.add(new Antagonist(TextureLoader.BERNARDTEXTURE, 6, 6));
            entityList.add(new Antagonist(TextureLoader.BERNARDTEXTURE, 6, 7));
            entityList.add(new Antagonist(TextureLoader.BERNARDTEXTURE, 6, 8));
            entityList.add(new Antagonist(TextureLoader.BERNARDTEXTURE, 6, 9));
            entityList.add(new Antagonist(TextureLoader.BERNARDTEXTURE, 6, 10));
            entityList.add(new Antagonist(TextureLoader.BERNARDTEXTURE, 6, 11));
            entityList.add(new Antagonist(TextureLoader.BERNARDTEXTURE, 6, 12));
            entityList.add(new Antagonist(TextureLoader.BERNARDTEXTURE, 6, 13));
            entityList.add(new Antagonist(TextureLoader.BERNARDTEXTURE, 6, 14));

            health = new ItemHeart(TextureLoader.HEALTHTEXTURE, 5, 3);
            entityList.add(health);
            
            health2 = new ItemHeart(TextureLoader.HEALTHTEXTURE, 6, 3);
            entityList.add(health2);

            trap = new TrapType1(TextureLoader.TRAPTEXTURE, 2, 2);
            entityList.add(trap);

            trap2 = new TrapType2(TextureLoader.TRAPTEXTURE2, 3, 2);
            entityList.add(trap2);
            
            bernard.addObserver(trap);
            bernard.addObserver(trap2);
            //Initialize Camera
            camera = new OrthographicCamera();
            camera.setToOrtho(false, Constants.SCREENWIDTH, Constants.SCREENHEIGHT);
            FitViewport fv = new FitViewport(Constants.SCREENWIDTH,Constants.SCREENHEIGHT, camera);
            stage = new Stage(fv, batch);
            centerCameraOn(bernard);
            
            // Add the entities to the stage
            for(int i = 0; i < entityList.size(); i++){
                stage.addActor(entityList.get(i));
                switch(entityList.get(i).getEntityType()){
                    case EntityGridCode.PLAYER:
                        stage.getActors().get(i).setZIndex(2);
                        break;
                    case EntityGridCode.ENEMY:
                        stage.getActors().get(i).setZIndex(1);
                        break;
                    case EntityGridCode.TRAP:
                    case EntityGridCode.ITEM:
                    default:
                        stage.getActors().get(i).setZIndex(0);
                        break;
                }
            }

            //Test Level Grid Creation
            levelGrid = createMap();
            for(int i = 0; i < levelGrid.length; i++){
                    for(int j = 0; j < levelGrid[i].length; j++){
                            if(j > 0 && j < levelGrid[i].length - 1 && i != 0 && i != levelGrid.length - 1)
                                    levelGrid[i][j] = MapGridCode.FLOOR;
                            else
                                    levelGrid[i][j] = MapGridCode.WALL;
                    }
            }

            levelGrid[4][4] = MapGridCode.WALL;
            levelGrid[5][4] = MapGridCode.WALL;
            levelGrid[6][4] = MapGridCode.WALL;
            levelGrid[7][4] = MapGridCode.WALL;
            levelGrid[8][4] = MapGridCode.WALL;
            levelGrid[9][4] = MapGridCode.WALL;
            levelGrid[9][3] = MapGridCode.WALL;
            levelGrid[9][2] = MapGridCode.WALL;
            levelGrid[9][1] = MapGridCode.WALL;

            //Map Grid and Entity Grid Creation
            map = new Map(levelGrid, tileDimension);
            entityGrid = new EntityGrid(map.getMapGrid(), entityList);
	}

	@Override
	public void render () {
            
            switch(gameState){
                case 1:
                    StartScreen();
                    break;
                    
                case 2:
                    GamePlay();
                    break;
            }
	}
        
        @Override
        public void dispose() {
            batch.dispose();
            tl.dispose();
        }
        
        public int[][] createMap(){
            //Will parse txt files to create maps in the future
            int[][] map;
//            FileHandle file = Gdx.files.internal("testmap.txt");
//            String fileString = file.readString();
            map = new int[32][18];
            return map;
        }
		
        public void centerCameraOn(Entity entity){
            camera.position.x = entity.getX() + entity.getWidth()/2;
            camera.position.y = entity.getY() + entity.getHeight()/2;
        }
        
        
        public void StartScreen()
        {
            Gdx.gl.glClearColor(1, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            batch.setProjectionMatrix(camera.combined);
            batch.begin();
            splash.draw(batch);
            startBut.draw(batch);
            quitBut.draw(batch);
            batch.end();
            
            if(Gdx.input.isTouched())
            {
                gameState = 2;
            }
        }
        
        public void GamePlay()
        {
                      
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            batch.setProjectionMatrix(camera.combined);
            batch.begin();
            map.render(batch);
            healthpoints = "HP: " + bernard.getHealth();
            healthDisplay.setColor(1.0f, 1.0f, 1.0f, 1.0f);
            healthDisplay.draw(batch, healthpoints, bernard.getX()+ 25, bernard.getY() + 250);
            batch.end();

            stage.act(Gdx.graphics.getDeltaTime());
            stage.draw();
                
            centerCameraOn(bernard);
            camera.update();

            if(Gdx.input.isKeyJustPressed(Keys.SPACE)){
                    bernard.setFiring(true);
            }

            if(Gdx.input.isKeyJustPressed(Keys.NUM_1)){
                    bernard.setExecuteSkillOne(true);
            }

            if(Gdx.input.isKeyJustPressed(Keys.NUM_2)){
                    bernard.setExecuteSkillTwo(true);
            }

            //Bernard Controls
            if(Gdx.input.isKeyJustPressed(Keys.W) && entityGrid.canMove(bernard, Direction.UP)) {
                bernard.setPlayTurn(true);
                bernard.notifyObservers();
                bernard.setDirection(Direction.UP);
                Gdx.app.log("MOVING", "UP");
            }
            else if(Gdx.input.isKeyJustPressed(Keys.S) && entityGrid.canMove(bernard, Direction.DOWN)) {
                bernard.setPlayTurn(true);
                bernard.notifyObservers();
                bernard.setDirection(Direction.DOWN);
                Gdx.app.log("MOVING", "DOWN");
            }
            else if(Gdx.input.isKeyJustPressed(Keys.A) && entityGrid.canMove(bernard, Direction.LEFT)) {
                bernard.setPlayTurn(true);
                bernard.notifyObservers();
                if(bernard.getDirection() == Direction.RIGHT){
                    bernard.flipTexture(Direction.LEFT);
                }
                bernard.setDirection(Direction.LEFT);
                Gdx.app.log("MOVING", "LEFT");
            }
            else if(Gdx.input.isKeyJustPressed(Keys.D) && entityGrid.canMove(bernard, Direction.RIGHT)) {
                bernard.setPlayTurn(true);
                bernard.notifyObservers();
                if(bernard.getDirection() == Direction.LEFT){
                    bernard.flipTexture(Direction.RIGHT);
                }
                bernard.setDirection(Direction.RIGHT);
                Gdx.app.log("MOVING", "RIGHT");
            }

            if(bernard.getPlayTurn()){
                for(int i = 0; i < entityList.size(); i++){
                    Entity entity = entityList.get(i);
                    entityGrid.calculateAITurn(entity);
                    entityGrid.moveEntity(entity);
                    entityGrid.collision(entity);
                    if(!entityGrid.isAlive(entity)){
                        entityList.remove(i);
                        entity.remove();
                    }
                    entity.update();
                }
                bernard.setPlayTurn(false);
            }
        
        }
}