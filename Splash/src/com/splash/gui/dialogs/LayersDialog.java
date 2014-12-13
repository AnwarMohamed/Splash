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
import com.splash.gui.elements.Layer;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class LayersDialog extends WebDialog {

    public DefaultListModel layersModel;
    private JList list;

    public ArrayList<Layer> layers = new ArrayList<>();
    private int width, height, layersSum = 0;

    public LayersDialog(WebFrame parent, int width, int height) {
        super(parent, "Layers", false);

        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);

        setResizable(true);
        setLayout(new BorderLayout());
        setSize(240, 300);

        this.width = width;
        this.height = height;

        layersModel = new DefaultListModel();
        list = new JList(layersModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane pane = new JScrollPane(list);
        pane.setSize(220, 200);

        JButton addButton = new JButton(new ImageIcon("res/images/list-add.png"));
        JButton removeButton = new JButton(new ImageIcon("res/images/list-remove.png"));

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNewLayer();
            }
        });
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (layers.size() > 0) {
                    removeLayer(list.getSelectedIndex());
                }
            }
        });

        add(pane, BorderLayout.NORTH);
        add(addButton, BorderLayout.WEST);
        add(removeButton, BorderLayout.EAST);

        addNewLayer();

        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                if (event.getClickCount() == 1) {
                    if (canvas != null) {
                        canvas.setSelectedLayer(list.getSelectedIndex());
                    }
                } else if (event.getClickCount() == 2) {
                    String layerLabel
                            = JOptionPane.showInputDialog(
                                    "Enter New Layer Label");
                    if (layerLabel != null && layerLabel.length() > 0) {
                        layersModel.set(list.getSelectedIndex(), layerLabel);
                    }
                }
            }
        });
    }

    private void addNewLayer() {
        if (layers.size() > 0) {
            layersModel.add(
                    list.getSelectedIndex(), "New Layer" + layersSum++);
            layers.add(list.getSelectedIndex() - 1, new Layer(width, height));
            list.setSelectedIndex(list.getSelectedIndex() - 1);
        } else {
            layersModel.addElement("New Layer" + layersSum++);
            layers.add(new Layer(width, height));
            list.setSelectedIndex(0);
        }

        if (canvas != null) {
            canvas.setSelectedLayer(list.getSelectedIndex());
            canvas.repaint();

            if (canvas.getMainFrame() != null) {
                canvas.getMainFrame().invalidate();
                canvas.getMainFrame().repaint();
            }
        }
    }

    private void removeLayer(int index) {
        layersModel.remove(index);
        layers.remove(index);

        if (layers.size() > index) {
            list.setSelectedIndex(index);
        } else if (layers.size() > 0) {
            list.setSelectedIndex(index - 1);
        } else {
            layersSum = 0;
            addNewLayer();
        }

        if (canvas != null) {
            canvas.setSelectedLayer(list.getSelectedIndex());
            canvas.repaint();

            if (canvas.getMainFrame() != null) {
                canvas.getMainFrame().invalidate();
                canvas.getMainFrame().repaint();
            }
        }
    }

    private Canvas canvas = null;

    public void linkCanvas(Canvas canvas) {
        this.canvas = canvas;
        canvas.setLayersDialog(LayersDialog.this);
    }

    public void addNewLayer(Layer layer) {
        if (layers.size() > 0) {
            layersModel.add(
                    list.getSelectedIndex(), "New Layer" + layersSum++);
            layers.add(list.getSelectedIndex() - 1, new Layer(width, height));
            list.setSelectedIndex(list.getSelectedIndex() - 1);
        } else {
            layersModel.addElement("New Layer" + layersSum++);
            layers.add(layer);
            list.setSelectedIndex(0);
        }

        if (canvas != null) {
            canvas.setSelectedLayer(list.getSelectedIndex());
            canvas.repaint();

            if (canvas.getMainFrame() != null) {
                canvas.getMainFrame().invalidate();
                canvas.getMainFrame().repaint();
            }
        }
    }

    public void clearLayers() {
        layersModel.clear();
        layers.clear();
        layersSum = 0;
    }
}
