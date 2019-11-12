/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Sub4sa
 */
class Map extends JPanel {

    private String address1, address2;
    private int x, y, x1, y1, x2, y2;
    private int row, col;
    public int widthImage, heightImage, widthFrame, heightFrame;
    private int scale;
    private int extend;
    private String images[][];
    private Point p;
    private ArrayList<Point> array;
    private ArrayList<Point> arrayNear;
    private ArrayList<Edge> arrayNearRoad;
    private ArrayList<String> arraySPRoad;
    private ArrayList<Point> arrayPointPath;
    private ArrayList<Edge> arrayPath;
    private ArrayList<Point> arrayPRoad;
    private ArrayList<Edge> arrayRoad;
    private ArrayList<String> address;
    private JTextField text1;
    //private JLabel jLabel1;
    private Point t1, t2;
    public static boolean pointInRoad(Point p,Edge e){
        Point average=new Point ((int)(((e.t1).getX()+(e.t2).getX())/2),(int)(((e.t1).getY()+(e.t2).getY())/2));
        double wt1=dist2Point(average, e.t1);
        double wp=dist2Point(average, p);
        //System.out.println("wt1:"+wt1 +" wp:"+wp+"true:"+(wp<=wt1));
        if(wp<=wt1)
            return true;
        return false;
    }
    public static void getPoints(ArrayList<Point> array) {
        try {
            BufferedReader fr = new BufferedReader(new InputStreamReader(new FileInputStream("input/input.txt"), "UTF-8"));
            String line;
            while ((line = fr.readLine()) != null) {
                String a[] = line.split("\t");
                int x = Integer.parseInt(a[0]);
                int y = Integer.parseInt(a[1]);
                array.add(new Point(x, y));
            }
            fr.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void addRoad(Point t1, Point t2, ArrayList<Edge> arrayRoad) {
        Edge e1 = new Edge(t2, t1);
        Edge e2 = new Edge(t1, t2);
        if ((!arrayRoad.contains(e1)) && (!arrayRoad.contains(e2))) {
            arrayRoad.add(e1);
        }
    }
    public static String getRoad(Edge e,ArrayList<Point> arrayPRoad, ArrayList<String> arraySPRoad) {
        String t="";
        for(int i=0;i<arrayPRoad.size();i++){
            if(e.t1.equals(arrayPRoad.get(i)))
                t+=arraySPRoad.get(i)+" ";
            if(e.t2.equals(arrayPRoad.get(i)))
                t+=arraySPRoad.get(i)+" ";
        }
        return t;
    }
    public static void getRoads(ArrayList<Point> arrayPRoad, ArrayList<Edge> arrayRoad, ArrayList<String> arraySPRoad) {
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
                arrayPRoad.add(new Point(Integer.parseInt(t[t.length - 2]), Integer.parseInt(t[t.length - 1])));
                arraySPRoad.add(t[t.length - 3]);
            }
            for (int i = 0; i < str.size(); i++) {
                String[] t = str.get(i).split("\t");
                if (Integer.parseInt(t[0]) != 0) {
                    Point t1 = arrayPRoad.get(i);
                    for (int j = 2; j < t.length - 2; j += 2) {
                        Point t2 = arrayPRoad.get(Integer.parseInt(t[j]));
                        addRoad(t1, t2, arrayRoad);
                    }
                }
            }
            fr.close();

        } catch (IOException e) {

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
        double[][] matrix =new double[2][3];
        matrix[0][0]=ux;
        matrix[0][1]=-nx;
        matrix[0][2]=(p.getX()-s.getX());
        matrix[1][0]=uy;
        matrix[1][1]=-ny;
        matrix[1][2]=(p.getY()-s.getY());
        double t1=1000000000.0;
        double t2=1000000000.0;
        //System.out.println(p+" "+ s+ " "+e);
        if(matrix[0][0]!=0){
            double heso=matrix[1][0]/matrix[0][0];
            for(int i=0;i<=2;i++){
             matrix[1][i]=matrix[1][i]-matrix[0][i]*heso;
            }
            //System.out.println(matrix[1][1]+" "+matrix[1][2]);
            t2=matrix[1][2]/matrix[1][1];
            t1=(matrix[0][2]-(matrix[0][1]*t2))/matrix[0][0];
            //System.out.println(t1+" "+t2);
        }
        else if(matrix[1][0]!=0){
            double heso=matrix[0][0]/matrix[1][0];
            for(int i=0;i<=2;i++){
             matrix[0][i]=matrix[0][i]-matrix[1][i]*heso;
            }
            t2=matrix[0][2]/matrix[0][1];
            t1=(matrix[1][2]-(matrix[0][1]*t2))/matrix[1][0];
        }
        double x1, x2, y1, y2;
        x1 = s.getX() + ux * t1;
        x2 = p.getX() + nx * t2;
        y1 = s.getY() + uy * t1;
        y2 = p.getY() + ny * t2;
        //System.out.println(t1+" "+t2+" "+x1+" "+x2+ " "+y1+" "+y2);
        if (x1 == x2 && y1 == y2) {
            return new Point((int) x1, (int) y1);
        }
        return null;
    }
    public static double getDist2Line(Point p, Point s, Point e) {
        Point point=getPoint2Line(p, s, e);
        //System.out.println("+++++"+point);
        if(point!=null&&pointInRoad(point,new Edge(s,e)))
            return dist2Point(p, point);
        return 100000000000.0;
    }
    public static Point getPoint(Point p,ArrayList<Edge> arrayRoad,ArrayList<Point>array,ArrayList<String> arraySP,ArrayList<Point> arrayPRoad,ArrayList<Edge> arrayNearRoad) {
//        ArrayList <Double> w=new ArrayList<Double>();
//        for(Edge e:arrayRoad){
//            w.add(getDist2Line(p,e.t1,e.t2));
//            //System.out.println("dis:"+getDist2Line(p,e.t1,e.t2));
//        }
//        System.out.print(w);
//        for(Edge e:arrayRoad){
//             if(getPoint2Line(p,e.t1,e.t2)!=null)
//                array.add(getPoint2Line(p,e.t1,e.t2));
//                System.out.print(Map.getRoad(e,arrayP,arraySP));
//        }
        ArrayList <Double> w=new ArrayList<Double>();
        for(Edge e:arrayRoad){
            w.add(getDist2Line(p,e.t1,e.t2));
        }
        int min=0;
        for(int i=1;i<w.size();++i)
            if(w.get(min)>w.get(i))
                min=i;
        arrayNearRoad.add(arrayRoad.get(min));
        Point t=getPoint2Line(p, arrayRoad.get(min).t2, arrayRoad.get(min).t1);
        if(t!=null)
            return t;
        return new Point();
    }
    public void addLocation(Point p){
        if(array.size()>=2){
            array.clear();
            arrayNear.clear();
            arrayNearRoad.clear();
            arrayPath.clear();
            arrayPointPath.clear();
        }
        array.add(p);
        arrayNear.add(Map.getPoint(p,arrayRoad,array,arraySPRoad,arrayPRoad,arrayNearRoad));
        x=-(int)(p.getX()-(this.getWidth()/2));
        y=-(int)(p.getY()-(this.getHeight()/2));
        repaint();
    }
    Map(JTextField text1) {
        super();
        this.text1 = text1;
        col = 138;
        row = 140;
        scale = 0;
        extend = 0;
        widthFrame = 256;
        heightFrame = 256;
        widthImage = col * widthFrame;
        heightImage = row * heightFrame;
        array = new ArrayList<Point>();
        arrayNear = new ArrayList<Point>();
        arrayNearRoad = new ArrayList<Edge>();
        arrayPointPath = new ArrayList<Point>();
        arrayPath = new ArrayList<Edge>();
        //Map.getPoints(arrayP);
        arraySPRoad = new ArrayList<String>();
        arrayPRoad = new ArrayList<Point>();
        arrayRoad = new ArrayList<Edge>();
        
        Map.getRoads(arrayPRoad, arrayRoad,arraySPRoad);
        try {
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
            addMouseWheelListener(new MouseAdapter(){
                @Override
                public void mouseWheelMoved(MouseWheelEvent e) {
                    super.mouseWheelMoved(e); //To change body of generated methods, choose Tools | Templates.
//                    if(e.getWheelRotation()==MouseWheelEvent.)
//                        System.out.println("Fuck");
                }
                
            });
            addMouseListener(new MouseAdapter() {

                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e); //To change body of generated methods, choose Tools | Templates.

                    if (e.getButton() == MouseEvent.BUTTON3) {
                        Point point = e.getPoint();
                        x1 = (int) point.getX();
                        y1 = (int) point.getY();
                        //System.out.println("Point1:" + point);
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    super.mouseReleased(e);
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        if(array.size()>=2){
                            array.clear();
                            arrayNear.clear();
                            arrayNearRoad.clear();
                            arrayPath.clear();
                            arrayPointPath.clear();
                            repaint();
                        }
                        Point point = e.getPoint();
                        //System.out.println(point);
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
                                    //System.out.println("Dường tròn" + w);
                                    if (w <= 0) {
                                        array.remove(i);
                                        arrayNear.remove(i);
                                        arrayNearRoad.remove(i);
                                        isCleared = true;
                                        break;
                                    }
                                }
                                if (isCleared == false) {
                                    array.add(new Point(xp, yp));
                                    arrayNear.add(Map.getPoint(new Point(xp, yp),arrayRoad,array,arraySPRoad,arrayPRoad,arrayNearRoad));
                                    text1.setText(xp + " " + yp);
//                                    text2.setText("");
//                                    text3.setText("");
//                                    jLabel1.setText("");
                                }
                            } else {
                                array.add(new Point(xp, yp));
                                arrayNear.add(Map.getPoint(new Point(xp, yp),arrayRoad,array,arraySPRoad,arrayPRoad,arrayNearRoad));
                                text1.setText(xp + " " + yp);
//                                text2.setText("");
//                                text3.setText("");
//                                jLabel1.setText("");
                            }
                            repaint();
                        }
                    }
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        //To change body of generated methods, choose Tools | Templates.
                        Point t = e.getPoint();
                        x2 = (int) t.getX();
                        y2 = (int) t.getY();
                        //System.out.println("Point2:" + t);
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

    public void addPoint(int xp, int yp) {
//        this.x = -x + (getWidth() / 2);
//        this.y = -y + (getHeight() / 2);
//        if (!array.contains(new Point(x, y))) {
//            array.add(new Point(x, y));
//        }
        array.add(new Point(xp, yp));
        repaint();
    }

    public int getScale() {
        return scale;
    }

    public int getExtend() {
        return extend;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public void setExtend(int extend) {
        this.extend = extend;
    }
    
    public void addRoads(Point t1, Point t2) {
        if (arrayRoad == null) {
            arrayRoad = new ArrayList<Edge>();
        }
        boolean isChoosed = false;
        Edge e1 = new Edge(t2, t1);
        Edge e2 = new Edge(t1, t2);
        if ((!arrayRoad.contains(e1)) && (!arrayRoad.contains(e2))) {
            arrayRoad.add(e1);
        }
        repaint();
    }
    
    public void drawFrame(Graphics g, int colFrame, int rowFrame, int xframe, int yframe) {
        //System.out.println("6" + xframe + " " + yframe);
        for (int i = (xframe / 256) - extend; i < (xframe / 256) + colFrame + extend; i += 1) {
            for (int j = (yframe / 256) - extend; j < (yframe / 256) + rowFrame + extend; j += 1) {
                if (i >= 0 && i < col && j >= 0 && j < row) {
                    Image image = new ImageIcon(images[j][i]).getImage();
                    g.setColor(Color.red);
                    g.drawImage(image, i * (256 + scale) + x, j * (256 + scale) + y, 256 + scale, 256 + scale, null);
                    //g.drawRect(i * (256 + scale) + x, j * (256 + scale) + y, 256 + scale, 256 + scale);
                    //System.out.println("7" + i + " " + j);
                }
            }

        }
    }
    public ArrayList<Point> getArrayNear(){
        return arrayNear;
    }
    public ArrayList<Edge> getArrayNearRoad(){
        return arrayNearRoad;
    }
    public void setPath(List<Point>path) {
        arrayPointPath.clear();
        arrayPath.clear();
        for(int i=0;i<path.size();i++){
            arrayPointPath.add(path.get(i));
        }
        for(int i=0;i<path.size()-1;i++){
            arrayPath.add(new Edge(path.get(i),path.get(i+1)));
        }
        repaint();
    }

    public void paintComponent(Graphics g) {
        removeAll();
        g.setColor(Color.white); //colors the window
        g.fillRect(0, 0, getWidth(), getHeight());
        int colframe = 8;
        int rowframe = 5;
        //System.out.println("8" + this.x + " " + this.y);

        int xframe = Math.abs(x);
        int yframe = Math.abs(y);
        //System.out.println("9" + xframe + " " + yframe);
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
        //System.out.println("10" + t + " " + k);

        drawFrame(g, colframe, rowframe, t - 256, k - 256);

        int dk = 20;
        for (Point point : array) {
            //System.out.println("Point" + point);
            g.setColor(Color.yellow);
            g.fillOval((int) point.getX() - (dk / 2) + this.x, (int) point.getY() - (dk / 2) + this.y, dk, dk);
            g.setColor(Color.red);
            g.drawOval((int) point.getX() - (dk / 2) + this.x, (int) point.getY() - (dk / 2) + this.y, dk, dk);
        }
        Graphics2D g2d = (Graphics2D) g;
//        g2d.setColor(Color.green);
//        for (Point point : arrayPRoad) {
//            //System.out.println("Point" + point);
//            g.setColor(Color.yellow);
//            g.fillOval((int) point.getX() - (dk / 2) + this.x, (int) point.getY() - (dk / 2) + this.y, dk, dk);
//            g.setColor(Color.red);
//            g.drawOval((int) point.getX() - (dk / 2) + this.x, (int) point.getY() - (dk / 2) + this.y, dk, dk);
//        }
//        for (Edge e : arrayRoad) {
//            g.setColor(Color.green);
//            g2d.setStroke(new BasicStroke(10));
//            g.drawLine((int) e.t2.getX() + x, (int) e.t2.getY() + y, (int) e.t1.getX() + x, (int) e.t1.getY() + y);
//        }
        for (Point point : arrayPointPath) {
            //System.out.println("Point" + point);
            g.setColor(Color.green);
            g.fillOval((int) point.getX() - (dk / 2) + this.x, (int) point.getY() - (dk / 2) + this.y, dk, dk);
            g.setColor(Color.black);
            g.drawOval((int) point.getX() - (dk / 2) + this.x, (int) point.getY() - (dk / 2) + this.y, dk, dk);
        }

        for (Edge e : arrayPath) {
            g.setColor(Color.red);
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

}

public class Assignment2 extends javax.swing.JFrame {

    /**
     * Creates new form Assignment2
     */
    ArrayList<Point> array;
    ArrayList<String> arrayName;
    ArrayList<String> arrayAdrress;
    public Assignment2() {
        array=new ArrayList<Point>();
        arrayName=new ArrayList<String>();
        arrayAdrress=new ArrayList<String>();
        try {
            BufferedReader fr = new BufferedReader(new InputStreamReader(new FileInputStream("input/input.txt"), "UTF-8"));
            String line;
            while ((line = fr.readLine()) != null) {
                String a[] = line.split("\t");
                int x = Integer.parseInt(a[0]);
                int y = Integer.parseInt(a[1]);
                array.add(new Point(x, y));
                arrayName.add(a[2]);
                arrayAdrress.add(a[3]);
            }
            fr.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(arrayName);
        initComponents();
        
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
        jTextField2 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jTextField4 = new javax.swing.JTextField();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel1 = new Map(jTextField2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jLabel1.setText("jLabel1");

        jLabel2.setText("jLabel1");

        jButton1.setText("Search");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Search");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Update Map");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Find");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton2)
                    .addComponent(jButton1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTextField2)
                                .addComponent(jComboBox2, 0, 173, Short.MAX_VALUE)
                                .addComponent(jTextField4)
                                .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jButton4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addGap(0, 20, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jButton1)
                .addGap(47, 47, 47)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(jButton4)
                .addGap(47, 47, 47)
                .addComponent(jButton3)
                .addGap(74, 74, 74))
        );

        jComboBox1.removeAllItems();
        for(String s:arrayName){
            jComboBox1.addItem(s);
        }
        //jComboBox1.setSize(64, 22);
        jComboBox2.removeAllItems();
        for(String s:arrayName){
            jComboBox2.addItem(s);
        }

        //jComboBox2.setSize(64, 22);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 616, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 509, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int index=0;
//        if(!jTextField2.getText().equals(""))
//            for(int i=0;i<arrayAdrress.size();i++)
//                if(jTextField2.getText().equals(arrayAdrress.get(i)))
//                    index=i;
//        else
            index = jComboBox1.getSelectedIndex();
        System.out.println(index);
        Point p=array.get(index);
        jPanel1.addLocation(p);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        int index=0;
//        if(!jTextField4.getText().equals("")||jTextField2.getText()!=null)
//            for(int i=0;i<arrayAdrress.size();i++)
//                if(jTextField4.getText().equals(arrayAdrress.get(i)))
//                    index=i;
//        else
            index = jComboBox2.getSelectedIndex();
        System.out.println(index);
        Point p=array.get(index);
        jPanel1.addLocation(p);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        UpdateMap um = new UpdateMap();
        um.setSize(1016, 538);
        um.setLocationRelativeTo(this);
        um.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        um.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        ShortestPath path=new ShortestPath("input/inputroad.txt"); 
        List<Point> result = path.getVertexPath(jPanel1.getArrayNear().get(0), jPanel1.getArrayNearRoad().get(0), jPanel1.getArrayNear().get(1), jPanel1.getArrayNearRoad().get(1));
        if(jPanel1.getArrayNearRoad().get(0).equals(jPanel1.getArrayNearRoad().get(1))){
            result.clear();
            result.add(jPanel1.getArrayNear().get(0));
            result.add(jPanel1.getArrayNear().get(1));
        }
        jPanel1.setPath(result);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Assignment2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Assignment2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Assignment2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Assignment2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Assignment2().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private Map jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}
