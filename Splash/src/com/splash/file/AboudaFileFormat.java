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
import com.splash.gui.elements.ImagedTool;
import com.splash.gui.elements.Layer;
import com.splash.gui.elements.PixeledTool;
import com.splash.gui.elements.Tool;
import com.splash.gui.tools.Circle;
import com.splash.gui.tools.Ellipse;
import com.splash.gui.tools.Eraser;
import com.splash.gui.tools.FreeHand;
import com.splash.gui.tools.IsocelesTriangle;
import com.splash.gui.elements.LinedTool;
import com.splash.gui.tools.Fill;
import com.splash.gui.tools.Rectangle;
import com.splash.gui.tools.RightTriangle;
import com.splash.gui.tools.RoundedRect;
import com.splash.gui.tools.Square;
import java.awt.Color;
import java.awt.Point;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class AboudaFileFormat {

    public final static byte OBJTYPE_CIRCLE = 1;
    public final static byte OBJTYPE_ELLIPSE = 2;
    public final static byte OBJTYPE_ROUNDED = 3;
    public final static byte OBJTYPE_ERASER = 4;
    public final static byte OBJTYPE_FILL = 5;
    public final static byte OBJTYPE_FREEHAND = 6;
    public final static byte OBJTYPE_ISOTRIANGLE = 7;
    public final static byte OBJTYPE_LINE = 8;
    public final static byte OBJTYPE_RECTANGLE = 9;
    public final static byte OBJTYPE_RIGHTTRIANGLE = 10;
    public final static byte OBJTYPE_SQUARE = 11;
    public final static byte OBJTYPE_TEXT = 12;
    public final static byte OBJTYPE_IMAGE = 13;

    public FileHeader fileHeader = new FileHeader();
    public ColorsData colorsData = new ColorsData();
    public LayersData layersData = new LayersData();
    public ImageData imageData = new ImageData();

    public AboudaFileFormat() {
    }

    public byte[] generateFile() throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        prepareColorsData();
        prepareLayersData();
        prepareImageData();

        buffer.write(fileHeader.signature);
        buffer.write(intToByteArray(fileHeader.fileSize));
        buffer.write(intToByteArray(fileHeader.colorsDataOffset));
        buffer.write(intToByteArray(fileHeader.colorsDataSize));
        buffer.write(intToByteArray(fileHeader.layersDataOffset));
        buffer.write(intToByteArray(fileHeader.layersDataSize));
        buffer.write(intToByteArray(fileHeader.imageDataOffset));
        buffer.write(intToByteArray(fileHeader.imageDataSize));

        if (fileHeader.colorsDataOffset > 0) {
            buffer.write(intToByteArray(colorsData.header.size));
            for (Integer item : colorsData.header.items) {
                buffer.write(intToByteArray(item));
            }
        }

        if (fileHeader.layersDataOffset > 0) {
            buffer.write(shortToByteArray(layersData.header.size));
            buffer.write(shortToByteArray(layersData.header.width));
            buffer.write(shortToByteArray(layersData.header.height));
            for (LayersDataItem layer : layersData.header.items) {
                buffer.write(shortToByteArray(layer.objectsSize));
                for (ObjectItem object : layer.objects) {
                    buffer.write(intToByteArray(object.offset));
                }
            }
        }

        if (fileHeader.imageDataOffset > 0) {
            for (ImageDataItem imageItem : imageData.header.items) {
                buffer.write(imageItem.type);
                buffer.write(shortToByteArray(imageItem.x));
                buffer.write(shortToByteArray(imageItem.y));
                buffer.write(intToByteArray(imageItem.color));
                buffer.write(imageItem.border);
                buffer.write(intToByteArray(imageItem.size));

                byte[] compressed;
                switch (imageItem.type) {
                    case OBJTYPE_CIRCLE:
                    case OBJTYPE_ELLIPSE:
                    case OBJTYPE_ROUNDED:
                    case OBJTYPE_ISOTRIANGLE:
                    case OBJTYPE_RECTANGLE:
                    case OBJTYPE_SQUARE:
                    case OBJTYPE_RIGHTTRIANGLE:
                        buffer.write(shortToByteArray(imageItem.width));
                        buffer.write(shortToByteArray(imageItem.height));
                        break;
                    case OBJTYPE_LINE:
                        buffer.write(shortToByteArray(imageItem.endX));
                        buffer.write(shortToByteArray(imageItem.endY));
                        break;
                    case OBJTYPE_ERASER:
                    case OBJTYPE_FREEHAND:
                        compressed = AboudaFactory.compressBuffer(
                                imageItem.pixels.toByteArray());

                        buffer.write(intToByteArray(compressed.length));
                        buffer.write(compressed);
                        break;
                    case OBJTYPE_FILL:
                    case OBJTYPE_IMAGE:
                        compressed = AboudaFactory.compressBuffer(
                                imageItem.pixels.toByteArray());

                        buffer.write(intToByteArray(compressed.length));
                        buffer.write(compressed);
                }
            }
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
        if (fileHeader.colorsDataSize > 0) {
            fileHeader.layersDataOffset
                    = fileHeader.colorsDataOffset + fileHeader.colorsDataSize;
        } else {
            fileHeader.layersDataOffset = 34;
        }

        fileHeader.layersDataSize = 6 + layersData.header.size * 2;
        for (LayersDataItem layer : layersData.header.items) {
            fileHeader.layersDataSize += layer.objectsSize * 4;
        }
    }

    private void prepareImageData() {
        if (fileHeader.layersDataSize > 0) {
            fileHeader.imageDataOffset
                    = fileHeader.layersDataOffset + fileHeader.layersDataSize;

            fileHeader.fileSize
                    = fileHeader.imageDataOffset + fileHeader.imageDataSize;
        } else {
            fileHeader.layersDataOffset = 0;
            fileHeader.layersDataSize = 0;
        }
    }

    private int addColor(Color color) {
        if (!colorsData.header.items.contains(color.getRGB())) {
            colorsData.header.items.add(color.getRGB());
            return colorsData.header.size++;
        } else {
            return colorsData.header.items.indexOf(color.getRGB());
        }
    }

    public void setImageDimentions(int width, int height) {
        layersData.header.width = (short) width;
        layersData.header.height = (short) height;
    }

    public void addLayer(Layer layer) throws IOException {
        LayersDataItem layerItem = new LayersDataItem();

        for (Tool tool : layer.getTools()) {
            ImageDataItem imageDataItem = new ImageDataItem();
            imageDataItem.border = (byte) tool.getBorderSize();
            imageDataItem.x = (short) tool.getX();
            imageDataItem.y = (short) tool.getY();
            imageDataItem.color = addColor(tool.getColor());

            if (tool instanceof DimensionedTool) {
                imageDataItem.height = (short) ((DimensionedTool) tool).getHeight();
                imageDataItem.width = (short) ((DimensionedTool) tool).getWidth();

                if (tool instanceof Circle) {
                    imageDataItem.type = OBJTYPE_CIRCLE;
                } else if (tool instanceof Ellipse) {
                    imageDataItem.type = OBJTYPE_ELLIPSE;
                } else if (tool instanceof RoundedRect) {
                    imageDataItem.type = OBJTYPE_ROUNDED;
                } else if (tool instanceof IsocelesTriangle) {
                    imageDataItem.type = OBJTYPE_ISOTRIANGLE;
                } else if (tool instanceof Rectangle) {
                    imageDataItem.type = OBJTYPE_RECTANGLE;
                } else if (tool instanceof Square) {
                    imageDataItem.type = OBJTYPE_SQUARE;
                } else if (tool instanceof RightTriangle) {
                    imageDataItem.type = OBJTYPE_RIGHTTRIANGLE;
                } else if (tool instanceof Fill) {
                    imageDataItem.type = OBJTYPE_FILL;
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(((Fill) tool).getMergedImage(), "png", baos);
                    imageDataItem.pixels.write(baos.toByteArray());
                } else if (tool instanceof ImagedTool) {
                    imageDataItem.type = OBJTYPE_IMAGE;
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(((ImagedTool) tool).getImage(), "png", baos);
                    imageDataItem.pixels.write(baos.toByteArray());                    
                }
            } else if (tool instanceof PixeledTool) {
                for (Point point : ((PixeledTool) tool).getPixels()) {
                    imageDataItem.pixels.write(
                            shortToByteArray((short) point.getX()));
                    imageDataItem.pixels.write(
                            shortToByteArray((short) point.getY()));
                }

                if (tool instanceof Eraser) {
                    imageDataItem.type = OBJTYPE_ERASER;
                } else if (tool instanceof FreeHand) {
                    imageDataItem.type = OBJTYPE_FREEHAND;
                }
            } else if (tool instanceof LinedTool) {
                imageDataItem.type = OBJTYPE_LINE;

                imageDataItem.endX = (short) ((LinedTool) tool).getEndX();
                imageDataItem.endY = (short) ((LinedTool) tool).getEndY();
            }

            ObjectItem objectItem = new ObjectItem();
            objectItem.offset = addImageDataItem(imageDataItem);

            layerItem.objects.add(objectItem);
            layerItem.objectsSize++;
        }

        layersData.header.items.add(layerItem);
        layersData.header.size++;

    }

    private int addImageDataItem(ImageDataItem item) throws IOException {
        switch (item.type) {
            case OBJTYPE_CIRCLE:
            case OBJTYPE_ELLIPSE:
            case OBJTYPE_ROUNDED:
            case OBJTYPE_ISOTRIANGLE:
            case OBJTYPE_RECTANGLE:
            case OBJTYPE_SQUARE:
            case OBJTYPE_RIGHTTRIANGLE:
            case OBJTYPE_LINE:
                item.size = 18;
                break;
            case OBJTYPE_FREEHAND:
            case OBJTYPE_ERASER:
            case OBJTYPE_FILL:
            case OBJTYPE_IMAGE:
                item.size = 18 + AboudaFactory.compressBuffer(
                        item.pixels.toByteArray()).length;
                break;
            default:
                item.size = 14;
        }

        imageData.header.items.add(item);

        fileHeader.imageDataSize += item.size;
        return fileHeader.imageDataSize - item.size;
    }

    static class FileHeader {              // 34 bytes

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
        ArrayList<Integer> items = new ArrayList<>();
    }

    static class LayersData {

        LayersDataHeader header = new LayersDataHeader();
    }

    static class LayersDataHeader {

        short size = 0;
        short width = 0;
        short height = 0;
        ArrayList<LayersDataItem> items = new ArrayList<>();
    }

    static class LayersDataItem {

        short objectsSize = 0;
        ArrayList<ObjectItem> objects = new ArrayList<>();
    }

    static class ObjectItem {

        int offset = 0;
    }

    static class ImageData {

        ImageDataHeader header = new ImageDataHeader();
    }

    static class ImageDataHeader {

        int size = 0;
        ArrayList<ImageDataItem> items = new ArrayList<>();
    }

    static class ImageDataItem {

        byte type = 0;
        byte border = 0;
        short x = 0;
        short y = 0;
        int color = 0;
        int size = 0;

        short width = 0;
        short height = 0;

        short endX = 0;
        short endY = 0;

        ByteArrayOutputStream pixels
                = new ByteArrayOutputStream();
    }

    private byte[] shortToByteArray(short value) {
        return ByteBuffer.allocate(2).putShort(value).array();
    }

    private byte[] intToByteArray(int value) {
        return ByteBuffer.allocate(4).putInt(value).array();
    }
}
