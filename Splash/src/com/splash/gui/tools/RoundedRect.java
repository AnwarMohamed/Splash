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
package com.splash.gui.tools;

import com.splash.gui.elements.DimensionedTool;
import java.awt.*;
import java.awt.geom.*;

public class RoundedRect extends DimensionedTool {

    public RoundedRect() {
        super();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Shape drawRoundRect = new RoundRectangle2D.Float(
                getX(), getY(), getWidth(), getHeight(),80,80);
        graph.draw(drawRoundRect);
    }

    @Override
    public RoundedRect newInstance() {
        return new RoundedRect();
    }
}
