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

import com.splash.gui.elements.PixeledTool;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Fill extends PixeledTool {

    public Fill() {
        super();
    }

    public void doFill(BufferedImage image) {

        ArrayList<Point> pointList = new ArrayList<>();
        int x = getX(), y = getY();
        int initialColor = image.getRGB(x, y);

        pointList.add(new Point(x, y));
        getPixels().clear();
        
        while (pointList.size() > 0) {
            Point p = pointList.remove(0);
            if (image.getRGB(p.x, p.y) == initialColor) {
                x = p.x;
                y = p.y;

                //image.setRGB(x, y, fillColor);
                getPixels().add(new Point(x, y));
                pointList.add(new Point(x - 1, y));
                pointList.add(new Point(x + 1, y));
                pointList.add(new Point(x, y - 1));
                pointList.add(new Point(x, y + 1));
                System.out.println(x + " " + y);
            } else {
                //getPixels().add(new Point(x, y));
            }
        }

        xPoints = new int[getPixels().size()];
        yPoints = new int[getPixels().size()];

        for (int i = 0; i < getPixels().size(); i++) {
            xPoints[i] = (int) getPixels().get(i).getX();
            yPoints[i] = (int) getPixels().get(i).getY();
        }

    }

    private int[] xPoints = new int[1];
    private int[] yPoints = new int[1];

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        graph.fillPolygon(xPoints, yPoints, yPoints.length);
    }

    @Override
    public Fill newInstance() {
        return new Fill();
    }

}
