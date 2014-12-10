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

import com.splash.gui.elements.Tool;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.Line2D;

public class Line extends Tool {

    private int endX, endY;

    public int getEndX() {
        return endX;
    }

    public int getEndY() {
        return endY;
    }

    public Line() {
        super();
        endX = endY = 0;
    }

    public Line(int x, int y) {
        super();
        endX = endY = 0;
        setLocation(x, y);
    }

    public void setEndPoint(int x, int y) {
        endX = x;
        endY = y;
    }

    @Override
    public void setCoordinates(int x, int y) {
        super.setCoordinates(x, y);
        setEndPoint(x, y);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Shape drawline = new Line2D.Float(
                getX(), getY(), getEndX(), getEndY());
        graph.draw(drawline);
    }

    @Override
    public Line newInstance() {
        return new Line();
    }
}
