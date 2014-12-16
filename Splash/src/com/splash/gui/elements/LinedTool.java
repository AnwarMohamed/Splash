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

import com.splash.gui.elements.Tool;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class LinedTool extends Tool {

    private int endX, endY;

    public int getEndX() {
        return endX;
    }

    public int getEndY() {
        return endY;
    }

    public LinedTool() {
        super();
        endX = endY = 0;
    }

    public LinedTool(int x, int y) {
        super();
        endX = endY = 0;
        setLocation(x, y);
    }

    public int getMinX() {
        return minX;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMinY() {
        return minY;
    }

    public int getMaxY() {
        return maxY;
    }

    private int maxX = Integer.MIN_VALUE;
    private int minY = Integer.MAX_VALUE;
    private int maxY = Integer.MIN_VALUE;
    private int minX = Integer.MAX_VALUE;

    public void setEndPoint(int x, int y) {
        endX = x;
        endY = y;

        minX = Math.min(getX(), getEndX()) - getBorderSize() / 2;
        minY = Math.min(getY(), getEndY()) - getBorderSize() / 2;
        maxX = minX + Math.abs(getX() - getEndX()) + getBorderSize();
        maxY = minY + Math.abs(getY() - getEndY()) + getBorderSize();
    }

    @Override
    public void setCoordinates(int x, int y) {
        super.setCoordinates(x, y);
        setEndPoint(x, y);
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
                    minX, minY, maxX - minX, maxY - minY);
            graph.draw(drawRect);
            graph.setStroke(new BasicStroke(1));
            drawResizePoints(graph);
            graph.setColor(getColor());
            graph.setStroke(new BasicStroke(getBorderSize()));
        }

        Shape drawline = new Line2D.Float(
                getX(), getY(), getEndX(), getEndY());
        graph.draw(drawline);
    }

    @Override
    public boolean withinBounds(int x, int y) {
        final java.awt.Rectangle cellBounds = new java.awt.Rectangle(
                minX, minY, maxX - minX, maxY - minY);
        return cellBounds.contains(x, y);
    }

    @Override
    public LinedTool newInstance() {
        return new LinedTool();
    }

    @Override
    public void drawResizePoints(Graphics2D graph2d) {
        drawResizePoint(
                min(getX(), getEndX()) - getBorderSize() / 2,
                min(getY(), getEndY()) - getBorderSize() / 2,
                graph2d);
        drawResizePoint(
                min(getX(), getEndX()) + abs(getEndX() - getX()) / 2,
                max(getY(), getEndY()) + getBorderSize() / 2,
                graph2d);
        drawResizePoint(
                min(getX(), getEndX()) + abs(getEndX() - getX()) + getBorderSize() / 2,
                max(getY(), getEndY()) + getBorderSize() / 2,
                graph2d);
        drawResizePoint(
                min(getX(), getEndX()) - getBorderSize() / 2,
                max(getY(), getEndY()) + getBorderSize() / 2,
                graph2d);
        drawResizePoint(
                min(getX(), getEndX()) - getBorderSize() / 2,
                min(getY(), getEndY()) + abs(getEndY() - getY()) / 2,
                graph2d);
        drawResizePoint(
                max(getX(), getEndX()) + getBorderSize() / 2,
                min(getY(), getEndY()) - getBorderSize() / 2,
                graph2d);
        drawResizePoint(
                max(getX(), getEndX()) + getBorderSize() / 2,
                min(getY(), getEndY()) + abs(getEndY() - getY()) / 2,
                graph2d);
        drawResizePoint(
                min(getX(), getEndX()) + abs(getEndX() - getX()) / 2,
                min(getY(), getEndY()) - getBorderSize() / 2,
                graph2d);
    }

    @Override
    public void translate(int x, int y) {
        int newX, newY, newEndX, newEndY;

        if (getX() < getEndX()) {
            newX = getX() + (x - getX());
            newEndX = getEndX() + (x - getX());
        } else {
            newX = getX() + (x - getEndX());
            newEndX = getEndX() + (x - getEndX());
        }

        if (getY() < getEndY()) {
            newY = getY() + (y - getY());
            newEndY = getEndY() + (y - getY());
        } else {
            newY = getY() + (y - getEndY());
            newEndY = getEndY() + (y - getEndY());
        }

        super.setCoordinates(newX, newY);
        setEndPoint(newEndX, newEndY);
    }
}
