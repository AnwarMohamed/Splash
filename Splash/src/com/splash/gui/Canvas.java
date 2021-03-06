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

import com.alee.laf.filechooser.WebFileChooser;
import com.splash.SnapshotManager;
import com.splash.gui.dialogs.BrushDialog;
import com.splash.gui.dialogs.ColorPickerDialog;
import com.splash.gui.dialogs.LayersDialog;
import com.splash.gui.dialogs.ToolBoxDialog;
import com.splash.gui.elements.DimensionedTool;
import com.splash.gui.elements.ImagedTool;
import com.splash.gui.elements.Layer;
import com.splash.gui.elements.PixeledTool;
import com.splash.gui.elements.Tool;
import com.splash.gui.tools.Fill;
import com.splash.gui.elements.LinedTool;
import com.splash.gui.tools.ColorPicker;
import com.splash.gui.tools.Move;
import com.splash.gui.tools.Select;
import com.splash.gui.tools.Text;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.TexturePaint;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Canvas extends JComponent implements MouseListener,
        MouseMotionListener {

    private int mouseX, mouseY;
    private JLabel mouseMoveLabel = null;

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

    private SnapshotManager snapshotManager;

    public SnapshotManager getSnapshotManager() {
        return snapshotManager;
    }

    public ArrayList<Tool> getSelectedObjects() {
        return selectedObjects;
    }

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

    public Canvas(MainWindow mainWindow, int x, int y, int width, int height) {
        super();

        setMinimumSize(new Dimension(8, 8));
        setDoubleBuffered(true);
        setFocusable(true);

        this.mainFrame = mainWindow;

        addMouseListener(Canvas.this);
        addMouseMotionListener(Canvas.this);

        updateBackground();

        imageHeight = height;
        imageWidth = width;
        imageX = x;
        imageY = y;

        setSize(width, height);
        selectedLayer = 0;

        snapshotManager = new SnapshotManager(Canvas.this);
        snapshotManager.updateDoers();

        try {
            backgroundLight = ImageIO.read(new File("res/tbg.png"));
        } catch (IOException e) {
        }

        KeyEventDispatcher keyEventDispatcher = new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(final KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    if (selectedObjects != null) {
                        for (Tool object : selectedObjects) {
                            if (layers.get(selectedLayer).getTools().contains(object)) {
                                layers.get(selectedLayer).getTools().remove(object);
                            }
                        }
                        snapshotManager.saveSnapshot(true);
                    }
                    repaint();
                    clearSelectedObjects();
                } else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_A) {
                    for (Tool tool : layers.get(selectedLayer).getTools()) {
                        tool.setSelected(true);

                        if (selectedObjects == null) {
                            selectedObjects = new ArrayList<>();
                        }

                        selectedObjects.add(tool);
                    }

                    mainFrame.getToolBoxDialog().moveAction.doClick();
                    repaint();
                }
                return false;
            }
        };

        KeyboardFocusManager.getCurrentKeyboardFocusManager()
                .addKeyEventDispatcher(keyEventDispatcher);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                fileChooser = new WebFileChooser();
                fileChooser.setAcceptAllFileFilterUsed(false);
                fileChooser.setFileFilter(
                        new FileNameExtensionFilter(
                                "Image Files", "jpg", "png", "gif", "jpeg"));
            }
        });
    }

    private WebFileChooser fileChooser;

    public static BufferedImage backgroundLight;

    private TexturePaint paintLight;

    static {
        try {
            backgroundLight = ImageIO.read(new File("res/tbg.png"));
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
                = new Rectangle2D.Float(0, 0, 16, 16);
        paintLight = new TexturePaint(backgroundLight, rect);
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

        if (withinBounds(e.getX(), e.getY()) && selectedTool != null) {
            if (selectedTool instanceof Fill) {

                ArrayList<Tool> objects = new ArrayList<>();
                Fill fillTool = (Fill) selectedTool;

                if (colorDialog != null) {
                    selectedTool.setColor(colorDialog.getColor());
                }

                for (Tool tool : layers.get(selectedLayer).getTools()) {
                    if (tool.withinBounds(
                            e.getX() - getImageX(),
                            e.getY() - getImageY())) {
                        objects.add(tool);
                    }
                }

                if (objects.size() == 1 && objects.get(0) instanceof Fill) {
                    if (colorDialog != null) {
                        objects.get(0).setColor(colorDialog.getColor());
                    }

                    ((Fill) objects.get(0)).fillArea(
                            e.getX() - getImageX(),
                            e.getY() - getImageY());

                    layers.get(selectedLayer).removeTool(fillTool);
                } else {
                    fillTool.mergeTools(objects);
                    fillTool.fillArea(
                            e.getX() - getImageX(),
                            e.getY() - getImageY());

                    for (Tool tool : objects) {
                        layers.get(selectedLayer).removeTool(tool);
                    }
                }

                getSnapshotManager().saveSnapshot(true);
            } else if (selectedTool instanceof Text) {

            } else if (selectedTool instanceof ImagedTool) {

                fileChooser.setDialogTitle("Insert Image");
                int returnVal = fileChooser.showSaveDialog(this);

                if (returnVal == WebFileChooser.APPROVE_OPTION) {
                    Image iconImage = new ImageIcon(
                            fileChooser.getSelectedFile().getAbsolutePath())
                            .getImage();

                    BufferedImage bufferedImage = new BufferedImage(
                            iconImage.getWidth(null), iconImage.getHeight(null),
                            BufferedImage.TYPE_INT_ARGB);

                    Graphics g = bufferedImage.createGraphics();
                    g.drawImage(iconImage, 0, 0, null);
                    g.dispose();

                    ((ImagedTool) selectedTool).setWidth(bufferedImage.getWidth());
                    ((ImagedTool) selectedTool).setHeight(bufferedImage.getHeight());
                    ((ImagedTool) selectedTool).setImage(bufferedImage);
                    selectedTool.setSize(
                            iconImage.getWidth(null),
                            iconImage.getHeight(null));

                    getSnapshotManager().saveSnapshot(true);
                }

            }

            selectedTool = selectedTool.newInstance();
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
                if (selectedObjects != null && selectedObjects.size() > 0) {
                    for (Tool selectedObject : selectedObjects) {
                        if (selectedObject.withinBounds(
                                e.getX() - getImageX(),
                                e.getY() - getImageY())) {
                            mouseX = e.getX() - getImageX();
                            mouseY = e.getY() - getImageY();
                            break;
                        }
                    }
                }
            } else {
                clearSelectedObjects();
            }

            if (selectedTool instanceof DimensionedTool) {
                ((DimensionedTool) selectedTool).setBaseCoordinates(
                        e.getX() - getImageX(), e.getY() - getImageY());
            } else if (selectedTool instanceof PixeledTool) {
            } else if (selectedTool instanceof Fill) {
            } else if (selectedTool instanceof ColorPicker) {
                if (mainFrame != null
                        && mainFrame.getColorPickerDialog() != null) {
                    mainFrame.getColorPickerDialog().setCurrentColor(
                            getRenderedImage(
                                    layers.get(selectedLayer)).getRGB(
                                    e.getX() - getImageX(),
                                    e.getY() - getImageY()));
                }
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
                    mainFrame.getBrushDialog().EnableBrushBox(true);
                }

            } else if (selectedTool instanceof Move
                    || selectedTool instanceof ColorPicker) {
                layers.get(selectedLayer).removeTool(selectedTool);

                snapshotManager.saveSnapshot(true);

            } else {

                if (selectedTool instanceof DimensionedTool) {
                    if (((DimensionedTool) selectedTool).getHeight() > 0
                            || ((DimensionedTool) selectedTool).getWidth() > 0) {
                        snapshotManager.saveSnapshot(true);
                    }
                } else if (selectedTool instanceof PixeledTool) {
                    if (((PixeledTool) selectedTool).getPixels().size() > 0) {
                        snapshotManager.saveSnapshot(true);
                    }
                } else if (selectedTool instanceof LinedTool) {
                    if (((LinedTool) selectedTool).getEndX() != 0
                            || ((LinedTool) selectedTool).getEndY() != 0) {
                        snapshotManager.saveSnapshot(true);
                    }
                }

                if (!(selectedTool instanceof Fill)
                        && !(selectedTool instanceof ImagedTool)) {
                    selectedTool = selectedTool.newInstance();
                }
            }

            repaint();

            if (mainFrame != null) {
                mainFrame.setEdited(true);
            }

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

        } else if (selectedTool instanceof LinedTool) {
            ((LinedTool) selectedTool).setEndPoint(
                    e.getX() - getImageX(),
                    e.getY() - getImageY());

        } else if (selectedTool instanceof PixeledTool) {
            ((PixeledTool) selectedTool).setCoordinates(
                    e.getX() - getImageX(),
                    e.getY() - getImageY());

        } else if (selectedTool instanceof Move) {

            boolean inRange = false;
            if (selectedObjects != null && selectedObjects.size() > 0) {
                for (Tool selectedObject : selectedObjects) {
                    if (selectedObject.withinBounds(
                            e.getX() - getImageX(),
                            e.getY() - getImageY())) {
                        inRange = true;
                        break;
                    }
                }

                if (inRange) {
                    for (Tool selectedObject : selectedObjects) {
                        selectedObject.translateBy(
                                e.getX() - getImageX() - mouseX,
                                e.getY() - getImageY() - mouseY);
                    }
                    mouseX = e.getX() - getImageX();
                    mouseY = e.getY() - getImageY();

                }
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
                    || selectedTool instanceof LinedTool
                    || selectedTool instanceof ColorPicker) {
                setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));

                if (selectedTool instanceof ColorPicker) {
                    if (mainFrame != null
                            && mainFrame.getColorPickerDialog() != null) {
                        mainFrame.getColorPickerDialog().setPreviewColor(
                                getRenderedImage(
                                        layers.get(selectedLayer)).getRGB(
                                        e.getX() - getImageX(),
                                        e.getY() - getImageY()));
                    }
                }
            } else {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        } else {
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }

    public void setLayersModel(ArrayList<Layer> layers) {
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

    private Tool withinBounds(int x, int y, Layer layer) {
        for (Tool tool : layer.getTools()) {
            if (tool.withinBounds(x, y)) {
                return tool;
            }
        }
        return null;
    }

    private MainWindow mainFrame = null;

    public MainWindow getMainFrame() {
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

    public void clearWorkspace() {
        if (mainFrame != null && mainFrame.getLayersDialog() != null) {
            clearLayers();
            snapshotManager.reset();
            snapshotManager.saveSnapshot(true);
        }
    }

    public Image getImageFromClipboard() throws Exception {
        Transferable transferable
                = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
        if (transferable != null
                && transferable.isDataFlavorSupported(DataFlavor.imageFlavor)) {
            return (Image) transferable.getTransferData(DataFlavor.imageFlavor);
        } else {
            return null;
        }
    }

    void addTool(ImagedTool tool) {
        layers.get(selectedLayer).addTool(tool);
        repaint();
    }
}
