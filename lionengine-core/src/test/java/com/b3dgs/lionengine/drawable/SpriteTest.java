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
package com.b3dgs.lionengine.drawable;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.b3dgs.lionengine.Graphic;
import com.b3dgs.lionengine.LionEngineException;
import com.b3dgs.lionengine.Transparency;
import com.b3dgs.lionengine.core.FactoryGraphicMock;
import com.b3dgs.lionengine.core.FactoryMediaMock;
import com.b3dgs.lionengine.core.ImageBuffer;
import com.b3dgs.lionengine.core.Media;
import com.b3dgs.lionengine.core.UtilityImage;

/**
 * Test the sprite class.
 * 
 * @author Pierre-Alexandre (contact@b3dgs.com)
 */
public class SpriteTest
{
    /** Image media. */
    private static Media media;
    /** Graphic test output. */
    private static Graphic g;

    /**
     * Prepare test.
     */
    @BeforeClass
    public static void setUp()
    {
        UtilityImage.setGraphicFactory(new FactoryGraphicMock());
        Media.setMediaFactory(new FactoryMediaMock());
        SpriteTest.media = Media.create(Media.getPath("src", "test", "resources", "drawable", "image.png"));
        SpriteTest.g = UtilityImage.createImageBuffer(100, 100, Transparency.OPAQUE).createGraphic();
    }

    /**
     * Clean up test.
     */
    @AfterClass
    public static void cleanUp()
    {
        UtilityImage.setGraphicFactory(null);
        Media.setMediaFactory(null);
    }

    /**
     * Test function around the sprite.
     */
    @Test
    public void testSprite()
    {
        // Sprite with existing surface
        final ImageBuffer surface = UtilityImage.createImageBuffer(16, 16, Transparency.OPAQUE);
        final Sprite spriteA = Drawable.loadSprite(surface);

        Assert.assertNotNull(spriteA.getSurface());
        Assert.assertEquals(surface, spriteA.getSurface());

        DrawableTestTool.testSpriteModification(2, spriteA);
        // Sprite clone (share surface only)
        Assert.assertEquals(spriteA, Drawable.loadSprite(spriteA.getSurface()));

        // Load from file
        final Sprite spriteB = Drawable.loadSprite(SpriteTest.media);
        DrawableTestTool.assertImageInfoCorrect(SpriteTest.media, spriteB);

        DrawableTestTool.testSpriteLoading(spriteB);
        DrawableTestTool.testImageRender(SpriteTest.g, spriteB);
        Assert.assertFalse(spriteB.equals(Drawable.loadSprite(SpriteTest.media)));

        // Hash code
        Assert.assertTrue(spriteA.hashCode() != spriteB.hashCode());
    }

    /**
     * Test function around the sprite.
     */
    @Test
    public void testSpriteFailure()
    {
        try
        {
            Drawable.loadSprite(Media.create("void"));
            Assert.fail();
        }
        catch (final LionEngineException exception)
        {
            // Success
        }
    }
}
