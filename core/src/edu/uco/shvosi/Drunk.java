package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;

public class Drunk extends Antagonist {
    
    private Animation drunkWalk;
    private boolean moving = false;
    private boolean flip = false;
    private float elapsedTime;
    private TextureRegion temp;
    
    public Drunk(Texture texture, int cX, int cY) {
        super(texture, cX, cY);
        this.setEnemyType(EnemyType.DRUNK);
        drunkWalk = TextureLoader.drunkWalk;
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
                temp = drunkWalk.getKeyFrame(elapsedTime);
                temp.flip(true, false);
                batch.draw(temp, this.getX(),getY(), Constants.TILEDIMENSION, Constants.TILEDIMENSION);
                temp.flip(true, false);
            } else {
                batch.draw(drunkWalk.getKeyFrame(elapsedTime), this.getX(), this.getY(), Constants.TILEDIMENSION *3, Constants.TILEDIMENSION);
            }
            if (drunkWalk.isAnimationFinished(elapsedTime)) {
                moving = false;
                elapsedTime = 0f;
            }
        
    }

    @Override
    public void calculateTurn(int[][] mapGrid, int[][] entityGrid) {
        //Random movement
        int random = 0;
        int tries = 0;
        Direction d = Direction.NONE;

        while (!canMove(d, mapGrid, entityGrid)) {
            random = (int) (Math.random() * entityGrid.length);
            switch (random % 4) {
                case 1:
                    d = Direction.UP;
                    break;
                case 2:
                    d = Direction.DOWN;
                    break;
                case 3:
                    d = Direction.LEFT;
                    break;
                default:
                    d = Direction.RIGHT;
                    break;
            }
            tries++;
            if(tries > 5)
                break;
        }

        this.setTurnAction(TurnAction.MOVE);
    }
    
    @Override
    public void moveAction() {
        MoveToAction moveAction = new MoveToAction();
        moveAction.setPosition((float) (this.getCX() * Constants.TILEDIMENSION),
                               (float) (this.getCY() * Constants.TILEDIMENSION));
        moveAction.setDuration(Constants.MOVEACTIONDURATION);
        this.addAction(moveAction);
        moving= true;
                 

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