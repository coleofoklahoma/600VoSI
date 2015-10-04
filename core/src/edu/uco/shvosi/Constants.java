package edu.uco.shvosi;

public final class Constants {
    public static final int TILEDIMENSION = 100;
    public static final int SCREENWIDTH = 800;
    public static final int SCREENHEIGHT = 450;
    public static final float MOVEACTIONDURATION = 0.5f;
    
    public static enum MapGridCode {
        FLOOR, WALL, STRUCTURE, EXIT;
    }
    
    public static enum EntityGridCode {
        NONE, PLAYER, ENEMY, ITEM, TRAP;
    }
    
    public static enum EnemyType {
	NONE,
        WANDERER,
        DRUNK,
        CATLADY;
    }
    
    public static enum ItemType {
	NONE,
        HEALTH,
        SHIELD;
    }
    
    public static enum TrapType {
        NONE,
        TRAP1,
        TRAP2,
        TRAP3;
    }
    
    public static enum Direction {
        NONE,
        UP, DOWN, LEFT, RIGHT,
        UP_LEFT, UP_RIGHT,
        DOWN_LEFT, DOWN_RIGHT;
    }
    
    public static enum TurnAction {
        NONE,
        MOVE, ATTACK;
    }
}