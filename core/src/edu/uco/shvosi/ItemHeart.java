package edu.uco.shvosi;

import com.badlogic.gdx.graphics.Texture;

public class ItemHeart extends Entity implements Observer{
    private int type;
    private int healAmount;
    
    public ItemHeart(Texture texture, int cX, int cY){
        super(EntityGridCode.ITEM, texture, cX, cY);
        this.healAmount = 25;
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
                bernard.setHealth(bernard.getHealth() + this.healAmount);
                this.setHealth(0);
                if (bernard.getHealth() > 100){
                    bernard.setHealth(100);
                }
            }
        }

    }
}
