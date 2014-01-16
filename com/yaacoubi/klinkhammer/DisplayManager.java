/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.yaacoubi.klinkhammer;

/**
 *
 * @author m.yaacoubi
 */
public class DisplayManager
{
    private javax.swing.JFrame mainFrame;
    private java.awt.GraphicsDevice[] allMonitors;
    private boolean fullscreen = false;
    private boolean fullscreenX = false;
    private java.awt.Rectangle bounds;
    private final java.awt.Rectangle nullBounds = new java.awt.Rectangle(0, 0, 0, 0);
    private java.awt.GraphicsDevice currMonitor;
    
    public DisplayManager(javax.swing.JFrame mainFrame)
    {
        this.mainFrame = mainFrame;
        allMonitors = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
        bounds = nullBounds;
        currMonitor = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    }

    public void refreshDisplays()
    {
        allMonitors = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
    }

    public static int countDisplays()
    {
        return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices().length;
    }

    @Deprecated
    public void toggleFullscreenVer1()  // version 1
    {
        if(fullscreen)
        {
            fullscreen = false;
            mainFrame.dispose();
            mainFrame.setUndecorated(false);
            mainFrame.setBounds(bounds);
            mainFrame.setVisible(true);
        }
        else
        {
            bounds = mainFrame.getBounds();
            fullscreen = true;
            if(fullscreenX ) fullscreenX = false;
            
            mainFrame.dispose();
            mainFrame.setUndecorated(true);
            mainFrame.setVisible(true);

            java.awt.GraphicsDevice currMonitor = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            java.awt.DisplayMode mode = currMonitor.getDisplayMode();
            mainFrame.setBounds(0,0,mode.getWidth(), mode.getHeight());
        }
    }

    public void toggleFullscreen()  // version 2 - optimized
    {
        fullscreen = !fullscreen;
        if(fullscreen)
        {
            if(fullscreenX)
                fullscreenX = false;
            else
                bounds = mainFrame.getBounds();
        }

        mainFrame.dispose();
        mainFrame.setUndecorated(fullscreen);
        mainFrame.setVisible(true);
        mainFrame.setBounds(fullscreen?nullBounds:bounds);
        if(fullscreen) mainFrame.setSize(currMonitor.getDisplayMode().getWidth(), currMonitor.getDisplayMode().getHeight());
    }

    public void toggleFullscreenX()
    {
        fullscreenX = !fullscreenX;
        if(fullscreenX)
        {
            if(fullscreen)
                fullscreen = false;
            else
                bounds = mainFrame.getBounds();
        }

        mainFrame.dispose();
        mainFrame.setUndecorated(fullscreenX);
        mainFrame.setVisible(true);
        mainFrame.setBounds(fullscreenX?nullBounds:bounds);

        int width = 0;

        java.awt.DisplayMode mode;
        for (java.awt.GraphicsDevice currMonitor : allMonitors)
            width += currMonitor.getDisplayMode().getWidth();

        if(fullscreenX) mainFrame.setSize(width, currMonitor.getDisplayMode().getHeight());
    }

    public boolean isFullscreen()
    {
        return fullscreen;
    }

    public boolean isFullscreenX()
    {
        return fullscreenX;
    }
    
}
