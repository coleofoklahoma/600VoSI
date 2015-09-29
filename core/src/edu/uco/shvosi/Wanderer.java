package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.util.List;

public class Wanderer extends Antagonist {
    
    private Animation wanderWalk;
    private boolean moving = false;
    private boolean flip = false;
    private float elapsedTime;
    private TextureRegion temp;
    private int bernardX;
    private int bernardY;
    private String XorY;

    public Wanderer(Texture texture, int cX, int cY) {
        super(texture, cX, cY);
        this.setEnemyType(EnemyType.WANDERER);
        wanderWalk = TextureLoader.wanderWalk;

    }

    @Override
    public void attackAction() {
        //Do Attack Stuffs?
    }
    
        @Override
    public void draw(Batch batch, float alpha) {
        super.draw(batch, alpha);
    
                
                elapsedTime += Gdx.graphics.getDeltaTime();
                
            if (flip) {
                temp = wanderWalk.getKeyFrame(elapsedTime);
                temp.flip(true, false);
                batch.draw(temp, this.getX(),getY(), Constants.TILEDIMENSION, Constants.TILEDIMENSION);
                temp.flip(true, false);
            } else {
                batch.draw(wanderWalk.getKeyFrame(elapsedTime), this.getX(), this.getY(), Constants.TILEDIMENSION *3, Constants.TILEDIMENSION);
            }
            if (wanderWalk.isAnimationFinished(elapsedTime)) {
                moving = false;
                elapsedTime = 0f;
            }
        
    }

    
    @Override
    public void calculateTurn(int[][] mapGrid, int[][] entityGrid, List<Entity> entityList) {
        //Random movement
        int random = 0;
        int tries = 0;
        Direction d = Direction.NONE;
        
        for(int i = 0; i < entityList.size(); i++)
        {
            if(entityList.get(i).getEntityType() == EntityGridCode.PLAYER);
            bernardX = entityList.get(i).getCX();
            bernardY = entityList.get(i).getCY();
            break;
        }
            
        


        this.setTurnAction(TurnAction.MOVE);
    }
    
        public void observerUpdate(Object o) {
        if (o instanceof Protagonist) {
            Protagonist bernard = (Protagonist) o;
            
            
        }
    }

    private boolean canMove(Direction direction, int[][] mapGrid, int[][] entityGrid) {
        if (direction == Direction.UP) {
            if (this.getCY() == mapGrid[0].length - 1) {
                return false;
            }
            if (mapGrid[this.getCX()][this.getCY() + 1] == MapGridCode.FLOOR
                    && entityGrid[this.getCX()][this.getCY() + 1] == EntityGridCode.EMPTY) {
                this.setDCY(this.getCY() + 1);
                return true;
            }
        } else if (direction == Direction.DOWN) {
            if (this.getCY() == 0) {
                return false;
            }
            if (mapGrid[this.getCX()][this.getCY() - 1] == MapGridCode.FLOOR
                    && entityGrid[this.getCX()][this.getCY() - 1] == EntityGridCode.EMPTY) {
                this.setDCY(this.getCY() - 1);
                return true;
            }
        } else if (direction == Direction.LEFT) {
            if (this.getCX() == 0) {
                return false;
            }
            if (mapGrid[this.getCX() - 1][this.getCY()] == MapGridCode.FLOOR
                    && entityGrid[this.getCX() - 1][this.getCY()] == EntityGridCode.EMPTY) {
                this.setDCX(this.getCX() - 1);
                return true;
            }
        } else if (direction == Direction.RIGHT) {
            if (this.getCX() == mapGrid.length - 1) {
                return false;
            }
            if (mapGrid[this.getCX() + 1][this.getCY()] == MapGridCode.FLOOR
                    && entityGrid[this.getCX() + 1][this.getCY()] == EntityGridCode.EMPTY) {
                this.setDCX(this.getCX() + 1);
                return true;
            }
        } else {
            // No direction
        }
        return false;
    }
}
