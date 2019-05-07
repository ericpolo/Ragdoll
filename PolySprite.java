import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.util.Vector;


/**
 * A simple demo of how to create a rectangular sprite.
 * 
 * Michael Terry & Jeff Avery
 */
public class PolySprite extends Sprite {

    private Polygon poly = new Polygon();
    private Vector<Point2D> points = new Vector<>();
    private ImageIcon image;
    //private RoundRectangle2D rect = null;

    /**
     * Creates a rectangle based at the origin with the specified
     * width and height
     */
    public PolySprite(int width, int height,String part,Point2D p,String type) {
        super();
        this.type = type;
        this.initialize(width,height,part);
        pivot.setLocation(p.getX(),p.getY());
    }
    /**
     * Creates a rectangle based at the origin with the specified
     * width, height, and parent
     */
    public PolySprite(int width, int height,String part, Sprite parentSprite) {
        super(parentSprite);
        this.initialize(width, height,part);
    }

    private void initialize(Vector<Point> pts) {
        for(int i =0;i<pts.size();i++){
            poly.addPoint(pts.get(i).x,pts.get(i).y);
            points.add(pts.get(i));
        }
    }
    private void initialize(int width, int height,String part) {
        this.part = part;
        rect = new RoundRectangle2D.Double(0,0,width,height,5,5);
        if(part == "head"){
            MAX_RADIAN=0.872665;
        } else if (part.equals("armLeftLow")||part.equals("armRightLow")){
            MAX_RADIAN=2.35619;
        } else if (part.substring(0,4).equals("hand")||part.substring(0,4).equals("foot")){
            MAX_RADIAN= 0.610865;
        } else if (part.substring(0,3).equals("leg")){
            MAX_RADIAN=1.5708;
        } else {
            MAX_RADIAN = 2*Math.PI;
            if(type.equals("Plane")) MAX_RADIAN = 0.610865;
        }
        //System.out.print("type:"+this.type+" image/"+this.type+"/"+part+".png\n");
    }
    
    /**
     * Test if our rectangle contains the point specified.
     */
    public boolean pointInside(Point2D p) {
        AffineTransform fullTransform = this.getFullTransform();
        AffineTransform inverseTransform = null;
        try {
            inverseTransform = fullTransform.createInverse();
        } catch (NoninvertibleTransformException e) {
            e.printStackTrace();
        }
        Robot rbt = null;
        Point2D newPoint = (Point2D)p.clone();
        inverseTransform.transform(newPoint, newPoint);

//        try{
//            rbt = new Robot();
//        } catch (AWTException e){
//        }
//        BufferedImage im = rbt.createScreenCapture(new Rectangle(0,0,1200,800));
//        int[] rgb = new int[3];
//        int pixel = im.getRGB((int)p.getX(),(int)p.getY());
//        rgb[0] = (pixel & 0xff0000) >> 16;
//        rgb[1] = (pixel & 0xff00) >> 8;
//        rgb[2] = (pixel & 0xff);
        return rect.contains(newPoint);
    }

    protected void drawSprite(Graphics2D g) {
        g.setColor(Color.BLACK);
        //g.draw(rect);
        image = new ImageIcon("image/"+this.type+"/"+part+".png");
        g.drawImage(image.getImage(), 0, 0, (int) rect.getWidth(), (int) rect.getHeight(),null);
    }
    
    public String toString() {
        return "PolySprite: " + rect;
    }
}
