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

import com.alee.laf.filechooser.WebFileChooser;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebFrame;
import static com.alee.managers.notification.NotificationIcon.image;
import com.splash.PrintActionListener;
import com.splash.file.AboudaFactory;
import com.splash.gui.dialogs.BrushDialog;
import com.splash.gui.dialogs.ColorPickerDialog;
import com.splash.gui.dialogs.LayersDialog;
import com.splash.gui.dialogs.ToolBoxDialog;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainWindow extends WebFrame implements WindowListener {

    private LayersDialog layersDialog = null;
    private ToolBoxDialog toolBoxDialog = null;
    private BrushDialog brushDialog = null;
    private ColorPickerDialog colorPickerDialog = null;

    public LayersDialog getLayersDialog() {
        return layersDialog;
    }

    public ToolBoxDialog getToolBoxDialog() {
        return toolBoxDialog;
    }

    public ColorPickerDialog getColorPickerDialog() {
        return colorPickerDialog;
    }

    private WebFileChooser fileChooser = null;

    private final int DEFAULT_WIDTH = 900;
    private final int DEFAULT_HEIGHT = 500;

    private String currentFilename = new String();
    private boolean edited = false;

    public MainWindow() {
        super("Splash | Just Another Awesome Paint Program");

        setIconImage((new ImageIcon("res/icon.png")).getImage());

        setSize(
                (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 10,
                (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 40);

        setMinimumSize(new Dimension(500, 500));
        setLocationRelativeTo(null);

        addWindowListener(MainWindow.this);

        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        initMenuBar();
        initToolBar();
        initCanvas(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        initStatusBar();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                layersDialog = new LayersDialog(
                        MainWindow.this, DEFAULT_WIDTH, DEFAULT_HEIGHT);
                layersDialog.setLocationRelativeTo(MainWindow.this);
                layersDialog.setLocation(1115, 105);
                layersDialog.setVisible(true);

                if (canvas != null) {
                    canvas.setLayersModel(layersDialog.layers);
                    layersDialog.linkCanvas(canvas);
                }
            }
        });

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                toolBoxDialog = new ToolBoxDialog(MainWindow.this);
                toolBoxDialog.setLocationRelativeTo(MainWindow.this);
                toolBoxDialog.setLocation(1115, 365);
                toolBoxDialog.setVisible(true);

                if (canvas != null) {
                    toolBoxDialog.setCanvas(canvas);
                    canvas.getSnapshotManager().updateDoers();
                }
            }
        });

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    colorPickerDialog = new ColorPickerDialog(MainWindow.this);
                    colorPickerDialog.setLocationRelativeTo(MainWindow.this);
                    colorPickerDialog.setLocation(8, 105);
                    colorPickerDialog.setVisible(true);
                    colorPickerDialog.setCanvas(canvas);
                } catch (IOException ex) {
                }
            }
        });

//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                brushDialog = new BrushDialog(thisFrame);
//                brushDialog.setLocationRelativeTo(thisFrame);
//                brushDialog.setLocation(1115, 485);
//                brushDialog.setVisible(true);
//                brushDialog.setCanvas(canvas);
//
//                if (toolBoxDialog != null) {
//                    toolBoxDialog.setBrushBox(brushDialog);
//                }
//            }
//        });
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                fileChooser = new WebFileChooser();
                fileChooser.setAcceptAllFileFilterUsed(false);
                fileChooser.setFileFilter(
                        new FileNameExtensionFilter(
                                "Splash Project file", "abouda"));
            }
        });
    }

    private ToolBar toolBar = null;

    public ToolBar getToolBarPanel() {
        return toolBar;
    }

    public MenuBar getMenuBarPanel() {
        return menuBar;
    }
    private MenuBar menuBar = null;
    private StatusBar statusBar = null;
    private Canvas canvas = null;

    private void initToolBar() {
        toolBar = new ToolBar();
        add(toolBar, BorderLayout.NORTH);

        toolBar.openAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                menuBar.openAction.doClick();
            }
        });

        toolBar.saveAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                menuBar.saveAction.doClick();
            }
        });

        toolBar.newAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                menuBar.newAction.doClick();
            }
        });
    }

    private void initMenuBar() {
        menuBar = new MenuBar();
        menuBar.setMainWindow(MainWindow.this);
        menuBar.aboutAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                AboutWindow aboutWindow = new AboutWindow(false);
                aboutWindow.show();
            }
        });

        menuBar.openAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                fileChooser.setDialogTitle("Open Project");
                int returnVal = fileChooser.showOpenDialog(MainWindow.this);

                if (returnVal == WebFileChooser.APPROVE_OPTION) {
                    if (AboudaFactory.parseInputFile(
                            fileChooser.getSelectedFile().getAbsolutePath(),
                            canvas)) {
                        currentFilename
                                = fileChooser.getSelectedFile().getAbsolutePath();
                    }
                }
            }
        });

        menuBar.saveAsAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                fileChooser.setDialogTitle("Save Project As");
                int returnVal = fileChooser.showSaveDialog(MainWindow.this);

                if (returnVal == WebFileChooser.APPROVE_OPTION) {
                    AboudaFactory.generateOutputFile(
                            fileChooser.getSelectedFile().getAbsolutePath(),
                            canvas);

                    currentFilename
                            = fileChooser.getSelectedFile().getAbsolutePath();

                    edited = false;
                }
            }
        });

        menuBar.saveAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                if (currentFilename.isEmpty()) {
                    fileChooser.setDialogTitle("Save Project");
                    int returnVal = fileChooser.showSaveDialog(MainWindow.this);

                    if (returnVal == WebFileChooser.APPROVE_OPTION) {
                        AboudaFactory.generateOutputFile(
                                fileChooser.getSelectedFile().getAbsolutePath(),
                                canvas);

                        currentFilename
                                = fileChooser.getSelectedFile().getAbsolutePath();
                    }
                } else {
                    AboudaFactory.generateOutputFile(currentFilename, canvas);
                }

                edited = false;
            }
        });

        menuBar.exportAsPngAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                fileChooser.setDialogTitle("Export As PNG");
                fileChooser.setFileFilter(
                        new FileNameExtensionFilter(
                                "PNG Image", "png"));

                int returnVal = fileChooser.showSaveDialog(MainWindow.this);

                if (returnVal == WebFileChooser.APPROVE_OPTION) {
                    try {
                        ImageIO.write(
                                canvas.getRenderedImage(),
                                "png",
                                new File(
                                        fileChooser.getSelectedFile().
                                        getAbsolutePath() + ".png"));
                    } catch (IOException ex) {
                    }
                }

                fileChooser.setFileFilter(
                        new FileNameExtensionFilter(
                                "Splash Project file", "abouda"));
            }
        });

        menuBar.exportAsGifAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                fileChooser.setDialogTitle("Export As GIF");
                fileChooser.setFileFilter(
                        new FileNameExtensionFilter(
                                "GIF Image", "gif"));

                int returnVal = fileChooser.showSaveDialog(MainWindow.this);

                if (returnVal == WebFileChooser.APPROVE_OPTION) {
                    try {
                        ImageIO.write(
                                canvas.getRenderedImage(),
                                "gif",
                                new File(
                                        fileChooser.getSelectedFile().
                                        getAbsolutePath() + ".gif"));
                    } catch (IOException ex) {
                    }
                }

                fileChooser.setFileFilter(
                        new FileNameExtensionFilter(
                                "Splash Project file", "abouda"));
            }
        });

        menuBar.exportAsJpgAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                fileChooser.setDialogTitle("Export As JPG");
                fileChooser.setFileFilter(
                        new FileNameExtensionFilter(
                                "JPG Image", "jpg"));

                int returnVal = fileChooser.showSaveDialog(MainWindow.this);

                if (returnVal == WebFileChooser.APPROVE_OPTION) {
                    try {
                        ImageIO.write(
                                canvas.getRenderedImage(),
                                "jpg",
                                new File(
                                        fileChooser.getSelectedFile().
                                        getAbsolutePath() + ".jpg"));
                    } catch (IOException ex) {
                    }
                }

                fileChooser.setFileFilter(
                        new FileNameExtensionFilter(
                                "Splash Project file", "abouda"));
            }
        });

        menuBar.exportAsBmpAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                fileChooser.setDialogTitle("Export As Bitmap");
                fileChooser.setFileFilter(
                        new FileNameExtensionFilter(
                                "Bitmap Image", "bmp"));

                int returnVal = fileChooser.showSaveDialog(MainWindow.this);

                if (returnVal == WebFileChooser.APPROVE_OPTION) {
                    try {
                        ImageIO.write(
                                canvas.getRenderedImage(),
                                "bmp",
                                new File(
                                        fileChooser.getSelectedFile().
                                        getAbsolutePath() + ".bmp"));
                    } catch (IOException ex) {
                    }
                }

                fileChooser.setFileFilter(
                        new FileNameExtensionFilter(
                                "Splash Project file", "abouda"));
            }
        });

        menuBar.exitAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                dispatchEvent(new WindowEvent(
                        MainWindow.this, WindowEvent.WINDOW_CLOSING));
            }
        });

        menuBar.printAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                (new Thread(new PrintActionListener(canvas.getRenderedImage()))).start();
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
                MainWindow.this,
                ((getWidth() - 50) / 2) - (width / 2),
                ((getHeight() - 160) / 2) - (height / 2), width, height);

        canvas.setMainWindow(MainWindow.this);
        canvas.clearWorkspace();

        add(canvas, BorderLayout.CENTER);
    }

    public BrushDialog getBrushDialog() {
        return brushDialog;
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        if (edited) {
            int result = JOptionPane.showConfirmDialog(
                    MainWindow.this, "Save before Exiting?");
            if (result == JOptionPane.OK_OPTION) {
                menuBar.saveAction.doClick();
                System.exit(0);
            } else if (result == JOptionPane.CANCEL_OPTION) {
            } else if (result == JOptionPane.NO_OPTION) {
                System.exit(0);
            }
        } else {
            System.exit(0);
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        if (menuBar != null) {
            if (colorPickerDialog != null) {
                if (menuBar.colorsAction.isSelected()) {
                    colorPickerDialog.setVisible(!colorPickerDialog.isVisible());
                }
                colorPickerDialog.setVisible(menuBar.colorsAction.isSelected());
            }

            if (brushDialog != null) {
                if (menuBar.brushAction.isSelected()) {
                    brushDialog.setVisible(!brushDialog.isVisible());
                }
                brushDialog.setVisible(menuBar.brushAction.isSelected());
            }

            if (toolBoxDialog != null) {
                if (menuBar.toolsAction.isSelected()) {
                    toolBoxDialog.setVisible(!toolBoxDialog.isVisible());
                }
                toolBoxDialog.setVisible(menuBar.toolsAction.isSelected());
            }

            if (layersDialog != null) {
                if (menuBar.layersAction.isSelected()) {
                    layersDialog.setVisible(!layersDialog.isVisible());
                }
                layersDialog.setVisible(menuBar.layersAction.isSelected());
            }
        }
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
