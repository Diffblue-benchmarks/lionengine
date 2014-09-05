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
package com.b3dgs.lionengine.core;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.IllegalComponentStateException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.b3dgs.lionengine.LionEngineException;
import com.b3dgs.lionengine.Resolution;

/**
 * Full screen implementation.
 * 
 * @author Pierre-Alexandre (contact@b3dgs.com)
 * @see Keyboard
 * @see Mouse
 */
final class ScreenFullAwt
        extends ScreenAwt
{
    /** Error message unsupported fullscreen. */
    private static final String ERROR_UNSUPPORTED_FULLSCREEN = "Unsupported fullscreen mode: ";

    /** Graphics device reference. */
    private final GraphicsDevice dev;
    /** Graphic configuration reference. */
    private final GraphicsConfiguration conf;
    /** Frame reference. */
    private final JFrame frame;
    /** Fullscreen window. */
    private java.awt.Window window;

    /**
     * Constructor.
     * 
     * @param renderer The renderer reference.
     */
    ScreenFullAwt(Renderer renderer)
    {
        super(renderer);

        // Initialize environment
        final GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        dev = env.getDefaultScreenDevice();
        conf = dev.getDefaultConfiguration();
        frame = initMainFrame(renderer);
    }

    /**
     * Prepare fullscreen mode.
     * 
     * @param output The output resolution
     * @param depth The bit depth color.
     */
    private void initFullscreen(Resolution output, int depth)
    {
        try
        {
            window = new java.awt.Window(frame, conf);
            window.setBackground(Color.BLACK);
            window.setIgnoreRepaint(true);
            frame.setUndecorated(true);
            window.setPreferredSize(new Dimension(output.getWidth(), output.getHeight()));

            dev.setFullScreenWindow(window);
            final DisplayMode disp = new DisplayMode(output.getWidth(), output.getHeight(), depth, output.getRate());
            dev.setDisplayMode(disp);
            window.validate();

            // Create buffer
            try
            {
                window.createBufferStrategy(2, conf.getBufferCapabilities());
            }
            catch (final AWTException exception)
            {
                window.createBufferStrategy(1);
            }
            buf = window.getBufferStrategy();

            // Set input listeners
            componentForKeyboard = frame;
            componentForMouse = window;
            componentForCursor = window;
            frame.validate();
        }
        catch (final UnsupportedOperationException
                     | IllegalComponentStateException
                     | IllegalArgumentException exception)
        {
            final StringBuilder builder = new StringBuilder("Supported display mode:\n");
            int i = 0;
            for (final DisplayMode display : dev.getDisplayModes())
            {
                final StringBuilder widthSpace = new StringBuilder("");
                final int width = display.getWidth();
                if (width < 1000)
                {
                    widthSpace.append(" ");
                }
                final StringBuilder heightSpace = new StringBuilder("");
                final int height = display.getHeight();
                if (height < 1000)
                {
                    heightSpace.append(" ");
                }
                final StringBuilder freqSpace = new StringBuilder("");
                final int freq = display.getRefreshRate();
                if (freq < 100)
                {
                    freqSpace.append(" ");
                }
                builder.append("[").append(widthSpace).append(width).append("*").append(heightSpace).append(height)
                        .append("*").append(display.getBitDepth()).append(" @").append(freqSpace).append(freq)
                        .append("Hz] ");
                i++;
                if (i % 5 == 0)
                {
                    builder.append("\n");
                }
            }
            throw new LionEngineException(ScreenFullAwt.ERROR_UNSUPPORTED_FULLSCREEN,
                    String.valueOf(output.getWidth()), "*", String.valueOf(output.getHeight()), "*",
                    String.valueOf(depth), " @", String.valueOf(output.getRate()), "Hz", "\n", builder.toString());
        }
    }

    /**
     * Initialize the main frame.
     * 
     * @param renderer The renderer reference.
     * @return The created main frame.
     */
    private JFrame initMainFrame(final Renderer renderer)
    {
        final JFrame frame = new JFrame(EngineCore.getProgramName() + " " + EngineCore.getProgramVersion(), conf);

        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent event)
            {
                renderer.end();
            }
        });
        frame.setResizable(false);
        frame.setIgnoreRepaint(true);

        return frame;
    }

    @Override
    protected void setResolution(Resolution output)
    {
        initFullscreen(output, config.getDepth());
        super.setResolution(output);
    }

    /*
     * Screen
     */

    @Override
    public void start()
    {
        super.start();
        frame.validate();
        frame.setEnabled(true);
        frame.setVisible(true);
    }

    @Override
    public void dispose()
    {
        super.dispose();
        buf.dispose();
        frame.dispose();
    }

    @Override
    public void requestFocus()
    {
        frame.requestFocus();
        super.requestFocus();
    }

    @Override
    public void setIcon(String filename)
    {
        final ImageIcon icon = new ImageIcon(filename);
        frame.setIconImage(icon.getImage());
    }
}