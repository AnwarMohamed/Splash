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

public class FreeHand extends PixeledTool {

    public FreeHand() {
        super();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        for (int i = 0; i < getPixels().size() - 1; i++) {
            graph.drawLine(
                    (int) getPixels().get(i).getX(),
                    (int) getPixels().get(i).getY(),
                    (int) getPixels().get(i + 1).getX(),
                    (int) getPixels().get(i + 1).getY());
        }
    }

    @Override
    public FreeHand newInstance() {
        return new FreeHand();
    }
}
