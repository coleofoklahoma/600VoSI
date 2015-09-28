package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class CatLady extends Antagonist {
    
    private Animation catLadyWalk;
    private boolean moving = false;
    private boolean flip = false;
    private float elapsedTime;
    private TextureRegion temp;

    public CatLady(Texture texture, int cX, int cY) {
        super(texture, cX, cY);
        this.setEnemyType(EnemyType.CATLADY);
        catLadyWalk = TextureLoader.catLadyWalk;

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
                temp = catLadyWalk.getKeyFrame(elapsedTime);
                temp.flip(true, false);
                batch.draw(temp, this.getX(),getY(), Constants.TILEDIMENSION, Constants.TILEDIMENSION);
                temp.flip(true, false);
            } else {
                batch.draw(catLadyWalk.getKeyFrame(elapsedTime), this.getX(), this.getY(), Constants.TILEDIMENSION *3, Constants.TILEDIMENSION);
            }
            if (catLadyWalk.isAnimationFinished(elapsedTime)) {
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
