/*
Jazzy - a Java library for Spell Checking
Copyright (C) 2001 Mindaugas Idzelis
Full text of license can be found in LICENSE.txt

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package com.swabunga.test.spell.event;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import com.swabunga.spell.TeXWordFinder;
import com.swabunga.spell.tokenizer.StringWordTokenizer;

public class StringWordTokenizerTest extends TestCase {

    StringWordTokenizer texTok;

    public StringWordTokenizerTest(String name) {
        super(name);
    }

    protected void setUp() {
        texTok = new StringWordTokenizer(
                stringValue(new File(StringWordTokenizerTest.class.getResource(
                        "/com/swabunga/test/spell/event/test.tex").getFile())),
                new TeXWordFinder());
    }

    protected void tearDown() {
        texTok = null;
    }

    public void testRead() {
        assertTrue(!texTok.getContext().equals(""));
    }

    public void testWordA() {
        assertEquals("width", texTok.nextWord());
        assertEquals("1", texTok.nextWord());
        assertEquals("1", texTok.nextWord());
        assertEquals("1cm", texTok.nextWord());
        assertEquals("1", texTok.nextWord());
        assertEquals("Key", texTok.nextWord());
        assertEquals("Words", texTok.nextWord());
    }

    public static void main(String[] args) {
        // System.out.println("No tests currently written for FileWordTokenizerTester.");
        TestRunner.run(new TestSuite(StringWordTokenizerTest.class));
    }

    private static String stringValue(File inFile) {
        StringBuilder out = new StringBuilder("");

        try {
            BufferedReader in = new BufferedReader(new FileReader(inFile));
            char[] c = new char[100];
            int count;
            while ((count = in.read(c, 0, c.length)) != -1) {
                out.append(c, 0, count);
            }
            in.close();
        } catch (IOException e) {
            System.err.println("File input error trying to open "
                    + inFile.toString() + " : " + e);
        }
        return out.toString();
    }

}
