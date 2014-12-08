/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.splash.gui.dialogs;

import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.rootpane.WebFrame;
import javax.swing.JFrame;

/**
 *
 * @author Anwar Mohamed
 */
public class ToolBoxDialog extends WebDialog {

    public ToolBoxDialog(WebFrame parent) {
        super(parent, "Tool Box", false);

        setSize(70, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setResizable(false);
    }
}
