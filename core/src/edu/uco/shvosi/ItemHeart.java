package edu.uco.shvosi;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;

public class ItemHeart extends Sprite{
    private int iX;
    private int iY;
    
    
    public ItemHeart(Texture texture, int iX, int iY){
        super(texture);
        this.iX = iX;
        this.iY = iY;
        this.setX(iX * Constants.TILEDIMENSION);
        this.setY(iY * Constants.TILEDIMENSION);
    }
    
    public int getIX(){
        return this.iX;
    }
    
    public int getIY(){
        return this.iY;
    }
    
    public void setIX(int iX){
        this.iX = iX;
    }
    
    public void setIY(int iY){
        this.iY = iY;
    }
    
    public void render(SpriteBatch batch){
        batch.begin();
        this.draw(batch);
        batch.end();
    }
    
    
}

