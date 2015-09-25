package edu.uco.shvosi;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class TextureLoader {
    public static final Texture WANDERTEXTURE = new Texture (Gdx.files.internal("characters/Wanderer.png"));
    public static final Texture DRUNKTEXTURE = new Texture (Gdx.files.internal("characters/Drunk.png"));
    public static final Texture BERNARDTEXTURE = new Texture(Gdx.files.internal("characters/bernard.png"));
    public static final Texture HEALTHTEXTURE = new Texture (Gdx.files.internal("items/health.png"));
    public static final Texture SKILLONETEXTURE = new Texture(Gdx.files.internal("skillOne.png"));
    public static final Texture REDLASERTEXTURE = new Texture(Gdx.files.internal("RedLaserAnimation.png"));
    public static final Texture DRUNKENTEXTURE = new Texture(Gdx.files.internal("characters/drunk_sheet.png"));
    public static final Texture TRAPTEXTURE = new Texture(Gdx.files.internal("traps/trap.png"));
    public static final Texture TRAPTEXTURE2 = new Texture(Gdx.files.internal("traps/trap2.png"));
    public static final Texture TRAPKUNAI = new Texture(Gdx.files.internal("traps/kunai.png"));
    public static final Texture TRAPPOWER = new Texture(Gdx.files.internal("traps/powerseal.png"));
    public static final Texture DETECTIONTEXTURE = new Texture(Gdx.files.internal("detection.png"));
    public static final Texture INVENTORYTEXTURE = new Texture(Gdx.files.internal("inventory.png"));
    public static final Texture INVENTORYSHIELDTEXTURE = new Texture(Gdx.files.internal("inventoryshield.png"));
    public static final Texture SHIELDTEXTURE = new Texture(Gdx.files.internal("items/shield.png"));
    private TextureRegion[] detectionFrames;
    private TextureRegion[] kunaiFrames;
    private TextureRegion[] powerFrames;
    private Array<TextureRegion> laserFrames;
    private Array<TextureRegion> skillOneFrames;
    private Array<TextureRegion> drunkFrames;
    public static Animation redLaser;
    public static Animation drunkWalk;
    public static Animation skillOne;
    public static Animation skillTwo;
    public static Animation kunaiTrap;
    public static Animation powerTrap;
    public static Animation detectionSkill;
    private static final int FRAME_COLS = 5;
    private static final int FRAME_ROWS = 4;
    
    TextureLoader()
    {
        //Big Ass Laser
        laserFrames = new Array<TextureRegion>(15); 
        for(int i = 0; i < 15; i++)
        {
            laserFrames.add(new TextureRegion(REDLASERTEXTURE, 25, i * 156, 731, 156));
        }
        
        redLaser = new Animation(0.05f, laserFrames, PlayMode.NORMAL);
        
        //Antagonist Drunk
        drunkFrames = new Array<TextureRegion>(15); 
        for(int i = 0; i < 15; i++)
        {
            drunkFrames.add(new TextureRegion(DRUNKENTEXTURE, 0, i * 100, 100, 100));
        }
        
        drunkWalk = new Animation(0.05f, drunkFrames, PlayMode.LOOP);
        
        //Skill One
        skillOneFrames = new Array<TextureRegion>(6);
         for(int i = 0; i < 6; i++)
        {
            skillOneFrames.add(new TextureRegion(SKILLONETEXTURE, 25, i * 90, 200, 100));
        }
        skillOne = new Animation(0.05f, skillOneFrames, PlayMode.NORMAL);
        
        //Skill Two
        skillTwo = new Animation(0.05f, skillOneFrames, PlayMode.LOOP);
        
        //Kunai Trap
        TextureRegion[][] tmp = TextureRegion.split(TextureLoader.TRAPKUNAI, TextureLoader.TRAPKUNAI.getWidth() / FRAME_COLS, TextureLoader.TRAPKUNAI.getHeight() / FRAME_ROWS);
        kunaiFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                kunaiFrames[index++] = tmp[i][j];
            }
        }
        kunaiTrap = new Animation(0.05f, kunaiFrames);
        
        //Powerseal Trap
        TextureRegion[][] tmp2 = TextureRegion.split(TextureLoader.TRAPPOWER, TextureLoader.TRAPPOWER.getWidth() / FRAME_COLS, TextureLoader.TRAPPOWER.getHeight() / FRAME_ROWS);
        powerFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index2 = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                powerFrames[index2++] = tmp2[i][j];
            }
        }
        powerTrap = new Animation(0.05f, powerFrames);
    
     //Detection
        TextureRegion[][] tmp3 = TextureRegion.split(TextureLoader.DETECTIONTEXTURE, TextureLoader.DETECTIONTEXTURE.getWidth() / FRAME_COLS, TextureLoader.DETECTIONTEXTURE.getHeight() / FRAME_COLS);
        detectionFrames = new TextureRegion[FRAME_COLS * FRAME_COLS];
        int index3 = 0;
        for (int i = 0; i < FRAME_COLS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                detectionFrames[index3++] = tmp3[i][j];
            }
        }
        detectionSkill = new Animation(0.05f, detectionFrames);
    }

    public void dispose()
    {
        BERNARDTEXTURE.dispose();
        WANDERTEXTURE.dispose();
        DRUNKTEXTURE.dispose();
        DRUNKENTEXTURE.dispose();
        HEALTHTEXTURE.dispose();
        TRAPTEXTURE.dispose();
        TRAPTEXTURE2.dispose();
        TRAPKUNAI.dispose();
        TRAPPOWER.dispose();
        DETECTIONTEXTURE.dispose();
        REDLASERTEXTURE.dispose();
        SKILLONETEXTURE.dispose();
    }
}