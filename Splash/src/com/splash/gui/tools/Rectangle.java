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

import com.splash.gui.elements.DimensionedTool;
import com.splash.gui.elements.Tool;
import java.awt.*;
import java.awt.geom.*;

public class Rectangle extends DimensionedTool {

    public Rectangle() {
        super();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D graph = (Graphics2D) g;

        /*
         if (getDragMode()) {
         g.setColor(new Color(255, 255, 255, 0));
         g.clearRect(getX(), getY(), getWidth(), getHeight());
         }
         */
        graph.setColor(getColor());
        graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Shape drawRect = new Rectangle2D.Float(getX(), getY(), getWidth(), getHeight());
        graph.draw(drawRect);
    }

    @Override
    public Rectangle newInstance() {
        return new Rectangle();
    }
}
