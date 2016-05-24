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
package com.b3dgs.lionengine.game.handler;

import com.b3dgs.lionengine.Updatable;
import com.b3dgs.lionengine.game.feature.FeaturableModel;
import com.b3dgs.lionengine.game.feature.identifiable.IdentifiableModel;

/**
 * Updatable object mock.
 */
class Updater extends FeaturableModel implements Updatable
{
    /** Updated flag. */
    private boolean updated;

    /**
     * Constructor.
     */
    public Updater()
    {
        super();
        addFeature(new IdentifiableModel());
    }

    /**
     * Check if has been updated.
     * 
     * @return <code>true</code> if updated, <code>false</code> else.
     */
    public boolean isUpdated()
    {
        return updated;
    }

    @Override
    public void update(double extrp)
    {
        updated = true;
    }
}
