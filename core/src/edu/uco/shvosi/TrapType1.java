package edu.uco.shvosi;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;

public class TrapType1 extends Sprite {
    private int tX;
    private int tY;
    private int state;
    
    public TrapType1(Texture texture, int tX, int tY){
        super(texture);
        this.tX = tX;
        this.tY = tY;
        this.setX(tX * Constants.TILEDIMENSION);
        this.setY(tY * Constants.TILEDIMENSION);
    }
    
    public int getTX(){
        return this.tX;
    }
    
    public int getTY(){
        return this.tY;
    }
    
    public void setTX(int tX){
        this.tX = tX;
    }
    
    public void setTY(int tY){
        this.tY = tY;
    }
    
    public void render(SpriteBatch batch){
        batch.begin();
        this.draw(batch);
        batch.end();
    }
    
    public void update() {
        
    }
}

