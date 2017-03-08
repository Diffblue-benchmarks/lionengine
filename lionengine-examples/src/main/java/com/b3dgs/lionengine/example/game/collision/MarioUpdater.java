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
package com.b3dgs.lionengine.example.game.collision;

import com.b3dgs.lionengine.Context;
import com.b3dgs.lionengine.Origin;
import com.b3dgs.lionengine.game.FeatureProvider;
import com.b3dgs.lionengine.game.Force;
import com.b3dgs.lionengine.game.Service;
import com.b3dgs.lionengine.game.Services;
import com.b3dgs.lionengine.game.feature.FeatureModel;
import com.b3dgs.lionengine.game.feature.Refreshable;
import com.b3dgs.lionengine.game.feature.Transformable;
import com.b3dgs.lionengine.game.feature.body.Body;
import com.b3dgs.lionengine.game.feature.collidable.Collidable;
import com.b3dgs.lionengine.game.feature.tile.Tile;
import com.b3dgs.lionengine.game.feature.tile.map.collision.Axis;
import com.b3dgs.lionengine.game.feature.tile.map.collision.TileCollidable;
import com.b3dgs.lionengine.game.feature.tile.map.collision.TileCollidableListener;
import com.b3dgs.lionengine.io.awt.Keyboard;

/**
 * Mario updating implementation.
 */
class MarioUpdater extends FeatureModel implements Refreshable, TileCollidableListener
{
    private static final double GRAVITY = 6.0;

    private final Force movement;
    private final Force jump;

    @Service private Body body;
    @Service private Transformable transformable;
    @Service private MarioController controller;
    @Service private Collidable collidable;
    @Service private TileCollidable tileCollidable;
    @Service private Keyboard keyboard;

    @Service private Context context;

    /**
     * Create updater.
     * 
     * @param model The model reference.
     */
    public MarioUpdater(MarioModel model)
    {
        movement = model.getMovement();
        jump = model.getJump();
    }

    @Override
    public void prepare(FeatureProvider provider, Services services)
    {
        super.prepare(provider, services);

        collidable.setOrigin(Origin.CENTER_BOTTOM);
        transformable.teleport(80, 32);

        body.setDesiredFps(context.getConfig().getSource().getRate());
        body.setGravity(GRAVITY);
        body.setVectors(movement, jump);
    }

    @Override
    public void update(double extrp)
    {
        controller.update(extrp);
        movement.update(extrp);
        jump.update(extrp);
        body.update(extrp);
        tileCollidable.update(extrp);

        if (transformable.getY() < 0)
        {
            transformable.teleportY(80);
            body.resetGravity();
        }
    }

    @Override
    public void notifyTileCollided(Tile tile, Axis axis)
    {
        if (Axis.Y == axis)
        {
            body.resetGravity();
        }
    }
}
