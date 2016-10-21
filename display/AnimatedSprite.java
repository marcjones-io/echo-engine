package edu.virginia.engine.display;

import java.awt.image.AreaAveragingScaleFilter;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class AnimatedSprite extends Sprite {

    private BufferedImage ogIMG;
    public BufferedImage frames[];


    private int startIndex = 0, endIndex = 7, defaultIndex = 3;
    public void setStartIndex(int x) { startIndex = x; }
    public void setEndIndex(int x) { endIndex = x; }
    public void setDefaultIndex(int x) { defaultIndex = x; }

    private long prev=0, speed=85000000;
    private boolean stopped = false;

    private int xPos = 0, yPos = 0; //sets to default
    private double alpha = 1; //opacity; 1 = opaque ; 0 = transparent
    private double scaleX = 1, scaleY = 1; //scale; 1 = 100%
    private double radians = 0; //rotation
    
    public AnimatedSprite(String id) {
        super(id);
    }

    public AnimatedSprite(String id, String imageFileName) {
        super(id);
        ogIMG = super.readImage(imageFileName);
        initializeFramesBoy();
        setImage(frames[0]);
    }

    @Override
    public void update(ArrayList<String> pressedKeys) {
        super.update(pressedKeys);
        if (!stopped) setImage(frames[startIndex]);
        tick();
    }

    /** GENERALIZE THIS METHOD FOR USE WITH ANY SHEET **/
    /* read in from custom sprite sheet */
    public void initializeFramesBoy() {
        frames = new BufferedImage[ ]{
            ogIMG.getSubimage(0, 0, 15, 35),
                ogIMG.getSubimage(15, 0, 15, 35),
                ogIMG.getSubimage(30, 0, 15, 35),
                ogIMG.getSubimage(45, 0, 15, 35),
                ogIMG.getSubimage(60, 0, 15, 35),
                ogIMG.getSubimage(75, 0, 15, 35),
                ogIMG.getSubimage(90, 0, 15, 35),
                ogIMG.getSubimage(105, 0, 15, 35)
        };
    }


    public void tick() {
        if ( System.nanoTime()-prev > speed && !stopped) {
            startIndex++;
            if(startIndex == endIndex) startIndex=0;
            prev = System.nanoTime();
        }
    }

    public void reverse_tick() {
        if ( System.nanoTime()-prev > speed && !stopped) {
            startIndex++;
            if(startIndex == endIndex) startIndex=0;
            prev = System.nanoTime();
        }
    }

    public void setSpeed (long newspeed) {
        speed = newspeed;
    }

    public void stop() {
        stopped = true;
        setImage(frames[defaultIndex]);
    }

    public void canMove(ArrayList<String> pressedKeys) {
        //movement: arrow keys or IJKL
        if (pressedKeys.contains("↑") || pressedKeys.contains("I")) yPos -= 10;
        if (pressedKeys.contains("↓") || pressedKeys.contains("K")) yPos += 10;
        if (pressedKeys.contains("←") || pressedKeys.contains("J")) xPos -= 10;
        if (pressedKeys.contains("→") || pressedKeys.contains("L")) xPos += 10;
        setPosition(xPos,yPos);

//        double scaleX = mario.getScaleX(); double scaleY = mario.getScaleY();
        //scaling: a = less / s = more
        if (pressedKeys.contains("A")) {  scaleX -= .1; scaleY -= .1; }
        if (pressedKeys.contains("S")) {  scaleX += .1; scaleY += .1; }
        if (scaleX < .1 || scaleY < .1) { scaleX = 0.1; scaleY = 0.1; }
        setScale(scaleX,scaleY);

//        double radians = mario.getRotation();
        //rotate: q = counter clockwise / w = clockwise
        if (pressedKeys.contains("Q")) radians -= .075;
        if (pressedKeys.contains("W")) radians += .075;
        setRotation(radians);

        //key visibility: holding v will make te obj disappear
        setVisibility(!pressedKeys.contains("V"));

//        double alpha = mario.getAlpha();
        //alpha: z = less / x = more
        if (pressedKeys.contains("Z")) alpha -= .025;
        if (pressedKeys.contains("X")) alpha += .025;
        setAlpha(alpha);


        if (pressedKeys.contains("M")) { if (speed < 15000000) speed = 15000000; else speed -= 5000000; }
        if (pressedKeys.contains("N")) { if (speed > 240000000) speed = 240000000; else speed += 5000000; }
        if (pressedKeys.contains("5")) stop();
    }

    public void animate(String mode) {
        if (mode.equalsIgnoreCase("walk")) ;  // call walking frameset
        else if (mode.equalsIgnoreCase("run")) ;  // call walking frameset
        else if (mode.equalsIgnoreCase("jump")) ; // call jump frameset
    }
}
