/*
 *  Copyright (C) 2014
 *                      Anwar Mohamed     <anwarelmakrahy@gmail.com>
 *                      Abdallah Elerian  <abdallah.elerian@gmail.com>
 *                      Moataz Hammouda   <moatazhammouda4@gmail.com>
 *                      Yasmine Elhabashi <yasmine.elhabashi@gmail.com>
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to authors.
 *
 */
package com.splash.gui.tools;

import com.splash.gui.elements.PixelTool;
import com.splash.gui.elements.Tool;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.util.ArrayList;

/**
 *
 * @author Abdallah
 */
public class FreeHand extends PixelTool {

        ArrayList<Point> point = new ArrayList<Point>();

    public FreeHand(int x, int y) {
        super();
    }
    
    public FreeHand(){
        
    }

    public void paint(Graphics g) {
        super.paint(g);
        /*Shape drawPoint = new Line2D.Float(
        getX(), getY(),getX(),getY());
        graph.draw(drawPoint);
        */
        for (int i = 0; i < point.size() - 1; i++)
            g.drawLine((int)point.get(i).getX(),/* arr[i].y*/(int) point.get(i).getY(),(int) point.get(i+1).getX(), (int) point.get(i+1).getY());
    }

    /**
     *
     * @return
     */
    public FreeHand newInstance() {
        return new FreeHand();
    }

}
