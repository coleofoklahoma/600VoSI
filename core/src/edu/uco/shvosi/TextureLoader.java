package edu.uco.shvosi;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;

public class TextureLoader {
    public static final Texture FLOORTILETEXTURE = new Texture(Gdx.files.internal("tiles/floor_tile.png"));
    public static final Texture WALLTILETEXTURE = new Texture(Gdx.files.internal("tiles/wall_tile.png"));
  
    public static final Texture BERNARDTEXTURE = new Texture(Gdx.files.internal("characters/bernard.png"));
    public static final Texture HEALTHTEXTURE = new Texture (Gdx.files.internal("items/health.png"));
}
