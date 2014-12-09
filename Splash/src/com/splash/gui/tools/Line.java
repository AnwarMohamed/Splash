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
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Line2D;

public class Line extends Tool {

    private int x, y;

    public Line() {
        super();
    }
    
    public Line(int x, int y) {
        super();
        this.x = x;
        this.y = y;
    }

    public Line(int x, int y, int x1, int y1) {
        super();

        setLocation(x, y);
        this.x = x1;
        this.y = y1;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D graph = (Graphics2D) g;
        graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Shape drawline = new Line2D.Float(getX(), getY(), x, y);
        graph.draw(drawline);
    }

    @Override
    public Tool newInstance() {
        return new Line(0, 0);
    }

}
