/*
 * The use this code commercially must only be done with permission of the author.
 * Any modification shall be advised and sent to the author.
 * The author is not responsible for any problem therefrom the use of this frameWork.
 *
 * @author Gefersom Cardoso Lima
 * Universidade Federal Fluminense - UFF - Brasil - 2010
 * Ciência da Computação
 */

package jplay;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import javax.swing.ImageIcon;

/**
 * Class responsible for modeling an image.
 * @version 1.0
 * @author Gefersom Cardoso Lima
 * Federal Fluminense University
 * Computer Science
 */
public class GameImage extends GameObject {

    /**
     * Reference for an Image.
     * @see java.awt.Image
     */
    Image image;


    /**
    * Class constructor, it loads an image.
    * @param fileName path of the image and its name.
    * @see #loadImage(java.lang.String)
    */
    public GameImage(String fileName)
    {
            loadImage(fileName);
    }

    /**
    * This method loads an image.
    * @param fileName path of the image and its name.
     */
    public void loadImage(String fileName)
    {
            ImageIcon icon = new ImageIcon(fileName);
            this.image = icon.getImage();
            this.width = image.getWidth(null);
            this.height = image.getHeight(null);
    }
    /**
     * Draw an image on the screen.
     */
    public void draw()
    {
            //Window.instance.getGameGraphics().drawImage(image, (int)x, (int)y, width, height, null);

            Graphics2D g2d = (Graphics2D) Window.getInstance().getGameGraphics();
            AffineTransform tx = new AffineTransform();

            double rot = rotation;
            tx.rotate( -rot,width/2,height/2);

            int newy = (int) (x*Math.sin(rot)+y*Math.cos(rot));
            int newx = (int) (x*Math.cos(rot)-y*Math.sin(rot));

            g2d.setTransform(tx);

            g2d.drawImage(image, newx, newy, width, height, null);
    }

    /** Draw an image on the screen.*/
//    public void draw()
//    {
//            Window.getInstance().getGameGraphics().drawImage(image, (int) x, (int) y, width, height, null);
//    }

    public void drawPartially(int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2)
    {
            Window.getInstance().getGameGraphics().drawImage(image, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
    }
}
