package com.nhlstenden.jabberpoint.slide;

import com.nhlstenden.jabberpoint.ContentVisitor;
import com.nhlstenden.jabberpoint.Presentation;

import java.awt.*;

public class ContentRenderVisitor implements ContentVisitor
{
    private final Graphics g;
    private final Rectangle area;
    private final Component observer;

    public ContentRenderVisitor(Graphics g, Rectangle area, Component observer) {
        this.g = g;
        this.area = area;
        this.observer = observer;
    }

    @Override
    public void visitPresentation(Presentation presentation) {
        Slide currentSlide = presentation.getCurrentSlide();
        if (currentSlide != null) {
            currentSlide.draw(g, area, observer);
        }
    }

    // Future: render for documents, etc.
}

