package com.nhlstenden.jabberpoint.menu;

import static org.junit.jupiter.api.Assertions.*;

import com.nhlstenden.jabberpoint.Presentation;
import com.nhlstenden.jabberpoint.files.FileHandler;
import com.nhlstenden.jabberpoint.slide.SlideViewerFrame;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class CommandContextTest
{
    @Test
    void testConstructorAndGetters()
    {
        // Arrange
        Presentation mockPresentation = Mockito.mock(Presentation.class);
        SlideViewerFrame mockFrame = Mockito.mock(SlideViewerFrame.class);
        FileHandler mockFileHandler = Mockito.mock(FileHandler.class);

        // Act
        CommandContext context = new CommandContext(mockPresentation, mockFrame, mockFileHandler);

        // Assert
        assertSame(mockPresentation, context.getPresentation(), "Presentation should be the same instance");
        assertSame(mockFrame, context.getFrame(), "Frame should be the same instance");
        assertSame(mockFileHandler, context.getFileHandler(), "FileHandler should be the same instance");
    }
}
