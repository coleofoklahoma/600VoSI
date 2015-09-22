/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uco.shvosi;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


/**
 *
 * @author Cary
 */
public class StartScreen extends Game{
private SpriteBatch batch;

private Sprite splash;
private Texture splashT;
private Sprite startBut;
private Texture startT;
private Sprite quitBut;
private Texture quitT;


public void TestScreen ()
{
    create();
}
    @Override
    public void create() {
        
        
        batch = new SpriteBatch();
        splashT = new Texture(Gdx.files.internal("splash.png"));
        splash = new Sprite(splashT, 800, 450);
        splash.setPosition(0, 0);
        startT = new Texture(Gdx.files.internal("startButtonS.png"));
        startBut = new Sprite(startT, 100, 50);
        startBut.setPosition(550, 15);
        quitT = new Texture(Gdx.files.internal("quitButton.png"));
        quitBut = new Sprite(quitT, 100, 50);
        quitBut.setPosition(650, 15);
  
        render();
        
    }
    
@Override
    public void render()
    {
        
        Gdx.gl.glClearColor(0, 0, 0, 1);
        
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        //batch.setProjectionMatrix(camera.combined);
        batch.begin();
        splash.draw(batch);
        startBut.draw(batch);
        quitBut.draw(batch);
        batch.end();
        
        if (Gdx.input.isTouched()) {
            MyGdxGame.gameState = 0;
        }
    }
    
}


