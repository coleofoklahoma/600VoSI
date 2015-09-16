package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.util.ArrayList;
import java.util.List;

public class Protagonist extends Sprite implements GameEntity, Observable {

    private int cX;
    private int cY;

    // Sorry to just throw all this in here lol --Cody
    private Animation redLaser;
    private Animation skillOne;
    private boolean executeSkillTwo;
    private boolean executeSkillOne;
    private boolean firing;
    private float firingTime;
    private float elapsedSkillOne;
    private float elapsedSkillTwo;
    private float skillTwoRotation;
    private TextureRegion temp;
    private List<Observer> observers;

    public Protagonist(Texture texture, int cX, int cY) {
        super(texture);
        this.cX = cX;
        this.cY = cY;
        this.setX(cX * Constants.TILEDIMENSION);
        this.setY(cY * Constants.TILEDIMENSION);
        this.observers=new ArrayList();
        // Laser stuffs --Cody
        redLaser = TextureLoader.redLaser;
        firing = false;
        firingTime = 0f;

        // Skill one
        skillOne = TextureLoader.skillOne;
        executeSkillOne = false;
        elapsedSkillOne = 0f;

        elapsedSkillTwo = 0f;
        skillTwoRotation = 0f;
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

    
  
    @Override
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

        if (executeSkillOne) {
            elapsedSkillOne += Gdx.graphics.getDeltaTime();
            if (this.isFlipX()) {
                temp = skillOne.getKeyFrame(elapsedSkillOne);
                temp.flip(true, false);
                batch.draw(temp, this.getX() - Constants.TILEDIMENSION * 2, this.getY(), Constants.TILEDIMENSION * 2, Constants.TILEDIMENSION);
                temp.flip(true, false);
            } else {
                batch.draw(skillOne.getKeyFrame(elapsedSkillOne), this.getX() + Constants.TILEDIMENSION, this.getY(), Constants.TILEDIMENSION * 2, Constants.TILEDIMENSION);
            }
            if (skillOne.isAnimationFinished(elapsedSkillOne)) {
                executeSkillOne = false;
                elapsedSkillOne = 0f;
            }
        }

        if (executeSkillTwo) {
            elapsedSkillTwo += Gdx.graphics.getDeltaTime();
            skillTwoRotation += 200 * Gdx.graphics.getDeltaTime();
            temp = TextureLoader.skillTwo.getKeyFrame(elapsedSkillTwo);
            batch.draw(temp, this.getX(), this.getY(), this.getWidth() / 2,this.getHeight() / 2, Constants.TILEDIMENSION * 2, Constants.TILEDIMENSION, 1, 1, skillTwoRotation);
            if (skillOne.isAnimationFinished(elapsedSkillTwo / 6)) {
                executeSkillTwo = false;
                elapsedSkillTwo = 0f;
                skillTwoRotation = 0f;
            }
        }

        batch.end();
    }

    public void setFiring(boolean firing) {
        this.firing = firing;
    }

    public void setExecuteSkillOne(boolean executeSkillOne) {
        this.executeSkillOne = executeSkillOne;
    }

    public void setExecuteSkillTwo(boolean executeSkillTwo) {
        this.executeSkillTwo = executeSkillTwo;
    }
    
    public void notifyObservers() {
        for(Observer o:observers){
            o.observerUpdate(this);
        }
    }
    
    public void addObserver(Observer o) {
        this.observers.add(o);
    }
    
    public void removeObserver(Observer o) {
        this.observers.remove(o);
    }
    
    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}