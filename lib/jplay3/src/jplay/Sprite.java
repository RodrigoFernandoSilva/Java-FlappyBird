/*
 * The use this code commercially must only be done with permission of the author.
 * Any modification shall be advised and sent to the author.
 * The author is not responsible for any problem therefrom the use of this frameWork.
 *
 * @author Gefersom Cardoso Lima
 * Federal Fluminense University
 * Computer Science
 */

package jplay;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

/**
 * Class responsible for controlling all actions and behaviors of sprite.
 */

public class Sprite extends Animation
{
    private double mass=0.5;
    private double friction=0.5;
    private double restitution=0.5;
    private float forceX =0;
    private float forceY=0;

    private Body body=null; // It's used in Physics

    private double jumpVelocity = 5.3;//It's used for the jump,
    private double velocityY = 0;
    private double gravity = 0.098;
    private boolean onFloor = false;
    private int floor;

    /**
     * Constructor of the class. Creates a class with one frame.
     * @param fileName Path of the file.
     */
    public Sprite(String fileName)
    {
            this(fileName, 1);
    }

    /**
     * Constructor of the class.
     * @param fileName Path of the file.
     * @param numFrames number of frames.
     */
    public Sprite(String fileName, int numFrames)
    {
            super(fileName, numFrames);
            velocityY = 0;
    }

    /**
     * Method used to move the sprite by the x-axis. The keys used to move the
     * sprite are LEFT_KEY and RIGHT_KEY.
     * @param velocity speed of locomotion in pixels.
     */


    public void moveX(double velocity)
    {
            moveX(Keyboard.LEFT_KEY, Keyboard.RIGHT_KEY, velocity);
    }

    /**
     * Method used to move the sprite by the x-axis.
     * @param leftKey Key used to move the sprite for the left.
     * @param rightKey Key used to move the sprite for the right.
     * @param velocity speed of locomotion in pixels.
     */
    public void moveX(int leftKey, int rightKey, double velocity)
    {
            Keyboard keyboard = Window.getInstance().getKeyboard();
            if (keyboard.keyDown(leftKey)&& this.x > 1)
            {
                this.x -= velocity;
            }

            if (keyboard.keyDown(rightKey) && this.x + this.width < Window.getInstance().getWidth())
            {
                this.x += velocity;
            }
    }
    /**
     * Method used to move the sprite by y-axis. The keys used to move the
     * sprite are UP_KEY and DOWN_KEY.
     * @param velocity speed of locomotion in pixels.
     */
    public void moveY(double velocity)
    {
        this.moveY(Keyboard.UP_KEY, Keyboard.DOWN_KEY, velocity);
    }

    /**
     * Method used to move the sprite by y-axis.
     * @param upKey Key used to move the sprite for the left.
     * @param downKey Key used to move the sprite for the right.
     * @param velocity speed of locomotion in pixels.
     */
    public void moveY(int upKey, int downKey, double velocity)
    {
            Keyboard keyboard = Window.getInstance().getKeyboard();
            if (keyboard.keyDown(upKey) && this.y > 1)
            {
                this.y -= velocity;
            }

            if (keyboard.keyDown(downKey) && this.y + this.height < Window.getInstance().getHeight())
            {
               this.y += velocity;
            }
    }
    /**
     * applies a force to the left using the keyboard
     * @param leftKey
     * @param velocity
     * @param behaviorKeyboard
     */
    public void applyForceXFromKeyboardLeft(int leftKey, double velocity,int behaviorKeyboard){

        Keyboard  keyboard = Window.getInstance().getKeyboard();

        double px = Physics.meterToPixels_X_Axis(body.getPosition().x);
        float velMeter = -(float) (velocity*3 + 10);

        if(keyboard.keyDown(leftKey))
            this.body.applyForce(new Vec2(velMeter,0), new Vec2(body.getPosition().x,body.getPosition().y));

    }
    /**
     * applies a force to the right using the keyboard
     * @param rightKey
     * @param velocity
     * @param behaviorKeyboard
     */
    public void applyForceXFromKeyboardRight(int rightKey, double velocity,int behaviorKeyboard){

        Keyboard  keyboard = Window.getInstance().getKeyboard();

        double px = Physics.meterToPixels_X_Axis(body.getPosition().x);
        float velMeter = -(float) (velocity*3 + 10);

        if(keyboard.keyDown(rightKey))
            this.body.applyForce(new Vec2(- velMeter,0), new Vec2(body.getPosition().x,body.getPosition().y));
    }
/**
 * applies a force to the left or to the right using the keyboard
 * @param leftKey
 * @param rightKey
 * @param velocity
 * @param behaviorKeyboard
 * @param boundsScreen sets the limits in which  the body can move.(the bounds are between 0 and window width)
 */
    public void applyForceXFromKeyboard(int leftKey, int rightKey,double velocity,int behaviorKeyboard, boolean boundsScreen){

        Keyboard  keyboard = Window.getInstance().getKeyboard();

        double px = Physics.meterToPixels_X_Axis(body.getPosition().x);
        float velMeter = -(float) (velocity*3 + 10);

        if(boundsScreen){
            if(keyboard.keyDown(leftKey) && px - this.width/2>0)

                this.body.applyForce(new Vec2(velMeter,0), new Vec2(body.getPosition().x,body.getPosition().y));

            else if(px-this.width/2<0){
                this.cancelForces();
                this.setX(0);
            }

            if(keyboard.keyDown(rightKey) && px + this.width/2 < Window.getInstance().getWidth())

               this.body.applyForce(new Vec2(- velMeter,0), new Vec2(body.getPosition().x,body.getPosition().y));

            else if (px + this.width/2 > Window.getInstance().getWidth()){
                this.cancelForces();
                this.setX(Window.getInstance().getWidth()-this.width);
            }

        }
        else{
            if(keyboard.keyDown(leftKey))

                this.body.applyForce(new Vec2(velMeter,0), new Vec2(body.getPosition().x,body.getPosition().y));

            else if(px-this.width/2<0)

                this.setX(0);

            if(keyboard.keyDown(rightKey) )

               this.body.applyForce(new Vec2(- velMeter,0), new Vec2(body.getPosition().x,body.getPosition().y));

            else if (px + this.width/2 > Window.getInstance().getWidth())

                this.setX(Window.getInstance().getWidth()-this.width);


        }
    }
    /**
     * applying an upward force by using the keyboard
     * @param upKey
     * @param downKey
     * @param velocity
     * @param behaviorKeyboard
     */
    public void applyForceYFromKeyboardUp(int upKey, int downKey,double velocity,int behaviorKeyboard){

        Keyboard keyboard = Window.getInstance().getKeyboard();

        double py = Physics.meterToPixels_Y_Axis(body.getPosition().y);
        float velMeter =  (float) ((velocity) + (this.body.getMass() * Physics.gravity)) ;

         if(keyboard.keyDown(upKey))
            this.body.applyForce(new Vec2(0,  velMeter), new Vec2(body.getPosition().x,body.getPosition().y));
    }
    /**
     * applying a down force by using the keyboard
     * @param upKey
     * @param downKey
     * @param velocity
     * @param behaviorKeyboard
     */
     public void applyForceYFromKeyboardDown(int upKey, int downKey,double velocity,int behaviorKeyboard){

        Keyboard keyboard = Window.getInstance().getKeyboard();

        double py = Physics.meterToPixels_Y_Axis(body.getPosition().y);
        float velMeter =  (float) ((velocity) + (this.body.getMass() * Physics.gravity)) ;

        if(keyboard.keyDown(downKey))

            this.body.applyForce(new Vec2(0,-velMeter), new Vec2(body.getPosition().x,body.getPosition().y));
    }
     /**
      * applies a force up or down using the keyboard
      * @param upKey
      * @param downKey
      * @param velocity
      * @param behaviorKeyboard
      * @param boundsScreen sets the limits in which  the body can move.(the bounds are between 0 and window height)
      */
    public void applyForceYFromKeyboard(int upKey, int downKey,double velocity,int behaviorKeyboard, boolean boundsScreen){

        Keyboard keyboard = Window.getInstance().getKeyboard();

        double py = Physics.meterToPixels_Y_Axis(body.getPosition().y);
        float velMeter =  (float) ((velocity) + (this.body.getMass() * Physics.gravity)) ;

        if(boundsScreen){
            if(keyboard.keyDown(upKey) && py - this.height/2 > 0)

                this.body.applyForce(new Vec2(0,  velMeter), new Vec2(body.getPosition().x,body.getPosition().y));

            else if(py-this.height/2<0){

                this.cancelForces();
                this.setY(0);
            }

            if(keyboard.keyDown(downKey) && py + this.height/2 < Window.getInstance().getHeight())

                this.body.applyForce(new Vec2(0,-velMeter), new Vec2(body.getPosition().x,body.getPosition().y));

            else if(py+this.height/2> Window.getInstance().getHeight()){
                this.cancelForces();
                this.setY(Window.getInstance().getHeight()-this.height);
            }
        }
        else{
             if(keyboard.keyDown(upKey) )

                this.body.applyForce(new Vec2(0,  velMeter), new Vec2(body.getPosition().x,body.getPosition().y));

            if(keyboard.keyDown(downKey) )

                this.body.applyForce(new Vec2(0,-velMeter), new Vec2(body.getPosition().x,body.getPosition().y));
        }
    }

    /**
     * Moves the sprite from a point to another.
     * @param x Target point on the x axis.
     * @param y Target point on the y axis.
     * @param velocity speed of locomotion in pixels.
    */
    public void moveTo(double x, double y, double velocity)
    {
            if (this.x < x && (this.x + this.width < Window.getInstance().getWidth()) )
            {
                this.x += velocity;
            }
            else
            {
                if (this.x > x)
                    this.x -= velocity;
            }

            if (this.y > y)
            {
                this.y -= velocity;
            }
            else
            {
                if (this.y < y)
                    this.y += velocity;
            }
    }

    /**
     * Sets the floor that will be used for the methods jump or fall.
     * @param floor Value of the coordinate x.
     */
    public void setFloor(int floor)
    {
            this.floor = floor;
    }

    /**
     * Return the value the current floor.
     * @return int
     */
    public int getFloor()
    {
            return floor;
    }

    /**
     * Makes the sprite jump.
     * @param jumpKey key used to start the jump.
     */
    public void jump(int jumpKey)
    {
            Keyboard keyboard = Window.getInstance().getKeyboard();
            if (keyboard.keyDown(jumpKey) && onFloor == true)
            {
                onFloor = false;
                velocityY = -jumpVelocity;
            }

            velocityY += gravity;
            this.y += velocityY;

            if ( this.y + this.height - floor > 0.0001 )
            {
                velocityY = 0;
                this.y = floor - this.height;
                onFloor = true;
            }
    }

    /**
     * Makes the sprite jump.
     * The key used is SPACE_KEY (SPACE_BAR).
     */
    public void jump()
    {
            jump(Keyboard.SPACE_KEY);
    }

    /**
     * Returns true if the sprite is executing the jump, false otherwise.
     * return boolean
     */
    public boolean isJumping()
    {
            return !onFloor;
    }

    /**
     * This method simulates the gravity.
     * It's necessary sets the floor before call this method.
     */
    public void fall()
    {
            if ( floor - this.y - this.height < 1 )
            {
                velocityY = 0;
                this.y = floor - this.height;
            }
            else
                velocityY += gravity;

            this.y += velocityY;
    }

    /**
     * Returns true if the sprite is on the floor, false otherwise.
     * @return boolean
     */
    public boolean isOnFloor()
    {
             return (this.y + this.height) >= floor;
    }

    /**
     * Sets the jump velocity for the sprite.
     * This velocity is used to control the height of jump.
     * @param velocity speed of locomotion in pixels.
     */
    public void setJumpVelocity(double velocity)
    {
            this.jumpVelocity = velocity;
    }

    /**
     * Returns the velocity of jump.
     * @return double
     */
    public double getJumpVelocity()
    {
            return this.jumpVelocity;
    }

    /**
     * Returns the moving speed in the Y axis.
     * @return double
     */
    public void setVelocityY(double velocityY)
    {
            this.velocityY = velocityY;
    }

    /**
     * Returns the moving speed in the Y axis.
     * @return double
     */
    public double getVelocityY()
    {
            return this.velocityY;
    }

    /**
     * Returns the value used for gravity.
     * @return double
     */
    public double getGravity()
    {
            return this.gravity;
    }

    /**
     * Sets the value for gravity.
     * @param gravity
     */
    public void setGravity(double gravity)
    {
            this.gravity = gravity;
    }
/**
 * Sets the value for restitution.
 * @param restitution
 */
    public void setRestitution(double restitution) {
        this.restitution = restitution;
    }
/**
 * Returns the value used for restitution.
 * @return double
 */
     public double getRestitution() {
        return restitution;
    }
/**
 * Sets the value for rotation.
 * @param rotation
 */
    public void setRotation(double rotation) {
        this.rotation = -rotation;
    }
/**
 * Returns the value used for rotation
 * @return double
 */
    public double getRotation() {
        return rotation;
    }
/**
 * Sets the value for friction.
 * @param friction
 */
    public void setFriction(double friction) {
        this.friction = friction;
    }
/**
 * Returns the value used for friction.
 * @return double
 */
    public double getFriction() {
        return friction;
    }
/**
 * Sets the value for mass.
 * @param mass
 */
     public void setMass(double mass) {
        this.mass = mass;
    }
/**
 * Returns the value used for mass.
 * @return double
 */
    public double getMass() {
        return mass;
    }

/**
 * Sets body.
 * @param b
 */
    public void setBody(Body b){

        body=b;
    }
/**
 * Returns body
 * @return Body
 */
    public Body getBody(){

        return body;

    }
//    private void setX(double x,float vel){
//
//         float newX = (float) Physics.pixelsToMeterX(x+this.width/2);
//         float newY = (float) Physics.pixelsToMeterY(y+this.height/2);
//
//         Vec2 vec = new Vec2(newX+vel,newY);
//         body.setXForm(vec, (float)this.rotation);
//         this.x=x;
//    }
//    private void setY(double y,float vel){
//
//        float newX = (float) Physics.pixelsToMeterX(x+this.width/2);
//        float newY = (float) Physics.pixelsToMeterY(y+this.height/2);
//
//        Vec2 vec = new Vec2(newX,newY+vel);
//        body.setXForm(vec, (float)this.rotation);
//        this.y=y;
//    }
//    private void setXY(double x, double y, float velX, float velY){
//
//        float newX = (float) Physics.pixelsToMeterX(x+this.width/2);
//        float newY = (float) Physics.pixelsToMeterY(y+this.height/2);
//
//         Vec2 vec = new Vec2(newX+velX,newY+velY);
//         body.setXForm(vec, (float)this.rotation);
//         this.x=x;
//         this.y=y;
//
//    }
/**
 * Sets the position in the x-axis
 * @param x
 */
    public void setX(double x){
        if(body == null)

            this.x=x;

        else{

            float newX = (float) Physics.pixelsToMeterX(x+this.width/2); // centro do sprite(this.width/2)
            float newY = (float) Physics.pixelsToMeterY(y+this.height/2);

            Vec2 vec = new Vec2(newX,newY);
            body.setXForm(vec, (float)this.rotation);
            this.x=x;

        }
    }
/**
 * Retuns the position in the x-axis
 * @return double
 */
    public double getX(){
        return this.x;
    }
/**
 * Sets the position in the y-axis
 * @param y
 */
    public void setY(double y){
        if(body==null)

            this.y=y;

        else{

            float newX = (float) Physics.pixelsToMeterX(x+this.width/2);
            float newY = (float) Physics.pixelsToMeterY(y+this.height/2);

            Vec2 vec = new Vec2(newX,newY);
            body.setXForm(vec, (float)this.rotation);
            this.y=y;

        }
    }
/**
 * Returns the position in the y-axis.
 * @return double
 */
    public double getY(){

        return this.y;
    }

/**
 * Sets all the attributes.
 * @param mass
 * @param friction
 * @param restituion
 * @param rotation
 */
    public void setAllAttributes(double mass,double friction,double restituion,double rotation){

        this.mass=mass;
        this.friction=friction;
        this.restitution=restituion;
        this.rotation=rotation;

    }
/**
 * Applies a force on the body in the x-axis
 * @param fx
 */
    public void applyForceX(double fx){
        float fx_meter = (float) (fx);

        this.body.applyForce(new Vec2((float) fx_meter,0), new Vec2(body.getPosition().x,body.getPosition().y));

    }
    /**
     * applies a force on the body in the y-axis
     * @param fy
     */
    public void applyForceY(double fy){

        float fy_meter = (float) (fy);

        this.body.applyForce(new Vec2(0,(float) fy_meter), new Vec2(body.getPosition().x,body.getPosition().y));

    }
    /**
     * Returns the value of the force applied to the x-axis
     * @return float
     */
    public float getForceX(){
        return body.getLinearVelocity().x;
    }
    /**
     * Returns the value of the force applied to the y-axis
     * @return float
     */
    public float getForceY(){
        return body.getLinearVelocity().y;
    }
    /**
     * Cancels all forces applied to the body
     */
    public void cancelForces(){
        body.setLinearVelocity(new Vec2(0,0));


    }
    /**
     * Cancels all forces applied to the body and sets limits on x-axis in which the body can move
     * @param value
     * @param lowerBoundX
     */
    public void cancelForcesAndSetBoundsX(double value, boolean lowerBoundX){
        cancelForces();
        if(lowerBoundX){
            if(this.getX()<value)
                this.setX(value);
        }
        else
            if(this.getX()+ this.width>value)
                this.setX(value-this.width);



    }
    /**
     * Cancels all forces applied to the body and sets limits on y-axis in which the body can move
     * @param value
     * @param lowerBoundY
     */
     public void cancelForcesAndSetBoundsY(double value, boolean lowerBoundY){
        cancelForces();
        if(lowerBoundY){
            if(this.getY()<value)
                this.setY(value);

        }
        else
            if(this.getY()+this.height>value)
                this.setY(value-this.height);
    }
     /**
      * Sets the sprite like a bullet to prevent it being ignored collisions with other sprites.
      * @param value
      */
     public void setBullet(boolean value){
         if(value)
             this.body.setBullet(true);

     }
     /**
      * Returns 'true' if the sprite has defined behavior as a bullet or 'false' otherwise.
      * @return boolean
      */
     public boolean isBullet(){
         return body.isBullet();
     }

}
