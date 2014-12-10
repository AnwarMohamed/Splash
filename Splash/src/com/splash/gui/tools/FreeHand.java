/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.splash.gui.tools;

import com.splash.gui.elements.Tool;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.Line2D;

/**
 *
 * @author Abdallah
 */
public class FreeHand extends Tool {

    public FreeHand(int x, int y) {
        super();
    }
    
    public FreeHand(){
        
    }

    public void paint(Graphics g) {
        super.paint(g);
        Shape drawPoint = new Line2D.Float(
                getX(), getY(),getX(),getY());
        graph.draw(drawPoint);
    }

    @Override
    public Tool newInstance() {
        return new FreeHand();
    }

}
