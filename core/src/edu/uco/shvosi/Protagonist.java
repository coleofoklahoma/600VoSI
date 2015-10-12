package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import static edu.uco.shvosi.GameScreen.invent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Protagonist extends Entity implements Observable {

    ParticleEffect smokeParticle;
    private boolean playTurn;
    private Constants.Direction direction;

    // Sorry to just throw all this in here lol --Cody
    private boolean executeDetection;
    private boolean executeBarrier;
    private boolean executeSkillTwo;
    private boolean executeSkillOne;
    private boolean firing;
    private boolean heal;

    private int itemHeld = 0;
    private int shieldFlag = 0;

    private int barrierLimit = 2;
    private int barrierDamage = 0;

    private float totalTimeSinceLastAnimation;
    private float animationPlaying;

    private boolean blind = false;
    private int actionCounter = 0;

    private List<Observer> observers;
    private HashMap<String, Skill> skills;

    Sound attack = Gdx.audio.newSound(Gdx.files.internal("sounds/attack.mp3"));
    Sound skill1 = Gdx.audio.newSound(Gdx.files.internal("sounds/skill1.mp3"));
    Sound skill2 = Gdx.audio.newSound(Gdx.files.internal("sounds/skill2.mp3"));
    Sound skill3 = Gdx.audio.newSound(Gdx.files.internal("sounds/skill3.mp3"));
    Sound skill4 = Gdx.audio.newSound(Gdx.files.internal("sounds/skill4.mp3"));
    Sound shield = Gdx.audio.newSound(Gdx.files.internal("sounds/shield.mp3"));

    public HashMap<String, Skill> getSkills() {
        return skills;
    }

    public Protagonist(int cX, int cY) {
        super(Constants.EntityGridCode.PLAYER, TextureLoader.BERNARDTEXTURE, cX, cY);
        this.playTurn = true;
        this.setHealth(100);

        this.skills = new HashMap<String, Skill>();

        this.observers = new ArrayList();
        // Laser stuffs --Cody
        firing = false;

        skills.put("RedLaser", new RedLaserSkill(0, 0, TextureLoader.redLaser, 25));

        // Detection
        executeDetection = false;

        skills.put("Detection", new DetectionSkill(0, 0, TextureLoader.detectionSkill, 0));

        // Barrier
        executeBarrier = false;

        skills.put("Barrier", new BarrierSkill(0, 0, TextureLoader.barrierSkill, 0));

        // Skill one
        executeSkillOne = false;

        skills.put("SkillOne", new SkillOne(0, 0, TextureLoader.skillOne, 5));

        // Skill two
        executeSkillTwo = false;

        skills.put("SkillTwo", new SkillTwo(0, 0, TextureLoader.skillTwo, 5));

        smokeParticle = new ParticleEffect();
        smokeParticle.load(Gdx.files.internal("traps/smoke.p"), Gdx.files.internal("traps"));

        totalTimeSinceLastAnimation = 0f;
        animationPlaying = 0f;
    }

    @Override
    public void draw(Batch batch, float alpha) {
        

        totalTimeSinceLastAnimation += Gdx.graphics.getDeltaTime();

        if (totalTimeSinceLastAnimation > 1) {

            super.getTextureRegion().setRegion(TextureLoader.bernardGlance.getKeyFrame(animationPlaying));
            //super.getTextureRegion().setRegion(0, 0, 90, 90);
            animationPlaying += Gdx.graphics.getDeltaTime();
            if (TextureLoader.bernardGlance.isAnimationFinished(animationPlaying)) {
                super.setImage(TextureLoader.BERNARDTEXTURE);
                totalTimeSinceLastAnimation = 0f;
                animationPlaying = 0f;
            }
            
            batch.draw(super.getTextureRegion(), this.getX() + 12, this.getY() + 12, 78, 78);

        }
        else
        {
            batch.draw(super.getTextureRegion(), this.getX(), this.getY());
        }

        if (firing) {
            skills.get("RedLaser").draw(batch, alpha, this);
            skills.get("RedLaser").notifyObservers();
            if (skills.get("RedLaser").isAnimationFinished()) {
                firing = false;
            }
        }

        if (executeBarrier) {
            skills.get("Barrier").draw(batch, alpha, this);
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

        if (actionCounter >= 2 && blind == true) {
            smokeParticle.start();
            smokeParticle.getEmitters().first().setPosition(this.getX() + 50, this.getY() + 35);
            smokeParticle.draw(batch, Gdx.graphics.getDeltaTime());
            if (actionCounter == 17) {
                blind = false;
                actionCounter = 0;
            }
        }
    }

    @Override
    public void update() {
        switch (getTurnAction()) {
            case MOVE:
                moveAction();
                if (blind == true) {
                    actionCounter++;
                }
                break;
            case ATTACK:
                attackAction();
                if (blind == true) {
                    actionCounter++;
                }
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

    public Constants.Direction getDirection() {
        return this.direction;
    }

    public void setDirection(Constants.Direction direction) {
        this.direction = direction;
    }

    public void setFiring(boolean firing) {
        attack.play(Constants.MASTERVOLUME);
        this.firing = firing;
    }

    public void setExecuteSkillOne(boolean executeSkillOne) {
        skill1.play(Constants.MASTERVOLUME);
        this.executeSkillOne = executeSkillOne;
    }

    public void setExecuteSkillTwo(boolean executeSkillTwo) {
        skill2.play(Constants.MASTERVOLUME);
        this.executeSkillTwo = executeSkillTwo;
    }

    public void setExecuteDetection(boolean executeDetection) {
        skill3.play(Constants.MASTERVOLUME);
        this.executeDetection = executeDetection;

    }

    public boolean getExecuteDetection() {
        return executeDetection;
    }

    public void setExecuteBarrier(boolean executeBarrier) {
        skill4.play(Constants.MASTERVOLUME);
        this.executeBarrier = executeBarrier;
    }

    public boolean getExecuteBarrier() {
        return executeBarrier;
    }

    public boolean getHeal() {
        return heal;
    }

    public void setHeal(boolean heal) {
        this.heal = heal;
    }

    public boolean getBlind() {
        return blind;
    }

    public void setBlind(boolean blind) {
        this.blind = blind;
    }

    public void setBarrierLimit(int b) {
        barrierLimit = b;
    }

    public int getBarrierLimit() {
        return barrierLimit;
    }

    public void setBarrierDamage(int b) {
        barrierDamage += b;
    }

    public int getBarrierDamage() {
        return barrierDamage;
    }

    public void resetBarrierDamage() {
        barrierDamage = 0;
    }

    @Override
    public void setItemHeld(int x) {
        itemHeld = x;
    }

    @Override
    public int getItemHeld() {
        return itemHeld;
    }

    public void useItem() {
        if (itemHeld == 0) {
        } else if (itemHeld == 1) {
            shield.play(Constants.MASTERVOLUME);
            this.setImage(TextureLoader.BERNARDSHIELDTEXTURE);
            itemHeld = 0;
            invent.setImage(TextureLoader.INVENTORYTEXTURE);
            shieldFlag = 1;
        }
    }

    public int getShieldFlag() {
        return shieldFlag;
    }

    public void setShieldFlag(int x) {
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

    @Override
    public void observerUpdate(Object o) {
        Gdx.app.log("ObserverUpdate", "Reached");

        Rectangle.tmp.set(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        if (Intersector.overlaps(((Antagonist) o).getBoundingBox(), Rectangle.tmp)) {
            this.setHealth(this.getHealth() - ((Antagonist) o).getDamage());
        }
    }
}
