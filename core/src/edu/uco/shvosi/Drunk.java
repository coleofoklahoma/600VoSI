package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import java.util.List;

public class Drunk extends Antagonist {
    
    private Animation drunkWalk;
    private boolean moving = false;
    private boolean flip = false;
    private float elapsedTime;
    private TextureRegion temp;
    
    public Drunk(int cX, int cY) {
        super(TextureLoader.DRUNKTEXTURE, cX, cY);
        this.setEnemyType(Constants.EnemyType.DRUNK);
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
                batch.draw(drunkWalk.getKeyFrame(elapsedTime), this.getX(), this.getY(), Constants.TILEDIMENSION , Constants.TILEDIMENSION);
            }
            if (drunkWalk.isAnimationFinished(elapsedTime)) {
                moving = false;
                elapsedTime = 0f;
            }
        
    }

    @Override
    public void calculateTurn(Constants.MapGridCode[][] mapGrid, Constants.EntityGridCode[][] entityGrid, List<Entity> entityList) {
        //Random movement
        int random = 0;
        int tries = 0;
        Constants.Direction d = Constants.Direction.NONE;

        while (!canMove(d, mapGrid, entityGrid)) {
            random = (int) (Math.random() * entityGrid.length);
            switch (random % 4) {
                case 1:
                    d = Constants.Direction.UP;
                    break;
                case 2:
                    d = Constants.Direction.DOWN;
                    break;
                case 3:
                    d = Constants.Direction.LEFT;
                    flip = true;
                    break;
                default:
                    d = Constants.Direction.RIGHT;
                    flip = false;
                    break;
            }
            tries++;
            if(tries > 5)
                break;
        }

        this.setTurnAction(Constants.TurnAction.MOVE);
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
        
    

    private boolean canMove(Constants.Direction direction, Constants.MapGridCode[][] mapGrid, Constants.EntityGridCode[][] entityGrid) {
        
        if (direction == Constants.Direction.UP) {
            if (this.getCY() == mapGrid[0].length - 1) {
                return false;
            }
            if (mapGrid[this.getCX()][this.getCY() + 1] == Constants.MapGridCode.FLOOR
                    && entityGrid[this.getCX()][this.getCY() + 1] == Constants.EntityGridCode.NONE) {
                this.setDCY(this.getCY() + 1);
                return true;
            }
        } else if (direction == Constants.Direction.DOWN) {
            if (this.getCY() == 0) {
                return false;
            }
            if (mapGrid[this.getCX()][this.getCY() - 1] == Constants.MapGridCode.FLOOR
                    && entityGrid[this.getCX()][this.getCY() - 1] == Constants.EntityGridCode.NONE) {
                this.setDCY(this.getCY() - 1);
                return true;
            }
        } else if (direction == Constants.Direction.LEFT) {
            if (this.getCX() == 0) {
                return false;
            }
            if (mapGrid[this.getCX() - 1][this.getCY()] == Constants.MapGridCode.FLOOR
                    && entityGrid[this.getCX() - 1][this.getCY()] == Constants.EntityGridCode.NONE) {
                this.setDCX(this.getCX() - 1);
                return true;
            }
        } else if (direction == Constants.Direction.RIGHT) {
            if (this.getCX() == mapGrid.length - 1) {
                return false;
            }
            if (mapGrid[this.getCX() + 1][this.getCY()] == Constants.MapGridCode.FLOOR
                    && entityGrid[this.getCX() + 1][this.getCY()] == Constants.EntityGridCode.NONE) {
                this.setDCX(this.getCX() + 1);
                return true;
            }
        } else {
            // No direction
        }
        return false;
    }
}