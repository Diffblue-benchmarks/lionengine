/*
 * Copyright (C) 2013-2016 Byron 3D Games Studio (www.b3dgs.com) Pierre-Alexandre (contact@b3dgs.com)
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
package com.b3dgs.lionengine.game.map.feature.viewer;

import com.b3dgs.lionengine.game.feature.displayable.Displayable;
import com.b3dgs.lionengine.game.map.feature.renderer.MapTileRenderer;

/**
 * Describe how the map viewing is performed. This will allow to customize map rendering.
 */
public interface MapTileViewer extends Displayable
{
    /**
     * Add a map tile renderer.
     * 
     * @param renderer The map tile renderer reference.
     */
    void addRenderer(MapTileRenderer renderer);

    /**
     * Remove a map tile renderer.
     * 
     * @param renderer The map tile renderer reference.
     */
    void removeRenderer(MapTileRenderer renderer);

    /**
     * Remove all renderers.
     */
    void clear();
}
