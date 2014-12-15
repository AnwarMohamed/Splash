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
package com.splash.gui.dialogs;

import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.rootpane.WebFrame;
import com.splash.gui.Canvas;
import com.splash.gui.elements.Tool;
import com.splash.gui.tools.Move;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JDialog;

public class ColorPickerDialog extends WebDialog implements MouseListener, MouseMotionListener {
    
    private BufferedImage wheelImage, stripImage, rendered;
    private Color current, preview;
    private Canvas canvas;
    
    public ColorPickerDialog(WebFrame parent) throws IOException {
        super(parent, "Color Picker", false);
        
        wheelImage = ImageIO.read(new File("res/images/color-picker-wheel.png"));
        stripImage = ImageIO.read(new File("res/images/color-picker-strip.png"));
        current = Color.BLACK;
        preview = Color.WHITE;
        
        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        
        setSize(250, 286);
        setResizable(false);
        
        addMouseListener(ColorPickerDialog.this);
        addMouseMotionListener(ColorPickerDialog.this);
        
        rendered = new BufferedImage(
                getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        
        Graphics2D gRendered = rendered.createGraphics();
        gRendered.drawImage(wheelImage, 45, 60, null);
        gRendered.drawImage(stripImage, 28, 230, null);
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        g.drawImage(rendered, 0, 0, null);
        
        g.setColor(Color.BLACK);
        g.drawRect(30, 195, 30, 30);
        g.setColor(current);
        g.fillRect(31, 196, 29, 29);
        
        g.setColor(Color.BLACK);
        g.drawRect(189, 195, 30, 30);
        g.setColor(preview);
        g.fillRect(190, 196, 29, 29);
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        current = new Color(rendered.getRGB(e.getX(), e.getY()));
        
        if (canvas != null && canvas.getSelectedTool() != null) {
            canvas.getSelectedTool().setColor(current);
            
            if (canvas.getSelectedTool() instanceof Move &&
                    canvas.getSelectedObjects() != null) {
                boolean changeDone = false;
                for (Tool object : canvas.getSelectedObjects()) {
                    
                    if (object.getColor() != current) {
                        changeDone = true;
                    }
                    
                    object.setColor(current);
                }
                canvas.repaint();
                
                if (changeDone) {
                    canvas.getSnapshotManager().saveSnapshot(true);
                }
            }
        }
        
        repaint();
    }
    
    public Color getColor() {
        return current;
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
        preview = new Color(rendered.getRGB(e.getX(), e.getY()));
        repaint();
    }
    
    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
        canvas.setColorPickerDialog(ColorPickerDialog.this);
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
}
