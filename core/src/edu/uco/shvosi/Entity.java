package edu.uco.shvosi;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Entity extends Image implements Observer{

    private int cX; // Coordinate X
    private int cY; // Coordinate Y
    private int dCX; // Destination Coordinate X
    private int dCY; // Destination Coordinate Y
    private Constants.EntityGridCode gridCode; //NONE if not on grid, has type otherwise
    private int health;
    private TextureRegion textureRegion;

    public TextureRegion getTextureRegion() {
        return textureRegion;
    }
    private Constants.TurnAction turnAction;

    public Entity(Constants.EntityGridCode gridCode, Texture texture, int cX, int cY) {
        this.cX = cX;
        this.cY = cY;
        this.dCX = this.cX;
        this.dCY = this.cY;
        this.setX(this.cX * Constants.TILEDIMENSION);
        this.setY(this.cY * Constants.TILEDIMENSION);
        setBounds(getX(),getY(),texture.getWidth(),texture.getHeight());
        this.gridCode = gridCode;
        this.health = 1;
        textureRegion = new TextureRegion(texture);
        this.turnAction = Constants.TurnAction.NONE;
        
        if(texture.getTextureData().getFormat() == Pixmap.Format.Alpha) {
            texture.dispose();
        }
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
	
    public Constants.EntityGridCode getGridCode(){
        return this.gridCode;
    }
    
    public int getHealth(){
        return this.health;
    }
    
    public void setHealth(int health){
        this.health = health;
    }
    
    public void setImage(Texture texture){
        textureRegion = new TextureRegion(texture);
    }
    public Constants.TurnAction getTurnAction(){
		return this.turnAction;
	}
	
    public void setTurnAction(Constants.TurnAction turnAction){
            this.turnAction = turnAction;
    }
    
    public void flipTexture(Constants.Direction direction){
        switch(direction){
            case LEFT:
                if(!textureRegion.isFlipX()) {
                    textureRegion.flip(true, false);
                }
                break;
            case RIGHT:
                if(textureRegion.isFlipX()) {
                    textureRegion.flip(true, false);
                }
                break;
            default:
                //Do nothing
                break;
       }
    }
	
    public void update() {
    }
    
    public void collision(Entity entity) {
        // Manipulate the entity collided with
    }
	
    @Override
    public void draw(Batch batch, float alpha){
        
        batch.draw(textureRegion,this.getX(),this.getY());
    }

    @Override
    public void observerUpdate(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void setItemHeld(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    int getItemHeld() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}