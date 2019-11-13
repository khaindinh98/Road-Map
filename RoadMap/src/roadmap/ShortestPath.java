/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roadmap;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sub4sa
 */
class Type<T, E, Y> {

    public T ID;
    public E value;
    public boolean visited;

    public Type(T ID, E value, boolean visited) {
        this.ID = ID;
        this.value = value;
        this.visited = visited;
    }
}

public class ShortestPath {

    private final double MAXWEIGHT = 1000000;
    private int vertexNumber;
    private String[] vertexName;
    private double[][] graphMatrix;
    private List<Integer>[] graphList;
    private Point[] vertexPosition;

    public ShortestPath(String input) {
        try {
            BufferedReader fr = new BufferedReader(new InputStreamReader(new FileInputStream(input), "UTF-8"));

            vertexNumber = Integer.parseInt(fr.readLine());
            graphMatrix = new double[vertexNumber + 2][vertexNumber + 2];
            vertexName = new String[vertexNumber + 2];
            graphList = new ArrayList[vertexNumber + 2]; //danh sách kề
            for (List<Integer> x : graphList) {
                x = new ArrayList<Integer>();
            }

            vertexPosition = new Point[vertexNumber + 2];
            for (int i = 0; i < vertexNumber + 2; ++i) {
                for (int j = 0; j < vertexNumber + 2; ++j) {
                    if (i == j) {
                        graphMatrix[i][j] = 0;
                    } else {
                        graphMatrix[i][j] = MAXWEIGHT;
                    }
                }
            }
            String[] line;
            for (int i = 0; i < vertexNumber; ++i) {
                line = ((String) fr.readLine()).split("\t");
                int n = Integer.parseInt(line[0]);
                graphList[i] = new ArrayList<Integer>();
                for (int j = 1; j <= (n * 2); j += 2) {
                    int k = Integer.parseInt(line[j+1]); //mỗi đỉnh kề của đỉnh i
                    //System.out.print(k+" ");
                    double w = Double.parseDouble(line[j]); //trọng số đính kèm từ đỉnh i đến đỉnh k
                    //System.out.print(w+" ");
                    graphList[i].add(k);
                    graphMatrix[i][k] = w;
                }
                vertexName[i] = line[n * 2 + 1]; //tên đỉnh
                vertexPosition[i] = new Point();
                vertexPosition[i].setLocation(Double.parseDouble(line[line.length - 2]), Double.parseDouble(line[line.length - 1])); //tọa độ x
                System.out.println(vertexPosition[i]);
                
            }
            fr.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private double Dijkstra(int start, int end, int[] parent) {
        double[] distance = new double[vertexNumber + 3];
        boolean[] visited = new boolean[vertexNumber +3];
        for (int i = 0; i < vertexNumber + 2; ++i) {
            distance[i] = graphMatrix[start][i];
            if(graphMatrix[start][i]!=MAXWEIGHT)
                parent[i] = start;
            else
                parent[i]=-1;
            visited[i] = false;
        }
        parent[start]=-2;
        visited[start] = true;
        distance[start] = MAXWEIGHT;
        distance[vertexNumber+2] = MAXWEIGHT;
        for(int x:parent)
            System.out.print(x+" ");
        System.out.println();
        for(double x:distance)
            System.out.print(x+" ");
        while (true) {
            for(int x:parent)
                System.out.print(x+" ");
            System.out.println();
            for(double x:distance)
                System.out.print(x+" ");
            System.out.println();
            for(boolean x:visited)
                if(x==true)
                    System.out.print(1+" ");
                else
                    System.out.print(0+" ");
            int min = vertexNumber+2;
            for (int i = vertexNumber +2; i >= 0; --i) {
                if (visited[i] == false && distance[i] < distance[min]) {
                    min = i;
                }
            }
            System.out.println("min:"+min+"!");
            if (min == vertexNumber+2 || min == end) {
                break;
            }
            
            int v = min;
            visited[v] = true;
            System.out.println(graphList[v]);
            for (Integer u : graphList[v]) {
                System.out.print(graphMatrix[v][u]+" ");
                Double sum = distance[v] + graphMatrix[v][u];
                if (visited[u] == false && distance[u] > sum) {
                    distance[u] = sum;
                    parent[u] = v;
                }
            }
             System.out.println();
        }
        return distance[end];
    }
//    private int[] Dijkstra(int start, int end) {
//        int[] parent=new int[vertexNumber];
//        double [] distance=new double [vertexNumber];
//        boolean[] visited = new boolean[vertexNumber];
//        for(int i=0;i<vertexNumber;i++){
//            parent[i]=-1;
//            distance[i]=MAXWEIGHT;
//            visited[i]=false;
//        }
//        int u=start;
//        while(true){
//            for(Integer k : graphList[u]){
//                if(distance[k]==MAXWEIGHT)
//                distance[k]=graphMatrix[u][k];
//            }
//                
//        
//        }
//        return parent;
//    }
    private void printMatrix(){
        for(int i=0;i<vertexNumber+2;i++){
            for(int j=0;j<vertexNumber+2;j++){
                System.out.print(graphMatrix[i][j]+" ");
            }
            System.out.println();
        }
    }
    private List<Point> getVertexPath(int start, int end, int[] parent) {
        for(int x:parent)
            System.out.print(x+" ");
        List<Point> result = new ArrayList<Point>();
        int temp = end;
        while (temp != start) {
            result.add(vertexPosition[temp]);
            temp = parent[temp];
        }
        result.add(vertexPosition[temp]);
        //Collections.reverse(result);
        //System.out.println(vertexPosition);
        //System.out.println(result);
        return result;
    }

    public static double dist2Point(Point t2, Point t1) {
        return Math.sqrt(Math.pow(t2.getX() - t1.getX(), 2) + Math.pow(t2.getY() - t1.getY(), 2));
    }

    public List <Point> getVertexPath(Point p1, Edge e1, Point p2, Edge e2) {
        printMatrix();
        int i11 = -1, i12 = -1, i21 = -1, i22 = -1;
        //System.out.println(vertexNumber);
        for (int i = 0; i < vertexNumber; ++i) {
           // System.out.println(i);
            Point x = vertexPosition[i];
            if(x!=null){
                if (x.equals(e1.t1)) {
                    i11 = i;
                }
                if (x.equals(e1.t2)) {
                    i12 = i;
                }
                if (x.equals(e2.t1)) {
                    i21 = i;
                }
                if (x.equals(e2.t2)) {
                    i22 = i;
                }
            }
        }
        printMatrix();
        System.out.println(i11+" "+i12+" "+i21+" "+i22);
        graphList[vertexNumber] = new ArrayList<Integer>();
        graphList[vertexNumber+1] = new ArrayList<Integer>();
        vertexPosition[vertexNumber]=p1;
        vertexPosition[vertexNumber+1]=p2;
        graphList[i11].add(vertexNumber);
        graphList[i12].add(vertexNumber);
        graphList[i21].add(vertexNumber+1);
        graphList[i22].add(vertexNumber+1);
        graphList[vertexNumber].add(i11);
        graphList[vertexNumber].add(i12);
        graphList[vertexNumber+1].add(i21);
        graphList[vertexNumber+1].add(i22);
        graphMatrix[vertexNumber][i11] = dist2Point(p1, e1.t1);
        graphMatrix[vertexNumber][i12] = dist2Point(p1, e1.t2);
        graphMatrix[i11][vertexNumber] = dist2Point(p1, e1.t1);
        graphMatrix[i12][vertexNumber] = dist2Point(p1, e1.t2);
        graphMatrix[vertexNumber+1][i21] = dist2Point(p2, e2.t1);
        graphMatrix[vertexNumber+1][i22] = dist2Point(p2, e2.t2);
        graphMatrix[i21][vertexNumber+1] = dist2Point(p2, e2.t1);
        graphMatrix[i22][vertexNumber+1] = dist2Point(p2, e2.t2);
        System.out.println("Out");
        printMatrix();
        int[] parent = new int[vertexNumber+2];
        Dijkstra(vertexNumber, vertexNumber + 1, parent);
        System.out.println(getVertexPath(vertexNumber, vertexNumber + 1, parent));
        return getVertexPath(vertexNumber, vertexNumber + 1, parent);
    }
}
