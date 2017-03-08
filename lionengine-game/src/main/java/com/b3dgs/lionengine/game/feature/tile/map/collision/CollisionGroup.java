/*
 * Copyright (C) 2013-2017 Byron 3D Games Studio (www.b3dgs.com) Pierre-Alexandre (contact@b3dgs.com)
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
package com.b3dgs.lionengine.game.feature.tile.map.collision;

import java.util.Collection;

import com.b3dgs.lionengine.Constant;
import com.b3dgs.lionengine.Nameable;

/**
 * Represents the collision group, which can be applied to a {@link com.b3dgs.lionengine.game.feature.tile.Tile}.
 * It allows to reference easily a set of {@link CollisionFormula} previously defined on the
 * {@link com.b3dgs.lionengine.game.feature.tile.map.MapTile}. Here a definition example:
 * 
 * <pre>
 * &lt;lionengine:groups xmlns:lionengine="http://lionengine.b3dgs.com"&gt;
 *    &lt;lionengine:group name="block"&gt;
 *       &lt;lionengine:formula&gt;top&lt;/lionengine:formula&gt;
 *       &lt;lionengine:formula&gt;bottom&lt;/lionengine:formula&gt;
 *       &lt;lionengine:formula&gt;left&lt;/lionengine:formula&gt;
 *       &lt;lionengine:formula&gt;right&lt;/lionengine:formula&gt;
 *    &lt;/lionengine:group&gt;
 * &lt;/lionengine:groups&gt;
 * </pre>
 * 
 * @see CollisionGroupConfig
 * @see CollisionFormula
 */
public class CollisionGroup implements Nameable
{
    /** Minimum to string characters. */
    private static final int MINIMUM_LENGTH = 32;

    /**
     * Check if tiles groups are same.
     * 
     * @param groupA The first group.
     * @param groupB The second group.
     * @return <code>true</code> if groups are same (<code>null</code> included).
     */
    public static boolean same(String groupA, String groupB)
    {
        final boolean result;
        if (groupA != null && groupB != null)
        {
            result = groupA.equals(groupB);
        }
        else if (groupA == null && groupB == null)
        {
            result = true;
        }
        else
        {
            result = false;
        }
        return result;
    }

    /** The group name. */
    private final String name;
    /** The collision formulas used. */
    private final Collection<CollisionFormula> formulas;

    /**
     * Create a collision group.
     * 
     * @param name The group name.
     * @param formulas The collision formulas reference.
     */
    public CollisionGroup(String name, Collection<CollisionFormula> formulas)
    {
        this.name = name;
        this.formulas = formulas;
    }

    /**
     * Get collision formulas reference.
     * 
     * @return The collision formulas reference.
     */
    public Collection<CollisionFormula> getFormulas()
    {
        return formulas;
    }

    /*
     * Nameable
     */

    @Override
    public String getName()
    {
        return name;
    }

    /*
     * Object
     */

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + name.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (object == null || object.getClass() != getClass())
        {
            return false;
        }
        final CollisionGroup other = (CollisionGroup) object;
        return getName().equals(other.getName());
    }

    @Override
    public String toString()
    {
        return new StringBuilder(MINIMUM_LENGTH).append(getClass().getSimpleName())
                                                .append(" (name=")
                                                .append(name)
                                                .append(")")
                                                .append(Constant.NEW_LINE)
                                                .append(Constant.TAB)
                                                .append(formulas)
                                                .toString();
    }
}
