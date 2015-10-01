package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TrapType2 extends Entity implements Observer {

    private int state = 0;
    private int damage;
    private float elapsedPower;
    private Animation power;
    private TextureRegion temp;
    private boolean activatePower;

    public TrapType2(Texture texture, int cX, int cY) {
        super(EntityGridCode.TRAP, texture, cX, cY);
        this.damage = 10;
        power = TextureLoader.powerTrap;
        activatePower = false;
        elapsedPower = 0f;
        this.setVisible(false);
        this.state = 0;
    }

    @Override
    public void draw(Batch batch, float alpha) {
        super.draw(batch, alpha);
        if (activatePower) {
            elapsedPower += Gdx.graphics.getDeltaTime();
            temp = power.getKeyFrame(elapsedPower);
            batch.draw(power.getKeyFrame(elapsedPower), this.getX(), this.getY(), Constants.TILEDIMENSION, Constants.TILEDIMENSION);
            if (power.isAnimationFinished(elapsedPower)) {
                activatePower = false;
                elapsedPower = 0f;
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
                this.setVisible(true);
                this.activatePower = true;

                if (bernard.getShieldFlag() == 1) {
                    bernard.setImage(TextureLoader.BERNARDTEXTURE);
                    bernard.setShieldFlag(0);
                } else {
                    if (bernard.getExecuteBarrier() == true) {
                        bernard.setHealth(bernard.getHealth() - this.damage / 2);
                        bernard.setBarrierDamage(this.damage / 2);
                        bernard.setBarrierLimit(bernard.getBarrierLimit() - 1);
                    } else {
                        bernard.setHealth(bernard.getHealth() - this.damage);
                    }
                    
                    if (bernard.getHealth() < 1) {
                        bernard.setHealth(0);
                    }
                }

            }
            if (bernard.getExecuteDetection() == true && bernard.getDetectionCollisionBox().intersects(this.getCX(), this.getCY(), 3, 3)) {
                this.setVisible(true);
            }
        }
    }
}
