package edu.uco.shvosi;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;

public class TrapType2 extends Entity {
    private int state;
    
    public TrapType2(Texture texture, int cX, int cY){
        super(EntityGridCode.TRAP, texture, cX, cY);
    }
    
    @Override
    public void collision(Entity entity){
        this.setHealth(0);
    }
}