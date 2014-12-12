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
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class AboudaFactory {

    public static byte[] compressBuffer(byte[] data) throws IOException {
        Deflater deflater = new Deflater();
        deflater.setInput(data);

        ByteArrayOutputStream outputStream
                = new ByteArrayOutputStream(data.length);

        deflater.finish();
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        outputStream.close();
        return outputStream.toByteArray();
    }

    public static byte[] decompressBuffer(byte[] data) throws IOException,
            DataFormatException {
        Inflater inflater = new Inflater();
        inflater.setInput(data);

        ByteArrayOutputStream outputStream
                = new ByteArrayOutputStream(data.length);

        byte[] buffer = new byte[1024];
        while (!inflater.finished()) {
            int count = inflater.inflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        outputStream.close();
        return outputStream.toByteArray();
    }

    public static void generateOutputFile(String filename, Canvas canvas) {

        try {
            OutputStream outputStream;
            outputStream = new FileOutputStream(
                    filename.endsWith(".abouda")
                            ? filename : filename.concat(".abouda"));

            AboudaFileStructure outputStruct = new AboudaFileStructure();

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

    }
}
