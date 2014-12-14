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
import com.splash.gui.elements.Layer;
import com.splash.gui.elements.PixeledTool;
import com.splash.gui.elements.Tool;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Select extends DimensionedTool {

    public Select() {
        super();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        graph.setColor(Color.BLACK);
        graph.setStroke(
                new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
                        BasicStroke.JOIN_MITER, 10.0f,
                        new float[]{3.0f}, 0.0f));
        Shape drawRect = new Rectangle2D.Float(
                getX(), getY(), getWidth(), getHeight());
        graph.draw(drawRect);
    }

    @Override
    public Select newInstance() {
        return new Select();
    }

    public ArrayList<Tool> selectInboundObjects(Layer layer) {
        ArrayList<Tool> selected = new ArrayList<>();
        for (Tool tool : layer.getTools()) {
            if (!(tool instanceof Select)
                    && !(tool instanceof Move)
                    && isInbound(tool)) {
                tool.setSelected(true);
                selected.add(tool);
            }
        }
        return selected;
    }

    private boolean isInbound(Tool tool) {
        if (tool instanceof DimensionedTool) {
            return (getX() <= tool.getX())
                    && (tool.getX()
                    + ((DimensionedTool) tool).getWidth()
                    <= getX() + getWidth())
                    && (getY() <= tool.getY())
                    && (tool.getY()
                    + ((DimensionedTool) tool).getHeight()
                    <= getY() + getHeight());
        } else if (tool instanceof PixeledTool) {
            return (getX() <= ((PixeledTool) tool).getMinX()
                    && ((PixeledTool) tool).getMaxX() <= getX() + getWidth()
                    && getY() <= ((PixeledTool) tool).getMinY()
                    && ((PixeledTool) tool).getMaxY() <= getY() + getHeight());
        } else if (tool instanceof Tool) {
            if (tool instanceof Line) {
                return (getX() <= tool.getX()
                        && tool.getX() <= getX() + getWidth()
                        && getY() <= tool.getY()
                        && tool.getY() <= getY() + getHeight()
                        && getX() <= ((Line) tool).getEndX()
                        && ((Line) tool).getEndX() <= getX() + getWidth()
                        && getY() <= ((Line) tool).getEndY()
                        && ((Line) tool).getEndY() <= getY() + getHeight());
            } else {
                return (getX() <= tool.getX()
                        && tool.getX() <= getX() + getWidth()
                        && getY() <= tool.getY()
                        && tool.getY() <= getY() + getHeight());
            }
        } else {
            return false;
        }
    }
}
