/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roadmap;

import java.awt.Point;

/**
 *
 * @author Sub4sa
 */
public class Edge {

    public Point t2, t1;
    public Edge() {
    }
    public Edge(Point t2, Point t1) {
        this.t2 = t2;
        this.t1 = t1;
    }
}