package com.nhlstenden.jabberpoint.menu;

import java.awt.Frame;
import javax.swing.JOptionPane;

/**
 * The About-box for JabberPoint.
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman, Rick Vinke
 * @version 1.7 2023/01/14 Rick Vinke
 */
public class AboutBox
{
    public static void show(Frame parent)
    {
        JOptionPane.showMessageDialog(parent,
                """
                        JabberPoint is a primitive slide-show program in Java(tm). It
                        is freely copyable as long as you keep this notice and
                        the splash screen intact.
                        Copyright (c) 1995-1997 by Ian F. Darwin, ian@darwinsys.com.
                        Adapted by Gert Florijn (version 1.1) and Sylvia Stuurman (version 1.2 and higher) for the OpenUniversity of the Netherlands, 2002 -- now.
                        Refactored by Thu Tran and Bocheng- NHL Stenden - 2025.
                        Author's version available from https://www.darwinsys.com/""",
                "About JabberPoint",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}
