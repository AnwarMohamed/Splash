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
import java.awt.image.BufferedImage;

public class ImagedTool extends DimensionedTool {

    private BufferedImage renderedImage;

    public BufferedImage getImage() {
        return renderedImage;
    }

    public void setImage(BufferedImage image) {
        renderedImage = image;
    }

    @Override
    public ImagedTool newInstance() {
        return new ImagedTool();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (renderedImage != null) {
            graph.drawImage(renderedImage, getX(), getY(), null);
        }
    }
}
