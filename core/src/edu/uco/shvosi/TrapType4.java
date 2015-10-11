package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

public class TrapType4 extends Entity implements Observer {

    ParticleEffect poisonParticle;
    private int state = 0;
    private int damage;
    private boolean activatePoison;
    Sound trap3 = Gdx.audio.newSound(Gdx.files.internal("sounds/trap3.mp3"));

    public TrapType4(int cX, int cY) {
        super(Constants.EntityGridCode.TRAP, TextureLoader.TRAPTEXTURE3, cX, cY);
        this.damage = 10;
        this.setCX(cX);
        this.setCY(cY);
        this.setVisible(false);
        this.state = 0;
        poisonParticle = new ParticleEffect();
        poisonParticle.load(Gdx.files.internal("traps/poison.p"), Gdx.files.internal("traps"));
    }

    @Override
    public void draw(Batch batch, float alpha) {
        super.draw(batch, alpha);
        if (activatePoison) {
            poisonParticle.start();
            poisonParticle.getEmitters().first().setPosition(this.getCX() * Constants.TILEDIMENSION + 50, this.getCY() * Constants.TILEDIMENSION + 50);
            poisonParticle.draw(batch, Gdx.graphics.getDeltaTime());
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
                this.activatePoison = true;
                trap3.play(1.0f);
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
                bernard.setPoison(true);
            }
            if (bernard.getExecuteDetection() == true && bernard.getDetectionCollisionBox().intersects(this.getCX(), this.getCY(), 3, 3)) {
                this.setVisible(true);
            }
        }
    }
}
