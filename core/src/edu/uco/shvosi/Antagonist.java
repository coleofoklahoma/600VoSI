package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import java.util.ArrayList;
import java.util.List;

public class Antagonist extends Entity{

    public Antagonist(Texture texture, int cX, int cY) {
        super(EntityGridCode.ENEMY, texture, cX, cY);
    }
  
    @Override
    public void draw(Batch batch, float alpha){
        super.draw(batch, alpha);
    }
	
    @Override
    public void update() {
        super.update();
    }
}