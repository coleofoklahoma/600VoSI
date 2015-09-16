package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TrapType1 extends Sprite implements Observer {

    private int tX;
    private int tY;
    private int state = 0;
    private float elapsedKunai;
    private boolean activateKunai;
    private TextureRegion temp;
    private Animation kunai;

    public TrapType1(Texture texture, int tX, int tY) {
        super(texture);
        this.tX = tX;
        this.tY = tY;
        this.setX(tX * Constants.TILEDIMENSION);
        this.setY(tY * Constants.TILEDIMENSION);
        kunai = TextureLoader.kunaiTrap;
        activateKunai = false;
        elapsedKunai = 0f;
    }

    public int getTX() {
        return this.tX;
    }

    public int getTY() {
        return this.tY;
    }

    public void setTX(int tX) {
        this.tX = tX;
    }

    public void setTY(int tY) {
        this.tY = tY;
    }

    public void render(SpriteBatch batch) {
        batch.begin();
        this.draw(batch);
        if (activateKunai) {
            elapsedKunai += Gdx.graphics.getDeltaTime();
            temp = kunai.getKeyFrame(elapsedKunai);
            batch.draw(kunai.getKeyFrame(elapsedKunai), this.getX(), this.getY(), Constants.TILEDIMENSION, Constants.TILEDIMENSION);
            if (kunai.isAnimationFinished(elapsedKunai)) {
                activateKunai = false;
                elapsedKunai = 0f;
                this.state = 1;
            }
        }
        batch.end();
    }

    public void observerUpdate(Object o) {
        if (o instanceof Protagonist) {
            Protagonist bernard = (Protagonist) o;
            Integer xCoordinate = bernard.getCY();
            Integer yCoordinate = bernard.getCX();
            if (xCoordinate == this.tX && yCoordinate == this.tY && this.state == 0) {
                this.setSize(0, 0);
                this.activateKunai = true;
            }
        }
    }
}
