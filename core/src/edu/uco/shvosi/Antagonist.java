package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import static com.badlogic.gdx.math.Rectangle.tmp;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import java.util.List;

public class Antagonist extends Entity implements Observer {
    private Constants.EnemyType enemyType;

    public Antagonist(Texture texture, int cX, int cY) {
        super(Constants.EntityGridCode.ENEMY, texture, cX, cY);
    }

    public Constants.EnemyType getEnemyType() {
        return this.enemyType;
    }

    public void setEnemyType(Constants.EnemyType enemyType) {
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

    public void calculateTurn(Constants.MapGridCode[][] mapGrid, Constants.EntityGridCode[][] entityGrid, List<Entity> entityList) {
        //Do AI Stuff

        //If you want to move it, set turn action to move and set its dx and dy
        //If you want to attack, set turn action to attack and i don't know
        // what to do yet from here
    }

    @Override
    public void observerUpdate(Object o) {
       Gdx.app.log("ObserverUpdate", "Reached");
       
       
       Rectangle.tmp.set(this.getX(), this.getY(), this.getWidth(), this.getHeight());
       Gdx.app.log("tmp", Rectangle.tmp.toString());
       
       Gdx.app.log("Skill box", ((Skill) o).getBoundingBox().toString());
       if(Intersector.overlaps(((Skill) o).getBoundingBox(), Rectangle.tmp)) {
           this.setHealth(this.getHealth() - ((Skill) o).getDamage());
       }
    }
}
