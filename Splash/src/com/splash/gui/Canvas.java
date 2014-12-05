/*
 *  Copyright (C) 2014
 *                      Anwar Mohamed     <anwarelmakrahy@gmail.com>
 *                      Abdallah Elerian  <abdallah.elerian@gmail.com>
 *                      Moataz Hamouda    <moatazhammouda4@gmail.com>
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
package com.splash.gui;

import com.splash.gui.elements.Layer;
import java.awt.Dimension;
import java.awt.TexturePaint;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JLabel;

public class Canvas extends JComponent implements MouseListener,
        MouseMotionListener, MouseWheelListener {

    private int mouseX, mouseY, mouseLastDragPosX, mouseLastDragPosY;
    private JLabel mouseMoveLabel = null;

    private float cam_zoom;
    private float cam_positionX;
    private float cam_positionY;

    public ArrayList<Layer> layers = new ArrayList<>();

    public Canvas(int width, int height) {
        super();

        setSize(width, height);

        setMinimumSize(new Dimension(8, 8));
        setDoubleBuffered(true);
        setFocusable(true);

        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);

        cam_zoom = 1;
        cam_positionX = 0;
        cam_positionY = 0;

        updateBackGround();

        mouseLastDragPosX = 0;
        mouseLastDragPosY = 0;

        layers.add(new Layer(width, height));
    }

    public static BufferedImage backgroundLight, backgroundDark;

    private Rectangle2D backgroundRectangle;
    private TexturePaint paintLight, paintDark;

    static {
        try {
            backgroundLight = ImageIO.read(new File("res/tbg.png"));
            backgroundDark = ImageIO.read(new File("res/tbgd.png"));
        } catch (IOException e) {
        }
    }

    private void updateBackGround() {
        Rectangle2D.Float rect = new Rectangle2D.Float(0, 0, 16f / cam_zoom, 16f / cam_zoom);
        paintLight = new TexturePaint(backgroundLight, rect);
        paintDark = new TexturePaint(backgroundDark, rect);
    }

    public void setMouseMoveLabel(JLabel label) {
        mouseMoveLabel = label;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (mouseMoveLabel != null) {
            mouseMoveLabel.setText(" " + (this.getAlignmentX() - e.getX()) + ", " + e.getY());
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
    }

}
