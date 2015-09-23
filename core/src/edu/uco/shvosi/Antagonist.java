package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;

public class Antagonist extends Entity {

    private EnemyType enemyType;

    public Antagonist(Texture texture, int cX, int cY) {
        super(EntityGridCode.ENEMY, texture, cX, cY);
    }

    public EnemyType getEnemyType() {
        return this.enemyType;
    }

    public void setEnemyType(EnemyType enemyType) {
        this.enemyType = enemyType;
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

    public void moveAction() {
        MoveToAction moveAction = new MoveToAction();
        moveAction.setPosition((float) (this.getCX() * Constants.TILEDIMENSION),
                (float) (this.getCY() * Constants.TILEDIMENSION));
        moveAction.setDuration(Constants.MOVEACTIONDURATION);
        this.addAction(moveAction);
    }

    public void attackAction() {
        //Do Attack Stuffs?
    }

    public void calculateTurn(int[][] mapGrid, int[][] entityGrid) {
        //Do AI Stuff

        //If you want to move it, set turn action to move and set its dx and dy
        
        //If you want to attack, set turn action to attack and i don't know
        // what to do yet from here
    }
}
