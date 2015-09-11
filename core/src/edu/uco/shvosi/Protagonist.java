package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Protagonist extends Sprite {

    private int cX;
    private int cY;

    // Sorry to just throw all this in here lol --Cody
    private Animation redLaser;
    private boolean firing;
    private float firingTime;
    private TextureRegion temp;

    public Protagonist(Texture texture, int cX, int cY) {
        super(texture);
        this.cX = cX;
        this.cY = cY;
        this.setX(cX * Constants.TILEDIMENSION);
        this.setY(cY * Constants.TILEDIMENSION);

        // Laser stuffs --Cody
        redLaser = TextureLoader.redLaser;
        firing = false;
        firingTime = 0f;

    }

    public int getCX() {
        return this.cX;
    }

    public int getCY() {
        return this.cY;
    }

    public void setCX(int cX) {
        this.cX = cX;
    }

    public void setCY(int cY) {
        this.cY = cY;
    }

    public void render(SpriteBatch batch) {
        batch.begin();
        this.draw(batch);
        if (firing) {
            firingTime += Gdx.graphics.getDeltaTime();
            if (this.isFlipX()) {
                temp = redLaser.getKeyFrame(firingTime);
                temp.flip(true, false);
                batch.draw(temp, this.getX() - Constants.TILEDIMENSION * 3, this.getY(), Constants.TILEDIMENSION * 3, Constants.TILEDIMENSION);
                temp.flip(true, false);
            } else {
                batch.draw(redLaser.getKeyFrame(firingTime), this.getX() + Constants.TILEDIMENSION, this.getY(), Constants.TILEDIMENSION * 3, Constants.TILEDIMENSION);
            }
            if (redLaser.isAnimationFinished(firingTime)) {
                firing = false;
                firingTime = 0f;
            }
        }
        batch.end();
    }

    public void setFiring(boolean firing) {
        this.firing = firing;
    }

}
