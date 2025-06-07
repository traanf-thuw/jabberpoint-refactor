package com.nhlstenden.jabberpoint.slide;

import com.nhlstenden.jabberpoint.ContentVisitor;
import com.nhlstenden.jabberpoint.Presentation;

import java.awt.*;

/**
 * <p>A visitor that renders the current slide of a {@code Presentation} to a graphical context.</p>
 * <p>This class implements the {@code ContentVisitor} interface and uses a {@code Graphics} object
 * along with a defined drawing {@code Rectangle} and rendering {@code Component} to display the current slide.</p>
 *
 * <p>The visitor delegates the rendering process to the {@code draw()} method of the current {@code Slide}
 * if one is present in the presentation.</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.7 2025/04/02 Thu Tran - Bocheng Peng
 */
public class ContentRenderVisitor implements ContentVisitor
{
    private final Graphics g;
    private final Rectangle area;
    private final Component observer;

    public ContentRenderVisitor(Graphics g, Rectangle area, Component observer)
    {
        this.g = g;
        this.area = area;
        this.observer = observer;
    }

    @Override
    public void visitPresentation(Presentation presentation)
    {
        Slide currentSlide = presentation.getCurrentSlide();
        if (currentSlide != null)
        {
            currentSlide.draw(g, area, observer);
        }
    }
}