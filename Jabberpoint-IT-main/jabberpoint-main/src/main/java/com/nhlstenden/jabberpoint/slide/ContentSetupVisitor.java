package com.nhlstenden.jabberpoint.slide;

import com.nhlstenden.jabberpoint.ContentVisitor;
import com.nhlstenden.jabberpoint.KeyController;
import com.nhlstenden.jabberpoint.Presentation;

public class ContentSetupVisitor implements ContentVisitor
{
    private final SlideViewerComponent component;
    private final SlideViewerFrame frame;

    public ContentSetupVisitor(SlideViewerComponent component, SlideViewerFrame frame) {
        this.component = component;
        this.frame = frame;
    }

    @Override
    public void visitPresentation(Presentation presentation) {
        presentation.setSlideViewComponent(component);
        frame.setMenuBar(frame.createMenuBar(presentation));
        frame.addKeyListener(new KeyController(presentation));
    }

    // Future: visitDocument(Document doc), etc.
}

