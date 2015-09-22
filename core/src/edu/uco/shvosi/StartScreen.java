/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uco.shvosi;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

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
   
        System.out.println("create");
        render();
        
    }
    
@Override
    public void render()
    {
        
        Gdx.gl.glClearColor(0, 0, 0, 1);
        
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        //batch.setProjectionMatrix(camera.combined);
        System.out.println("render");
        batch.begin();
        splash.draw(batch);
        startBut.draw(batch);
        quitBut.draw(batch);
        batch.end();
        System.out.println("render");

        if (Gdx.input.isTouched()) {
            MyGdxGame.gameState = 2;
            System.out.println(""+MyGdxGame.gameState);
        }
    }
    
}

/*changes in MyGdxGame
public static int gameState = -1;


in rendor
        switch (gameState) {
            case 1:
                StartScreen();
                break;

            case 2:
                GamePlay();
                break;
            case -1:
                TestScreen ts = new TestScreen();
                ts.create();
                break;
                
        }*/
