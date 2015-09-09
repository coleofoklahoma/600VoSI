package edu.uco.shvosi;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Input.Keys;

public class MyGdxGame extends ApplicationAdapter {
	private SpriteBatch batch;
        private OrthographicCamera camera;
        private Protagonist bernard;
        private Map map;
        private EntityGrid entityGrid;
        private int tileDimension;
        
        //Temporary Level Grid Creation Var
        private int[][] levelGrid;
	
	@Override
	public void create () {
                tileDimension = Constants.TILEDIMENSION;
            
		batch = new SpriteBatch();
                
                bernard = new Protagonist(TextureLoader.BERNARDTEXTURE, 1, 1);
                
                camera = new OrthographicCamera();
                camera.setToOrtho(false, Constants.SCREENWIDTH, Constants.SCREENHEIGHT);
                camera.position.x = bernard.getX() + bernard.getWidth()/2;
                camera.position.y = bernard.getY() + bernard.getHeight()/2;
                
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
                entityGrid = new EntityGrid(map.getMapGrid(), bernard);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                
                camera.update();
                batch.setProjectionMatrix(camera.combined);
                
                //Draw background
                map.render(batch);
                
                //Draw Bernard
                bernard.render(batch);
                
                //Move Camera
                if(Gdx.input.isKeyJustPressed(Keys.A) && entityGrid.canMove("left")) camera.position.x -= tileDimension;
                else if(Gdx.input.isKeyJustPressed(Keys.D) && entityGrid.canMove("right")) camera.position.x += tileDimension;
                else if(Gdx.input.isKeyJustPressed(Keys.W) && entityGrid.canMove("up")) camera.position.y += tileDimension;
                else if(Gdx.input.isKeyJustPressed(Keys.S) && entityGrid.canMove("down")) camera.position.y -= tileDimension;
                
                //Bernard follows camera center
                bernard.setX(camera.position.x - bernard.getWidth()/2);
                bernard.setY(camera.position.y - bernard.getHeight()/2);
	}
        
        @Override
        public void dispose() {
            batch.dispose();
        }
        
        public int[][] createMap(){
            //Will parse txt files to create maps in the future
            int[][] map;
//            FileHandle file = Gdx.files.internal("testmap.txt");
//            String fileString = file.readString();
            map = new int[32][18];
            return map;
        }
}
