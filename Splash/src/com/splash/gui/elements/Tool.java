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
import java.awt.RenderingHints;
import javax.swing.JComponent;

public abstract class Tool extends JComponent {

    private Color color = Color.BLACK;
    private int borderSize = 1;

    public int getBorderSize() {
        return borderSize;
    }

    public void setBorderSize(int borderSize) {
        this.borderSize = borderSize;
    }

    public Tool() {
        setLocation(0, 0);
    }

    public void setCoordinates(int x, int y) {
        setLocation(x, y);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public abstract void translate(int x, int y);
    
    protected Graphics2D graph;

    @Override
    public void paint(Graphics g) {
        graph = (Graphics2D) g;
        graph.setColor(getColor());
        graph.setStroke(new BasicStroke(getBorderSize()));
        graph.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
    }

    public abstract Tool newInstance();

    public void drawResizePoint(int x, int y, Graphics2D graph2d) {
        graph2d.setColor(Color.BLACK);
        graph2d.drawOval(x - 3, y - 3, 7, 7);
        graph2d.setColor(Color.WHITE);
        graph2d.fillOval(x - 2, y - 2, 6, 6);
    }

    public abstract void drawResizePoints(Graphics2D graph2d);

    private boolean selected = false;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}
