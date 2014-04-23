/*
 * Copyright (C) 2013-2014 Byron 3D Games Studio (www.b3dgs.com) Pierre-Alexandre (contact@b3dgs.com)
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package com.b3dgs.lionengine;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.b3dgs.lionengine.core.EngineCore;
import com.b3dgs.lionengine.core.FactoryGraphicMock;
import com.b3dgs.lionengine.core.FactoryMediaMock;
import com.b3dgs.lionengine.core.ImageBuffer;
import com.b3dgs.lionengine.core.UtilityImage;
import com.b3dgs.lionengine.core.Verbose;

/**
 * Test the text class.
 * 
 * @author Pierre-Alexandre (contact@b3dgs.com)
 */
public class TextTest
{
    /** Text value. */
    private static final String VALUE = "test";
    /** Graphic. */
    private static Graphic g;

    /**
     * Setup test.
     */
    @BeforeClass
    public static void setUp()
    {
        EngineCore.start("TextTest", Version.create(1, 0, 0), Verbose.NONE, new FactoryGraphicMock(),
                new FactoryMediaMock());
        final ImageBuffer buffer = UtilityImage.createImageBuffer(320, 240, Transparency.OPAQUE);
        TextTest.g = buffer.createGraphic();
    }

    /**
     * Clean up test.
     */
    @AfterClass
    public static void cleanUp()
    {
        TextTest.g.dispose();
        EngineCore.terminate();
    }

    /**
     * Test the text normal.
     */
    @Test
    public void testTextNormal()
    {
        final Text text1 = UtilityImage.createText(Text.DIALOG, 12, TextStyle.NORMAL);
        text1.draw(TextTest.g, 0, 0, TextTest.VALUE);
        text1.draw(TextTest.g, 0, 0, Align.CENTER, TextTest.VALUE);
        text1.draw(TextTest.g, 0, 0, Align.LEFT, TextTest.VALUE);
        text1.draw(TextTest.g, 0, 0, Align.RIGHT, TextTest.VALUE);
        text1.setAlign(Align.CENTER);
        text1.setColor(ColorRgba.BLACK);
        text1.setLocation(1, 5);
        text1.setText(TextTest.VALUE);
        Assert.assertEquals(12, text1.getSize());
        Assert.assertEquals(1, text1.getLocationX());
        Assert.assertEquals(5, text1.getLocationY());
        Assert.assertTrue(text1.getWidth() == 0);
        Assert.assertTrue(text1.getHeight() == 0);
        text1.render(TextTest.g);
        text1.render(TextTest.g);
        Assert.assertTrue(text1.getWidth() > 0);
        Assert.assertTrue(text1.getHeight() > 0);
    }

    /**
     * Test the text bold.
     */
    @Test
    public void testTextBold()
    {
        final Text text = UtilityImage.createText(Text.DIALOG, 12, TextStyle.BOLD);
        text.draw(TextTest.g, 0, 0, TextTest.VALUE);
    }

    /**
     * Test the text italic.
     */
    @Test
    public void testTextItalic()
    {
        final Text text = UtilityImage.createText(Text.DIALOG, 12, TextStyle.ITALIC);
        text.draw(TextTest.g, 0, 0, TextTest.VALUE);
    }
}
