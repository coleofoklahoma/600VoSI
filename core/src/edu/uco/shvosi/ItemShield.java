package edu.uco.shvosi;

import com.badlogic.gdx.graphics.Texture;

public class ItemShield extends Entity implements Observer{
    private int type;

    
    public ItemShield(Texture texture, int cX, int cY){
        super(EntityGridCode.ITEM, texture, cX, cY);
    }
    
    @Override
    public void collision(Entity entity){
       
    }
    
    @Override
    public void observerUpdate(Object o) {
        if (o instanceof Protagonist) {
            Protagonist bernard = (Protagonist) o;
            Integer xCoordinate = bernard.getDCX();
            Integer yCoordinate = bernard.getDCY();
            if (xCoordinate == this.getCX() && yCoordinate == this.getCY()) {
                if (bernard.getItemHeld() == 0){
                    bernard.setItemHeld(1);
                    GameScreen.invent.setImage(TextureLoader.INVENTORYSHIELDTEXTURE);
                    this.setHealth(0);
                }
                
            }
        }

    }
}