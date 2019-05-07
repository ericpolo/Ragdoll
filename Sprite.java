
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.util.Vector;
import java.awt.geom.NoninvertibleTransformException;
/**
 * A building block for creating your own shapes that can be
 * transformed and that can respond to input. This class is
 * provided as an example; you will likely need to modify it
 * to meet the assignment requirements.
 * 
 * Michael Terry & Jeff Avery
 */
public abstract class Sprite {
    
    /**
     * Tracks our current interaction mode after a mouse-down
     */
    protected enum InteractionMode {
        IDLE,
        DRAGGING,
        SCALING,
        ROTATING
    }

    private Sprite parent = null;                                       // Pointer to our parent
    private Vector<Sprite> children = new Vector<Sprite>();             // Holds all of our children
    private AffineTransform transform = new AffineTransform();          // Our transformation matrix
    protected Point2D lastPoint = null;                                 // Last mouse point
    protected InteractionMode interactionMode = InteractionMode.IDLE;   // current state
    public String type;
    public String part;
    public Point2D pivot = new Point2D.Double();
    public double radian = 0;
    public double MAX_RADIAN=0;
    public RoundRectangle2D rect = null;
    public Sprite() {
    }
    
    public Sprite(Sprite parent) {
        if (parent != null) {
            parent.addChild(this);
        }
    }

    public void addChild(Sprite s) {
        children.add(s);
        s.setParent(this);
    }
    public Sprite getParent() {
        return parent;
    }
    private void setParent(Sprite s) {
        this.parent = s;
    }

    /**
     * Test whether a point, in world coordinates, is within our sprite.
     */
    public abstract boolean pointInside(Point2D p);

    /**
     * Handles a mouse down event, assuming that the event has already
     * been tested to ensure the mouse point is within our sprite.
     */
    protected void handleMouseDownEvent(MouseEvent e) {
        lastPoint = e.getPoint();
        if (e.getButton() == MouseEvent.BUTTON1) {
            if(this.part.equals("body")) interactionMode = InteractionMode.DRAGGING;
            if(this.part.equals("head")|| this.part.substring(0,4).equals("foot") ||
                    this.part.substring(0,3).equals("arm") || this.part.substring(0,4).equals("hand"))
                interactionMode = InteractionMode.ROTATING;
            if(this.part.substring(0,3).equals("leg")){
                interactionMode = InteractionMode.SCALING;
            }
        }
        // Handle rotation, scaling mode depending on input
    }

    /**
     * Handle mouse drag event, with the assumption that we have already
     * been "selected" as the sprite to interact with.
     * This is a very simple method that only works because we
     * assume that the coordinate system has not been modified
     * by scales or rotations. You will need to modify this method
     * appropriately so it can handle arbitrary transformations.
     */
    protected void handleMouseDragEvent(MouseEvent e) {
        Point2D oldPoint = lastPoint;
        Point2D newPoint = e.getPoint();
        switch (interactionMode) {
            case IDLE:
                ; // no-op (shouldn't get here)
                break;
            case DRAGGING:
                double x_diff;
                double y_diff;
                x_diff = newPoint.getX() - oldPoint.getX();
                y_diff = newPoint.getY() - oldPoint.getY();
                transform.translate(x_diff, y_diff);
                break;
            case ROTATING:
                AffineTransform inverseTransform = null;
                AffineTransform fullTransform = this.getFullTransform();
                try {
                    inverseTransform = fullTransform.createInverse();
                } catch (NoninvertibleTransformException exception) {
                    exception.printStackTrace();
                }
                inverseTransform.transform(oldPoint, oldPoint);
                inverseTransform.transform(newPoint, newPoint);
                double angle = getAngle(newPoint,oldPoint);
                if(Math.abs(radian+angle)<MAX_RADIAN){
                    radian+=angle;
                    transform.rotate(angle,pivot.getX(),pivot.getY());
//                    System.out.print(transform.toString());
//                    System.out.print("\n");
                }
                break;
            case SCALING:
                inverseTransform = null;
                fullTransform = this.getFullTransform();
                try {
                    inverseTransform = fullTransform.createInverse();
                } catch (NoninvertibleTransformException exception) {
                    exception.printStackTrace();
                }
                inverseTransform.transform(oldPoint, oldPoint);
                inverseTransform.transform(newPoint, newPoint);
                angle = getAngle(newPoint,oldPoint);
                if(Math.abs(radian+angle)<MAX_RADIAN){
                    radian+=angle;
                    transform.rotate(angle,pivot.getX(),pivot.getY());
//                    System.out.print(transform.toString());
//                    System.out.print("\n");
                }
                x_diff = newPoint.getX()-oldPoint.getX();
                y_diff = newPoint.getY()-oldPoint.getY();
                double diff = 0;
                if(x_diff==0) diff=y_diff;
                if(y_diff==0) diff=x_diff;
                if(x_diff!=0&&y_diff>0) diff=Math.sqrt(y_diff*y_diff+x_diff*x_diff);
                if(x_diff!=0&&y_diff<0) diff=-Math.sqrt(y_diff*y_diff+x_diff*x_diff);
                if(rect.getHeight()+diff>5) {
                    rect = new RoundRectangle2D.Double(0, 0, rect.getWidth(), rect.getHeight() + diff, 5, 5);
                    if (children.size() != 0) {
                        children.get(0).transform.translate( diff*Math.sin(children.get(0).radian),diff*Math.cos(children.get(0).radian));
                        if(children.get(0).part.equals("legRightLow")||children.get(0).part.equals("legLeftLow")){
                            if(children.get(0).rect.getHeight()+diff>5){
                                children.get(0).rect = new RoundRectangle2D.Double(0, 0, children.get(0).rect.getWidth(), children.get(0).rect.getHeight() + diff, 5, 5);
                                if(children.get(0).children.size()!=0){
                                    children.get(0).children.get(0).transform.translate(diff*Math.sin(children.get(0).children.get(0).radian), diff*Math.cos(children.get(0).children.get(0).radian));
                                }
                            }
                        }
                    }

                }
                break;
        }
        // Save our last point, if it's needed next time around
        lastPoint = e.getPoint();
    }
    
    protected void handleMouseUp(MouseEvent e) {
        interactionMode = InteractionMode.IDLE;
        // Do any other interaction handling necessary here
    }
    
    /**
     * Locates the sprite that was hit by the given event.
     * You *may* need to modify this method, depending on
     * how you modify other parts of the class.
     * 
     * @return The sprite that was hit, or null if no sprite was hit
     */
    public Sprite getSpriteHit(MouseEvent e) {
        for (Sprite sprite : children) {
            Sprite s = sprite.getSpriteHit(e);
            if (s != null) {
                return s;
            }
        }
        if (this.pointInside(e.getPoint())) {
            return this;
        }
        return null;
    }
    
    /*
     * Important note: How transforms are handled here are only an example. You will
     * likely need to modify this code for it to work for your assignment.
     */
    
    /**
     * Returns the full transform to this object from the root
     */
    public AffineTransform getFullTransform() {
        AffineTransform returnTransform = new AffineTransform();
        Sprite curSprite = this;
        while (curSprite != null) {
            returnTransform.preConcatenate(curSprite.getLocalTransform());
            curSprite = curSprite.getParent();
        }
        return returnTransform;
    }

    /**
     * Returns our local transform
     */
    public AffineTransform getLocalTransform() {
        return (AffineTransform)transform.clone();
    }
    
    /**
     * Performs an arbitrary transform on this sprite
     */
    public void transform(AffineTransform t) {
        transform.concatenate(t);
    }

    /**
     * Draws the sprite. This method will call drawSprite after
     * the transform has been set up for this sprite.
     */
    public void draw(Graphics2D g) {
        AffineTransform oldTransform = g.getTransform();

        // Set to our transform
        Graphics2D g2 = (Graphics2D)g;
        AffineTransform currentAT = g2.getTransform();
        currentAT.concatenate(this.getFullTransform());
        g2.setTransform(currentAT);

        // Draw the sprite (delegated to sub-classes)
        this.drawSprite(g);

        // Restore original transform
        g.setTransform(oldTransform);

        // Draw children
        for (Sprite sprite : children) {
            sprite.draw(g);
        }
    }

    /**
     * Get angle between two points
     */
    protected double getAngle(Point2D newp,Point2D oldp){
        double newy_diff = newp.getY()-pivot.getY();
        double newx_diff = newp.getX()-pivot.getX();
        double oldy_diff = oldp.getY()-pivot.getY();
        double oldx_diff = oldp.getX()-pivot.getX();
        return Math.atan2(newy_diff,newx_diff)-Math.atan2(oldy_diff,oldx_diff);
    }
    /**
     * The method that actually does the sprite drawing. This method
     * is called after the transform has been set up in the draw() method.
     * Sub-classes should override this method to perform the drawing.
     */
    protected abstract void drawSprite(Graphics2D g);
}
