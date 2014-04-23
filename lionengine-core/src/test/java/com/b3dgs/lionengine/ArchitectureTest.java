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

import org.junit.Assert;
import org.junit.Test;

/**
 * Test architecture class.
 * 
 * @author Pierre-Alexandre (contact@b3dgs.com)
 */
public class ArchitectureTest
{
    /**
     * Test the architecture enum.
     */
    @Test
    public void testArchitectureEnum()
    {
        Assert.assertNotNull(Architecture.X64);
        Assert.assertNotNull(Architecture.X86);
        Assert.assertNotNull(Architecture.UNKNOWN);

        Assert.assertNotNull(Architecture.values());
        Assert.assertEquals(Architecture.X64, Architecture.valueOf(Architecture.X64.name()));
    }

    /**
     * Test the architecture enum switch.
     */
    @Test
    public void testArchitectureEnumSwitch()
    {
        for (final Architecture architecture : Architecture.values())
        {
            switch (architecture)
            {
                case X64:
                    // Success
                    break;
                case X86:
                    // Success
                    break;
                case UNKNOWN:
                    // Success
                    break;
                default:
                    Assert.fail();
            }
        }
    }
}
