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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

public class Select extends DimensionedTool {

    public Select() {
        super();
    }

    @Override
    public void paint(Graphics g) {
        /*Graphics2D graph2 = (Graphics2D) g;*/

        /*if (getDragMode()) {
         g.clearRect(getX(), getY(), getWidth(), getHeight());
         }
         */
        /*
         graph2.setColor(getColor());
         Shape drawEllipse = new Ellipse2D.Float(getX(), getY(), getWidth(), getHeight());
         graph2.draw(drawEllipse);
         */
    }

    @Override
    public Select newInstance() {
        return new Select();
    }
}
