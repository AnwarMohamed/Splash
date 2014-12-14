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
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

public abstract class DimensionedTool extends Tool {

    private int width;
    private int height;
    private int baseX, baseY;

    public int getBaseX() {
        return baseX;
    }

    public int getBaseY() {
        return baseY;
    }

    public void setBaseCoordinates(int x, int y) {
        baseX = x;
        baseY = y;
    }

    public DimensionedTool() {
        super();
        width = 0;
        height = 0;
        baseX = 0;
        baseY = 0;
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
    public void drawResizePoint(int x, int y, Graphics2D graph2d) {
        graph2d.setColor(Color.BLACK);
        graph2d.drawOval(x - 3, y - 3, 7, 7);
        graph2d.setColor(Color.WHITE);
        graph2d.fillOval(x - 2, y - 2, 6, 6);
    }

    @Override
    public void drawResizePoints(Graphics2D graph2d) {
        drawResizePoint(getX(), getY(), graph2d);
        drawResizePoint(getX() + getWidth() / 2, getY(), graph2d);
        drawResizePoint(getX() + getWidth(), getY(), graph2d);
        drawResizePoint(getX(), getY() + getHeight(), graph2d);
        drawResizePoint(getX(), getY() + getHeight() / 2, graph2d);
        drawResizePoint(getX() + getWidth(), getY() + getHeight(), graph2d);
        drawResizePoint(getX() + getWidth() / 2, getY() + getHeight(), graph2d);
        drawResizePoint(getX() + getWidth(), getY() + getHeight() / 2, graph2d);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (isSelected()) {
            graph.setColor(Color.BLACK);
            graph.setStroke(
                    new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
                            BasicStroke.JOIN_MITER, 10.0f,
                            new float[]{3.0f}, 0.0f));
            Shape drawRect = new Rectangle2D.Float(
                    getX(), getY(), getWidth(), getHeight());
            graph.draw(drawRect);
            graph.setStroke(new BasicStroke(getBorderSize()));
            drawResizePoints(graph);
            graph.setColor(getColor());
        }
    }
}
