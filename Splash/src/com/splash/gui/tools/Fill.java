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

import com.splash.gui.elements.Tool;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class Fill extends Tool {

    public Fill() {
        super();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        /*ArrayList<Point> pointList = new ArrayList<Point>();
         int initialColor = this..c.getRGB(x, y);
         pointList.add(new Point(x, y));

         while (pointList.size() > 0) {
         Point p = pointList.remove(0);
         if (image.getRGB(p.x, p.y) == initialColor) {
         x = p.x;
         y = p.y;
         image.setRGB(x, y, fillColor);

         pointList.add(new Point(x - 1, y));
         pointList.add(new Point(x + 1, y));
         pointList.add(new Point(x, y - 1));
         pointList.add(new Point(x, y + 1));
         }
         }
         */
    }

    @Override
    public Fill newInstance() {
        return new Fill();
    }

}
