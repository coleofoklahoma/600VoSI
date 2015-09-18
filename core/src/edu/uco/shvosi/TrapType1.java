package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TrapType1 extends Entity implements Observer {

    private int state = 0;
    private float elapsedKunai;
    private boolean activateKunai;
    private TextureRegion temp;
    private Animation kunai;
    private int damage;

    public TrapType1(Texture texture, int cX, int cY) {
        super(EntityGridCode.TRAP, texture, cX, cY);
        kunai = TextureLoader.kunaiTrap;
        activateKunai = false;
        elapsedKunai = 0f;
        this.damage = 25;
    }
	
    @Override
    public void draw(Batch batch, float alpha) {
        super.draw(batch, alpha);
        if (activateKunai) {
            elapsedKunai += Gdx.graphics.getDeltaTime();
            temp = kunai.getKeyFrame(elapsedKunai);
            batch.draw(kunai.getKeyFrame(elapsedKunai), this.getX(), this.getY(), Constants.TILEDIMENSION, Constants.TILEDIMENSION);
            if (kunai.isAnimationFinished(elapsedKunai)) {
                activateKunai = false;
                elapsedKunai = 0f;
                this.state = 1;
                this.setHealth(0);
            }
        }
    }
    
    @Override
    public void collision(Entity entity){
        //this.setHealth(0);
        entity.setHealth(entity.getHealth() - this.damage);
        if (entity.getHealth() < 1){
            entity.setHealth(0);
        }
    }

    public void observerUpdate(Object o) {
        if (o instanceof Protagonist) {
            Protagonist bernard = (Protagonist) o;
            Integer xCoordinate = bernard.getCX();
            Integer yCoordinate = bernard.getCY();
            if (xCoordinate == this.getCX() && yCoordinate == this.getCY() && this.state == 0) {
                this.activateKunai = true;
            }
        }
    }
}
