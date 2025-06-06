package com.nhlstenden.jabberpoint.slide;

import com.nhlstenden.jabberpoint.Content;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SlideViewerComponentTest {

    private SlideViewerComponent viewer;
    private Content mockContent;
    private SlideViewerFrame mockFrame;

    @BeforeEach
    void setUp() {
        mockContent = mock(Content.class);
        mockFrame = mock(SlideViewerFrame.class);
        viewer = new SlideViewerComponent(mockContent, mockFrame);
    }

    @Test
    void testConstructor_InitializesCorrectly() {
        assertEquals(new Dimension(Slide.WIDTH, Slide.HEIGHT), viewer.getPreferredSize());
    }

    @Test
    void testUpdate_AssignsContentAndUpdatesTitle() {
        when(mockContent.getTitle()).thenReturn("New Title");

        viewer.update(mockContent);

        verify(mockFrame).setTitle("New Title");
    }

    @Test
    void testPaintComponent_DrawsCorrectlyAndCallsAccept() {
        // Arrange
        Graphics mockGraphics = mock(Graphics.class);
        when(mockContent.getShowListSize()).thenReturn(3);
        Dimension mockSize = new Dimension(1200, 800);
        when(mockContent.getTitle()).thenReturn("Some Title");

        SlideViewerComponent spyViewer = spy(viewer);
        doReturn(mockSize).when(spyViewer).getSize();
        doReturn(mockSize.width).when(spyViewer).getWidth();
        doReturn(mockSize.height).when(spyViewer).getHeight();

        // Act
        spyViewer.paintComponent(mockGraphics);

        // Assert drawing
        verify(mockGraphics).setColor(Color.white);
        verify(mockGraphics).fillRect(0, 0, mockSize.width, mockSize.height);
        verify(mockGraphics).setFont(any(Font.class));
        verify(mockGraphics).setColor(Color.black);
        verify(mockGraphics).drawString("Item 3 shown", 1100, 20);

        // Assert visitor call
        verify(mockContent).accept(any(ContentRenderVisitor.class));
    }
}
