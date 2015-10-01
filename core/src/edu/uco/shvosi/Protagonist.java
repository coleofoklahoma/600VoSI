package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import static edu.uco.shvosi.GameScreen.invent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Protagonist extends Entity implements Observable {

    private boolean playTurn;
    private Direction direction;

    // Sorry to just throw all this in here lol --Cody
    private boolean executeDetection;
    private boolean executeSkillTwo;
    private boolean executeSkillOne;
    private boolean firing;
    
    private int itemHeld = 0;
    private int shieldFlag = 0;
    
    
    private List<Observer> observers;
    private HashMap<String, Skill> skills;

    public HashMap<String, Skill> getSkills() {
        return skills;
    }

    public Protagonist(Texture texture, int cX, int cY) {
        super(EntityGridCode.PLAYER, texture, cX, cY);
        this.playTurn = false;
        this.setHealth(100);

        this.skills = new HashMap<String, Skill>();

        this.observers = new ArrayList();
        // Laser stuffs --Cody
        firing = false;

        skills.put("RedLaser", new RedLaserSkill(0, 0, TextureLoader.redLaser, 25));

        // Detection
        executeDetection = false;

        skills.put("Detection", new DetectionSkill(0, 0, TextureLoader.detectionSkill, 0));

        // Skill one
        executeSkillOne = false;

        skills.put("SkillOne", new SkillOne(0, 0, TextureLoader.skillOne, 5));

        // Skill two
        executeSkillTwo = false;

        skills.put("SkillTwo", new SkillTwo(0, 0, TextureLoader.skillTwo, 5));
    }

    @Override
    public void draw(Batch batch, float alpha) {
        super.draw(batch, alpha);
        if (firing) {
            skills.get("RedLaser").draw(batch, alpha, this);
            skills.get("RedLaser").notifyObservers();
            if (skills.get("RedLaser").isAnimationFinished()) {
                firing = false;
            }
        }

        if (executeDetection) {
            skills.get("Detection").draw(batch, alpha, this);
            if (skills.get("Detection").isAnimationFinished()) {
                executeDetection = false;
            }
        }

        if (executeSkillOne) {
            skills.get("SkillOne").draw(batch, alpha, this);
            skills.get("SkillOne").notifyObservers();
            if (skills.get("SkillOne").isAnimationFinished()) {
                executeSkillOne = false;
            }
        }

        if (executeSkillTwo) {
            skills.get("SkillTwo").draw(batch, alpha, this);
            skills.get("SkillTwo").notifyObservers();
            if (skills.get("SkillTwo").isAnimationFinished()) {
                executeSkillTwo = false;
            }
        }
    }

    @Override
    public void update() {
        switch (getTurnAction()) {
            case MOVE:
                moveAction();
                break;
            case ATTACK:
                attackAction();
                break;
            default:
                //Do Nothing
                break;
        }
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
    
    @Override
    public void setItemHeld(int x){
        itemHeld = x;
    } 
    
     public void useItem() {
        if (itemHeld== 0){
        }
        else if (itemHeld == 1){
            this.setImage(TextureLoader.BERNARDSHIELDTEXTURE);
            itemHeld = 0;
            invent.setImage(TextureLoader.INVENTORYTEXTURE);
            shieldFlag = 1;
        }
    }

    public int getShieldFlag(){
        return shieldFlag;
    } 
    
    public void setShieldFlag(int x){
        shieldFlag = x;
    } 
     
    public Rectangle2D.Double getDetectionCollisionBox() {
        return new Rectangle2D.Double(this.getCX(), this.getCY(), 2, 2);
    }

    public void notifyObservers() {
        for (Observer o : observers) {
            o.observerUpdate(this);
        }
    }

    public void addObserver(Observer o) {
        this.observers.add(o);
    }

    public void removeObserver(Observer o) {
        this.observers.remove(o);
    }
    
    public void removeAllObservers() {
        this.observers.clear();
    }

    public void moveAction() {
        MoveToAction moveAction = new MoveToAction();
        moveAction.setPosition((float) (this.getCX() * Constants.TILEDIMENSION),
                (float) (this.getCY() * Constants.TILEDIMENSION));
        moveAction.setDuration(Constants.MOVEACTIONDURATION);
        this.addAction(moveAction);
    }

    public void attackAction() {
        //Do Stuffs
    }
}
