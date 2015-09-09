package edu.uco.shvosi;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Map {
    private int[][] mapGrid;
    private int tileDimension;
    private int width;
    private int height;
    
    //private Texture floorTileTexture;
    
    public Map(int[][] mapGrid, int tileDimension){
        this.mapGrid = mapGrid;
        this.tileDimension = tileDimension;
        this.width = mapGrid.length * tileDimension;
        this.height = mapGrid[0].length * tileDimension;
    }
    
    public void render(SpriteBatch batch){
        for(int i = 0; i < mapGrid.length; i++){
            for(int j = 0; j < mapGrid[i].length; j++){
                batch.begin();
                switch(mapGrid[i][j]){
                    case MapGridCode.FLOOR:
                        batch.draw(TextureLoader.FLOORTILETEXTURE, i*tileDimension, j*tileDimension);
                        break;
                    case MapGridCode.WALL:
                        batch.draw(TextureLoader.WALLTILETEXTURE, i*tileDimension, j*tileDimension);
                        break;
                    default:
                        //Do Nothing
                }
		batch.end();
            }
        }
    }
    
    public int getWidth(){
        return this.width;
    }
    
    public int getHeight(){
        return this.height;
    }
    
    public int[][] getMapGrid(){
        return this.mapGrid;
    }
}
