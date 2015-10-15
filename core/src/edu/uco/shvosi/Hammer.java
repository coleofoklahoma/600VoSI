package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.util.List;

public class Hammer extends Antagonist {
    
    private Animation hammerWalk;
    private Animation hammerAttack;
    private boolean moving = false;
    private boolean flip = false;
    private float elapsedTime;
    private TextureRegion temp;
    private int bernardX;
    private int bernardY;
    private String XorY;
    private int xdis;
    private int ydis;
    private int damage = 50;
    private boolean active = false;
    

    public Hammer(int cX, int cY) {
        super(TextureLoader.HAMMERTETEXTURE, cX, cY);
        this.setEnemyType(Constants.EnemyType.HAMMER);
        hammerWalk = TextureLoader.hammerWalk;
        hammerAttack = TextureLoader.hammerAttack;

        this.setBoundingBox(120);
        this.damage = damage;

    }

    @Override
    public void attackAction() {
        //Do Attack Stuffs?
        //MeleeAttack meleeAttach = new MeleeAttack(bernardX, bernardY, damage);
        
    }
    
        @Override
    public void draw(Batch batch, float alpha) {
        super.draw(batch, alpha);
        
                         
            elapsedTime += Gdx.graphics.getDeltaTime();
            if(xdis >=0)
            {
                flip = true;
            }
            else
            {
                flip = false;
            }
            if(xdis >2 || ydis >2){    
                if (flip) {
                    temp = hammerWalk.getKeyFrame(elapsedTime);
                    temp.flip(true, false);
                    batch.draw(temp, this.getX(),getY(), Constants.TILEDIMENSION, Constants.TILEDIMENSION);
                    temp.flip(true, false);
                } else {
                    batch.draw(hammerWalk.getKeyFrame(elapsedTime), this.getX(), this.getY(), Constants.TILEDIMENSION, Constants.TILEDIMENSION);
                }
                if (hammerWalk.isAnimationFinished(elapsedTime)) {
                    moving = false;
                    elapsedTime = 0f;
                }
            }
            if(xdis <=2 && ydis <=2){    
                if (flip) {
                    temp = hammerAttack.getKeyFrame(elapsedTime);
                    temp.flip(true, false);
                    batch.draw(temp, this.getX(),getY(), Constants.TILEDIMENSION, Constants.TILEDIMENSION);
                    temp.flip(true, false);
                } else {
                    batch.draw(hammerAttack.getKeyFrame(elapsedTime), this.getX(), this.getY(), Constants.TILEDIMENSION, Constants.TILEDIMENSION);
                }
                if (hammerAttack.isAnimationFinished(elapsedTime)) {
                    moving = false;
                    elapsedTime = 0f;
                }
            }
        
    }

    
    @Override
    public void calculateTurn(Constants.MapGridCode[][] mapGrid, Constants.EntityGridCode[][] entityGrid, List<Entity> entityList) {
        //Random movement
        int tries = 0;
        Constants.Direction d = Constants.Direction.NONE;

            for(int i = 0; i < entityList.size(); i++)
            {
                if(entityList.get(i).getGridCode() == Constants.EntityGridCode.PLAYER){
                    bernardX = entityList.get(i).getCX();
                    bernardY = entityList.get(i).getCY();
                    break;
                }
            }
          
            xdis = this.getCX() - bernardX;
            ydis = this.getCY() - bernardY;
            if(xdis < 5 && ydis < 5)
            {
                active = true;
            }
        
            if (active)
             {
            
            while (!canMove(d, mapGrid, entityGrid)) {
        
                if(Math.abs(xdis) > Math.abs(ydis))
                {
                    XorY="X";
                }
                else
                {
                    XorY="Y";
                }
        
                if("X".equals(XorY) && xdis >= 0)
                {
                    d = Constants.Direction.LEFT;
                }
        
            if("X".equals(XorY) && xdis < 0)
            {
                d = Constants.Direction.RIGHT;
            }
            if("Y".equals(XorY) && ydis >= 0)
            {
                d = Constants.Direction.DOWN;
            }
        
            if("Y".equals(XorY) && ydis < 0)
            {
                d = Constants.Direction.UP;
            }
        
            tries++;
            if(tries > 5){
                this.setTurnAction(Constants.TurnAction.NONE);
                return;
            }
        }//end while
   
        this.setTurnAction(Constants.TurnAction.MOVE);
             }//end if active
//        else if (active && (xdis <= 1 && ydis <= 1))
//        {
//           this.setTurnAction(Constants.TurnAction.ATTACK);
//
//        }
    }
    
    @Override
    public void collision(Entity entity) {
        entity.setHealth(entity.getHealth() - this.damage);
        if (entity.getHealth() < 1) {
            entity.setHealth(0);
        }
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
