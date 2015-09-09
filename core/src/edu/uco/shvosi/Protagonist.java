package edu.uco.shvosi;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;

public class Protagonist extends Sprite{
    private int cX;
    private int cY;
    
    public Protagonist(Texture texture, int cX, int cY){
        super(texture);
        this.cX = cX;
        this.cY = cY;
        this.setX(cX * Constants.TILEDIMENSION);
        this.setY(cY * Constants.TILEDIMENSION);
    }
    
    public int getCX(){
        return this.cX;
    }
    
    public int getCY(){
        return this.cY;
    }
    
    public void setCX(int cX){
        this.cX = cX;
    }
    
    public void setCY(int cY){
        this.cY = cY;
    }
    
    public void render(SpriteBatch batch){
        batch.begin();
        this.draw(batch);
        batch.end();
    }
}
