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
package com.b3dgs.lionengine.game.object.trait.extractable;

import com.b3dgs.lionengine.game.Alterable;
import com.b3dgs.lionengine.game.map.MapTile;
import com.b3dgs.lionengine.game.object.ObjectGame;
import com.b3dgs.lionengine.game.object.Services;
import com.b3dgs.lionengine.game.object.TraitModel;
import com.b3dgs.lionengine.game.object.trait.transformable.Transformable;

/**
 * Extractable model implementation.
 * <p>
 * The {@link ObjectGame} owner must have the following {@link com.b3dgs.lionengine.game.object.Trait}:
 * </p>
 * <ul>
 * <li>{@link Transformable}</li>
 * </ul>
 * <p>
 * The {@link Services} must provide the following services:
 * </p>
 * <ul>
 * <li>{@link MapTile}</li>
 * </ul>
 */
public class ExtractableModel extends TraitModel implements Extractable
{
    /** Resources count. */
    private final Alterable resources = new Alterable(Integer.MAX_VALUE);
    /** Map reference. */
    private MapTile map;
    /** Transformable model. */
    private Transformable transformable;
    /** Resource type. */
    private Enum<?> type;

    /**
     * Create an extractable model.
     */
    public ExtractableModel()
    {
        super();
    }

    /*
     * Extractable
     */

    @Override
    public void prepare(ObjectGame owner, Services services)
    {
        super.prepare(owner, services);

        transformable = owner.getTrait(Transformable.class);
        map = services.get(MapTile.class);
    }

    @Override
    public int extractResource(int quantity)
    {
        return resources.decrease(quantity);
    }

    @Override
    public void setResourcesQuantity(int quantity)
    {
        resources.set(quantity);
    }

    @Override
    public void setResourcesType(Enum<?> type)
    {
        this.type = type;
    }

    @Override
    public int getResourceQuantity()
    {
        return resources.getCurrent();
    }

    @Override
    public Enum<?> getResourceType()
    {
        return type;
    }

    @Override
    public int getInTileX()
    {
        return map.getInTileX(transformable);
    }

    @Override
    public int getInTileY()
    {
        return map.getInTileY(transformable);
    }

    @Override
    public int getInTileWidth()
    {
        return (int) Math.floor(transformable.getWidth() / (double) map.getTileWidth());
    }

    @Override
    public int getInTileHeight()
    {
        return (int) Math.floor(transformable.getHeight() / (double) map.getInTileHeight());
    }
}
