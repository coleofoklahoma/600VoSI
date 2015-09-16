/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uco.shvosi;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 *
 * @author cody
 */
public interface GameEntity {
    
    public void update();
    public void render(SpriteBatch batch);
    
}
