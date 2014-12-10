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

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AboudaFile {

    public AboudaFile(byte[] buffer) {
        ByteArrayInputStream is = new ByteArrayInputStream(buffer);
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(is));
        parseFile(bufferedReader);
    }

    public AboudaFile(String filename) {
        try {
            BufferedReader bufferedReader
                    = new BufferedReader(new FileReader(filename));
            parseFile(bufferedReader);
        } catch (IOException e) {
        }
    }

    public AboudaFile(BufferedReader buffer) {
        parseFile(buffer);
    }

    public final static String signature = "ABOUDA";

    private void parseFile(BufferedReader buffer) {

    }

    private boolean isReady = false;

    public boolean isIsReady() {
        return isReady;
    }
}
