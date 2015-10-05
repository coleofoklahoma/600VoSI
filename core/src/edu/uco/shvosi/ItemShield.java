package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class ItemShield extends Entity implements Observer{
    private int type;
    private int state = 0;
    Sound invent = Gdx.audio.newSound(Gdx.files.internal("sounds/invent.mp3"));
    
    public ItemShield(int cX, int cY){
        super(Constants.EntityGridCode.ITEM, TextureLoader.SHIELDTEXTURE, cX, cY);
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
            if (xCoordinate == this.getCX() && yCoordinate == this.getCY() && this.state == 0) {
                if (bernard.getItemHeld() == 0){
                    bernard.setItemHeld(1);
                    GameScreen.invent.setImage(TextureLoader.INVENTORYSHIELDTEXTURE);
                    invent.play(1.0f);
                    this.setHealth(0);
                    this.state = 1;
                }
                
            }
        }

    }
}