package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class Protagonist extends Entity implements Observable {
	
    private boolean playTurn;
    private Direction direction;

    // Sorry to just throw all this in here lol --Cody
    private Animation redLaser;
    private Animation skillOne;
    private Animation detection;
    private boolean executeDetection;
    private boolean executeSkillTwo;
    private boolean executeSkillOne;
    private boolean firing;
    private float elapsedDetection;
    private float firingTime;
    private float elapsedSkillOne;
    private float elapsedSkillTwo;
    private float skillTwoRotation;
    private TextureRegion temp;
    private List<Observer> observers;

    public Protagonist(Texture texture, int cX, int cY) {
        super(EntityGridCode.PLAYER, texture, cX, cY);
        this.playTurn = false;
        this.setHealth(100);
		
        this.observers=new ArrayList();
        // Laser stuffs --Cody
        redLaser = TextureLoader.redLaser;
        firing = false;
        firingTime = 0f;

        // Detection
        detection = TextureLoader.detectionSkill;
        executeDetection = false;
        elapsedDetection = 0f;
        
        // Skill one
        skillOne = TextureLoader.skillOne;
        executeSkillOne = false;
        elapsedSkillOne = 0f;
        
        // Skill two
        elapsedSkillTwo = 0f;
        skillTwoRotation = 0f;
    }
  
    @Override
    public void draw(Batch batch, float alpha){
        super.draw(batch, alpha);
        if (firing) {
            firingTime += Gdx.graphics.getDeltaTime();
            if (this.getDirection() == Direction.LEFT) {
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

         if (executeDetection) {
            elapsedDetection += Gdx.graphics.getDeltaTime();
            temp = detection.getKeyFrame(elapsedDetection);
            batch.draw(detection.getKeyFrame(elapsedDetection), this.getX() - 205, this.getY() - 205, Constants.TILEDIMENSION * 5, Constants.TILEDIMENSION * 5);
            if (detection.isAnimationFinished(elapsedDetection)) {
                executeDetection = false;
                elapsedDetection = 0f;
            }
        }
        
        if (executeSkillOne) {
            elapsedSkillOne += Gdx.graphics.getDeltaTime();
            if (this.getDirection() == Direction.LEFT) {
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
    }
	
    @Override
    public void update() {
        super.update();
    }
	
    public boolean getPlayTurn() {
            return this.playTurn;
    }

    public void setPlayTurn(boolean playTurn) {
            this.playTurn = playTurn;
    }
    
    public Direction getDirection() {
        return this.direction;
    }
    
    public void setDirection(Direction direction) {
        this.direction = direction;
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
    
     public void setExecuteDetection(boolean executeDetection) {
        this.executeDetection = executeDetection;
    }

    public boolean getExecuteDetection() {
        return executeDetection;
    }

    public Rectangle2D.Double getDetectionCollisionBox() {
        return new Rectangle2D.Double(this.getCX(), this.getCY(), 2, 2);
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
}