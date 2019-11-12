/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.awt.Point;

/**
 *
 * @author Sub4sa
 */
public class Test {
    public static double dist2Point(Point t2, Point t1) {
        return Math.sqrt(Math.pow((t2.getX() - t1.getX()), 2) + Math.pow((t2.getY() - t1.getY()), 2));
    }
    public static void main(String[] args) {
        Point p = new Point(2,1);
        Point s = new Point(1, 1);
        System.out.print(dist2Point(p,s));
    }
}
