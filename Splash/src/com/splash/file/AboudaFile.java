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

import com.alee.utils.FileUtils;
import com.splash.file.AboudaFileFormat.FileHeader;
import com.splash.gui.Canvas;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class AboudaFile {

    public AboudaFile(byte[] buffer) {
        try {
            if (buffer.length >= 34) {
                InputStream inputStream = new ByteArrayInputStream(buffer);
                parseFile(inputStream);
            }
        } catch (IOException ex) {
        }
    }

    public AboudaFile(String filename) {
        try {
            InputStream inputStream
                    = new BufferedInputStream(new FileInputStream(filename));
            parseFile(inputStream);
        } catch (IOException e) {
        }
    }

    public final static String signature = "ABOUDA";

    private void parseFile(InputStream buffer) throws IOException {

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

        if (fileHeader.colorsDataSize > 0) {
            int colorsSize = intFromByteArray(buffer);
            ArrayList<Integer> colors = new ArrayList<>();

            for (int i = 0; i < colorsSize; i++) {
                colors.add(intFromByteArray(buffer));
            }
        }

        isReady = true;
    }

    private boolean isReady = false;

    public boolean isReady() {
        return isReady;
    }

    private short shortFromByteArray(InputStream is)
            throws IOException {
        byte[] tempShort = null;
        is.read(tempShort, 0, 2);
        return ByteBuffer.wrap(tempShort).getShort();
    }

    private int intFromByteArray(InputStream is)
            throws IOException {
        byte[] tempInt = null;
        is.read(tempInt, 0, 4);
        return ByteBuffer.wrap(tempInt).getInt();
    }

    public void reloadCanvas(Canvas canvas) {
        if (isReady()) {

        }
    }
}
