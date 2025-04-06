package com.nhlstenden.jabberpoint.files.loading;

import com.nhlstenden.jabberpoint.*;
import com.nhlstenden.jabberpoint.slide.BitmapItem;
import com.nhlstenden.jabberpoint.slide.Slide;
import com.nhlstenden.jabberpoint.style.DefaultStyle;
import com.nhlstenden.jabberpoint.style.Style;

import java.io.File;

/**
 * <p>The loader for the Demo presentation.<p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.7 2025/04/02 Thu Tran - Bocheng Peng
 */
public class DemoLoader implements LoadStrategy
{

    @Override
    public void loadPresentation(Presentation presentation, File file)
    {
        presentation.setShowTitle("Demo Presentation");
        Style style = new DefaultStyle();
        Slide slide;
        slide = new Slide(style);
        slide.setTitle("JabberPoint");
        slide.addText(1, "The Java prestentation tool");
        slide.addText(2, "Copyright (c) 1996-2000: Ian Darwin");
        slide.addText(2, "Copyright (c) 2000-now:");
        slide.addText(2, "Gert Florijn and Sylvia Stuurman");
        slide.addText(4, "Calling Jabberpoint without a filename");
        slide.addText(4, "will show this presentation");
        slide.addText(1, "Navigate:");
        slide.addText(3, "Next slide: PgDn or Enter");
        slide.addText(3, "Previous slide: PgUp or up-arrow");
        slide.addText(3, "Quit: q or Q");
        presentation.append(slide);

        slide = new Slide(style);
        slide.setTitle("Demonstration of levels and styles");
        slide.addText(1, "Level 1");
        slide.addText(2, "Level 2");
        slide.addText(1, "Again level 1");
        slide.addText(1, "Level 1 has style number 1");
        slide.addText(2, "Level 2 has style number 2");
        slide.addText(3, "This is how level 3 looks like");
        slide.addText(4, "And this is level 4");
        presentation.append(slide);

        slide = new Slide(style);
        slide.setTitle("The third slide");
        slide.addText(1, "To open a new presentation,");
        slide.addText(2, "use File->Open from the menu.");
        slide.addText(1, " ");
        slide.addText(1, "This is the end of the presentation.");
        slide.addSlideItem(new BitmapItem(1, "JabberPoint.jpg"));
        presentation.append(slide);
    }
}
