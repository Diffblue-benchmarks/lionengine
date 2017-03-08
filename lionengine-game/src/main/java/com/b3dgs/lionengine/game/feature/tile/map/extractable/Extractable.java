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
package com.b3dgs.lionengine.game.feature.tile.map.extractable;

import com.b3dgs.lionengine.game.Feature;
import com.b3dgs.lionengine.game.Tiled;

/**
 * Represents an entity that can be extractible, such as a Gold Mine.
 */
public interface Extractable extends Feature, Tiled
{
    /**
     * Extract the specified quantity if possible.
     * 
     * @param quantity The quantity to extract.
     * @return The extracted quantity.
     */
    int extractResource(int quantity);

    /**
     * Set the resources quantity.
     * 
     * @param quantity The resources quantity.
     */
    void setResourcesQuantity(int quantity);

    /**
     * Set the resources type.
     * 
     * @param type The resources type.
     */
    void setResourcesType(Enum<?> type);

    /**
     * Get the current resource quantity.
     * 
     * @return The current resource quantity.
     */
    int getResourceQuantity();

    /**
     * Get the resource type that can be extracted.
     * 
     * @return The resource type.
     */
    Enum<?> getResourceType();
}
