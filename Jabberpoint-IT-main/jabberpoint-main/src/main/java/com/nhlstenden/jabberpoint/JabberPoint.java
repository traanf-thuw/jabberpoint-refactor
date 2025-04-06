package com.nhlstenden.jabberpoint;

import com.nhlstenden.jabberpoint.files.FileHandler;
import com.nhlstenden.jabberpoint.files.loading.DemoLoader;
import com.nhlstenden.jabberpoint.slide.SlideViewerFrame;

/**
 * JabberPoint Main Program
 * <p>This program is distributed under the terms of the accompanying
 * COPYRIGHT.txt file (which is NOT the GNU General Public License).
 * Please read it. Your use of the software constitutes acceptance
 * of the terms in the COPYRIGHT.txt file.</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman, Rick Vinke
 * @version 1.7 2025/04/02 Thu Tran - Bocheng Peng
 */
public class JabberPoint
{
    public static final String JAB_NAME = "Jabberpoint";
    public static final String JAB_VERSION = "1.7 - OU version";

    /**
     * The main program
     */
    public static void main(String[] argv)
    {
        Presentation presentation = new Presentation();
        new SlideViewerFrame(JAB_NAME + " " + JAB_VERSION, presentation);
        if (argv.length == 0)
        { //a demo presentation
            new DemoLoader().loadPresentation(presentation, null);
        }
        else
        {
            new FileHandler().loadFile(presentation, argv[0]);
        }

        presentation.setSlideNumber(0);
    }
}
