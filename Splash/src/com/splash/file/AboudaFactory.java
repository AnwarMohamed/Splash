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

import com.splash.gui.Canvas;
import com.splash.gui.elements.Layer;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.DataFormatException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class AboudaFactory {

    public static byte[] compressBuffer(byte[] data) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (GZIPOutputStream zos = new GZIPOutputStream(baos)) {
            zos.write(data);
        }
        return baos.toByteArray();
    }

    public static byte[] decompressBuffer(byte[] data) throws IOException,
            DataFormatException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ByteArrayInputStream bais = new ByteArrayInputStream(data);

        try (GZIPInputStream zis = new GZIPInputStream(bais)) {
            byte[] tmpBuffer = new byte[256];
            int n;
            while ((n = zis.read(tmpBuffer)) >= 0) {
                baos.write(tmpBuffer, 0, n);
            }
        }
        return baos.toByteArray();
    }

    public static void generateOutputFile(String filename, Canvas canvas) {

        try {
            OutputStream outputStream;
            outputStream = new FileOutputStream(
                    filename.endsWith(".abouda")
                            ? filename : filename.concat(".abouda"));

            AboudaFileFormat outputStruct = new AboudaFileFormat();

            if (canvas != null && canvas.getLayers() != null) {

                outputStruct.setImageDimentions(
                        canvas.getImageWidth(),
                        canvas.getImageHeight());

                for (Layer layer : canvas.getLayers()) {
                    outputStruct.addLayer(layer);
                }
            }

            outputStream.write(outputStruct.generateFile());
            outputStream.close();
        } catch (IOException ex) {
        }
    }

    public static void parseInputFile(String filename, Canvas canvas) {
        AboudaFile inputFile = new AboudaFile(filename, canvas);
    }
}
