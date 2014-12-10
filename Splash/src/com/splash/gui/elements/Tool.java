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
package com.splash.gui.elements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JComponent;

public abstract class Tool extends JComponent {

    private Color color;
    //private boolean dragMode;

    public Tool() {
        setLocation(0, 0);
        color = Color.BLACK;
        //    dragMode = false;
    }

    //public boolean getDragMode() {
    //    return dragMode;
    //}
    //public void setDragMode(boolean dragMode) {
    //    this.dragMode = dragMode;
    //}
    public void setCoordinates(int x, int y) {
        setLocation(x, y);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    protected Graphics2D graph;

    @Override
    public void paint(Graphics g) {
        graph = (Graphics2D) g;
        graph.setColor(getColor());
        graph.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON);
    }

    public abstract Tool newInstance();
}
