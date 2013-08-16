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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.Before;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import com.swabunga.spell.engine.SpellDictionaryHashMap;
import com.swabunga.spell.event.FileWordTokenizer;
import com.swabunga.spell.event.SpellCheckEvent;
import com.swabunga.spell.event.SpellCheckListener;
import com.swabunga.spell.event.SpellChecker;
import com.swabunga.spell.event.TeXWordFinder;

public class SpellCheckerTest extends TestCase implements SpellCheckListener {

    SpellChecker checker;
    List<String> misspelled;

    public SpellCheckerTest(String name) {
        super(name);
    }

    @Before
    public void setUp() {
        File dict = new File(SpellCheckerTest.class.getResource(
                "/dict/en_US/english.0").getFile());
        try {
            checker = new SpellChecker(new SpellDictionaryHashMap(dict));
        } catch (FileNotFoundException e) {
            System.err.println("Dictionary File " + dict + " not found! " + e);
        } catch (IOException ex) {
            System.err.println("IO problem: " + ex);
        }
        FileWordTokenizer texTok = new FileWordTokenizer(new File(
                SpellCheckerTest.class.getResource(
                        "/com/swabunga/test/spell/event/test.tex").getFile()),
                new TeXWordFinder());
        misspelled = new ArrayList<String>();

        checker.addSpellCheckListener(this);
        checker.checkSpelling(texTok);
    }

    @After
    protected void tearDown() {
        checker = null;
    }

    public void testTeXNext() {
        Iterator<String> it = misspelled.iterator();
        assertEquals("Anthony", it.next());
        assertEquals("Stell", it.next());
        assertEquals("Leeds", it.next());
        assertEquals("heere", it.next());
        assertEquals("Coinvexity", it.next());
        assertEquals("serction", it.next());
        assertEquals("conccepts", it.next());
    }

    public void spellingError(SpellCheckEvent event) {
        event.ignoreWord(true);
        misspelled.add(event.getInvalidWord());
    }

    public static void main(String[] args) {
        // System.out.println("No tests currently written for FileWordTokenizerTester.");
        TestRunner.run(new TestSuite(SpellCheckerTest.class));
    }

}
