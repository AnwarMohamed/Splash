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

import com.alee.laf.rootpane.WebFrame;
import java.awt.BorderLayout;
import javax.swing.JFrame;

public class MainWindow extends WebFrame {

    public MainWindow() {
        super("Splash | Just Another Awesome Paint Program");        
        
        setBounds(50, 100, 800, 600); 
        setLocationRelativeTo(null);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        
        initMenuBar();
        initToolBar();
    }
    
    private ToolBar toolBar;
    private void initToolBar() {
        toolBar = new ToolBar();        
        
        add(toolBar, BorderLayout.NORTH);
    }
    
    private MenuBar menuBar; 
    private void initMenuBar() {
        menuBar = new MenuBar();
        setJMenuBar(menuBar);                
    }
}
