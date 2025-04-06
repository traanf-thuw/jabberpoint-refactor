package com.nhlstenden.jabberpoint.slide;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;

class ButtonItemTest
{

    private ButtonItem button;
    private Graphics2D mockGraphics;
    private ImageObserver mockObserver;
    private ActionListener mockListener;
    private FontMetrics mockMetrics;

    @BeforeEach
    void setUp()
    {
        button = new ButtonItem(1, "Test");
        mockListener = mock(ActionListener.class);
        mockGraphics = mock(Graphics2D.class);
        mockObserver = mock(ImageObserver.class);
        mockMetrics = mock(FontMetrics.class);

        when(mockGraphics.getFontMetrics()).thenReturn(mockMetrics);
        when(mockMetrics.stringWidth("Test")).thenReturn(40);
        when(mockMetrics.getHeight()).thenReturn(20);
        when(mockMetrics.getAscent()).thenReturn(16);
    }

    @Test
    void handleClick_InsideBounds_TriggersAction()
    {
        button.setActionListener(mockListener);
        button.draw(mockGraphics, new Rectangle(0, 0, 100, 50), mockObserver);

        button.handleClick(new Point(50, 25));

        verify(mockListener).actionPerformed(any(ActionEvent.class));
    }

    @Test
    void handleClick_OutsideBounds_DoesNothing()
    {
        button.setActionListener(mockListener);
        button.draw(mockGraphics, new Rectangle(0, 0, 100, 50), mockObserver);

        button.handleClick(new Point(150, 75));

        verifyNoInteractions(mockListener);
    }

    @Test
    void handleClick_NoListener_DoesNothing()
    {
        button.draw(mockGraphics, new Rectangle(0, 0, 100, 50), mockObserver);
        button.handleClick(new Point(50, 25));

        verifyNoInteractions(mockListener);
    }

    @Test
    void draw_SetsBoundsAndRendersCorrectly()
    {
        Rectangle area = new Rectangle(10, 20, 200, 100);

        button.draw(mockGraphics, area, mockObserver);

        // Verify bounds setting
        assertEquals(area, button.bounds);

        // Verify background drawing
        verify(mockGraphics).fillRoundRect(10, 20, 200, 100, 8, 8);

        // Verify text positioning
        ArgumentCaptor<String> textCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Integer> xCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> yCaptor = ArgumentCaptor.forClass(Integer.class);

        verify(mockGraphics).drawString(
                textCaptor.capture(),
                xCaptor.capture(),
                yCaptor.capture()
        );

        assertEquals("Test", textCaptor.getValue());
        assertEquals(10 + (200 - 40) / 2, xCaptor.getValue().intValue()); // Centered X
        assertEquals(20 + ((100 - 20) / 2) + 16, yCaptor.getValue().intValue()); // Centered Y
    }

    @Test
    void getBoundingBox_CalculatesCorrectSize()
    {
        Rectangle bounds = button.getBoundingBox(mockGraphics, mockObserver, 1.0f);

        // Expected: width = 40 + 20 padding = 60
        // height = 20 + 10 padding = 30
        assertEquals(new Rectangle(0, 0, 60, 30), bounds);
    }

    @Test
    void setActionListener_UpdatesListener()
    {
        button.setActionListener(mockListener);
        assertSame(mockListener, button.actionListener);
    }

    @Test
    void getContent_ReturnsNull()
    {
        assertNull(button.getContent());
    }
}