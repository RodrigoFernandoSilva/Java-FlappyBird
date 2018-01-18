
package jplay;

import java.util.ArrayList;

/**
 *
 * @author Leandro Emiliano Guimarães
 * --UFF---
 * Computer Science
 */
public class ParallaxLayers extends GameImage{

   
    private double velX=0;
    private double velY=0;
/**
 * Constructor of the class. .
 * @param background - path of the file
 */
    public ParallaxLayers(String background){
        super(background);



    }
    /**
     * Sets velocity.
     * @param vel_X - set the velocity of the layer in the x-axis.
     * @param vel_Y - set the velocity of the layer in the y-axis.
     */
    public void setVel(double vel_X, double vel_Y){

        this.velX=vel_X;
        this.velY=vel_Y;
       
    }
    /**
     * Sets velocity in the x-axis.
     * @param vel_X -  set the velocity of the layer in the x-axis.
     */
    public void setVelX(double vel_X){
        this.velX=vel_X;
    }
    /**
     * Sets velocity in the y-axis.
     * @param vel_Y  - set the velocity of the layer in the y-axis.
     */
    public void setVelY(double vel_Y){
        this.velY=vel_Y;
    }

/**
 * Returns the velocity in the x-axis.
 * @return double
 */
    public double getVelX(){
        return velX;
    }
    /**
     * Returns the velocity in the y-axis
     * @return double
     */
    public double getVelY(){
        return velY;
    }
    /**
     * Move the layer in the x-axis
     * @param left - 'true' to move the layer to the left, 'false' to move the layer to the right.
     */
    public void moveLayerX(boolean left){
        if(left)
            this.x-=velX;
        else
            this.x+=velX;
    }
    /**
     * Move the layer in the y-axis
     * @param up - 'true' to move the layer up, 'false' to move the layer down.
     */
    public void moveLayerY(boolean up){
        if(up)
            this.y-=velY;
        else
            this.y+=velY;
    }

}
