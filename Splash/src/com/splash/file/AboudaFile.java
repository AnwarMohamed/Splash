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
package com.splash.file;

import com.splash.file.AboudaFileFormat.FileHeader;
import com.splash.file.AboudaFileFormat.ImageDataItem;
import static com.splash.file.AboudaFileFormat.OBJTYPE_CIRCLE;
import static com.splash.file.AboudaFileFormat.OBJTYPE_ELLIPSE;
import static com.splash.file.AboudaFileFormat.OBJTYPE_EQUTRIANGLE;
import static com.splash.file.AboudaFileFormat.OBJTYPE_ISOTRIANGLE;
import static com.splash.file.AboudaFileFormat.OBJTYPE_LINE;
import static com.splash.file.AboudaFileFormat.OBJTYPE_RECTANGLE;
import static com.splash.file.AboudaFileFormat.OBJTYPE_RIGHTTRIANGLE;
import static com.splash.file.AboudaFileFormat.OBJTYPE_SQUARE;
import com.splash.gui.Canvas;
import com.splash.gui.elements.DimensionedTool;
import com.splash.gui.elements.Layer;
import com.splash.gui.elements.Tool;
import com.splash.gui.tools.Circle;
import com.splash.gui.tools.Ellipse;
import com.splash.gui.tools.EquilateralTriangle;
import com.splash.gui.tools.IsocelesTriangle;
import com.splash.gui.tools.Line;
import com.splash.gui.tools.Rectangle;
import com.splash.gui.tools.RightAngledTriangle;
import com.splash.gui.tools.Square;
import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class AboudaFile {

    public AboudaFile(byte[] buffer, Canvas canvas) {
        try {
            if (buffer.length >= 34) {
                InputStream inputStream = new ByteArrayInputStream(buffer);
                parseFile(inputStream, canvas);
            }
        } catch (IOException ex) {
        }
    }

    public AboudaFile(String filename, Canvas canvas) {
        try {
            InputStream inputStream
                    = new BufferedInputStream(new FileInputStream(filename));
            parseFile(inputStream, canvas);
        } catch (IOException e) {
        }
    }

    public final static String signature = "ABOUDA";

    private void parseFile(InputStream buffer, Canvas canvas)
            throws IOException {

        if (buffer.available() < 34) {
            return;
        }

        FileHeader fileHeader = new FileHeader();
        buffer.read(fileHeader.signature, 0, 6);

        if (!signature.equals(
                new String(fileHeader.signature, 0, 6))) {
            return;
        }

        fileHeader.fileSize = intFromByteArray(buffer);
        fileHeader.colorsDataOffset = intFromByteArray(buffer);
        fileHeader.colorsDataSize = intFromByteArray(buffer);
        fileHeader.layersDataOffset = intFromByteArray(buffer);
        fileHeader.layersDataSize = intFromByteArray(buffer);
        fileHeader.imageDataOffset = intFromByteArray(buffer);
        fileHeader.imageDataSize = intFromByteArray(buffer);

        ArrayList<Integer> colors = null;

        if (fileHeader.colorsDataSize > 0) {
            int colorsSize = intFromByteArray(buffer);
            colors = new ArrayList<>();

            for (int i = 0; i < colorsSize; i++) {
                colors.add(intFromByteArray(buffer));
            }
        }

        ArrayList<ArrayList<Integer>> layers;
        ArrayList<Integer> offsets;
        int imageWidth, imageHeight;

        if (fileHeader.layersDataSize > 0) {
            int layersSize = shortFromByteArray(buffer);
            imageWidth = shortFromByteArray(buffer);
            imageHeight = shortFromByteArray(buffer);
            layers = new ArrayList<>();

            for (int i = 0; i < layersSize; i++) {
                int objectsSize = shortFromByteArray(buffer);
                offsets = new ArrayList<>();

                for (int j = 0; j < objectsSize; j++) {
                    offsets.add(intFromByteArray(buffer));
                }

                layers.add(offsets);
            }

            if (fileHeader.imageDataSize > 0) {
                byte[] imageData = new byte[buffer.available()];
                buffer.read(imageData);

                canvas.clearLayers();

                Collections.reverse(layers);
                for (ArrayList<Integer> layerItem : layers) {
                    Layer newLayer = new Layer(imageWidth, imageHeight);

                    Tool newTool = null;
                    ImageDataItem imageItem;

                    for (Integer offset : layerItem) {
                        imageItem = new ImageDataItem();

                        imageItem.type = imageData[offset];
                        imageItem.x = shortFromByteArray(
                                Arrays.copyOfRange(
                                        imageData, offset + 1, offset + 3));
                        imageItem.y = shortFromByteArray(
                                Arrays.copyOfRange(
                                        imageData, offset + 3, offset + 5));
                        imageItem.color = intFromByteArray(
                                Arrays.copyOfRange(
                                        imageData, offset + 5, offset + 9));
                        imageItem.border = imageData[offset + 9];
                        imageItem.size = intFromByteArray(
                                Arrays.copyOfRange(
                                        imageData, offset + 10, offset + 14));

                        switch (imageItem.type) {
                            case OBJTYPE_CIRCLE:
                                newTool = new Circle();
                                break;
                            case OBJTYPE_ELLIPSE:
                                newTool = new Ellipse();
                                break;
                            case OBJTYPE_EQUTRIANGLE:
                                newTool = new EquilateralTriangle();
                                break;
                            case OBJTYPE_ISOTRIANGLE:
                                newTool = new IsocelesTriangle();
                                break;
                            case OBJTYPE_RECTANGLE:
                                newTool = new Rectangle();
                                break;
                            case OBJTYPE_SQUARE:
                                newTool = new Square();
                                break;
                            case OBJTYPE_RIGHTTRIANGLE:
                                newTool = new RightAngledTriangle();
                                break;
                            case OBJTYPE_LINE:
                                newTool = new Line();
                                break;
                        }

                        newTool.setLocation(imageItem.x, imageItem.y);
                        newTool.setColor(new Color(colors.get(imageItem.color)));
                        newTool.setBorderSize(imageItem.border);

                        switch (imageItem.type) {
                            case OBJTYPE_CIRCLE:
                            case OBJTYPE_ELLIPSE:
                            case OBJTYPE_EQUTRIANGLE:
                            case OBJTYPE_ISOTRIANGLE:
                            case OBJTYPE_RECTANGLE:
                            case OBJTYPE_SQUARE:
                            case OBJTYPE_RIGHTTRIANGLE:
                                imageItem.width = shortFromByteArray(
                                        Arrays.copyOfRange(imageData,
                                                offset + 14, offset + 16));
                                imageItem.height = shortFromByteArray(
                                        Arrays.copyOfRange(imageData,
                                                offset + 16, offset + 18));

                                ((DimensionedTool) newTool).setWidth(
                                        imageItem.width);
                                ((DimensionedTool) newTool).setHeight(
                                        imageItem.height);
                                break;
                            case OBJTYPE_LINE:
                                imageItem.endX = shortFromByteArray(
                                        Arrays.copyOfRange(imageData,
                                                offset + 14, offset + 16));
                                imageItem.endY = shortFromByteArray(
                                        Arrays.copyOfRange(imageData,
                                                offset + 16, offset + 18));
                                ((Line) newTool).setEndPoint(
                                        imageItem.endX, imageItem.endY);
                                break;
                        }

                        newLayer.addTool(newTool);
                    }

                    canvas.addLayer(newLayer);
                }
            }
        }

        isReady = true;
    }

    private boolean isReady = false;

    public boolean isReady() {
        return isReady;
    }

    private short shortFromByteArray(byte[] buffer) {
        return ByteBuffer.wrap(buffer).getShort();
    }

    private int intFromByteArray(byte[] buffer) {
        return ByteBuffer.wrap(buffer).getInt();
    }

    private short shortFromByteArray(InputStream is)
            throws IOException {
        byte[] tempShort = new byte[2];
        is.read(tempShort, 0, 2);
        return ByteBuffer.wrap(tempShort).getShort();
    }

    private int intFromByteArray(InputStream is)
            throws IOException {
        byte[] tempInt = new byte[4];
        is.read(tempInt, 0, 4);
        return ByteBuffer.wrap(tempInt).getInt();
    }
}
