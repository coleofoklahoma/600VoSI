package edu.uco.shvosi;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class Map {
    private TiledMap tiledMap;
    private TiledMapRenderer renderer;
	
    private int[][] mapGrid;
    private int tileDimension;
    private int width;
    private int height;
    
    public Map(String tmxFileName){
        this.tiledMap = new TmxMapLoader().load(tmxFileName);
        this.renderer = new OrthogonalTiledMapRenderer(this.tiledMap);
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);

        //Setup mapGrid
        mapGrid = new int[layer.getWidth()][layer.getHeight()];
        for(int x = 0; x < layer.getWidth();x++){
            for(int y = 0; y < layer.getHeight();y++){
                TiledMapTileLayer.Cell cell = layer.getCell(x,y);
                MapProperties properties = cell.getTile().getProperties();
                if(properties.get("WALL") != null){
                    mapGrid[x][y] = MapGridCode.WALL;
                }
                else if(properties.get("STRUCTURE") != null){
                    mapGrid[x][y] = MapGridCode.STRUCTURE;
                }
                else{
                    mapGrid[x][y] = MapGridCode.FLOOR;
                }
				
                //To animate a cell tile, see below
                //cell.setTile(new AnimatedTiledMapTile(0.5f,waterTiles));
            }
        }
		
        this.tileDimension = Constants.TILEDIMENSION;
        this.width = mapGrid.length * tileDimension;
        this.height = mapGrid[0].length * tileDimension;
    }
    
    public void render(OrthographicCamera camera){
        renderer.setView(camera);
        renderer.render();
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
