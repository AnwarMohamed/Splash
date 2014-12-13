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
package com.splash.gui.elements;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

public abstract class DimensionedTool extends Tool {

    private int width;
    private int height;

    public DimensionedTool() {
        super();
        width = 0;
        height = 0;
    }

    @Override
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        if (isSelected()) {
            graph.setStroke(
                    new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
                            BasicStroke.JOIN_MITER, 10.0f,
                            new float[]{3.0f}, 0.0f));
            Shape drawRect = new Rectangle2D.Float(
                    getX(), getY(), getWidth(), getHeight());
            graph.draw(drawRect);
            graph.setStroke(new BasicStroke(getBorderSize()));
        }
    }
}
