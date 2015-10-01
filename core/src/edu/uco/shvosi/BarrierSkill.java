package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BarrierSkill extends Skill {

    private float elapsedHeal;
    private TextureRegion temp;
    private Animation heal;

    public BarrierSkill(int x, int y, Animation mainAnimation, int damage) {
        super(x, y, mainAnimation);
        this.damage = damage;
        heal = TextureLoader.heal;
        elapsedHeal = 0f;
    }

    @Override
    public void draw(Batch batch, float alpha, Protagonist bernard) {
        update();

        if (bernard.getHeal() == true) {
            elapsedHeal += Gdx.graphics.getDeltaTime();
            temp = heal.getKeyFrame(elapsedHeal);
            batch.draw(heal.getKeyFrame(elapsedHeal), bernard.getX(), bernard.getY(), Constants.TILEDIMENSION, Constants.TILEDIMENSION);
            if (heal.isAnimationFinished(elapsedHeal)) {
                bernard.setHeal(false);
                elapsedHeal = 0f;
                bernard.setHealth(bernard.getHealth() + bernard.getBarrierDamage() / 2 + 10);
                if(bernard.getHealth() > 100)
                {
                    bernard.setHealth(100);
                }
                bernard.resetBarrierDamage();
                bernard.setExecuteBarrier(false);
                bernard.setBarrierLimit(2);
            }
        } else {
            batch.draw(mainAnimation.getKeyFrame(skillRunTime), bernard.getX() - 24, bernard.getY() - 35, Constants.TILEDIMENSION + 50, Constants.TILEDIMENSION + 50);
        }
    }
}
