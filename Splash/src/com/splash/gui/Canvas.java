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

import com.alee.laf.rootpane.WebFrame;
import com.splash.gui.dialogs.BrushDialog;
import com.splash.gui.dialogs.ColorPickerDialog;
import com.splash.gui.dialogs.LayersDialog;
import com.splash.gui.dialogs.ToolBoxDialog;
import com.splash.gui.elements.DimensionedTool;
import com.splash.gui.elements.Layer;
import com.splash.gui.elements.PixeledTool;
import com.splash.gui.elements.Tool;
import com.splash.gui.tools.Fill;
import com.splash.gui.tools.FreeHand;
import com.splash.gui.tools.Line;
import com.splash.gui.tools.Move;
import com.splash.gui.tools.Select;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.TexturePaint;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JLabel;

public class Canvas extends JComponent implements MouseListener,
        MouseMotionListener {

    private int mouseX, mouseY, mouseLastDragPosX, mouseLastDragPosY;

    private JLabel mouseMoveLabel = null;

    private float cam_zoom;
    private float cam_positionX;
    private float cam_positionY;

    private int selectedLayer;

    private ToolBoxDialog toolBox;
    private Tool selectedTool;
    private ColorPickerDialog colorDialog;

    public Tool getSelectedTool() {
        return selectedTool;
    }

    private BufferedImage image;
    private int imageHeight, imageWidth, imageX, imageY;
    private ArrayList<Layer> layers;
    private LayersDialog layersDialog;

    private ArrayList<Tool> selectedObjects;

    public int getImageHeight() {
        return imageHeight;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageX() {
        return imageX;
    }

    public int getImageY() {
        return imageY;
    }

    public Canvas(int x, int y, int width, int height) {
        super();

        setMinimumSize(new Dimension(8, 8));
        setDoubleBuffered(true);
        setFocusable(true);

        addMouseListener(Canvas.this);
        addMouseMotionListener(Canvas.this);

        cam_zoom = 1;
        cam_positionX = 0;
        cam_positionY = 0;

        updateBackground();

        mouseLastDragPosX = 0;
        mouseLastDragPosY = 0;

        imageHeight = height;
        imageWidth = width;
        imageX = x;
        imageY = y;

        setSize(width, height);
        selectedLayer = 0;
    }

    public static BufferedImage backgroundLight, backgroundDark;

    private Rectangle2D backgroundRectangle;
    private TexturePaint paintLight, paintDark;

    static {
        try {
            backgroundLight = ImageIO.read(new File("res/tbg.png"));
            backgroundDark = ImageIO.read(new File("res/tbgd.png"));
        } catch (IOException e) {
        }
    }

    @Override
    public void paintComponent(Graphics _g) {
        Graphics2D g = (Graphics2D) _g;
        g.setPaint(paintLight);
        g.fillRect(imageX, imageY, imageWidth, imageHeight);
        g.drawImage(getRenderedImage(), imageX, imageY, null);
    }

    @Override
    public void setLocation(int x, int y) {
        super.setLocation(x, y);
        imageX = x;
        imageY = y;
    }

    private void updateBackground() {
        Rectangle2D.Float rect
                = new Rectangle2D.Float(0, 0, 16f / cam_zoom, 16f / cam_zoom);
        paintLight = new TexturePaint(backgroundLight, rect);
        paintDark = new TexturePaint(backgroundDark, rect);
    }

    public BufferedImage getRenderedImage() {
        image = new BufferedImage(
                imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        {
            if (layers != null) {
                Graphics2D g = image.createGraphics();
                for (Layer layer : layers) {
                    layer.paintComponent(g);
                }
                g.dispose();
            }
        }
        return image;
    }

    public BufferedImage getRenderedImage(Layer layer) {
        image = new BufferedImage(
                imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        {
            if (layer != null) {
                Graphics2D g = image.createGraphics();
                layer.paintComponent(g);
                g.dispose();
            }
        }
        return image;
    }

    public void setMouseMoveLabel(JLabel label) {
        mouseMoveLabel = label;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (withinBounds(
                e.getX() - getImageX(),
                e.getY() - getImageY(),
                layers.get(selectedLayer)) == null) {
            clearSelectedObjects();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (withinBounds(e.getX(), e.getY()) && selectedTool != null) {
            layers.get(selectedLayer).addTool(selectedTool);
            selectedTool.setCoordinates(
                    e.getX() - getImageX(), e.getY() - getImageY());

            if (colorDialog != null) {
                selectedTool.setColor(colorDialog.getColor());
            }

            if (mainFrame != null && mainFrame.getBrushDialog() != null) {
                selectedTool.setBorderSize(
                        mainFrame.getBrushDialog().getStrokeSize());
            }

            if (selectedTool instanceof Move) {
                if (selectedObjects != null && selectedObjects.size() == 1
                        && withinBounds(
                                e.getX() - getImageX(),
                                e.getY() - getImageY(),
                                selectedObjects.get(0))) {
                    mouseX = e.getX() - getImageX() - selectedObjects.get(0).getX();
                    mouseY = e.getY() - getImageY() - selectedObjects.get(0).getY();
                }
            } else {
                clearSelectedObjects();
            }

            if (selectedTool instanceof DimensionedTool) {
                ((DimensionedTool) selectedTool).setBaseCoordinates(
                        e.getX() - getImageX(), e.getY() - getImageY());
            } else if (selectedTool instanceof PixeledTool) {
            } else if (selectedTool instanceof Fill) {
                ((Fill) selectedTool).doFill(
                        getRenderedImage(layers.get(selectedLayer)));
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (selectedTool != null) {
            if (selectedTool instanceof Select) {
                if (selectedObjects != null) {
                    selectedObjects.clear();
                }

                selectedObjects
                        = ((Select) selectedTool).selectInboundObjects(
                                layers.get(selectedLayer));

                layers.get(selectedLayer).removeTool(selectedTool);

                if (selectedObjects.size() > 0) {
                    mainFrame.getToolBoxDialog().moveAction.doClick();
                }

            } else {
                selectedTool = selectedTool.newInstance();
            }

            repaint();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {

        if (selectedTool instanceof DimensionedTool) {
            mouseY = e.getY() - getImageY();
            mouseX = e.getX() - getImageX();

            if (mouseX >= ((DimensionedTool) selectedTool).getBaseX()) {
                selectedTool.setLocation(
                        ((DimensionedTool) selectedTool).getBaseX(),
                        selectedTool.getY());
                ((DimensionedTool) selectedTool).setWidth(
                        mouseX - selectedTool.getX());
            } else {
                selectedTool.setLocation(
                        mouseX,
                        selectedTool.getY());
                ((DimensionedTool) selectedTool).setWidth(
                        ((DimensionedTool) selectedTool).getBaseX() - mouseX);
            }

            if (mouseY >= ((DimensionedTool) selectedTool).getBaseY()) {
                selectedTool.setLocation(
                        selectedTool.getX(),
                        ((DimensionedTool) selectedTool).getBaseY());
                ((DimensionedTool) selectedTool).setHeight(
                        mouseY - selectedTool.getY());
            } else {
                selectedTool.setLocation(
                        selectedTool.getX(),
                        mouseY);
                ((DimensionedTool) selectedTool).setHeight(
                        ((DimensionedTool) selectedTool).getBaseY() - mouseY);
            }

        } else if (selectedTool instanceof Line) {
            ((Line) selectedTool).setEndPoint(
                    e.getX() - getImageX(),
                    e.getY() - getImageY());

        } else if (selectedTool instanceof PixeledTool) {
            ((PixeledTool) selectedTool).setCoordinates(
                    e.getX() - getImageX(),
                    e.getY() - getImageY());

        } else if (selectedTool instanceof Move) {

            if (selectedObjects != null && selectedObjects.size() == 1
                    && withinBounds(
                            e.getX() - getImageX(),
                            e.getY() - getImageY(),
                            selectedObjects.get(0))) {
                selectedObjects.get(0).setCoordinates(
                        e.getX() - getImageX() - mouseX,
                        e.getY() - getImageY() - mouseY);
            }           
        }

        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (mouseMoveLabel != null) {
            mouseMoveLabel.setText(
                    " " + (e.getX() - getImageX())
                    + ", " + (e.getY() - getImageY()));
        }

        Tool tool;
        if (withinBounds(e.getX(), e.getY())) {
            if (selectedTool instanceof Move
                    && (tool = withinBounds(
                            e.getX() - getImageX(),
                            e.getY() - getImageY(),
                            layers.get(selectedLayer))) != null
                    && tool.isSelected()) {
                setCursor(new Cursor(Cursor.MOVE_CURSOR));
            } else if (selectedTool instanceof DimensionedTool
                    || selectedTool instanceof PixeledTool
                    || selectedTool instanceof Line) {
                setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
            } else {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        } else {
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }

    void setLayersModel(ArrayList<Layer> layers) {
        this.layers = layers;
    }

    public void setSelectedLayer(int index) {
        selectedLayer = index;
    }

    public void setToolBox(ToolBoxDialog toolBoxDialog) {
        toolBox = toolBoxDialog;
    }

    public void setSelectedTool(Tool tool) {
        selectedTool = tool;
    }

    private boolean withinBounds(int x, int y) {
        final java.awt.Rectangle cellBounds
                = new java.awt.Rectangle(
                        getImageX(), getImageY(),
                        getImageWidth(), getImageHeight());
        return cellBounds.contains(x, y);
    }

    private boolean withinBounds(int x, int y, Tool tool) {
        if (tool instanceof DimensionedTool) {
            if (x >= tool.getX()
                    && (tool.getX()
                    + ((DimensionedTool) tool).getWidth() >= x)
                    && (y >= tool.getY())
                    && (tool.getY()
                    + ((DimensionedTool) tool).getHeight() >= y)) {
                return true;
            }
        } else if (tool instanceof PixeledTool) {
            if (x >= ((PixeledTool) tool).getMinX()
                    && ((PixeledTool) tool).getMaxX() >= x
                    && y >= ((PixeledTool) tool).getMinY()
                    && ((PixeledTool) tool).getMaxY() >= y) {
                return true;
            }
        } else if (tool instanceof Tool) {
            if (tool instanceof Line) {
                if (x >= tool.getX()
                        && tool.getX() >= x
                        && y >= tool.getY()
                        && tool.getY() >= y
                        && x >= ((Line) tool).getEndX()
                        && ((Line) tool).getEndX() >= x
                        && y >= ((Line) tool).getEndY()
                        && ((Line) tool).getEndY() >= y) {
                    return true;
                }
            } else {
                /*
                 if (x >= tool.getX()
                 && tool.getX() >= x
                 && y >= tool.getY()
                 && tool.getY() >= y) {
                 return tool;
                 }
                 */
            }
        }

        return false;
    }

    private Tool withinBounds(int x, int y, Layer layer) {
        for (Tool tool : layer.getTools()) {
            if (withinBounds(x, y, tool)) {
                return tool;
            }
        }

        return null;
    }

    private MainWindow mainFrame = null;

    public WebFrame getMainFrame() {
        return mainFrame;
    }

    public void setMainWindow(MainWindow mainFrame) {
        this.mainFrame = mainFrame;
    }

    public void setBrushBox(BrushDialog aThis) {
    }

    public ArrayList<Layer> getLayers() {
        return layers;
    }

    public void setLayersDialog(LayersDialog layersDialog) {
        this.layersDialog = layersDialog;
    }

    public void setColorPickerDialog(ColorPickerDialog colorDialog) {
        this.colorDialog = colorDialog;
    }

    public void addLayer(Layer layer) {
        if (layersDialog != null) {
            layersDialog.addNewLayer(layer);
        }
    }

    public void clearLayers() {
        if (layersDialog != null) {
            layersDialog.clearLayers();
        }
    }

    public void clearSelectedObjects() {
        if (selectedObjects != null) {
            for (Tool object : selectedObjects) {
                object.setSelected(false);
            }
            selectedObjects.clear();
            selectedObjects = null;
        }
    }
}
