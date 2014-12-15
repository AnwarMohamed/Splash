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

import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.rootpane.WebFrame;
import com.splash.gui.Canvas;
import com.splash.gui.elements.Tool;
import com.splash.gui.elements.WrapLayout;
import com.splash.gui.tools.Move;
import java.awt.BorderLayout;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public final class BrushDialog extends WebDialog implements ChangeListener {

    private JSlider slider = new JSlider(1, 50, 1);
    private Canvas canvas = null;
    private JComboBox fontComboBox = new JComboBox();
    private JComboBox fontSizeComboBox = new JComboBox();
    private WebPanel formatPanel = new WebPanel();

    public JToggleButton boldAction = new JToggleButton(
            new ImageIcon("res/images/format-text-bold.png"));
    public JToggleButton italicAction = new JToggleButton(
            new ImageIcon("res/images/format-text-italic.png"));
    public JToggleButton strikeAction = new JToggleButton(
            new ImageIcon("res/images/format-text-strikethrough.png"));
    public JToggleButton underlineAction = new JToggleButton(
            new ImageIcon("res/images/format-text-underline.png"));

    public BrushDialog(WebFrame parent) {
        super(parent, "Brush Box", false);

        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);

        setSize(240, 200);
        setResizable(true);
        setLayout(new GridLayout(4, 0));

        slider.setMinorTickSpacing(1);
        slider.setMajorTickSpacing(60);
        slider.addChangeListener(BrushDialog.this);

        add(slider);

//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                String fonts[]
//                        = GraphicsEnvironment.getLocalGraphicsEnvironment()
//                        .getAvailableFontFamilyNames();
//
//                for (String font : fonts) {
//                    fontComboBox.addItem(font);
//                }
//            }
//        });
        add(fontComboBox);

        int fontSizes[]
                = {8, 9, 10, 11, 12, 14, 16, 18, 20,
                    22, 24, 26, 28, 36, 48, 72, 96};
        for (int fontSize : fontSizes) {
            fontSizeComboBox.addItem(Integer.toString(fontSize));
        }

        add(fontSizeComboBox);

        formatPanel.setLayout(new WrapLayout());
        formatPanel.add(boldAction);
        formatPanel.add(italicAction);
        formatPanel.add(strikeAction);
        formatPanel.add(underlineAction);

        add(formatPanel, BorderLayout.SOUTH);

        EnableFontBox(false);
        EnableBrushBox(false);
    }

    public int getStrokeSize() {
        return slider.getValue();
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public void EnableFontBox(boolean bool) {
        boldAction.setEnabled(bool);
        italicAction.setEnabled(bool);
        strikeAction.setEnabled(bool);
        underlineAction.setEnabled(bool);
        fontComboBox.setEnabled(bool);
        fontSizeComboBox.setEnabled(bool);
    }

    public void EnableBrushBox(boolean bool) {
        slider.setEnabled(bool);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (canvas != null && canvas.getSelectedTool() != null) {
            if (canvas.getSelectedTool() instanceof Move
                    && canvas.getSelectedObjects() != null) {
                for (Tool object : canvas.getSelectedObjects()) {
                    object.setBorderSize(slider.getValue());
                }
                canvas.repaint();
                canvas.getSnapshotManager().saveSnapshot(true);
            }
        }
    }
}
