/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uco.shvosi;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 *
 * @author cody
 */
public class Skill extends Sprite implements GameEntity {
    
    private float skillRunTime;
    private Animation mainAnimation;
    
    public Skill(Animation mainAnimation) {
        this.mainAnimation = mainAnimation;
        skillRunTime = 0f;
    }
    


    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void render(SpriteBatch batch) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
