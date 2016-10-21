package edu.virginia.engine.display;

import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;

import java.awt.*;
import java.util.ArrayList;

public class DisplayObjectContainer extends DisplayObject {

    /* Local Variables */
    private ArrayList<DisplayObjectContainer> children = new ArrayList<DisplayObjectContainer>();

    /* Constructors */
    public DisplayObjectContainer(String id) {
        super(id);
    }
    public DisplayObjectContainer(String id, String imageFileName) {
        super(id, imageFileName);
    }
    
    /* Accessor & Mutator Methods */
    public ArrayList<DisplayObjectContainer> getChildren() { return children; }

    public void addChild(DisplayObjectContainer child) {
        children.add(child);
        child.setParent(this);
        child.setScale(getScaleX(),getScaleY());
        child.setPosition(getPosition());
        //child.setPivotPoint(getPivotPoint());
    }
    public void addChild(DisplayObjectContainer child, int index) {
        children.add(index, child);
        child.setParent(this);
        child.setScale(getScaleX(),getScaleY());
        child.setPosition(getPosition());
        child.setPivotPoint(getPivotPoint());
    }

    public void removeChild(DisplayObjectContainer child) {
        child.setParent(null);
        children.remove(child);
    }
    public void removeChild(int index){
        getChildren().get(index).setParent(null);
        children.remove(index);
    }
    public void removeAll() {
        for( DisplayObject child : children) {
            child.setParent(null);
        }
        children.clear();
    }

    public DisplayObjectContainer getChild(int index) {
        return children.get(index);
    }
    public DisplayObjectContainer getChild(String id) {
        if (children.size() == 0) ; else {
            for( DisplayObjectContainer child : children) {
                if (child.getId() == id)
                    return child;
            }
        } return null;
    }

    public boolean contains(DisplayObjectContainer child) {
        if (children.size()==0) return false;
        else return children.contains(child);
    }

    @Override
    public void setPosition(int x, int y){
        if(hasParent()) {
            super.setPosition(getPosition().x+x, y+getPosition().y);
        }
        else {
            super.setPosition(x,y);
        }
    }


    @Override
    protected void update(ArrayList<String> pressedKeys){
        super.update(pressedKeys);
    }

    @Override
    public void draw (Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        if (getVisibility()) super.draw(g);

        applyTransformations(g2d);
        for(DisplayObjectContainer child : children) {
            if (child.getVisibility()) {
//                child.setRotation(getRotation());
                child.draw(g);
            }
            /*if (child.getVisibility())
            g2d.drawImage(child.getImage(), 0,0,
                    ((int)child.getUnscaledWidth()),
                    ((int)child.getUnscaledHeight()), null);*/
        }
        reverseTransformations(g2d);


    }
}



/*
    public Point local2global (Point local) {
        if (getParent() == null) {
            System.out.println(getId()+" did not have a parent in the local2global function");
            return local;
        } //DOUBLE CHECK THIS NULL MAY NOT ACT LIKE YOU THINK IT DOES
        else {
            System.out.println(getId()+" had a parent in the local2global function");
            Point newloc = getParent().getPosition();
            return new Point(getPosition().x+newloc.x,getPosition().y+newloc.y); //return your position + your parents position
        }
    }

    public Point global2local (Point global) {
        Point p = local2global(new Point(getPosition().x, getPosition().y));
        return new Point (global.x-p.x, global.y-p.y);
    }
*/