package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MeleeAttack extends Entity implements Observer {

    private int state = 0;
    private float elapsedAttack;
    private boolean activateAttack;
    private TextureRegion temp;
    private Animation meleeAttack;
    private int damage;
   // Sound meleeSound = Gdx.audio.newSound(Gdx.files.internal("sounds/trap1.mp3"));

    public MeleeAttack(int cX, int cY) {
        super(Constants.EntityGridCode.TRAP, TextureLoader.MELEETEXTURE, cX, cY);
        meleeAttack = TextureLoader.meleeAttack;
        activateAttack = false;
        elapsedAttack = 0f;
        this.damage = 25;
        this.setVisible(false);
        this.state = 0;
    }

    @Override
    public void draw(Batch batch, float alpha) {
        super.draw(batch, alpha);
        if (activateAttack) {
            elapsedAttack += Gdx.graphics.getDeltaTime();
            temp = meleeAttack.getKeyFrame(elapsedAttack);
            batch.draw(meleeAttack.getKeyFrame(elapsedAttack), this.getX(), this.getY(), Constants.TILEDIMENSION, Constants.TILEDIMENSION);
            if (meleeAttack.isAnimationFinished(elapsedAttack)) {
                activateAttack = false;
                elapsedAttack = 0f;
                this.state = 1;               
                this.setHealth(0);
            }
        }
    }

    @Override
    public void collision(Entity entity) {
    }

    public void observerUpdate(Object o) {
        if (o instanceof Protagonist) {
            Protagonist bernard = (Protagonist) o;
            Integer xCoordinate = bernard.getDCX();
            Integer yCoordinate = bernard.getDCY();

            if (xCoordinate == this.getCX() && yCoordinate == this.getCY() && this.state == 0) {
                this.setVisible(true);
                this.activateAttack = true;
                //meleeSound.play(1.0f);
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
