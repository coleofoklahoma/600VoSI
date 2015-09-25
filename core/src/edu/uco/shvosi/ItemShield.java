package edu.uco.shvosi;

import com.badlogic.gdx.graphics.Texture;

public class ItemShield extends Entity{
    private int type;
    private int healAmount;
    
    public ItemShield(Texture texture, int cX, int cY){
        super(EntityGridCode.ITEM, texture, cX, cY);
        this.healAmount = 25;
    }
    
    @Override
    public void collision(Entity entity){
        GameScreen.invent.setImage(TextureLoader.INVENTORYSHIELDTEXTURE);
        this.setHealth(0);
        }
}