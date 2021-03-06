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
package com.splash.gui.dialogs;

import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.rootpane.WebFrame;
import com.splash.gui.Canvas;
import com.splash.gui.elements.ImagedTool;
import com.splash.gui.elements.Tool;
import com.splash.gui.elements.WrapLayout;
import com.splash.gui.tools.Circle;
import com.splash.gui.tools.Ellipse;
import com.splash.gui.tools.Eraser;
import com.splash.gui.tools.Fill;
import com.splash.gui.elements.LinedTool;
import com.splash.gui.tools.ColorPicker;
import com.splash.gui.tools.Rectangle;
import com.splash.gui.tools.FreeHand;
import com.splash.gui.tools.IsocelesTriangle;
import com.splash.gui.tools.Move;
import com.splash.gui.tools.RightTriangle;
import com.splash.gui.tools.RoundedRect;
import com.splash.gui.tools.Select;
import com.splash.gui.tools.Square;
import com.splash.gui.tools.Text;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;

public class ToolBoxDialog extends WebDialog {

    public JToggleButton rectangleAction = new JToggleButton(
            new ImageIcon("res/images/draw-rectangle.png"));
    public JToggleButton squareAction = new JToggleButton(
            new ImageIcon("res/images/draw-square.png"));
    public JToggleButton isocelesTriangleAction = new JToggleButton(
            new ImageIcon("res/images/draw-triangle3.png"));
    public JToggleButton rightTriangleAction = new JToggleButton(
            new ImageIcon("res/images/draw-triangle.png"));
    public JToggleButton circleAction = new JToggleButton(
            new ImageIcon("res/images/draw-circle.png"));
    public JToggleButton ellipseAction = new JToggleButton(
            new ImageIcon("res/images/draw-ellipse.png"));
    public JToggleButton lineAction = new JToggleButton(
            new ImageIcon("res/images/draw-line.png"));
    public JToggleButton freeHandAction = new JToggleButton(
            new ImageIcon("res/images/draw-freehand.png"));
    public JToggleButton eraserAction = new JToggleButton(
            new ImageIcon("res/images/draw-eraser.png"));
    public JToggleButton textAction = new JToggleButton(
            new ImageIcon("res/images/draw-text.png"));
    public JToggleButton roundedAction = new JToggleButton(
            new ImageIcon("res/images/draw-rectangle-round.png"));
    public JToggleButton fillAction = new JToggleButton(
            new ImageIcon("res/images/fill-color.png"));
    public JToggleButton selectAction = new JToggleButton(
            new ImageIcon("res/images/select-rectangular.png"));
    public JToggleButton moveAction = new JToggleButton(
            new ImageIcon("res/images/edit-select.png"));
    public JToggleButton pickAction = new JToggleButton(
            new ImageIcon("res/images/color-picker-grey.png"));
    public JToggleButton imageAction = new JToggleButton(
            new ImageIcon("res/images/edit-image-face-add.png"));

    private Canvas canvas = null;
    private Tool currentTool = null;
    private BrushDialog brushBox = null;

    public ToolBoxDialog(WebFrame parent) {
        super(parent, "Tool Box", false);

        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);

        setSize(240, 185);
        setResizable(true);
        setLayout(new WrapLayout(0, 3, 0));

        moveAction.setSelected(true);

        add(moveAction);
        add(selectAction);
        add(freeHandAction);
        add(fillAction);
        add(imageAction);
        add(eraserAction);
        add(textAction);
        add(pickAction);
        add(lineAction);
        add(rectangleAction);
        add(roundedAction);
        add(squareAction);
        add(isocelesTriangleAction);
        add(rightTriangleAction);
        add(circleAction);
        add(ellipseAction);

        imageAction.setToolTipText("Image");
        imageAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                untoggleButtons();
                imageAction.setSelected(true);

                currentTool = new ImagedTool();
                setCanvasTool(currentTool);
            }
        });

        moveAction.setToolTipText("Move Selection");
        moveAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                untoggleButtons();
                moveAction.setSelected(true);

                currentTool = new Move();
                setCanvasTool(currentTool);
            }
        });

        selectAction.setToolTipText("Rectangle Select");
        selectAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                untoggleButtons();
                selectAction.setSelected(true);

                currentTool = new Select();
                setCanvasTool(currentTool);
            }
        });

        freeHandAction.setToolTipText("Pencil");
        freeHandAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                untoggleButtons();
                freeHandAction.setSelected(true);

                currentTool = new FreeHand();
                setCanvasTool(currentTool);

                if (brushBox != null) {
                    brushBox.EnableBrushBox(true);
                }
            }
        });

        fillAction.setToolTipText("Paint Bucket");
        fillAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                untoggleButtons();
                fillAction.setSelected(true);

                currentTool = new Fill();
                setCanvasTool(currentTool);
            }
        });

        roundedAction.setToolTipText("Rounded Rectangle");
        roundedAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                untoggleButtons();
                roundedAction.setSelected(true);

                currentTool = new RoundedRect();
                setCanvasTool(currentTool);

                if (brushBox != null) {
                    brushBox.EnableBrushBox(true);
                }
            }
        });

        eraserAction.setToolTipText("Eraser");
        eraserAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                untoggleButtons();
                eraserAction.setSelected(true);

                currentTool = new Eraser();
                setCanvasTool(currentTool);

                if (brushBox != null) {
                    brushBox.EnableBrushBox(true);
                }
            }
        });

        pickAction.setToolTipText("Color Picker");
        pickAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                untoggleButtons();
                pickAction.setSelected(true);

                currentTool = new ColorPicker();
                setCanvasTool(currentTool);
            }
        });

        lineAction.setToolTipText("Line");
        lineAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                untoggleButtons();
                lineAction.setSelected(true);

                currentTool = new LinedTool();
                setCanvasTool(currentTool);

                if (brushBox != null) {
                    brushBox.EnableBrushBox(true);
                }
            }
        });

        rectangleAction.setToolTipText("Rectangle");
        rectangleAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                untoggleButtons();
                rectangleAction.setSelected(true);

                currentTool = new Rectangle();
                setCanvasTool(currentTool);

                if (brushBox != null) {
                    brushBox.EnableBrushBox(true);
                }
            }
        });

        rightTriangleAction.setToolTipText("Right-Angled Triangle");
        rightTriangleAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                untoggleButtons();
                rightTriangleAction.setSelected(true);

                currentTool = new RightTriangle();
                setCanvasTool(currentTool);

                if (brushBox != null) {
                    brushBox.EnableBrushBox(true);
                }
            }
        });

        isocelesTriangleAction.setToolTipText("Isoceles Triangle");
        isocelesTriangleAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                untoggleButtons();
                isocelesTriangleAction.setSelected(true);

                currentTool = new IsocelesTriangle();
                setCanvasTool(currentTool);

                if (brushBox != null) {
                    brushBox.EnableBrushBox(true);
                }
            }
        });

        circleAction.setToolTipText("Circle");
        circleAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                untoggleButtons();
                circleAction.setSelected(true);

                currentTool = new Circle();
                setCanvasTool(currentTool);

                if (brushBox != null) {
                    brushBox.EnableBrushBox(true);
                }
            }
        });

        ellipseAction.setToolTipText("Ellipse");
        ellipseAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                untoggleButtons();
                ellipseAction.setSelected(true);

                currentTool = new Ellipse();
                setCanvasTool(currentTool);

                if (brushBox != null) {
                    brushBox.EnableBrushBox(true);
                }
            }
        });

        textAction.setToolTipText("Text");
        textAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                untoggleButtons();
                textAction.setSelected(true);

                currentTool = new Text();
                setCanvasTool(currentTool);

                if (brushBox != null) {
                    brushBox.EnableFontBox(true);
                }
            }
        });

        squareAction.setToolTipText("Square");
        squareAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                untoggleButtons();
                squareAction.setSelected(true);

                currentTool = new Square();
                setCanvasTool(currentTool);

                if (brushBox != null) {
                    brushBox.EnableBrushBox(true);
                }
            }
        });
    }

    private void untoggleButtons() {
        moveAction.setSelected(false);
        selectAction.setSelected(false);
        freeHandAction.setSelected(false);
        fillAction.setSelected(false);
        roundedAction.setSelected(false);
        eraserAction.setSelected(false);
        pickAction.setSelected(false);
        lineAction.setSelected(false);
        rectangleAction.setSelected(false);
        rightTriangleAction.setSelected(false);
        isocelesTriangleAction.setSelected(false);
        circleAction.setSelected(false);
        ellipseAction.setSelected(false);
        squareAction.setSelected(false);
        textAction.setSelected(false);
        imageAction.setSelected(false);

        if (brushBox != null) {
            brushBox.EnableBrushBox(false);
            brushBox.EnableFontBox(false);
        }
    }

    private void setCanvasTool(Tool tool) {
        if (canvas != null) {
            canvas.setSelectedTool(tool);
        }
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
        canvas.setToolBox(this);
    }

    public void setBrushBox(BrushDialog brushBox) {
        this.brushBox = brushBox;
    }
}
