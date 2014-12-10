/*
 *  Copyright (C) 2014
 *                      Anwar Mohamed     <anwarelmakrahy@gmail.com>
 *                      Abdallah Elerian  <abdallah.elerian@gmail.com>
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
import java.awt.BorderLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class BrushDialog extends WebDialog {

    public JSlider slider = new JSlider(1, 20, 1);
    private int strokesize = 1;
    private Canvas canvas = null;

    public BrushDialog(WebFrame parent) {
        super(parent, "Brush Box", false);
        
        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        
        setSize(240, 160);        
        setResizable(true);
        setLayout(new BorderLayout());
        
        slider.setMinorTickSpacing(1);
        slider.setMajorTickSpacing(60);

        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent ce) {
                strokesize = (int) slider.getValue();
            }
        });
        add(slider);
    }

    public int getStrokeSize() {
        return strokesize;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }
}
