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
class MeleeAttack extends Skill {

    private TextureRegion temp;
    
    public MeleeAttack(int x, int y, Animation mainAnimation, int damage) {
        super(x, y, mainAnimation);
        this.damage = damage;
    }

    @Override
    public void draw(Batch batch, float alpha, Protagonist bernard) {
        update();
 

    }

}