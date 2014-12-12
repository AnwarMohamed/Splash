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

import com.splash.gui.elements.DimensionedTool;
import com.splash.gui.elements.Layer;
import com.splash.gui.elements.Tool;
import com.splash.gui.tools.Circle;
import com.splash.gui.tools.Ellipse;
import com.splash.gui.tools.EquilateralTriangle;
import com.splash.gui.tools.IsocelesTriangle;
import com.splash.gui.tools.Rectangle;
import com.splash.gui.tools.RightAngledTriangle;
import com.splash.gui.tools.Square;
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class AboudaFileStructure {

    public FileHeader fileHeader = new FileHeader();
    public ColorsData colorsData = new ColorsData();
    public LayersData layersData = new LayersData();
    public ImageData imageData = new ImageData();

    public AboudaFileStructure() {
    }

    public byte[] generateFile() throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        prepareColorsData();
        prepareLayersData();
        prepareImageData();

        buffer.write(fileHeader.signature);
        buffer.write(toByteArray(fileHeader.fileSize));
        buffer.write(toByteArray(fileHeader.colorsDataOffset));
        buffer.write(toByteArray(fileHeader.colorsDataSize));
        buffer.write(toByteArray(fileHeader.layersDataOffset));
        buffer.write(toByteArray(fileHeader.layersDataSize));
        buffer.write(toByteArray(fileHeader.imageDataOffset));
        buffer.write(toByteArray(fileHeader.imageDataSize));

        if (fileHeader.colorsDataOffset > 0) {
            buffer.write(toByteArray(colorsData.header.size));
            for (ColorDataItem item : colorsData.header.items) {
                buffer.write(toByteArray(item.argb));
            }
        }

        if (fileHeader.layersDataOffset > 0) {
            buffer.write(toByteArray(layersData.header.size));
            buffer.write(toByteArray(layersData.header.width));
            buffer.write(toByteArray(layersData.header.height));
            for (LayersDataItem layer : layersData.header.items) {
                buffer.write(toByteArray(layer.objectsSize));
                for (ObjectItem object : layer.objects) {
                    buffer.write(toByteArray(object.offset));
                }
            }
        }

        if (fileHeader.imageDataOffset > 0) {

        }

        return buffer.toByteArray();
    }

    private void prepareColorsData() {
        if (colorsData.header.size > 0) {
            fileHeader.colorsDataOffset = 34;
            fileHeader.colorsDataSize = 4 + colorsData.header.size * 4;
        } else {
            fileHeader.colorsDataOffset = 0;
            fileHeader.colorsDataSize = 0;
        }
    }

    private void prepareLayersData() {
        fileHeader.layersDataOffset
                = fileHeader.colorsDataOffset + fileHeader.colorsDataSize;
        fileHeader.layersDataSize = 12 + layersData.header.size * 4;
        for (LayersDataItem layer : layersData.header.items) {
            fileHeader.layersDataSize += layer.objectsSize;
        }
    }

    private void prepareImageData() {
        fileHeader.layersDataOffset
                = fileHeader.layersDataOffset + fileHeader.layersDataSize;
    }

    private int addColor(Color color) {
        ColorDataItem colorItem = new ColorDataItem();
        colorItem.argb = color.getRGB();

        if (!colorsData.header.items.contains(colorItem)) {
            colorsData.header.items.add(colorItem);
            return colorsData.header.size++;
        } else {
            return colorsData.header.items.indexOf(colorItem);
        }
    }

    public void setImageDimentions(int width, int height) {
        layersData.header.width = width;
        layersData.header.height = height;
    }

    public final static int OBJTYPE_CIRCLE = 1;
    public final static int OBJTYPE_ELLIPSE = 2;
    public final static int OBJTYPE_EQUTRIANGLE = 3;
    public final static int OBJTYPE_ERASER = 4;
    public final static int OBJTYPE_FILL = 5;
    public final static int OBJTYPE_FREEHAND = 6;
    public final static int OBJTYPE_ISOTRIANGLE = 7;
    public final static int OBJTYPE_LINE = 8;
    public final static int OBJTYPE_RECTANGLE = 9;
    public final static int OBJTYPE_RIGHTTRIANGLE = 10;
    public final static int OBJTYPE_SQUARE = 11;
    public final static int OBJTYPE_TEXT = 12;

    public void addLayer(Layer layer) {
        LayersDataItem layerItem = new LayersDataItem();

        for (Tool tool : layer.getTools()) {
            ImageDataItem imageDataItem = new ImageDataItem();
            imageDataItem.border = (byte) tool.getBorderSize();
            imageDataItem.x = tool.getX();
            imageDataItem.y = tool.getY();
            imageDataItem.color = addColor(tool.getColor());

            if (tool instanceof DimensionedTool) {
                imageDataItem.height = ((DimensionedTool) tool).getHeight();
                imageDataItem.width = ((DimensionedTool) tool).getWidth();

                if (tool instanceof Circle) {
                    imageDataItem.type = OBJTYPE_CIRCLE;
                } else if (tool instanceof Ellipse) {
                    imageDataItem.type = OBJTYPE_ELLIPSE;
                } else if (tool instanceof EquilateralTriangle) {
                    imageDataItem.type = OBJTYPE_EQUTRIANGLE;
                } else if (tool instanceof IsocelesTriangle) {
                    imageDataItem.type = OBJTYPE_ISOTRIANGLE;
                } else if (tool instanceof Rectangle) {
                    imageDataItem.type = OBJTYPE_RECTANGLE;
                } else if (tool instanceof Square) {
                    imageDataItem.type = OBJTYPE_SQUARE;
                } else if (tool instanceof RightAngledTriangle) {
                    imageDataItem.type = OBJTYPE_RIGHTTRIANGLE;
                }
            }

            ObjectItem objectItem = new ObjectItem();
            objectItem.offset = addImageDataItem(imageDataItem);

            layerItem.objects.add(objectItem);
            layerItem.objectsSize++;
        }

        layersData.header.items.add(layerItem);
        layersData.header.size++;
    }

    class FileHeader {              // 34 bytes

        byte[] signature = new byte[]{'A', 'B', 'O', 'U', 'D', 'A'};
        int fileSize = 0;           // 4 bytes
        int colorsDataOffset = 0;   // 4 bytes
        int colorsDataSize = 0;     // 4 bytes
        int layersDataOffset = 0;   // 4 bytes
        int layersDataSize = 0;     // 4 bytes
        int imageDataOffset = 0;    // 4 bytes
        int imageDataSize = 0;      // 4 bytes
    }

    class ColorsData {

        ColorsDataHeader header = new ColorsDataHeader();
    }

    class ColorsDataHeader {

        int size = 0;       // 4 bytes
        ArrayList<ColorDataItem> items = new ArrayList<>();
    }

    class ColorDataItem {

        int argb;
    }

    class LayersData {

        LayersDataHeader header = new LayersDataHeader();
    }

    class LayersDataHeader {

        int size = 0;                           // 4 bytes
        int width = 0;                          // 4 bytes
        int height = 0;                         // 4 bytes
        ArrayList<LayersDataItem> items = new ArrayList<>();
    }

    class LayersDataItem {

        int objectsSize = 0;                // 4 bytes
        ArrayList<ObjectItem> objects = new ArrayList<>();
    }

    class ObjectItem {

        int offset = 0;     // 4 bytes
    }

    class ImageData {

        ImageDataHeader header = new ImageDataHeader();
    }

    class ImageDataHeader {

        int size = 0;   // 4 bytes
        ArrayList<ImageDataItem> items = new ArrayList<>();
    }

    class ImageDataItem {

        int color = 0;
        byte border = 0;
        int x = 0;
        int y = 0;
        int width = 0;
        int height = 0;

        byte type = 0;      // 1 byte
        byte flags = 0;     // 1 byte
        int size = 0;       // 4 bytes
    }

    private int addImageDataItem(ImageDataItem item) {
        return 0;
    }

    private byte[] toByteArray(int value) {
        return ByteBuffer.allocate(4).putInt(value).array();
    }

    private int fromByteArray(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getInt();
    }
}
