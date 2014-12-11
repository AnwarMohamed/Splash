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
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;

public class EquilateralTriangle extends DimensionedTool {

    public EquilateralTriangle() {
        super();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Shape drawTriangle = new Polygon(
                new int[]{getX(),
                    getX() + Math.max(getWidth(), getHeight()),
                    (getX() + Math.max(getWidth(), getHeight())) / 2},
                new int[]{getY(),
                    getY(),
                    getY() * (1 + (int) Math.sqrt(3) / 2)},
                3);

        Dimension size = this.getSize();
        Point p1 = new Point(size.width / 3, (2 * size.height) / 3);
        Point p2 = new Point(size.width / 2, size.height / 3);
        Point p3 = new Point((2 * size.width) / 3, (2 * size.height) / 3);
        int[] x = {p1.x, p2.x, p3.x};
        int[] y = {p1.y, p2.y, p3.y};
        Polygon triangle = new Polygon(x, y, x.length);
        graph.draw(triangle);
        //graph.draw(drawTriangle);
    }

    @Override
    public EquilateralTriangle newInstance() {
        return new EquilateralTriangle();
    }
}
