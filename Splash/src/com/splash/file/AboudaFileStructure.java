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

import com.splash.gui.elements.Layer;
import com.splash.gui.elements.Tool;
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
        }

        return -1;
    }

    public void setImageDimentions(int width, int height) {
        layersData.header.width = width;
        layersData.header.height = height;
    }

    public void addLayer(Layer layer) {
        LayersDataItem layerItem = new LayersDataItem();

        for (Tool tool : layer.getTools()) {
            ObjectItem objectItem = new ObjectItem();
            ImageDataItem imageDataItem = new ImageDataItem();

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
