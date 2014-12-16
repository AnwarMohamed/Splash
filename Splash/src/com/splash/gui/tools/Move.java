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
import java.awt.Graphics2D;

public class Move extends Tool {

    public Move() {
        super();
    }

    @Override
    public Move newInstance() {
        return new Move();
    }

    @Override
    public void drawResizePoint(int x, int y, Graphics2D graph2d) {
    }

    @Override
    public void drawResizePoints(Graphics2D graph2d) {
    }

    @Override
    public void translate(int x, int y) {
    }

    @Override
    public boolean withinBounds(int x, int y) {
        return false;
    }
}
