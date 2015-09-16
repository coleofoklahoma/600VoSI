package edu.uco.shvosi;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class TextureLoader {
    public static final Texture FLOORTILETEXTURE = new Texture(Gdx.files.internal("tiles/floor_tile.png"));
    public static final Texture WALLTILETEXTURE = new Texture(Gdx.files.internal("tiles/wall_tile.png"));
  
    public static final Texture BERNARDTEXTURE = new Texture(Gdx.files.internal("characters/bernard.png"));
    public static final Texture HEALTHTEXTURE = new Texture (Gdx.files.internal("items/health.png"));
    public static final Texture SKILLONETEXTURE = new Texture(Gdx.files.internal("skillOne.png"));
    public static final Texture REDLASERTEXTURE = new Texture(Gdx.files.internal("RedLaserAnimation.png"));
    public static final Texture TRAPTEXTURE = new Texture(Gdx.files.internal("traps/trap.png"));
    public static final Texture TRAPTEXTURE2 = new Texture(Gdx.files.internal("traps/trap2.png"));
    private Array<TextureRegion> laserFrames;
    private Array<TextureRegion> skillOneFrames;
    public static Animation redLaser;
    public static Animation skillOne;
    public static Animation skillTwo;
    TextureLoader()
    {
        laserFrames = new Array<TextureRegion>(15);
        
        for(int i = 0; i < 15; i++)
        {
            laserFrames.add(new TextureRegion(REDLASERTEXTURE, 25, i * 156, 731, 156));
        }
        
        redLaser = new Animation(0.05f, laserFrames, PlayMode.NORMAL);
        
        skillOneFrames = new Array<TextureRegion>(6);
         for(int i = 0; i < 6; i++)
        {
            skillOneFrames.add(new TextureRegion(SKILLONETEXTURE, 25, i * 90, 200, 100));
        }
        skillOne = new Animation(0.05f, skillOneFrames, PlayMode.NORMAL);
        
        skillTwo = new Animation(0.05f, skillOneFrames, PlayMode.LOOP);
    }
    
    public void dispose()
    {
        FLOORTILETEXTURE.dispose();
        WALLTILETEXTURE.dispose();
        BERNARDTEXTURE.dispose();
        HEALTHTEXTURE.dispose();
        TRAPTEXTURE.dispose();
        TRAPTEXTURE2.dispose();
        REDLASERTEXTURE.dispose();
        SKILLONETEXTURE.dispose();
    }
}
