/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uco.shvosi;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 *
 * @author cody
 */
class SkillOne extends Skill {

    private TextureRegion temp;

    public SkillOne(int x, int y, Animation mainAnimation, int damage) {
        super(x, y, mainAnimation);
        this.damage = damage;
    }

    @Override
    public void draw(Batch batch, float alpha, Protagonist bernard) {
        update();

        

        if (((Entity) bernard).getTextureRegion().isFlipX()) {
            setX(bernard.getX() - Constants.TILEDIMENSION * 2);
            setY(bernard.getY());
            boundingBox.set(this.getX(), this.getY(), Constants.TILEDIMENSION * 2, Constants.TILEDIMENSION);
            temp = mainAnimation.getKeyFrame(skillRunTime);
            temp.flip(true, false);
            batch.draw(temp, getX(), getY(), Constants.TILEDIMENSION * 2, Constants.TILEDIMENSION);
            temp.flip(true, false);
        } else {
            setX(bernard.getX() + Constants.TILEDIMENSION);
            setY(bernard.getY());
            boundingBox.set(this.getX(), this.getY(), Constants.TILEDIMENSION * 2, Constants.TILEDIMENSION);
            batch.draw(mainAnimation.getKeyFrame(skillRunTime), getX(), getY(), Constants.TILEDIMENSION * 2, Constants.TILEDIMENSION);
        }

    }
}
