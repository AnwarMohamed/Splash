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

import com.splash.gui.elements.Layer;
import com.splash.gui.elements.MergedTool;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Fill extends MergedTool {

    public Fill() {
        super();
    }

    public void fillArea(int x, int y) {
        x -= getX();
        y -= getY();
        
        if (getMergedImage() != null
                && getMergedImage().getData().getBounds().contains(x, y)) {
            ArrayList<Point> pointList = new ArrayList<>();
            int initialColor = getMergedImage().getRGB(x, y);
            pointList.add(new Point(x, y));

            while (pointList.size() > 0) {
                Point p = pointList.remove(0);
                if (getMergedImage().getRGB(p.x, p.y) == initialColor) {
                    x = p.x;
                    y = p.y;
                    getMergedImage().setRGB(x, y, getColor().getRGB());

                    pointList.add(new Point(x - 1, y));
                    pointList.add(new Point(x + 1, y));
                    pointList.add(new Point(x, y - 1));
                    pointList.add(new Point(x, y + 1));
                }
            }
        }
    }

    public void fillAround(int x, int y, Layer layer) {

    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }

    @Override
    public Fill newInstance() {
        return new Fill();
    }

}
