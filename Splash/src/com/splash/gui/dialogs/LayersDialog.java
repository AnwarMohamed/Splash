/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Anwar Mohamed
 */
public class LayersDialog extends WebDialog {

    public DefaultListModel layersModel;
    private JList list;

    public ArrayList<Layer> layers = new ArrayList<>();
    private int width, height;

    public LayersDialog(WebFrame parent, int width, int height) {
        super(parent, "Layers", false);

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());
        setSize(240, 300);
        setUndecorated(true);

        this.width = width;
        this.height = height;

        layersModel = new DefaultListModel();
        list = new JList(layersModel);

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
