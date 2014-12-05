/*
 *  Copyright (C) 2014
 *                      Anwar Mohamed     <anwarelmakrahy@gmail.com>
 *                      Abdallah Elerian  <abdallah.elerian@gmail.com>
 *                      Moataz Hamouda    <moatazhammouda4@gmail.com>
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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;

public class AboutWindow extends JWindow {

    public AboutWindow(final boolean splash) {
        super();

        setSize(502, 266);
        setLocationRelativeTo(null);

        JLabel label = new JLabel(new ImageIcon("res/logo.png"));

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!splash) {
                    setVisible(false);
                    dispose();
                }
            }
        });

        add(label);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowLostFocus(WindowEvent e) {
                if (!splash) {
                    setVisible(false);
                    dispose();
                }
            }

            @Override
            public void windowStateChanged(WindowEvent e) {
                if (!splash) {
                    setVisible(false);
                    dispose();
                }
            }
        });
    }

}
