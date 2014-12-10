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
import java.awt.Image;
import java.awt.RenderingHints;
import java.util.ArrayList;
import javax.swing.JComponent;

public class Layer extends JComponent {

    private Image image = null;
    private Graphics2D graphics2D = null;

    private ArrayList<Tool> objects = new ArrayList<>();
    
    public static final Color TRANSPARENT = new Color(255, 255, 255, 0);
    public static final int SELECTION_OVERLAY = 0x3f3f5f7f;

    public Layer(int width, int height) {
        super();
        
        setSize(width, height);
    }

    public void addTool(Tool component) {
        objects.add(component);
    }        
    
    @Override
    public void paintComponent(Graphics g) {        
        super.paintComponent(g);

        for (Tool object: objects) {
            object.paint(g);
        }
    }

    public void clear() {
        objects.clear();
        
        graphics2D.setPaint(TRANSPARENT);
        graphics2D.fillRect(0, 0, getSize().width, getSize().height);
        graphics2D.setPaint(Color.black);
        repaint();
    }
}
