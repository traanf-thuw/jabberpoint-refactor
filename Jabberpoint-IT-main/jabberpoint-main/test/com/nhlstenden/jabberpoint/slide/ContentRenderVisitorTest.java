package com.nhlstenden.jabberpoint.slide;

import static org.mockito.Mockito.*;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.nhlstenden.jabberpoint.Presentation;
import org.junit.jupiter.api.Test;

public class ContentRenderVisitorTest {

    @Test
    void visitPresentation_shouldCallDrawOnCurrentSlide_whenSlideNotNull() {
        // Mocks
        Graphics g = mock(Graphics.class);
        Rectangle area = new Rectangle(0, 0, 100, 100);
        Component observer = mock(Component.class);

        Slide slide = mock(Slide.class);
        Presentation presentation = mock(Presentation.class);

        when(presentation.getCurrentSlide()).thenReturn(slide);

        ContentRenderVisitor visitor = new ContentRenderVisitor(g, area, observer);
        visitor.visitPresentation(presentation);

        // Verify that slide.draw was called once with correct args
        verify(slide, times(1)).draw(g, area, observer);
    }

    @Test
    void visitPresentation_shouldNotCallDraw_whenCurrentSlideIsNull() {
        Graphics g = mock(Graphics.class);
        Rectangle area = new Rectangle(0, 0, 100, 100);
        Component observer = mock(Component.class);

        Presentation presentation = mock(Presentation.class);
        when(presentation.getCurrentSlide()).thenReturn(null);

        ContentRenderVisitor visitor = new ContentRenderVisitor(g, area, observer);
        visitor.visitPresentation(presentation);
    }
}