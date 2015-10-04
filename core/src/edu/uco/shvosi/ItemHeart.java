package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ItemHeart extends Entity implements Observer {

    private int type;
    private int healAmount;
    private int state = 0;
    
    private boolean activateHeal;
    private float elapsedHeal;
    private Animation heal;
    private TextureRegion temp;

    public ItemHeart(int cX, int cY) {
        super(Constants.EntityGridCode.ITEM, TextureLoader.HEALTHTEXTURE, cX, cY);
        this.healAmount = 25;
        heal = TextureLoader.heal;
        activateHeal = false;
        elapsedHeal = 0f;
    }

    @Override
    public void draw(Batch batch, float alpha) {
        super.draw(batch, alpha);
        if (activateHeal) {
            elapsedHeal += Gdx.graphics.getDeltaTime();
            temp = heal.getKeyFrame(elapsedHeal);
            batch.draw(heal.getKeyFrame(elapsedHeal), this.getX(), this.getY(), Constants.TILEDIMENSION, Constants.TILEDIMENSION);
            if (heal.isAnimationFinished(elapsedHeal)) {
                activateHeal = false;
                elapsedHeal = 0f;
                this.state = 1;
                this.setHealth(0);
            }
        }
    }

    @Override
    public void collision(Entity entity) {
    }

    @Override
    public void observerUpdate(Object o) {
        if (o instanceof Protagonist) {
            Protagonist bernard = (Protagonist) o;
            Integer xCoordinate = bernard.getDCX();
            Integer yCoordinate = bernard.getDCY();
            if (xCoordinate == this.getCX() && yCoordinate == this.getCY() && this.state == 0) {
                bernard.setHealth(bernard.getHealth() + this.healAmount);
                if (bernard.getHealth() > 100) {
                    bernard.setHealth(100);
                }
                activateHeal = true;
            }
        }

    }
}
