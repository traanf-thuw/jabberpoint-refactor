package com.nhlstenden.jabberpoint.slide;

import com.nhlstenden.jabberpoint.Presentation;
import com.nhlstenden.jabberpoint.slide.Slide;
import com.nhlstenden.jabberpoint.slide.SlideViewerComponent;
import com.nhlstenden.jabberpoint.slide.SlideViewerFrame;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SlideViewerComponentTest
{
    private SlideViewerComponent viewer;
    private Presentation mockPresentation;
    private Slide mockSlide;
    private JFrame mockFrame;
    private Graphics2D mockGraphics;

    @BeforeEach
    void setUp()
    {
        mockPresentation = mock(Presentation.class);
        mockFrame = new SlideViewerFrame("New", mockPresentation);
        mockSlide = mock(Slide.class);
        mockGraphics = mock(Graphics2D.class);

        viewer = new SlideViewerComponent(mockPresentation, (SlideViewerFrame) mockFrame);
    }

    @Test
    void getPreferredSize_returnsCorrectDimensions()
    {
        // Act
        Dimension size = viewer.getPreferredSize();

        // Assert
        assertEquals(Slide.WIDTH, size.width);
        assertEquals(Slide.HEIGHT, size.height);
    }

    @Test
    void update_withNullSlide_repaintsWithoutError()
    {
        // Arrange
        when(mockPresentation.getCurrentSlideNumber()).thenReturn(-1);

        // Act & Assert
        assertDoesNotThrow(() -> viewer.update(mockPresentation));
    }

    @Test
    void paintComponent_withNullSlide_doesNotThrowException()
    {
        // Arrange
        viewer.update(mockPresentation);
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.createGraphics();

        // Act & Assert
        assertDoesNotThrow(() -> viewer.paintComponent(g));
    }
}