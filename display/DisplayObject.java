package edu.virginia.engine.display;

import edu.virginia.engine.events.Event;
import edu.virginia.engine.events.EventDispatcher;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DisplayObject extends EventDispatcher {

    /* Local Variables */
    private DisplayObject parent = null; /** how to assign to parent?? **/
    public boolean hasParent() { return (parent != null) ;}

	private String id; //each obj has its own id
	private BufferedImage displayImage; //has a displayable image

    private Point position = new Point(); //position
    private Point pivotPoint = new Point(getPosition().x,getPosition().y); //pivotPoint

    private double alpha = 1; //opacity; 1 = opaque ; 0 = transparent
	private boolean visible = true; //visibility
	private double scaleX = 1, scaleY = 1; //scale; 1 = 100%
	private double radians = 0; //rotation

	/* Constructors */
	public DisplayObject(String id) {
		this.setId(id);
	}
	public DisplayObject(String id, String fileName) {
		this.setId(id);
		this.setImage(fileName);
	}
    public DisplayObject(DisplayObject other){
        this.id = other.id;
        this.displayImage = other.displayImage;
    }

	/* Accessor & Mutator Methods */
    public DisplayObject getParent(){ return parent; }
    public void setParent(DisplayObject newParent) { parent = new DisplayObject(newParent); }

	public String getId() {
		return id;
	}
    public void setId(String id) {
        this.id = id;
    }

    public BufferedImage getImage() {
        return this.displayImage;
    }
    public BufferedImage readImage(String imageName) {
        BufferedImage image = null;
        try {
            String file = ("resources" + File.separator + imageName);
            image = ImageIO.read(new File(file));
        } catch (IOException e) {
            System.out.println("[Error in DisplayObject.java:readImage] Could not read image " + imageName);
            e.printStackTrace();
        }
        return image;
    } //reads image from file
    protected void setImage(String imageName) {
        if (imageName == null) {
            return;
        }
        displayImage = readImage(imageName);
        if (displayImage == null) {
            System.err.println("[DisplayObject.setImage] ERROR: " + imageName + " does not exist!");
        }
    } //accepts filename
    public void setImage(BufferedImage image) {
        if (image == null) return;
        displayImage = image;
    } //accepts buffered image

    public Point getPosition() {
        return position;
    }
    public void setPosition(int x, int y) {
        position.setLocation(x, y);
    }
    public void setPosition(Point p) {
        position.setLocation(p);
    }

    public Point getPivotPoint() {
        return pivotPoint;
    }
    public void setPivotPoint(int x, int y) {
        pivotPoint.setLocation(x, y);
    }
    public void setPivotPoint(Point p) {
        pivotPoint = p;
    }
    public void setPivotPointToCenter() {
        pivotPoint.setLocation(
                getPosition().x+getScaledWidth()/2,
                getPosition().y+getScaledHeight()/2 );
    }

    public double getAlpha() { return alpha; }
    public void setAlpha(double newAlpha) {
        if (!visible) alpha = 0;
        else if (newAlpha <= 0) alpha = 0.0;
        else if (newAlpha >= 1) alpha = 1;
        else alpha = newAlpha;
    } // 0-1 range;

    public boolean getVisibility() {
        return visible;
    }
    public void setVisibility(boolean bool) {
        visible = bool;
    }

    public int getUnscaledWidth() {
        if (displayImage == null) return 0;
        return displayImage.getWidth();
    }
    public int getUnscaledHeight() {
        if (displayImage == null) return 0;
        return displayImage.getHeight();
    }
    public double getScaledWidth() { return getUnscaledWidth()*scaleX; }
    public double getScaledHeight() { return getUnscaledHeight()*scaleY; }
    public double getScaleX() { return scaleX; }
    public double getScaleY() { return scaleY; }
    public void setScale(double x, double y) {
        if (x < 0|| y < 0) { scaleX = 0.1; scaleY = 0.1; }
        else { scaleX = x; scaleY = y;}

        } // positive values only;
    public void setScale(double x) {
        if (x < 0) { scaleX = 0.1; scaleY = 0.1; }
        else { scaleX = x; scaleY = x;}

    } // positive values only;

    public double getRotation() {return radians;}
    public void setRotation(double newRad) {radians = newRad;}

    /* Standalone Functions */
	protected void applyTransformations(Graphics2D g2d) {
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)alpha));
		g2d.translate(position.getX(),position.getY());
		g2d.scale(scaleX, scaleY);
		g2d.rotate(radians);//,pivotPoint.x,pivotPoint.y);
        g2d.translate(-pivotPoint.getX(), -pivotPoint.getY());

    } //applies any requested transformations to the graphics object
	protected void reverseTransformations(Graphics2D g2d) {
        g2d.translate(pivotPoint.getX(), pivotPoint.getY());
        g2d.rotate(-radians);//,pivotPoint.x,pivotPoint.y);
        g2d.scale(1/scaleX, 1/scaleY);
		g2d.translate(-position.getX(),-position.getY());
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
	} //returns grapics object to orignial state

    /** LAB 5 PHYSISCS */
	public Rectangle getHitBox(){
		int x = (int) (getPosition().getX());
		int y = (int) (getPosition().getY());
		int w = (int) (getScaledWidth());
		int h = (int) (getScaledHeight());

		return new Rectangle(x,y,w,h);
	}
	//consider adding the source to the end of the collision string
	public void collidesWith(DisplayObject other){
		dispatchEvent(new Event(this,"collision"));
		dispatchEvent(new Event(other,"collision"));
	}
    /** * * * * * * * * * **/

	protected void update(ArrayList<String> pressedKeys) {

    }

    //	Draws this image & is automatically invoked on every frame
	public void draw(Graphics g) {
		if (displayImage != null) {

			Graphics2D g2d = (Graphics2D) g;
			applyTransformations(g2d);
            g2d.drawImage(displayImage, 0,0,
                    ((int)getUnscaledWidth()),
                    ((int)getUnscaledHeight()), null);
			reverseTransformations(g2d);
		}
	}
}

//    /* Standalone Functions */
//    protected void applyTransformations(Graphics2D g2d) {
//        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
//        g2d.translate(position.getX(),position.getY());
//        g2d.scale(scaleX, scaleY);
//        g2d.rotate(radians);//,pivotPoint.x,pivotPoint.y);
//        g2d.translate(-pivotPoint.getX(), -pivotPoint.getY());
//    } //applies any requested transformations to the graphics object
//    protected void reverseTransformations(Graphics2D g2d) {
//        g2d.translate(pivotPoint.getX(), pivotPoint.getY());
//        g2d.rotate(-radians);//,pivotPoint.x,pivotPoint.y);
//        g2d.scale(1/scaleX, 1/scaleY);
//        g2d.translate(-position.getX(),-position.getY());
//        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1/alpha));
//    } //returns grapics object to orignial state

//    public void draw(Graphics g) {
//        if (displayImage != null) {
//            Graphics2D g2d = (Graphics2D) g;
//            applyTransformations(g2d);
//            g2d.drawImage(displayImage, getPosition().x, getPosition().y,
//                    ((int)getScaledWidth()),
//                    ((int)getScaledHeight()), null);
//            reverseTransformations(g2d);
//        }
//    }