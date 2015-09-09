package edu.uco.shvosi;

public class EntityGrid {
    private int[][] mapGrid;
    private int[][] entityGrid;
    private Protagonist bernard;
    
    public EntityGrid(int[][] map, Protagonist bernard){
        this.mapGrid = map;
        this.bernard = bernard;
        this.entityGrid = new int[mapGrid.length][mapGrid[0].length];
        for(int i = 0; i < entityGrid.length; i++){
            for(int j = 0; j < entityGrid[i].length; j++){
                entityGrid[i][j] = EntityGridCode.EMPTY;
            }
        }
        this.entityGrid[bernard.getCX()][bernard.getCY()] = EntityGridCode.PLAYER;
    }
    
    public boolean canMove(String direction){
        if(direction.equalsIgnoreCase("left")){
            if(bernard.getCX() == 0){
                return false;
            }
            if(mapGrid[bernard.getCX() - 1][bernard.getCY()] == MapGridCode.FLOOR &&
                    entityGrid[bernard.getCX() - 1][bernard.getCY()] == EntityGridCode.EMPTY){
                entityGrid[bernard.getCX()][bernard.getCY()] = EntityGridCode.EMPTY;
                bernard.setCX(bernard.getCX() - 1);
                entityGrid[bernard.getCX()][bernard.getCY()] = EntityGridCode.PLAYER;
                return true;
            }
        }
        if(direction.equalsIgnoreCase("right")){
            if(bernard.getCX() == mapGrid.length - 1){
                return false;
            }
            if(mapGrid[bernard.getCX() + 1][bernard.getCY()] == MapGridCode.FLOOR &&
                    entityGrid[bernard.getCX() + 1][bernard.getCY()] == EntityGridCode.EMPTY){
                entityGrid[bernard.getCX()][bernard.getCY()] = EntityGridCode.EMPTY;
                bernard.setCX(bernard.getCX() + 1);
                entityGrid[bernard.getCX()][bernard.getCY()] = EntityGridCode.PLAYER;
                return true;
            }
        }
        if(direction.equalsIgnoreCase("up")){
            if(bernard.getCY() == mapGrid[0].length - 1){
                return false;
            }
            if(mapGrid[bernard.getCX()][bernard.getCY() + 1] == MapGridCode.FLOOR &&
                    entityGrid[bernard.getCX()][bernard.getCY() + 1] == EntityGridCode.EMPTY){
                entityGrid[bernard.getCX()][bernard.getCY()] = EntityGridCode.EMPTY;
                bernard.setCY(bernard.getCY() + 1);
                entityGrid[bernard.getCX()][bernard.getCY()] = EntityGridCode.PLAYER;
                return true;
            }
        }
        if(direction.equalsIgnoreCase("down")){
            if(bernard.getCY() == 0){
                return false;
            }
            if(mapGrid[bernard.getCX()][bernard.getCY() - 1] == MapGridCode.FLOOR &&
                    entityGrid[bernard.getCX()][bernard.getCY() - 1] == EntityGridCode.EMPTY){
                entityGrid[bernard.getCX()][bernard.getCY()] = EntityGridCode.EMPTY;
                bernard.setCY(bernard.getCY() - 1);
                entityGrid[bernard.getCX()][bernard.getCY()] = EntityGridCode.PLAYER;
                return true;
            }
        }
        return false;
    }
}
