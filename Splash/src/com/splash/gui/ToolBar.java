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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

class ToolBar extends JToolBar {

    public JButton newAction = new JButton(
            new ImageIcon("res/images/document-new.png"));
    public JButton openAction = new JButton(
            new ImageIcon("res/images/document-open.png"));
    public JButton saveAction = new JButton(
            new ImageIcon("res/images/document-save.png"));
    public JButton saveAsAction = new JButton(
            new ImageIcon("res/images/document-save-as.png"));
   
    public JButton undoAction = new JButton(
            new ImageIcon("res/images/edit-undo.png"));
    public JButton redoAction = new JButton(
            new ImageIcon("res/images/edit-redo.png"));
    public JButton copyAction = new JButton(
            new ImageIcon("res/images/edit-copy.png"));
    public JButton cutAction = new JButton(
            new ImageIcon("res/images/edit-cut.png"));
    public JButton pasteAction = new JButton(
            new ImageIcon("res/images/edit-paste.png"));

    public ToolBar() {
        super();
        
        setFloatable(false);
        
        newAction.setToolTipText("New");
        openAction.setToolTipText("Open");
        saveAction.setToolTipText("Save");
        cutAction.setToolTipText("Cut");
        copyAction.setToolTipText("Copy");
        pasteAction.setToolTipText("Paste");
        undoAction.setToolTipText("Undo");
        redoAction.setToolTipText("Redo");
        
        add(newAction);
        add(openAction);
        add(saveAction);
        add(new JSeparator(SwingConstants.VERTICAL));
        add(undoAction);
        add(redoAction);
        add(new JSeparator(SwingConstants.VERTICAL));
        add(cutAction);
        add(copyAction);
        add(pasteAction);
        add(new JSeparator(SwingConstants.VERTICAL));
    }
}
