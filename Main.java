import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.io.*;


public class Main {
    static String type = "Normal";
    public static void main(String[] args) {
        Model model = new Model();
        Canvas canvas= new Canvas(model);
        JMenuBar menu = new JMenuBar();

        JFrame win = new JFrame("Ragdoll");
        win.setMinimumSize(new Dimension(1200,800));
        win.setMaximumSize(new Dimension(1200,800));
        win.setSize(new Dimension(1200,800));
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //setup canvas
        canvas.addSprite(Main.makeSprite());
        //setup menubar
        JMenu fileList = new JMenu("File");
        JMenu dollList = new JMenu("Ragdolls");
        JMenuItem normal = new JMenuItem("Normal");
        JMenuItem tree = new JMenuItem("Tree");
        JMenuItem plane = new JMenuItem("Plane");
        JMenuItem reset = new JMenuItem("Reset");
        JMenuItem quit = new JMenuItem("Quit");
        menu.add(fileList);
        menu.add(dollList);
        fileList.add(reset);
        fileList.addSeparator();
        fileList.add(quit);
        dollList.add(normal);
        dollList.add(tree);
        dollList.add(plane);

        // adding listeners for menu items
        //TODO:setup load and save function;

        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                win.dispose();
            }
        });

        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.newSprite(makeSprite());
                canvas.repaint();
            }
        });
        normal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                type = "Normal";
                canvas.newSprite(makeSprite());
                canvas.repaint();
            }
        });
        tree.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                type = "Tree";
                canvas.newSprite(makeSprite());
                canvas.repaint();
            }
        });
        plane.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                type = "Plane";
                canvas.newSprite(makeSprite());
                canvas.repaint();
            }
        });
        reset.setAccelerator(KeyStroke.getKeyStroke("control R"));
        quit.setAccelerator(KeyStroke.getKeyStroke("control Q"));
        win.setJMenuBar(menu);
        win.add(canvas);
        win.setVisible(true);
    }
    private static Sprite makeSprite(){
        Sprite body = null;
        if(type.equals("Normal")) {
            // create body
            body = new PolySprite(118, 223, "body", new Point2D.Double(550, 200),"Normal");
            Sprite head = new PolySprite(75, 100, "head", new Point2D.Double(37.5, 100),"Normal");
            body.transform(AffineTransform.getTranslateInstance(550, 110));
            head.transform(AffineTransform.getTranslateInstance(17, -90));

            Sprite leftupArm = new PolySprite(59, 89, "armLeftUp", new Point2D.Double(50, 35),"Normal");
            Sprite leftlowArm = new PolySprite(86, 76, "armLeftLow", new Point2D.Double(80, 10),"Normal");
            Sprite lefthand = new PolySprite(41, 60, "handLeft", new Point2D.Double(31, 3.33),"Normal");
            leftupArm.addChild(leftlowArm);
            leftlowArm.addChild(lefthand);
            Sprite rightupArm = new PolySprite(59, 89, "armRightUp", new Point2D.Double(9, 35),"Normal");
            Sprite rightlowArm = new PolySprite(86, 76, "armRightLow", new Point2D.Double(6, 10),"Normal");
            Sprite righthand = new PolySprite(41, 60, "handRight", new Point2D.Double(10, 3.33),"Normal");
            rightupArm.addChild(rightlowArm);
            rightlowArm.addChild(righthand);
            Sprite leftupLeg = new PolySprite(65, 118, "legLeftUp", new Point2D.Double(32.66, 0),"Normal");
            Sprite leftlowLeg = new PolySprite(42, 132, "legLeftLow", new Point2D.Double(23.5, 0),"Normal");
            Sprite leftfoot = new PolySprite(53, 84, "footLeft", new Point2D.Double(40, 0),"Normal");
            leftupLeg.addChild(leftlowLeg);
            leftlowLeg.addChild(leftfoot);
            Sprite rightupLeg = new PolySprite(65, 118, "legRightUp", new Point2D.Double(32.33, 0),"Normal");
            Sprite rightlowLeg = new PolySprite(42, 132, "legRightLow", new Point2D.Double(28.5, 0),"Normal");
            Sprite rightfoot = new PolySprite(53, 90, "footRight", new Point2D.Double(14, 0),"Normal");
            rightupLeg.addChild(rightlowLeg);
            rightlowLeg.addChild(rightfoot);
            body.addChild(head);
            body.addChild(leftupArm);
            body.addChild(rightupArm);
            body.addChild(leftupLeg);
            body.addChild(rightupLeg);
            leftlowArm.transform(AffineTransform.getTranslateInstance(-63, 66));
            lefthand.transform(AffineTransform.getTranslateInstance(-20, 66));
            leftupArm.transform(AffineTransform.getRotateInstance(Math.toRadians(-5)));
            leftupArm.transform(AffineTransform.getTranslateInstance(-47, 27));

            rightlowArm.transform(AffineTransform.getTranslateInstance(33, 66));
            righthand.transform(AffineTransform.getTranslateInstance(65, 65));
            rightupArm.transform(AffineTransform.getTranslateInstance(105, 27));
            rightupArm.transform(AffineTransform.getRotateInstance(Math.toRadians(5)));

            leftupLeg.transform(AffineTransform.getTranslateInstance(-7, 220));
            leftlowLeg.transform(AffineTransform.getTranslateInstance(-5, 100));
            leftfoot.transform(AffineTransform.getTranslateInstance(-27, 123));

            rightupLeg.transform(AffineTransform.getTranslateInstance(62, 220));
            rightlowLeg.transform(AffineTransform.getTranslateInstance(27, 100));
            rightfoot.transform(AffineTransform.getTranslateInstance(15, 123));
        } else if (type.equals("Tree")) {
            body = new PolySprite(100, 250, "body", new Point2D.Double(0, 0),"Tree");
            Sprite largeTreeBranch1 = new PolySprite(30, 100, "armLeftup", new Point2D.Double(15, 100),"Tree");
            Sprite largeTreeBranch2 = new PolySprite(30, 100, "armLeftup", new Point2D.Double(15, 100),"Tree");
            Sprite largeTreeBranch3 = new PolySprite(30, 100, "armLeftup", new Point2D.Double(15, 100),"Tree");

            Sprite treeBranch1 = new PolySprite(20, 75, "armLeftlow", new Point2D.Double(10, 75),"Tree");
            Sprite treeBranch2 = new PolySprite(20, 75, "armLeftlow", new Point2D.Double(10, 75),"Tree");
            Sprite treeBranch3 = new PolySprite(20, 75, "armLeftlow", new Point2D.Double(10, 75),"Tree");
            Sprite treeBranch4 = new PolySprite(20, 75, "armLeftlow", new Point2D.Double(10, 75),"Tree");
            Sprite treeBranch5 = new PolySprite(20, 75, "armLeftlow", new Point2D.Double(10, 75),"Tree");
            Sprite treeBranch6 = new PolySprite(20, 75, "armLeftlow", new Point2D.Double(10, 75),"Tree");

            Sprite treeEnd1 = new PolySprite(15, 50, "handLeft", new Point2D.Double(7.5, 50),"Tree");
            Sprite treeEnd2 = new PolySprite(15, 50, "handLeft", new Point2D.Double(7.5, 50),"Tree");
            Sprite treeEnd3 = new PolySprite(15, 50, "handLeft", new Point2D.Double(7.5, 50),"Tree");
            Sprite treeEnd4 = new PolySprite(15, 50, "handLeft", new Point2D.Double(7.5, 50),"Tree");
            Sprite treeEnd5 = new PolySprite(15, 50, "handLeft", new Point2D.Double(7.5, 50),"Tree");
            Sprite treeEnd6 = new PolySprite(15, 50, "handLeft", new Point2D.Double(7.5, 50),"Tree");
            Sprite treeEnd7 = new PolySprite(15, 50, "handLeft", new Point2D.Double(7.5, 50),"Tree");
            Sprite treeEnd8 = new PolySprite(15, 50, "handLeft", new Point2D.Double(7.5, 50),"Tree");
            Sprite treeEnd9 = new PolySprite(15, 50, "handLeft", new Point2D.Double(7.5, 50),"Tree");
            Sprite treeEnd10 = new PolySprite(15, 50, "handLeft", new Point2D.Double(7.5, 50),"Tree");
            Sprite treeEnd11 = new PolySprite(15, 50, "handLeft", new Point2D.Double(7.5, 50),"Tree");
            Sprite treeEnd12 = new PolySprite(15, 50, "handLeft", new Point2D.Double(7.5, 50),"Tree");

            /* Building scene graph */
            treeBranch1.addChild(treeEnd1);
            treeBranch1.addChild(treeEnd2);
            treeBranch2.addChild(treeEnd3);
            treeBranch2.addChild(treeEnd4);
            treeBranch3.addChild(treeEnd5);
            treeBranch3.addChild(treeEnd6);
            treeBranch4.addChild(treeEnd7);
            treeBranch4.addChild(treeEnd8);
            treeBranch5.addChild(treeEnd9);
            treeBranch5.addChild(treeEnd10);
            treeBranch6.addChild(treeEnd11);
            treeBranch6.addChild(treeEnd12);

            largeTreeBranch1.addChild(treeBranch1);
            largeTreeBranch1.addChild(treeBranch2);
            largeTreeBranch2.addChild(treeBranch3);
            largeTreeBranch2.addChild(treeBranch4);
            largeTreeBranch3.addChild(treeBranch5);
            largeTreeBranch3.addChild(treeBranch6);

            body.addChild(largeTreeBranch1);
            body.addChild(largeTreeBranch2);
            body.addChild(largeTreeBranch3);


            /* Transforms */
            body.transform(AffineTransform.getTranslateInstance(550,400));
            largeTreeBranch1.transform(AffineTransform.getTranslateInstance(160, -90));
            largeTreeBranch1.transform(AffineTransform.getRotateInstance(Math.PI / 4));
            largeTreeBranch2.transform(AffineTransform.getTranslateInstance(35, -120));
            largeTreeBranch3.transform(AffineTransform.getTranslateInstance(-90,-72));
            largeTreeBranch3.transform(AffineTransform.getRotateInstance(-Math.PI / 4));

            treeBranch1.transform(AffineTransform.getTranslateInstance(50, -80));
            treeBranch1.transform(AffineTransform.getRotateInstance(0.5));
            treeBranch2.transform(AffineTransform.getTranslateInstance(-40, -70));
            treeBranch2.transform(AffineTransform.getRotateInstance(-0.5));

            treeBranch3.transform(AffineTransform.getTranslateInstance(50, -80));
            treeBranch3.transform(AffineTransform.getRotateInstance(0.5));
            treeBranch4.transform(AffineTransform.getTranslateInstance(-40, -70));
            treeBranch4.transform(AffineTransform.getRotateInstance(-0.5));

            treeBranch5.transform(AffineTransform.getTranslateInstance(50, -80));
            treeBranch5.transform(AffineTransform.getRotateInstance(0.5));
            treeBranch6.transform(AffineTransform.getTranslateInstance(-40, -70));
            treeBranch6.transform(AffineTransform.getRotateInstance(-0.5));

            treeEnd1.transform(AffineTransform.getTranslateInstance(40, -50));
            treeEnd1.transform(AffineTransform.getRotateInstance(0.5));
            treeEnd2.transform(AffineTransform.getTranslateInstance(-30, -45));
            treeEnd2.transform(AffineTransform.getRotateInstance(-0.5));

            treeEnd3.transform(AffineTransform.getTranslateInstance(40, -50));
            treeEnd3.transform(AffineTransform.getRotateInstance(0.5));
            treeEnd4.transform(AffineTransform.getTranslateInstance(-30, -45));
            treeEnd4.transform(AffineTransform.getRotateInstance(-0.5));

            treeEnd5.transform(AffineTransform.getTranslateInstance(40, -50));
            treeEnd5.transform(AffineTransform.getRotateInstance(0.5));
            treeEnd6.transform(AffineTransform.getTranslateInstance(-30, -45));
            treeEnd6.transform(AffineTransform.getRotateInstance(-0.5));

            treeEnd7.transform(AffineTransform.getTranslateInstance(40, -50));
            treeEnd7.transform(AffineTransform.getRotateInstance(0.5));
            treeEnd8.transform(AffineTransform.getTranslateInstance(-30, -45));
            treeEnd8.transform(AffineTransform.getRotateInstance(-0.5));

            treeEnd9.transform(AffineTransform.getTranslateInstance(40, -50));
            treeEnd9.transform(AffineTransform.getRotateInstance(0.5));
            treeEnd10.transform(AffineTransform.getTranslateInstance(-30, -45));
            treeEnd10.transform(AffineTransform.getRotateInstance(-0.5));

            treeEnd11.transform(AffineTransform.getTranslateInstance(40, -50));
            treeEnd11.transform(AffineTransform.getRotateInstance(0.5));
            treeEnd12.transform(AffineTransform.getTranslateInstance(-30, -45));
            treeEnd12.transform(AffineTransform.getRotateInstance(-0.5));

        } else if (type.equals("Plane")){
            body = new PolySprite(224, 454, "body", new Point2D.Double(0, 0),"Plane");
            Sprite head = new PolySprite(39, 51, "head", new Point2D.Double(19.5, 51),"Plane");
            Sprite armLeftup = new PolySprite(139, 244, "armLeftup", new Point2D.Double(139, 122),"Plane");
            Sprite armRightup = new PolySprite(139, 244, "armRightup", new Point2D.Double(0, 122),"Plane");
            Sprite armLeftlow = new PolySprite(54, 112, "armLeftlow", new Point2D.Double(54, 56),"Plane");
            Sprite armRightlow = new PolySprite(54, 112, "armRightlow", new Point2D.Double(0, 56),"Plane");
            Sprite hand = new PolySprite(31, 91, "hand", new Point2D.Double(15.5, 91),"Plane");
            Sprite footLeft = new PolySprite(93, 86, "footLeft", new Point2D.Double(93, 43),"Plane");
            Sprite footRight = new PolySprite(93, 86, "footRight", new Point2D.Double(0, 43),"Plane");

            armLeftup.addChild(armLeftlow);
            armRightup.addChild(armRightlow);

            body.addChild(head);
            body.addChild(armLeftup);
            body.addChild(armRightup);
            body.addChild(hand);
            body.addChild(footRight);
            body.addChild(footLeft);

            body.transform(AffineTransform.getTranslateInstance(488,100));
            head.transform(AffineTransform.getTranslateInstance(94,-35));
            armLeftup.transform(AffineTransform.getTranslateInstance(-139+63,146.6666));
            armRightup.transform(AffineTransform.getTranslateInstance(224-61,146.6666));
            footLeft.transform(AffineTransform.getTranslateInstance(-10,380));
            footRight.transform(AffineTransform.getTranslateInstance(141,380));
            hand.transform(AffineTransform.getTranslateInstance(97,30));
            armLeftlow.transform(AffineTransform.getTranslateInstance(106,170.6666));
            armRightlow.transform(AffineTransform.getTranslateInstance(-28,170.6666));
        }
        return  body;
    }
}
