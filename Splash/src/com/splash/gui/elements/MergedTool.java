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

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import static java.lang.Math.max;
import static java.lang.Math.min;
import java.util.ArrayList;

public class MergedTool extends DimensionedTool {

    private ArrayList<Tool> mergedTools = new ArrayList<>();
    private BufferedImage mergedImage;

    public MergedTool() {
        super();
    }

    public ArrayList<Tool> getMergedTools() {
        return mergedTools;
    }

    public void mergeTools(ArrayList<Tool> tools) {
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = 0;
        int maxY = 0;

        for (Tool tool : tools) {
            if (tool instanceof DimensionedTool) {
                DimensionedTool dimensionedTool = (DimensionedTool) tool;
                minX = min(minX, dimensionedTool.getX());
                maxX = max(maxX,
                        dimensionedTool.getX() + dimensionedTool.getWidth());
                minY = min(minY, dimensionedTool.getY());
                maxY = max(maxY,
                        dimensionedTool.getY() + dimensionedTool.getHeight());
            } else if (tool instanceof PixeledTool) {
                PixeledTool pixeledTool = (PixeledTool) tool;
                minX = pixeledTool.getMinX();
                minY = pixeledTool.getMinY();
                maxX = pixeledTool.getMaxX();
                maxY = pixeledTool.getMaxY();
            } else if (tool instanceof LinedTool) {
                LinedTool linedTool = (LinedTool) tool;
                minX = linedTool.getMinX();
                minY = linedTool.getMinY();
                maxX = linedTool.getMaxX();
                maxY = linedTool.getMaxY();
            }
        }

        setLocation(minX, minY);
        setWidth(maxX - minX + 1);
        setHeight(maxY - minY + 1);

        mergedImage = new BufferedImage(
                maxX - minX + 1, maxY - minY + 1, BufferedImage.TYPE_INT_ARGB);

        Graphics2D mergedGraphics = mergedImage.createGraphics();

        for (Tool tool : tools) {
            if (tool instanceof DimensionedTool) {
                DimensionedTool dimensionedTool = (DimensionedTool) tool;
                dimensionedTool.setCoordinates(
                        dimensionedTool.getX() - minX,
                        dimensionedTool.getY() - minY);

            } else if (tool instanceof PixeledTool) {
                PixeledTool pixeledTool = (PixeledTool) tool;

            } else if (tool instanceof LinedTool) {
                LinedTool linedTool = (LinedTool) tool;

            }

            tool.paint(mergedGraphics);
        }
    }

    public BufferedImage getMergedImage() {
        return mergedImage;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (mergedImage != null) {
            graph.drawImage(mergedImage, getX(), getY(), null);
        }
    }

    @Override
    public MergedTool newInstance() {
        return new MergedTool();
    }

    @Override
    public void translate(int x, int y) {
        setCoordinates(x, y);
    }
}
