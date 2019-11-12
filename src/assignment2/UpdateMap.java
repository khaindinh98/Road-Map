/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

/**
 *
 * @author Sub4sa
 */
//import assignment2.ShortestPath;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

//class DisplayJLabel extends Component {
//
//    public String address ;
//    public String text;
//    private int x, y;
//
//    public DisplayJLabel(String text, address inf, int x, int y) {
//        this.text = text;
//        this.address = address;
//        this.x = x;
//        this.y = y;
//    }
//
//    public void paint(Graphics g) {
//        //g.setColor(Color.GREEN); //colors the window
//        //g.fillOval(x-(getWidth()/2),y-(getHeight()/2), getWidth() - 1, getHeight() - 1);
//        g.setColor(Color.RED);
//        g.drawString(text, x - (getWidth() / 3), y + (getHeight() / 6));
//    }
//
//    public void paintFind(Graphics g) {
//        g.setColor(Color.white);
//        g.fillOval(x - (getWidth() / 2), y - (getHeight() / 2), getWidth() - 1, getHeight() - 1);
//        g.setColor(Color.GREEN); //colors the window
//        g.fillOval(x - (getWidth() / 2), y - (getHeight() / 2), getWidth() - 1, getHeight() - 1);
//        g.setColor(Color.white);
//        g.drawString(text, x - (getWidth() / 3), y + (getHeight() / 6));
//    }
//
//    public void paintDefault(Graphics g) {
//        g.setColor(Color.white);
//        g.fillOval(x - (getWidth() / 2), y - (getHeight() / 2), getWidth() - 1, getHeight() - 1);
//        g.setColor(Color.GREEN); //colors the window
//        g.drawOval(x - (getWidth() / 2), y - (getHeight() / 2), getWidth() - 1, getHeight() - 1);
//        g.setColor(Color.BLACK);
//        g.drawString(text, x - (getWidth() / 3), y + (getHeight() / 6));
//    }
//
//}


class UpMap extends JPanel {

    private String address1, address2;
    private int x, y, x1, y1, x2, y2;
    private int row, col;
    public int widthImage, heightImage, widthFrame, heightFrame;
    //public double Ox, Oy;
    //Oy trai giam dan Ox phai tang dan
    //public double errorX, errorY;
    private int scale;
    private int extend;
    private String images[][];
    private Point p;
    private ArrayList<Point> array;
    private ArrayList<Edge> arrayRoad;
    private ArrayList<String> address;
    private ShortestPath graph;
    private JTextField text1, text2, text3;
    private JLabel jLabel1;
    private Point t1, t2;

    UpMap(JTextField text1, JTextField text2, JTextField text3, JLabel jLabel1) {
        super();
        this.text1 = text1;
        this.text2 = text2;
        this.text3 = text3;
        this.jLabel1 = jLabel1;
        col = 138;
        row = 140;
        scale = 0;
        extend = 0;
        widthFrame = 256;
        heightFrame = 256;
        widthImage = col * widthFrame;
        heightImage = row * heightFrame;
        //Ox = 106.676216125488280; //14 106
        //Oy = 10.79789745961630;//14
        //double endX = 106.723594665527340;
        //double endY = 10.750679605168770;//14
        //errorX = (endX - Ox) / widthImage;
        //errorY = (endY - Oy) / heightImage;
        //System.out.println(widthImage + " " + heightImage + " " + errorX + " " + errorY);
        array = new ArrayList<Point>();
        arrayRoad = new ArrayList<Edge>();
        try {
            //graph = new ShortestPath("input/test.txt");
            File f = new File("input/abc/");
            String[] files = f.list();
            int k = 0;
            images = new String[row][col];
            for (int i = 0; i < col; i++) {
                for (int j = 0; j < row; j++) {
                    String t = "input/abc/" + files[k];
                    images[j][i] = t;
                    k += 1;
                    //System.out.println()
                }
            }

            x1 = 0;
            y1 = 0;
            x2 = 0;
            y2 = 0;
//            x=0;
//            y=0;
            x = -18070 + 256;
            y = -27948 + 256;

            addMouseListener(new MouseAdapter() {

                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e); //To change body of generated methods, choose Tools | Templates.

                    if (e.getButton() == MouseEvent.BUTTON3) {
                        Point point = e.getPoint();
                        x1 = (int) point.getX();
                        y1 = (int) point.getY();
                        System.out.println("Point1:" + point);
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    super.mouseReleased(e);
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        Point point = e.getPoint();
                        System.out.println(point);
                        int xp = -1, yp = -1;
                        if (point.getX() - x >= 0) {
                            xp = (int) point.getX() - x;
                        }
                        if (point.getY() - y >= 0) {
                            yp = (int) point.getY() - y;
                        }
                        if (xp != -1 && yp != -1) {

                            if (!array.isEmpty()) {
                                boolean isCleared = false;
                                for (int i = 0; i < array.size(); i++) {
                                    int bk = 10;
                                    Point t = array.get(i);
                                    double w = Math.pow(xp - t.getX(), 2) + Math.pow(yp - t.getY(), 2) - bk * bk;
                                    System.out.println("Dường tròn" + w);
                                    if (w <= 0) {
                                        array.remove(i);
                                        isCleared = true;
                                        break;
                                    }
                                }
                                if (isCleared == false) {
                                    array.add(new Point(xp, yp));
                                    text1.setText(xp + " " + yp);
                                    text2.setText("");
                                    text3.setText("");
                                    jLabel1.setText("");
                                }
                            } else {
                                array.add(new Point(xp, yp));
                                text1.setText(xp + " " + yp);
                                text2.setText("");
                                text3.setText("");
                                jLabel1.setText("");
                            }
                            repaint();
                        }
                    }

                    if (e.getButton() == MouseEvent.BUTTON2) {
                        int xp1 = -1, yp1 = -1, xp2 = -1, yp2 = -1;
                        //To change body of generated methods, choose Tools | Templates.
                        if (t1 == null) {
                            t1 = e.getPoint();
                            if (t1.getX() - x >= 0) {
                                xp1 = (int) t1.getX() - x;
                            }
                            if (t1.getY() - y >= 0) {
                                yp1 = (int) t1.getY() - y;
                            }
                            if (xp1 != -1 && yp1 != -1) {

                                if (!array.isEmpty()) {
                                    boolean isChoosed = false;
                                    System.out.println("Fuck:" + t1);
                                    for (int i = 0; i < array.size(); i++) {
                                        int bk = 10;
                                        Point t = array.get(i);
                                        double w = Math.pow(xp1 - t.getX(), 2) + Math.pow(yp1 - t.getY(), 2) - bk * bk;
                                        System.out.println("Đường tròn" + w);
                                        if (w <= 0) {
                                            t1 = t;
                                            isChoosed = true;
                                            System.out.println("Point1:" + t1);
                                            break;
                                        }
                                    }
                                    if (isChoosed == false) {
                                        t1 = null;
                                        t2 = null;
                                    }
                                }
                            }

                        } else {

                            t2 = e.getPoint();
                            System.out.println("Zui:" + t2);
                            if (t2.getX() - x >= 0) {
                                xp2 = (int) t2.getX() - x;
                            }
                            if (t2.getY() - y >= 0) {
                                yp2 = (int) t2.getY() - y;
                            }
                            if (xp2 != -1 && yp2 != -1) {

                                if (!array.isEmpty()) {
                                    boolean isChoosed = false;
                                    for (int i = 0; i < array.size(); i++) {
                                        int bk = 10;
                                        Point t = array.get(i);
                                        double w = Math.pow(xp2 - t.getX(), 2) + Math.pow(yp2 - t.getY(), 2) - bk * bk;
                                        System.out.println("Đường tròn" + w);
                                        if (w <= 0) {
                                            t2 = t;
                                            isChoosed = true;
                                            System.out.println("Point1:" + t2);
                                            break;
                                        }
                                    }
                                    if (isChoosed == true) {
                                        boolean isChoosed2 = false;
                                        for (int i = 0; i < arrayRoad.size(); i++) {
                                            Point temp1 = (arrayRoad.get(i)).t1;
                                            Point temp2 = (arrayRoad.get(i)).t2;
                                            if ((temp1.equals(t1) && temp2.equals(t2)) || (temp1.equals(t2) && temp2.equals(t1))) {
                                                arrayRoad.remove(i);
                                                isChoosed2 = true;
                                                break;
                                            }
                                        }
                                        if (isChoosed2 == false && t1 != null && t2 != null&&!t1.equals(t2)) {
                                            arrayRoad.add(new Edge(t2, t1));
                                        }
                                    }
                                }
                                t1 = null;
                                t2 = null;
                                repaint();
                            }
                        }
                    }
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        //To change body of generated methods, choose Tools | Templates.
                        Point t = e.getPoint();
                        x2 = (int) t.getX();
                        y2 = (int) t.getY();
                        System.out.println("Point2:" + t);
                        x = x + (x2 - x1);
                        y = y + (y2 - y1);
                        repaint();
                    }

                }
            });
        } catch (Exception e) {
            System.out.println("Fail");
        }
    }

    public void setAddress(String address1, String address2) {
        this.address1 = address1;
        this.address2 = address2;
    }

    public ArrayList<Edge> getRoads() {
        return arrayRoad;
    }

    public ArrayList<Point> getPoints() {
        return array;
    }
//    public void getVertexPath() {
//        List<Integer> a = graph.getVertexPath(address1, address2);
//        for (Integer x : a) {
//            System.out.println(a);
//        }
//    }
//
//    public void drawVertex() {
//        List<Point2D.Double> array = graph.getPoint(address1, address2);
//        for (Point2D.Double x : array) {
//            System.out.println(x);
//        }
//    }

    public void addPoint(int xp, int yp) {
//        this.x = -x + (getWidth() / 2);
//        this.y = -y + (getHeight() / 2);
//        if (!array.contains(new Point(x, y))) {
//            array.add(new Point(x, y));
//        }
        array.add(new Point(xp, yp));
        repaint();
    }

    public void addRoads(Point t1, Point t2) {
        if(arrayRoad==null)
            arrayRoad=new ArrayList<Edge>();
        boolean isChoosed=false;
        Edge e1=new Edge(t2,t1);
        Edge e2=new Edge(t1,t2);
        if((!arrayRoad.contains(e1))&&(!arrayRoad.contains(e2)))
                arrayRoad.add(e1);
        repaint();
    }

    public void drawFrame(Graphics g, int colFrame, int rowFrame, int xframe, int yframe) {
        System.out.println("6" + xframe + " " + yframe);
        for (int i = (xframe / 256) - extend; i < (xframe / 256) + colFrame + extend; i += 1) {
            for (int j = (yframe / 256) - extend; j < (yframe / 256) + rowFrame + extend; j += 1) {
                if (i >= 0 && i < col && j >= 0 && j < row) {
                    Image image = new ImageIcon(images[j][i]).getImage();
                    g.setColor(Color.red);
                    g.drawImage(image, i * (256 + scale) + x, j * (256 + scale) + y, 256 + scale, 256 + scale, null);
                    //g.drawRect(i * (256 + scale) + x, j * (256 + scale) + y, 256 + scale, 256 + scale);
                    System.out.println("7" + i + " " + j);
                }
            }

        }
    }

    //public void drawFrameExtend
    public void paintComponent(Graphics g) {
        removeAll();
        g.setColor(Color.white); //colors the window
        g.fillRect(0, 0, getWidth(), getHeight());
        int colframe = 8;
        int rowframe = 5;
        System.out.println("8" + this.x + " " + this.y);

        int xframe = Math.abs(x);
        int yframe = Math.abs(y);
        System.out.println("9" + xframe + " " + yframe);
        int t = 0, k = 0;
        for (t = 0; t <= widthImage; t += 256) {
            if (t >= xframe) {
                break;
            }
        }
        for (k = 0; k <= heightImage; k += 256) {
            if (k >= yframe) {
                break;
            }
        }
        System.out.println("10" + t + " " + k);

        drawFrame(g, colframe, rowframe, t - 256, k - 256);

        int dk = 20;
        for (Point point : array) {
            System.out.println("Point" + point);
            g.setColor(Color.yellow);
            g.fillOval((int) point.getX() - (dk / 2) + this.x, (int) point.getY() - (dk / 2) + this.y, dk, dk);
            g.setColor(Color.red);
            g.drawOval((int) point.getX() - (dk / 2) + this.x, (int) point.getY() - (dk / 2) + this.y, dk, dk);
        }
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.green);
        for (Edge e : arrayRoad) {
            g2d.setStroke(new BasicStroke(10));
            g.drawLine((int) e.t2.getX() + x, (int) e.t2.getY() + y, (int) e.t1.getX() + x, (int) e.t1.getY() + y);
        }
    }
//        for (int i = xframe-widthFrame; i < xframe+widthFrame; i+=widthFrame) {
//            for (int j = yframe-heightFrame; j < yframe+heightFrame; j+=heightFrame) {
//                Image image=new ImageIcon(images[j/heightFrame][i/widthFrame]).getImage();
//                g.setColor(Color.red);
//                g.drawImage(image,x+i*256,y+j*256,256,256, null);
//                g.drawRect(x+i*256, y+j*256, 256, 256);
//            }
//        }0
//        for (int i = 0; i < colframe; i++) {
//            for (int j = 0; j < rowframe; j++) {
//                Image image=new ImageIcon(images[j][i]).getImage();
//                g.setColor(Color.red);
//                g.drawImage(image,x+i*256-scale/2,y+j*256-scale/2,widthImage+scale,heightImage+scale, null);
//                g.drawRect(x+i*256, y+j*256, 256, 256);
//            }
//        }

    public void removePoint() {
        array.remove(array.size() - 1);
        repaint();
    }

    public void removeAllList() {
        array = new ArrayList<Point>();
        repaint();
    }

    public void resetRoads() {
        arrayRoad = new ArrayList<Edge>();
        repaint();
    }
}

public class UpdateMap extends javax.swing.JFrame {

    /**
     * Creates new form Assignment2
     */
    public UpdateMap() {
        initComponents();
    }

    public static void addRoad(ArrayList<String> array, Point p1, Point p2) {
        int i1 = -1, i2 = -1;
        for (int i = 0; i < array.size(); i++) {
            String[] str1 = (array.get(i)).split("\t");
            String[] str2 = (array.get(i)).split("\t");
            if (Integer.parseInt(str1[str1.length - 2]) == p1.getX() && Integer.parseInt(str1[str1.length - 1]) == p1.getY()) {
                i1 = i;
            }
            if (Integer.parseInt(str2[str2.length - 2]) == p2.getX() && Integer.parseInt(str2[str2.length - 1]) == p2.getY()) {
                i2 = i;
            }
        }
        if (i1 != -1 && i2 != -1) {
            String[] str1 = (array.get(i1)).split("\t");
            String[] str2 = (array.get(i2)).split("\t");
            boolean bool1 = false;
            boolean bool2 = false;
            for (int i = 2; i < str1.length - 3; i += 2) {
                if (Integer.parseInt(str1[i]) == i2) {
                    bool1 = true;
                }
            }
            for (int i = 2; i < str2.length - 3; i += 2) {
                if (Integer.parseInt(str2[i]) == i1) {
                    bool2 = true;
                }
            }
            double w = dist2Point(p1, p2);
            String line1, line2;
            if (bool1 == false) {
                int vertexNext = Integer.parseInt(str1[0]);
                vertexNext += 1;
                line1 = "" + vertexNext + "\t" + w + "\t" + i2;
                for (int i = 1; i < str1.length; i++) {
                    line1 += "\t" + str1[i];
                }
                array.set(i1, line1);
            }

            if (bool2 == false) {
                int vertexNext = Integer.parseInt(str2[0]);
                vertexNext += 1;
                line2 = "" + vertexNext + "\t" + w + "\t" + i1;
                for (int i = 1; i < str2.length; i++) {
                    line2 += "\t" + str2[i];
                }
                array.set(i2, line2);
            }
        }
    }

    public static double dist2Point(Point t2, Point t1) {
        return Math.sqrt(Math.pow((t2.getX() - t1.getX()), 2) + Math.pow((t2.getY() - t1.getY()), 2));
    }
    public static Point getPoint2Line(Point p, Point s, Point e) {
        double ux = e.getX() - s.getX();
        double uy = e.getY() - s.getY();
        double nx = -uy;
        double ny = ux;
        double t1 = (p.getX() - s.getX()) / (ux - nx);
        double t2 = (p.getY() - s.getY()) / (uy - ny);
        double x1, x2, y1, y2;
        x1 = s.getX() + ux * t1;
        x2 = p.getX() + nx * t2;
        y1 = s.getY() + uy * t1;
        y2 = p.getY() + ny * t2;
        if (x1 == x2 && y1 == y2) {
            return new Point((int) x1, (int) y1);
        }
        return null;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jTextField3 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new UpMap(jTextField1, jTextField2, jTextField3, jLabel1);

        jButton4.setText("Clear");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Clear All");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Load Location");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("Load Road");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton1.setText("Add Location");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Add Road");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Update Road");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(47, 47, 47))
                            .addComponent(jTextField3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 87, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton7)
                .addGap(30, 30, 30))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 707, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        ArrayList<String> str = new ArrayList<String>();
        try {
            BufferedReader fr = new BufferedReader(new InputStreamReader(new FileInputStream("input/inputroad.txt"), "UTF-8"));
            int vertexNumber = Integer.parseInt(fr.readLine());
            String line;
            while ((line = fr.readLine()) != null) {
                str.add(line);
            }
            for (String s : str) {
                String[] t = s.split("\t");
                jPanel1.addPoint(Integer.parseInt(t[t.length - 2]), Integer.parseInt(t[t.length - 1]));
            }
            ArrayList<Point> points = jPanel1.getPoints();
            for (int i = 0; i < str.size(); i++) {
                String[] t = str.get(i).split("\t");
                if (Integer.parseInt(t[0]) != 0) {
                    Point t1 = points.get(i);
                    for (int j = 2; j < t.length - 2; j += 2) {
                        Point t2 = points.get(Integer.parseInt(t[j]));
                        jPanel1.addRoads(t1, t2);
                    }
                }
            }
            fr.close();

        } catch (IOException e) {
            jLabel1.setText("Fail");
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String address, name;
        int x, y;
        String[] a = (jTextField1.getText().trim()).split(" ");
        //try {
        x = Integer.parseInt(a[0]);
        y = Integer.parseInt(a[1]);
        //jPanel1.addPoint(x, y);
        name = jTextField2.getText().trim();
        address = jTextField3.getText().trim();
        try {
            BufferedWriter fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("input/input.txt", true), "UTF-8"));
            String line = "" + x + "\t" + y + "\t" + name + "\t" + address + "\n";
            fw.write(line);
            fw.close();
        } catch (IOException e) {
            jLabel1.setText("Fail");
        }
        jLabel1.setText("Done");
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        //} catch (Exception e) {
        //address = jTextField1.getText().trim();
        //}
//        try{
//            x=Double.parseDouble(a[0]);
//            y=Double.parseDouble(a[1]);
//            address=null;
//        }
//        catch(Exception e){
//            address=jTextField1.getText().trim();
//        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        ArrayList<String> str = new ArrayList<String>();
        try {
            BufferedReader fr = new BufferedReader(new InputStreamReader(new FileInputStream("input/inputroad.txt"), "UTF-8"));
            int vertexNumber = Integer.parseInt(fr.readLine());
            String line;
            while ((line = fr.readLine()) != null) {
                str.add(line);
            }
            for (Edge s : jPanel1.getRoads()) {
                UpdateMap.addRoad(str, s.t1, s.t2);
            }
            BufferedWriter fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("input/inputroad.txt"), "UTF-8"));
            fw.write("" + vertexNumber + "\n");
            for (String s : str) {
                fw.write(s + "\n");
            }
            fw.close();
            fr.close();

        } catch (IOException e) {
            jLabel1.setText("Fail");
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        ArrayList<String> str = new ArrayList<String>();
        try {
            BufferedReader fr = new BufferedReader(new InputStreamReader(new FileInputStream("input/inputroad.txt"), "UTF-8"));
            //int vertexNumber = Integer.parseInt(fr.readLine());
            String line;
            while ((line = fr.readLine()) != null) {
                str.add(line);
            }
            String name;
            int x, y;
            String[] a = (jTextField1.getText().trim()).split(" ");
            //try {
            x = Integer.parseInt(a[0]);
            y = Integer.parseInt(a[1]);
            //jPanel1.addPoint(x, y);
            name = jTextField2.getText().trim();
            BufferedWriter fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("input/inputroad.txt"), "UTF-8"));
            String out = "" + 0 + "\t" + name + "\t" + x + "\t" + y;
            str.add(out);
            String n = "" + (str.size() - 1);
            str.set(0, n);
            System.out.println(str.get(0));
            for (String l : str) {
                fw.write(l + "\n");
            }
            fw.close();
            fr.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        jPanel1.removePoint();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        jPanel1.removeAllList();
        jPanel1.resetRoads();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        try {
            BufferedReader fr = new BufferedReader(new InputStreamReader(new FileInputStream("input/input.txt"), "UTF-8"));
            String line;
            while ((line = fr.readLine()) != null) {
                String a[] = line.split("\t");
                int x = Integer.parseInt(a[0]);
                int y = Integer.parseInt(a[1]);
                jPanel1.addPoint(x, y);
            }
            fr.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String [] args){
        new UpdateMap().setVisible(true);
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private UpMap jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}
