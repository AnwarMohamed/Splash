/*
 *  Copyright (C) 2014
 *                      Abdallah Elerian  <abdallah.elerian@gmail.com>
 *                      Anwar Mohamed     <anwarelmakrahy@gmail.com>
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

import com.splash.gui.elements.DimensionedTool;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Shape;

public class Triangle extends DimensionedTool {

    private int x1, x2, x3, y1, y2, y3;

    public Triangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        super();
        this.x1 = x1;
        this.x2 = x2;
        this.x3 = x3;
        this.y1 = y1;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;
    }

    public Triangle() {
        super();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Shape drawTriangle = new Polygon(new int[]{x1, x2, x3}, new int[]{y1, y2, y3}, 3);
        graph.draw(drawTriangle);
    }

    @Override
    public Triangle newInstance() {
        return new Triangle();
    }
}