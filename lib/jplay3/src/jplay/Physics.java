
package jplay;

import java.util.ArrayList;
import org.jbox2d.collision.AABB;
import org.jbox2d.collision.CircleDef;
import org.jbox2d.collision.PolygonDef;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;


/**
 *
 * @author Leandro Emiliano Guimarães
 * ---UFF---
 * Computer Science
 */
public class Physics {

   public static double PIXELS_PER_METER = 30;

   private static double WIN_WIDTH_PIXELS =0;
   private static double WIN_HEIGHT_PIXELS =0;

   private static float timeStep = 1f/60f;
   protected static float gravity= -9.8f; // Negative value implies downward gravitational force.
   private static float wind=0f;

   World world;


   ArrayList<Sprite> sprs = new ArrayList<Sprite>();


       /** The method below takes care of creating the world in which all objects will be simulated.
        * @param winWidth  window width (Window Class)
        * @param winHeight window height (Window Class)
        * lowerbound and upperbound are respectively the lower and upper bounds related to the space corresponding to the physical world.
        * wind - adds a horizontal gravity can be used as wind.
        */
    public void createWorld(double winWidth,double winHeight,float lowerBoundX, float lowerBoundY,
                            float upperBoundX,float upperBoundY, float wind , float gravity){

        this.setWindowWidth(winWidth);
        this.setWindowHeight(winHeight);

        AABB worldAABB = new AABB ();

        worldAABB.lowerBound.set((float)pixelsToMeterX(lowerBoundX),(float)pixelsToMeterY(lowerBoundY));
        worldAABB.upperBound.set((float)pixelsToMeterX(upperBoundX),(float)pixelsToMeterY(upperBoundY));

        Vec2 gravityOn = new Vec2(wind,gravity);

        Boolean doSleep = true ;

        World w = new World(worldAABB,gravityOn,doSleep);

        this.setWorld(w);
    }
     /**
     * Overloading of the public void createWorld(double winWidth,double winHeight,
      *                                           float lowerBoundX, float lowerBoundY,
                                                  float upperBoundX,float upperBoundY,
      *                                           float wind , float gravity){
     * The parameters 'float lowerBoundX, float lowerBoundY , float upperBoundX and float upperBoundY '
     * have respectively -900f,900f,900f,-900f.
     * The parameter 'float wind' is equal to 0.
     * The parameter 'float gravity' is equal to -9.8f
     * (the value is negative but the gravity is down).
     */
    public void createWorld(double winWidth, double winHeight){

        this.createWorld(winWidth, winHeight, -90000f, (float)winHeight+90000, (float)winWidth+90000, -90000f, 0f,  gravity);

    }

    /**
     * This method is responsible for creating a body of the sprite.
     * @param spr - Path of the file
     * @param isStatic - 'true' for static bodies and 'false' for dynamic objects
     */
    public void createBodyFromSprite(Sprite spr, boolean isStatic){

        PolygonDef pd = new PolygonDef();

        //pd.setAsBox((float)pixelsToMeterX(spr.width/2), (float)pixelsToMeterY(spr.height/2));
        pd.setAsBox((float)((spr.width/2)/PIXELS_PER_METER), (float)((spr.height/2)/PIXELS_PER_METER));

         // setAsBox - os parâmetros passados são atribuídos com o dobro do valor.

        BodyDef bd = new BodyDef();

        bd.position.set((float)pixelsToMeterX((spr.x+(spr.width/2))), (float) pixelsToMeterY((spr.y+(spr.height/2))));

        /*body.position.set atribui esse valor ao centro do sprite, por isso deve ser somado a metade da largura do sprite.
         * O mesmo acontece para a altura
         */

        Body body = new Body(bd,world);

        pd.density= (float) spr.getMass();
        pd.friction=(float) spr.getFriction();
        pd.restitution=(float) spr.getRestitution();
        bd.angle=  (float) spr.getRotation();


        body= world.createBody(bd);
        body.createShape(pd);

        if(!isStatic)
            body.setMassFromShapes();

        spr.setBody(body);
        sprs.add(spr);  // adiciona na lista



    }
//    public void createSphereBodyFromSprite(Sprite spr, boolean isStatic){
//
//        CircleDef cd = new CircleDef();
//
//        cd.radius=(float) ((spr.width/ 2)/PIXELS_PER_METER);
//        cd.localPosition.set((float) pixelsToMeterX(spr.x+(spr.width/2)),(float) pixelsToMeterY((spr.y+(spr.height/2))) );
//
//        BodyDef bd = new BodyDef();
//
//        Body body = new Body(bd,world);
//
//        cd.density= (float) spr.getMass();
//        cd.friction=(float) spr.getFriction();
//        cd.restitution=(float) spr.getRestitution();
//        bd.angle=  (float) spr.getRotation();
//
//        body= world.createBody(bd);
//        body.createShape(cd);
//
//        if(!isStatic)
//            body.setMassFromShapes();
//
//        spr.setBody(body);
//        sprs.add(spr);
//
//    }
//    public void checkPositionBodyWithSprite(){
//
//        for (int i = 0; i < sprs.size(); i++) {
//
//            if(sprs.get(i).x +sprs.get(i).width/2 != meterToPixels_X_Axis(sprs.get(i).getBody().getPosition().x)){
//
//                sprs.get(i).getBody().getPosition().x= (float)pixelsToMeterX(sprs.get(i).x +sprs.get(i).width/2);
//            }
//
//            if(sprs.get(i).y + sprs.get(i).height/2 != meterToPixels_Y_Axis(sprs.get(i).getBody().getPosition().y)){
//
//                sprs.get(i).getBody().getPosition().y=(float)pixelsToMeterY(sprs.get(i).y + sprs.get(i).height/2);
//            }
//        }
//    }

    /**
     * Method responsible for updating all physical laws applied to the bodies
     */
    public void update(){

        world.step(timeStep, 500);

        //this.checkPositionBodyWithSprite();

        for (int i = 0; i < sprs.size(); i++) {

            sprs.get(i).x = meterToPixels_X_Axis(sprs.get(i).getBody().getPosition().x) - (sprs.get(i).width/2);
            sprs.get(i).y= meterToPixels_Y_Axis(sprs.get(i).getBody().getPosition().y) - (sprs.get(i).height/2);

            sprs.get(i).rotation = sprs.get(i).getBody().getAngle();

            //sprs.get(i).draw();
            //sprs.get(i).update();



        }
    }
/**
 *
 * @param g - set the gravity value
 */
   public void setGravity(float g){
       gravity= - g;
   }
   /**
    * Returns the gravity value
    * @return float
    */
   public float getGravity(){
       return -gravity;
   }
   /**
    *
    * @param w - set the wind value
    */
   public void setWind(float w){
       wind = w;
   }
   /**
    * Returns the wind value
    * @return float
    */
   public float getWind(){
       return wind;
   }

   private void setWorld(World w){
       world=w;
   }
   /**
    * Set the window width
    * @param width - window width (Window Class).
    */
   public void setWindowWidth(double width){
       WIN_WIDTH_PIXELS = width;
   }
   /**
    * Set the window height
    * @param height - window height (Window Class).
    */
   public void setWindowHeight(double height){
       WIN_HEIGHT_PIXELS = height;
   }
   /**
    * Sets the rate.
    * @param ts - TimeStep. The default value = 1f/60f.
    */
   public void setTimeStep(float ts){
       timeStep=ts;
   }

   protected static double pixelsToMeterX(double pixels){
       return (pixels - (WIN_WIDTH_PIXELS/2))/PIXELS_PER_METER;
   }

   protected static double pixelsToMeterY(double pixels){
       return  - (pixels - (WIN_HEIGHT_PIXELS/2))/PIXELS_PER_METER;
   }

    protected static double meterToPixels_Y_Axis(double pixelsY){

        return ((WIN_HEIGHT_PIXELS/2)-(pixelsY*PIXELS_PER_METER));
    }


    protected static double meterToPixels_X_Axis(double pixelsX){

        return (( WIN_WIDTH_PIXELS/2)+(pixelsX*PIXELS_PER_METER));
    }


}
