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
package com.b3dgs.lionengine.example.game.selector;

import com.b3dgs.lionengine.ColorRgba;
import com.b3dgs.lionengine.Origin;
import com.b3dgs.lionengine.Viewer;
import com.b3dgs.lionengine.core.Core;
import com.b3dgs.lionengine.core.Graphic;
import com.b3dgs.lionengine.core.Renderable;
import com.b3dgs.lionengine.core.Updatable;
import com.b3dgs.lionengine.drawable.Drawable;
import com.b3dgs.lionengine.drawable.SpriteAnimated;
import com.b3dgs.lionengine.game.SelectorListener;
import com.b3dgs.lionengine.game.object.ObjectGame;
import com.b3dgs.lionengine.game.object.Services;
import com.b3dgs.lionengine.game.object.SetupSurface;
import com.b3dgs.lionengine.game.trait.Collidable;
import com.b3dgs.lionengine.game.trait.CollidableModel;
import com.b3dgs.lionengine.game.trait.Pathfindable;
import com.b3dgs.lionengine.game.trait.PathfindableModel;
import com.b3dgs.lionengine.game.trait.Transformable;
import com.b3dgs.lionengine.game.trait.TransformableModel;
import com.b3dgs.lionengine.geom.Rectangle;

/**
 * Peon entity implementation.
 * 
 * @author Pierre-Alexandre (contact@b3dgs.com)
 */
class Peon
        extends ObjectGame
        implements Updatable, Renderable, SelectorListener
{
    /** Setup reference. */
    private static final SetupSurface SETUP = new SetupSurface(Core.MEDIA.create("Peon.xml"));

    /** Transformable model. */
    private final Transformable transformable;
    /** Pathfindable model. */
    private final Pathfindable pathfindable;
    /** Collidable model. */
    private final Collidable collidable;
    /** Surface reference. */
    private final SpriteAnimated surface;
    /** Viewer reference. */
    private final Viewer viewer;
    /** Selected flag. */
    private boolean selected;

    /**
     * Create a peon.
     * 
     * @param services The services reference.
     */
    public Peon(Services services)
    {
        super(SETUP, services);

        transformable = new TransformableModel(this, SETUP.getConfigurer());
        addTrait(transformable);

        pathfindable = new PathfindableModel(this, SETUP.getConfigurer(), services);
        addTrait(pathfindable);

        collidable = new CollidableModel(this, SETUP.getConfigurer(), services);
        collidable.setOrigin(Origin.MIDDLE);
        addTrait(collidable);

        viewer = services.get(Viewer.class);

        surface = Drawable.loadSpriteAnimated(SETUP.surface, 15, 9);
        surface.setOrigin(Origin.MIDDLE);
        surface.setFrameOffsets(-8, -8);

        transformable.teleport(240, 160);
    }

    @Override
    public void update(double extrp)
    {
        pathfindable.update(extrp);
        collidable.update(extrp);
        surface.setLocation(viewer, transformable);
    }

    @Override
    public void render(Graphic g)
    {
        surface.render(g);
        if (selected)
        {
            g.setColor(ColorRgba.GREEN);
            g.drawRect(viewer, Origin.MIDDLE, transformable.getX() + 8, transformable.getY() + 8,
                    transformable.getWidth(), transformable.getHeight(), false);
        }
    }

    /*
     * ControlPanelListener
     */

    @Override
    public void notifySelectionStarted(Rectangle selection)
    {
        selected = false;
    }

    @Override
    public void notifySelectionDone(Rectangle selection)
    {
        for (final Rectangle rectangle : collidable.getCollisionBounds())
        {
            if (selection.contains(rectangle) || selection.intersects(rectangle))
            {
                selected = true;
                break;
            }
        }
    }
}