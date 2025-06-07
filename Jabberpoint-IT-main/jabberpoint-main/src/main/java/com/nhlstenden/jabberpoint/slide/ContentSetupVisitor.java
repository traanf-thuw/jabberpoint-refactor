package com.nhlstenden.jabberpoint.slide;

import com.nhlstenden.jabberpoint.ContentVisitor;
import com.nhlstenden.jabberpoint.KeyController;
import com.nhlstenden.jabberpoint.Presentation;

/**
 * <p>A visitor that sets up the user interface components for a {@code Presentation}.</p>
 * <p>This class implements the {@code ContentVisitor} interface and is responsible for
 * initializing the presentation's view component, configuring the frame's menu bar, and
 * attaching keyboard controls for slide navigation and interaction.</p>
 *
 * <p>The setup includes:
 * <ul>
 *   <li>Assigning the {@code SlideViewerComponent} to the presentation</li>
 *   <li>Creating and setting a menu bar for the {@code SlideViewerFrame}</li>
 *   <li>Registering a {@code KeyController} as a key listener to the frame</li>
 * </ul></p>
 *
 * <p>Designed to prepare the presentation for viewing and interaction within a GUI context.</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.7 2025/04/02 Thu Tran - Bocheng Peng
 */
public class ContentSetupVisitor implements ContentVisitor
{
    private final SlideViewerComponent component;
    private final SlideViewerFrame frame;

    public ContentSetupVisitor(SlideViewerComponent component, SlideViewerFrame frame)
    {
        this.component = component;
        this.frame = frame;
    }

    @Override
    public void visitPresentation(Presentation presentation)
    {
        presentation.setSlideViewComponent(component);
        frame.setMenuBar(frame.createMenuBar(presentation));
        frame.addKeyListener(new KeyController(presentation));
    }

    // Future: visitDocument(Document doc), etc.
}