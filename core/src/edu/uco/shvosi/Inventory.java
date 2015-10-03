package edu.uco.shvosi;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;


public class Inventory extends Entity{
    private Constants.Direction direction;


    public Inventory(Texture texture, int X, int Y) {
        super(Constants.EntityGridCode.NONE, texture, X, Y);
    }

    @Override
    public void draw(Batch batch, float alpha) {
        super.draw(batch, alpha);
        }

    @Override
    public void update() {
        switch (getTurnAction()) {
            case MOVE:
                break;
            default:
                //Do Nothing
                break;
        }
    }

    public Constants.Direction getDirection() {
        return this.direction;
    }
    
    public void setDirection(Constants.Direction direction) {
        this.direction = direction;
    }

    public void moveAction() {
        MoveToAction moveAction = new MoveToAction();
        moveAction.setPosition((float) (this.getX() * Constants.TILEDIMENSION),
                (float) (this.getY() * Constants.TILEDIMENSION));
        moveAction.setDuration(Constants.MOVEACTIONDURATION);
        this.addAction(moveAction);
    }

}
