package com.nhlstenden.jabberpoint.slide;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.nhlstenden.jabberpoint.slide.CompositeSlideItem;
import com.nhlstenden.jabberpoint.slide.SlideComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.awt.*;
import java.awt.image.ImageObserver;

class CompositeSlideItemTest
{

    private CompositeSlideItem composite;
    private SlideComponent child1;
    private SlideComponent child2;
    private Graphics mockGraphics;
    private ImageObserver mockObserver;

    @BeforeEach
    void setUp()
    {
        composite = new CompositeSlideItem(1);
        child1 = mock(SlideComponent.class);
        child2 = mock(SlideComponent.class);
        mockGraphics = mock(Graphics.class);
        mockObserver = mock(ImageObserver.class);
    }

    @Test
    void addChild_ShouldAddToChildrenList()
    {
        composite.addChild(child1);
        assertTrue(composite.getChildren().contains(child1));
        assertEquals(1, composite.getChildren().size());
    }

    @Test
    void removeChild_ShouldRemoveFromChildrenList()
    {
        composite.addChild(child1);
        composite.removeChild(child1);
        assertFalse(composite.getChildren().contains(child1));
        assertEquals(0, composite.getChildren().size());
    }

    @Test
    void hasChildren_ReturnsCorrectState()
    {
        assertFalse(composite.hasChildren());
        composite.addChild(child1);
        assertTrue(composite.hasChildren());
    }

    @Test
    void draw_WithChildren_DividesAreaEqually()
    {
        composite.addChild(child1);
        composite.addChild(child2);
        Rectangle area = new Rectangle(0, 0, 200, 100);

        composite.draw(mockGraphics, area, mockObserver);

        ArgumentCaptor<Rectangle> rectCaptor = ArgumentCaptor.forClass(Rectangle.class);
        verify(child1).draw(eq(mockGraphics), rectCaptor.capture(), eq(mockObserver));
        verify(child2).draw(eq(mockGraphics), rectCaptor.capture(), eq(mockObserver));

        // Verify child1's area: x=0, width=100
        Rectangle child1Area = rectCaptor.getAllValues().get(0);
        assertEquals(0, child1Area.x);
        assertEquals(100, child1Area.width);

        // Verify child2's area: x=100, width=100
        Rectangle child2Area = rectCaptor.getAllValues().get(1);
        assertEquals(100, child2Area.x);
        assertEquals(100, child2Area.width);
    }

    @Test
    void draw_NoChildren_DoesNothing()
    {
        composite.draw(mockGraphics, new Rectangle(0, 0, 200, 100), mockObserver);
        verifyNoInteractions(child1, child2, mockGraphics);
    }

    @Test
    void getBoundingBox_NoChildren_ReturnsEmptyRect()
    {
        Rectangle result = composite.getBoundingBox(mockGraphics, mockObserver, 1.0f);
        assertEquals(new Rectangle(0, 0, 0, 0), result);
    }

    @Test
    void getBoundingBox_SingleChild_ReturnsChildBounds()
    {
        when(child1.getBoundingBox(any(), any(), anyFloat()))
                .thenReturn(new Rectangle(0, 0, 50, 20));
        composite.addChild(child1);

        Rectangle result = composite.getBoundingBox(mockGraphics, mockObserver, 1.0f);
        assertEquals(new Rectangle(0, 0, 50, 20), result);
    }

    @Test
    void getBoundingBox_MultipleChildren_ReturnsUnion()
    {
        when(child1.getBoundingBox(any(), any(), anyFloat()))
                .thenReturn(new Rectangle(10, 10, 30, 40));
        when(child2.getBoundingBox(any(), any(), anyFloat()))
                .thenReturn(new Rectangle(50, 20, 40, 50));
        composite.addChild(child1);
        composite.addChild(child2);

        Rectangle result = composite.getBoundingBox(mockGraphics, mockObserver, 1.0f);
        assertEquals(new Rectangle(10, 10, 80, 60), result); // x=10,y=10,w=80 (10+30 to 50+40), h=60 (10 to 20+50)
    }

    @Test
    void getContent_ReturnsNull()
    {
        assertNull(composite.getContent());
    }
}