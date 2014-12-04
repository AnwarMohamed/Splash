/*
 *  Copyright (C) 2014
 *                      Anwar Mohamed     <anwarelmakrahy@gmail.com>
 *                      Abdallah Elerian  <abdallah.elerian@gmail.com>
 *                      Moataz Hamouda    <>
 *                      Yasmine Elhabashi <yasmine.elhabashi@gmail.com>
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
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

import java.awt.Event;
import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

class MenuBar extends JMenuBar {

    private JMenu fileMenu = new JMenu("File");
    private JMenu editMenu = new JMenu("Edit");
    private JMenu viewMenu = new JMenu("View");
    private JMenu windowMenu = new JMenu("Window");
    private JMenu helpMenu = new JMenu("Help");

    public JMenuItem newAction = new JMenuItem("New");
    public JMenuItem openAction = new JMenuItem("Open");
    public JMenuItem saveAction = new JMenuItem("Save");
    public JMenuItem saveAsAction = new JMenuItem("Save As");
    public JMenuItem exitAction = new JMenuItem("Exit");

    public JMenuItem copyAction = new JMenuItem("Copy");
    public JMenuItem cutAction = new JMenuItem("Cut");
    public JMenuItem pasteAction = new JMenuItem("Paste");

    public JMenuItem aboutAction = new JMenuItem("About");

    public MenuBar() {
        fileMenu.add(newAction);
        newAction.setMnemonic('N');
        newAction.setAccelerator(
                KeyStroke.getKeyStroke(
                        KeyEvent.VK_N,
                        Event.CTRL_MASK));
        fileMenu.add(openAction);
        openAction.setMnemonic('O');
        openAction.setAccelerator(
                KeyStroke.getKeyStroke(
                        KeyEvent.VK_O,
                        Event.CTRL_MASK));
        fileMenu.addSeparator();
        fileMenu.add(saveAction);
        saveAction.setMnemonic('S');
        saveAction.setAccelerator(
                KeyStroke.getKeyStroke(
                        KeyEvent.VK_S,
                        Event.CTRL_MASK));
        fileMenu.add(saveAsAction);
        saveAsAction.setAccelerator(KeyStroke.getKeyStroke("control alt S"));
        fileMenu.addSeparator();
        fileMenu.add(exitAction);
        exitAction.setMnemonic('E');
        exitAction.setAccelerator(
                KeyStroke.getKeyStroke(
                        KeyEvent.VK_F4,
                        Event.ALT_MASK));
        add(fileMenu);

        editMenu.add(cutAction);
        cutAction.setMnemonic('X');
        cutAction.setAccelerator(
                KeyStroke.getKeyStroke(
                        KeyEvent.VK_X,
                        Event.CTRL_MASK));
        editMenu.add(copyAction);
        copyAction.setMnemonic('C');
        copyAction.setAccelerator(
                KeyStroke.getKeyStroke(
                        KeyEvent.VK_C,
                        Event.CTRL_MASK));
        editMenu.add(pasteAction);
        pasteAction.setMnemonic('V');
        pasteAction.setAccelerator(
                KeyStroke.getKeyStroke(
                        KeyEvent.VK_V,
                        Event.CTRL_MASK));
        add(editMenu);

        add(viewMenu);

        add(windowMenu);

        helpMenu.add(aboutAction);
        add(helpMenu);

    }
}
