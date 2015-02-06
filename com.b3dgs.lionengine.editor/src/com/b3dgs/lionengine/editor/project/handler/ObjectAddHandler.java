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
package com.b3dgs.lionengine.editor.project.handler;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

import com.b3dgs.lionengine.LionEngineException;
import com.b3dgs.lionengine.core.Media;
import com.b3dgs.lionengine.editor.InputValidator;
import com.b3dgs.lionengine.editor.Tools;
import com.b3dgs.lionengine.editor.UtilEclipse;
import com.b3dgs.lionengine.editor.project.ProjectTreeCreator;
import com.b3dgs.lionengine.editor.project.ProjectsModel;
import com.b3dgs.lionengine.editor.project.ProjectsPart;
import com.b3dgs.lionengine.game.factory.Factory;

/**
 * Add an object in the selected folder.
 * 
 * @author Pierre-Alexandre (contact@b3dgs.com)
 */
public class ObjectAddHandler
{
    /** Default new object name. */
    private static final String DEFAULT_NEW_OBJECT_NAME = "object";

    /**
     * Create the object
     * 
     * @param object The object file destination.
     * @param clazz The object class.
     * @throws IOException If error when creating the object.
     */
    private static void createObject(File object, Class<?> clazz) throws IOException
    {
        final File template = Tools.getTemplate(Tools.TEMPLATE_OBJECT);
        final Collection<String> lines = Files.readAllLines(template.toPath(), StandardCharsets.UTF_8);
        final Collection<String> dest = new ArrayList<>();
        for (final String line : lines)
        {
            if (line.contains(Tools.TEMPLATE_CLASS_AREA))
            {
                dest.add(line.replace(Tools.TEMPLATE_CLASS_AREA, clazz.getName()));
            }
            else
            {
                dest.add(line);
            }
        }
        Files.write(object.toPath(), dest, StandardCharsets.UTF_8);
        lines.clear();
        dest.clear();
    }

    /**
     * Add the object
     * 
     * @param partService The part service reference.
     * @param selection The current folder selection.
     * @param object The object file destination.
     * @param clazz The object class.
     */
    private static void addObject(EPartService partService, Media selection, File object, Class<?> clazz)
    {
        try
        {
            ObjectAddHandler.createObject(object, clazz);
            final ProjectsPart part = UtilEclipse.getPart(partService, ProjectsPart.ID, ProjectsPart.class);
            part.addTreeItem(selection, object, ProjectTreeCreator.ICON_OBJECT);
        }
        catch (final IOException exception)
        {
            throw new LionEngineException(exception);
        }
    }

    /**
     * Execute the handler.
     * 
     * @param partService The part service reference.
     * @param parent The shell parent.
     */
    @Execute
    public void execute(EPartService partService, Shell parent)
    {
        final Media selection = ProjectsModel.INSTANCE.getSelection();
        final InputDialog inputDialog = new InputDialog(parent, Messages.AddObject_Title, Messages.AddObject_Text,
                ObjectAddHandler.DEFAULT_NEW_OBJECT_NAME, new InputValidator(InputValidator.NAME_MATCH,
                        com.b3dgs.lionengine.editor.Messages.InputValidator_Error_Name));
        final int code = inputDialog.open();
        if (code == Window.OK)
        {
            final String name = inputDialog.getValue();
            final File object = new File(selection.getFile(), name + "." + Factory.FILE_DATA_EXTENSION);

            if (object.exists())
            {
                MessageDialog.openError(parent, Messages.AddObject_Error_Title, Messages.AddObject_Error_Text);
                execute(partService, parent);
            }
            else
            {
                final File classFile = Tools.selectClassFile(parent);
                if (classFile != null)
                {
                    final Class<?> clazz = Tools.getClass(classFile);
                    ObjectAddHandler.addObject(partService, selection, object, clazz);
                }
            }
        }
    }
}