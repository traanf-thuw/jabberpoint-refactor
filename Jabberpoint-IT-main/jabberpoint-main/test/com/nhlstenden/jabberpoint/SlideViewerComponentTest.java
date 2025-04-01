package com.nhlstenden.jabberpoint;

import com.nhlstenden.jabberpoint.slide.Slide;
import com.nhlstenden.jabberpoint.slide.SlideViewerComponent;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SlideViewerComponentTest {
    private SlideViewerComponent viewer;
    private Presentation mockPresentation;
    private Slide mockSlide;
    private JFrame mockFrame;
    private Graphics2D mockGraphics;

    @BeforeEach
    void setUp() {
        mockPresentation = mock(Presentation.class);
        mockFrame = mock(JFrame.class);
        mockSlide = mock(Slide.class);
        mockGraphics = mock(Graphics2D.class);

        viewer = new SlideViewerComponent(mockPresentation, mockFrame);
    }

    @Test
    void getPreferredSize_returnsCorrectDimensions() {
        // Act
        Dimension size = viewer.getPreferredSize();

        // Assert
        assertEquals(Slide.WIDTH, size.width);
        assertEquals(Slide.HEIGHT, size.height);
    }

    @Test
    void update_withNullSlide_repaintsWithoutError() {
        // Arrange
        when(mockPresentation.getCurrentSlideNumber()).thenReturn(-1);

        // Act & Assert
        assertDoesNotThrow(() -> viewer.update(mockPresentation, null));
    }

    @Test
    void update_withValidSlide_updatesTitleAndRepaints() {
        // Arrange
        when(mockPresentation.getShowTitle()).thenReturn("Test Presentation");
        when(mockPresentation.getCurrentSlideNumber()).thenReturn(0);
        when(mockPresentation.getSize()).thenReturn(5);

        // Act
        viewer.update(mockPresentation, mockSlide);

        // Assert
        verify(mockFrame).setTitle("Test Presentation");
    }

    @Test
    void paintComponent_withNullSlide_doesNotThrowException() {
        // Arrange
        viewer.update(mockPresentation, null);
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.createGraphics();

        // Act & Assert
        assertDoesNotThrow(() -> viewer.paintComponent(g));
    }

    @Test
    void paintComponent_withValidSlide_drawsSlide()
    {
        // Arrange
        when(mockPresentation.getCurrentSlideNumber()).thenReturn(1);
        when(mockPresentation.getSize()).thenReturn(3);
        viewer.update(mockPresentation, mockSlide);

        BufferedImage image = new BufferedImage(Slide.WIDTH, Slide.HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();

        // Act
        viewer.paintComponent(g);

        // Assert
        verify(mockSlide).draw(any(), any(Rectangle.class), any());
    }

    @Test
    void paintComponent_withNegativeSlideNumber_doesNotDrawSlide() {
        // Arrange
        when(mockPresentation.getCurrentSlideNumber()).thenReturn(-1);
        viewer.update(mockPresentation, mockSlide);

        BufferedImage image = new BufferedImage(Slide.WIDTH, Slide.HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();

        // Act
        viewer.paintComponent(g);

        // Assert
        verify(mockSlide, never()).draw(any(), any(), any());
    }
}