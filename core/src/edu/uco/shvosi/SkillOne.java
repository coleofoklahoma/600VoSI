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

        if (bernard.getDirection() == Direction.LEFT) {
            temp = mainAnimation.getKeyFrame(skillRunTime);
            temp.flip(true, false);
            batch.draw(temp, bernard.getX() - Constants.TILEDIMENSION * 2, bernard.getY(), Constants.TILEDIMENSION * 2, Constants.TILEDIMENSION);
            temp.flip(true, false);
        } else {
            batch.draw(mainAnimation.getKeyFrame(skillRunTime), bernard.getX() + Constants.TILEDIMENSION, bernard.getY(), Constants.TILEDIMENSION * 2, Constants.TILEDIMENSION);
        }

    }
}
