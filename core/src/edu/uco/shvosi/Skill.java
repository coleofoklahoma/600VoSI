/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;

/**
 *
 * @author cody
 */
public class Skill extends Entity {

    protected float skillRunTime;
    protected Animation mainAnimation;
    protected int damage;

    public Skill(int x, int y, Animation mainAnimation) {
        super(EntityGridCode.SKILL, TextureLoader.BERNARDTEXTURE, x, y);
        this.mainAnimation = mainAnimation;
        skillRunTime = 0f;
    }

    @Override
    public void update() {
        skillRunTime += Gdx.graphics.getDeltaTime();
    }

    public boolean isAnimationFinished() {
        if (mainAnimation.isAnimationFinished(skillRunTime)) {
            skillRunTime = 0f;
            return true;
        } else {
            return false;
        }
    }
    
    public void draw(Batch batch, float alpha, Protagonist bernard) {
    }
    
    public int getDamage() {
        return damage;
    }

}
