package edu.uco.shvosi;

import com.badlogic.gdx.graphics.Texture;

public class ItemShield extends Entity{
    private int type;
    
    public ItemShield(Texture texture, int cX, int cY){
        super(EntityGridCode.ITEM, texture, cX, cY);
    }
    
    @Override
    public void collision(Entity entity){
    }
}
