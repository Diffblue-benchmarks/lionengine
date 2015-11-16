/*
 * Copyright (C) 2013-2015 Byron 3D Games Studio (www.b3dgs.com) Pierre-Alexandre (contact@b3dgs.com)
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
package com.b3dgs.lionengine.game.object;

import com.b3dgs.lionengine.LionEngineException;

/**
 * Trait model base implementation.
 */
public abstract class TraitModel implements Trait
{
    /** Cast error. */
    private static final String ERROR_CAST = "Unable to cast: ";

    /** The owner reference. */
    private ObjectGame owner;

    /**
     * Create a trait model.
     */
    public TraitModel()
    {
        // Nothing to do
    }

    /*
     * Trait
     */

    @Override
    public void prepare(ObjectGame owner, Services services)
    {
        this.owner = owner;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <O extends ObjectGame> O getOwner()
    {
        try
        {
            return (O) owner;
        }
        catch (final ClassCastException exception)
        {
            throw new LionEngineException(exception, ERROR_CAST, owner.getClass().getName());
        }
    }
}