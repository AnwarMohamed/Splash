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

import java.util.ArrayList;

public class AboudaFileStructure {

    public FileHeader fileHeader;
    public ColorsData colorsData;
    public LayersData layersData;
    public ImageData imageData;

    class FileHeader {

        char[] signature;
        int fileSize;
        int colorsDataOffset;
        int colorsDataSize;
        int layersDataOffset;
        int layersDataSize;
        int imageDataOffset;
        int imageDataSize;
    }

    class ColorsData {

        ColorsDataHeader header;
    }

    class ColorsDataHeader {

        int size;
        ArrayList<ColorDataItem> items;
    }

    class ColorDataItem {

        char red;
        char green;
        char blue;
        char alpha;
    }

    class LayersData {

        LayersDataHeader header;
    }

    class LayersDataHeader {

        int size;
        int width;
        int height;
        ArrayList<LayersDataItem> items;
    }

    class LayersDataItem {
        int objectsSize;
        ArrayList<ObjectItem> objects;
    }

    class ObjectItem {
        int offset;
    }
    
    class ImageData {

        ImageDataHeader header;
    }

    class ImageDataHeader {

        int size;
        ArrayList<ImageDataItem> items;
    }

    class ImageDataItem {
        char type;
        char flags;
    }
}
