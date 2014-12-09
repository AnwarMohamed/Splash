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
import com.splash.gui.elements.Layer;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class LayersDialog extends WebDialog {

    public DefaultListModel layersModel;
    private JList list;

    public ArrayList<Layer> layers = new ArrayList<>();
    private int width, height;

    public LayersDialog(WebFrame parent, int width, int height) {
        super(parent, "Layers", false);

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

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

        ListSelectionListener listSelectionListener = 
                new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                if (canvas != null) {
                    canvas.setSelectedLayer(listSelectionEvent.getFirstIndex());
                }
            }
        };
        
        list.addListSelectionListener(listSelectionListener);
    }

    private void addNewLayer() {
        if (layers.size() > 0) {
            layersModel.add(list.getSelectedIndex(), "New Layer" + layers.size());
            layers.add(list.getSelectedIndex(), new Layer(width, height));
            list.setSelectedIndex(list.getSelectedIndex());
        } else {
            layersModel.addElement("New Layer" + layers.size());
            layers.add(new Layer(width, height));
            list.setSelectedIndex(layers.size() - 1);
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
            addNewLayer();
        }
    }
    
    private Canvas canvas = null;
    
    public void linkCanvas(Canvas canvas) {
        this.canvas = canvas;
    }
}
