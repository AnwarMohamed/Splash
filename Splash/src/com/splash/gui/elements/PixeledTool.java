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
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public abstract class PixeledTool extends Tool {

    private ArrayList<Point> pixels = new ArrayList<>();
    private int minX = Integer.MAX_VALUE;
    private int maxX = Integer.MIN_VALUE;
    private int minY = Integer.MAX_VALUE;
    private int maxY = Integer.MIN_VALUE;

    public int getMinY() {
        return minY;
    }

    public int getMinX() {
        return minX;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    public ArrayList<Point> getPixels() {
        return pixels;
    }

    public void setPixels(ArrayList<Point> pixels) {
        this.pixels.addAll(pixels);
    }

    @Override
    public void setCoordinates(int x, int y) {
        super.setCoordinates(x, y);
        pixels.add(new Point(x, y));

        minX = Math.min(minX, x);
        maxX = Math.max(maxX, x);
        minY = Math.min(minY, y);
        maxY = Math.max(maxY, y);
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
        drawResizePoint(getMinX(), getMinY(), graph2d);
        drawResizePoint((getMinX() + getMaxX()) / 2, getMinY(), graph2d);
        drawResizePoint(getMaxX(), getMinY(), graph2d);
        drawResizePoint(getMaxX(), (getMinY() + getMaxY()) / 2, graph2d);
        drawResizePoint(getMaxX(), getMaxY(), graph2d);
        drawResizePoint((getMinX() + getMaxX()) / 2, getMaxY(), graph2d);
        drawResizePoint(getMinX(), getMaxY(), graph2d);
        drawResizePoint(getMinX(), (getMinY() + getMaxY()) / 2, graph2d);
    }

    public PixeledTool() {
        super();
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
                    getMinX(), getMinY(),
                    Math.abs(getMaxX() - getMinX()),
                    Math.abs(getMaxY() - getMinY()));
            graph.draw(drawRect);
            graph.setStroke(new BasicStroke(getBorderSize()));            
            drawResizePoints(graph);
            graph.setColor(getColor());            
        }
    }
}
