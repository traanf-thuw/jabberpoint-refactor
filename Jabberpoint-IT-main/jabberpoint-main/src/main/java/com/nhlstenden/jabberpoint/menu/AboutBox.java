package com.nhlstenden.jabberpoint.menu;

import java.awt.Frame;
import javax.swing.JOptionPane;

/**The About-box for JabberPoint.
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 * @version 1.7 2025/04/02 Thu Tran - Bocheng Peng
 */
public class AboutBox
{
    public static void show(Frame parent)
    {
        JOptionPane.showMessageDialog(parent,
                "JabberPoint is a primitive slide-show program in Java(tm). \n" +
                        "It is freely copyable as long as you keep this notice and the splash screen intact.\n" +
                        "Copyright (c) 1995-1997 by Ian F. Darwin, ian@darwinsys.com.\n" +
                        "Adapted by Gert Florijn (version 1.1) and Sylvia Stuurman (version 1.2 and higher) \n" +
                        "for the Open University of the Netherlands, 2002 -- now. \n" +
                        "Author's version available from http://www.darwinsys.com/",
                "About JabberPoint",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}
