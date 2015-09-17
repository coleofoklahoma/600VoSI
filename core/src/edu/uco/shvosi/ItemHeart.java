package edu.uco.shvosi;

import com.badlogic.gdx.graphics.Texture;

public class ItemHeart extends Entity{
    private int type;
    private int healAmount;
    
    public ItemHeart(Texture texture, int cX, int cY){
        super(EntityGridCode.ITEM, texture, cX, cY);
        this.healAmount = 1;
    }
    
    @Override
    public void collision(Entity entity){
        entity.setHealth(entity.getHealth() + this.healAmount);
        this.setHealth(0);
    }
}
