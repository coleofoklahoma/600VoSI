package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Entity extends Image {

    private int cX; // Coordinate X
    private int cY; // Coordinate Y
    private int dCX; // Destination Coordinate X
    private int dCY; // Destination Coordinate Y
    private int entityType; // EntityGridCode
    private int health;
    private TextureRegion textureRegion;

    public Entity(int entityType, Texture texture, int cX, int cY) {
        this.cX = cX;
        this.cY = cY;
        this.dCX = this.cX;
        this.dCY = this.cY;
        this.setX(this.cX * Constants.TILEDIMENSION);
        this.setY(this.cY * Constants.TILEDIMENSION);
        setBounds(getX(),getY(),texture.getWidth(),texture.getHeight());
        this.entityType = entityType;
        this.health = 1;
        textureRegion = new TextureRegion(texture);
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

    public int getDCX() {
        return this.dCX;
    }

    public int getDCY() {
        return this.dCY;
    }

    public void setDCX(int dCX) {
        this.dCX = dCX;
    }

    public void setDCY(int dCY) {
        this.dCY = dCY;
    }
	
    public int getEntityType(){
        return this.entityType;
    }
    
    public int getHealth(){
        return this.health;
    }
    
    public void setHealth(int health){
        this.health = health;
    }
    
    public void flipTexture(Direction direction){
        textureRegion.flip(true, false);
//        switch(direction){
//            case LEFT:
//            case RIGHT:
//                textureRegion.flip(true, false);
//                break;
//            default:
//                //Do nothing
//                break;
//        }
    }
	
    public void update() {
        MoveToAction moveAction = new MoveToAction();
        moveAction.setPosition((float)(this.getCX() * Constants.TILEDIMENSION),
                               (float)(this.getCY() * Constants.TILEDIMENSION));
        moveAction.setDuration(Constants.MOVEACTIONDURATION);
        this.addAction(moveAction);
    }
    
    public void collision(Entity entity) {
        // Manipulate the entity collided with
    }
	
    @Override
    public void draw(Batch batch, float alpha){
        batch.draw(textureRegion,this.getX(),this.getY());
    }
}