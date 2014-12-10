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
package com.splash.gui;

import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebFrame;
import com.splash.gui.dialogs.BrushDialog;
import com.splash.gui.dialogs.LayersDialog;
import com.splash.gui.dialogs.ToolBoxDialog;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class MainWindow extends WebFrame {

    private LayersDialog layersDialog = null;
    private ToolBoxDialog toolBoxDialog = null;
    private BrushDialog brushDialog = null;
    private WebFrame thisFrame = this;

    private final int DEFAULT_WIDTH = 900;
    private final int DEFAULT_HEIGHT = 500;

    public MainWindow() {
        super("Splash | Just Another Awesome Paint Program");

        setIconImage((new ImageIcon("res/icon.png")).getImage());

        setSize(1360, 750);
        setMinimumSize(new Dimension(500, 500));
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initMenuBar();
        initToolBar();
        initCanvas(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        initStatusBar();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                layersDialog = new LayersDialog(
                        thisFrame, DEFAULT_WIDTH, DEFAULT_HEIGHT);
                layersDialog.setLocationRelativeTo(thisFrame);
                layersDialog.setLocation(1115, 120);
                layersDialog.setVisible(true);
                canvas.setLayersModel(layersDialog.layers);
            }
        });

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                toolBoxDialog = new ToolBoxDialog(thisFrame);
                toolBoxDialog.setLocationRelativeTo(thisFrame);
                toolBoxDialog.setLocation(1115, 380);
                toolBoxDialog.setVisible(true);
                toolBoxDialog.setCanvas(canvas);
            }
        });

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                brushDialog = new BrushDialog(thisFrame);
                brushDialog.setLocationRelativeTo(thisFrame);
                brushDialog.setLocation(1115, 500);
                brushDialog.setVisible(true);
                brushDialog.setCanvas(canvas);
            }
        });
    }

    private ToolBar toolBar = null;
    private MenuBar menuBar = null;
    private StatusBar statusBar = null;
    private Canvas canvas = null;

    private void initToolBar() {
        toolBar = new ToolBar();
        add(toolBar, BorderLayout.NORTH);
    }

    private void initMenuBar() {
        menuBar = new MenuBar();
        menuBar.aboutAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                AboutWindow aboutWindow = new AboutWindow(false);
                aboutWindow.show();
            }
        });

        setJMenuBar(menuBar);
    }

    private void initStatusBar() {
        statusBar = new StatusBar();
        add(statusBar, BorderLayout.SOUTH);

        if (canvas != null) {
            canvas.setMouseMoveLabel(statusBar.positionLabel);
        }
    }

    private void initCanvas(int width, int height) {
        WebPanel centre = new WebPanel();
        centre.setLayout(new GridBagLayout());
        centre.setSize(width, height);

        canvas = new Canvas(
                ((getWidth() - 50) / 2) - (width / 2),
                ((getHeight() - 160) / 2) - (height / 2), width, height);
        
        add(canvas, BorderLayout.CENTER);
    }
}
