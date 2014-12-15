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
package com.splash;

import com.splash.file.AboudaFile;
import com.splash.file.AboudaFileFormat;
import com.splash.gui.Canvas;
import com.splash.gui.elements.Layer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Stack;

public final class SnapshotManager {

    private Canvas canvas = null;
    private AboudaFileFormat outputStruct;
    private Stack<byte[]> inEvents = new Stack<>();
    private Stack<byte[]> outEvents = new Stack<>();
    private AboudaFile snapshot;

    public SnapshotManager(Canvas canvas) {
        this.canvas = canvas;
        updateDoers();

        if (canvas.getMainFrame() != null
                && canvas.getMainFrame().getMenuBarPanel() != null) {
            canvas.getMainFrame().getMenuBarPanel().redoAction
                    .addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            redoSnapshot();
                            updateDoers();
                        }
                    });

            canvas.getMainFrame().getMenuBarPanel().undoAction
                    .addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            undoSnapshot();
                            updateDoers();
                        }
                    });
        }

        if (canvas.getMainFrame().getToolBarPanel() != null) {
            canvas.getMainFrame().getToolBarPanel().redoAction
                    .addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            redoSnapshot();
                            updateDoers();
                        }
                    });

            canvas.getMainFrame().getToolBarPanel().undoAction
                    .addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            undoSnapshot();
                            updateDoers();
                        }
                    });
        }
    }

    public void saveSnapshot(boolean clear) {
        try {
            outputStruct = new AboudaFileFormat();
            if (canvas != null && canvas.getLayers() != null) {
                outputStruct.setImageDimentions(
                        canvas.getImageWidth(),
                        canvas.getImageHeight());
                for (Layer layer : canvas.getLayers()) {
                    outputStruct.addLayer(layer);
                }
            }

            inEvents.push(outputStruct.generateFile());

            if (clear && !outEvents.empty()) {
                outEvents.clear();
            }

            updateDoers();
        } catch (IOException ex) {
        }
    }

    public void redoSnapshot() {
        if (canRedoSnapshot()) {
            inEvents.push(outEvents.pop());
            applySnapshot();
        }
    }

    public void undoSnapshot() {
        if (canUndoSnapshot()) {
            outEvents.push(inEvents.pop());
            applySnapshot();
            updateDoers();
        }
    }

    public boolean canUndoSnapshot() {
        return inEvents.size() > 1;
    }

    public boolean canRedoSnapshot() {
        return !outEvents.empty();
    }

    public void applySnapshot() {
        if (!inEvents.empty()) {
            snapshot = new AboudaFile(inEvents.peek(), canvas);
        }
    }

    public void updateDoers() {
        if (canvas.getMainFrame() != null) {
            if (canvas.getMainFrame().getMenuBarPanel() != null) {
                canvas.getMainFrame().getMenuBarPanel().redoAction.setEnabled(canRedoSnapshot());
                canvas.getMainFrame().getMenuBarPanel().undoAction.setEnabled(canUndoSnapshot());
            }

            if (canvas.getMainFrame().getToolBarPanel() != null) {
                canvas.getMainFrame().getToolBarPanel().redoAction.setEnabled(canRedoSnapshot());
                canvas.getMainFrame().getToolBarPanel().undoAction.setEnabled(canUndoSnapshot());
            }
        }
    }

    public void reset() {
        inEvents.clear();
        outEvents.clear();
        updateDoers();
    }
}
