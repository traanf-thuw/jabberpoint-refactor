package com.nhlstenden.jabberpoint.menu;

import static org.mockito.Mockito.*;

import com.nhlstenden.jabberpoint.slide.SlideViewerFrame;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

public class AboutCommandTest
{
    @Test
    void testExecute_callsAboutBoxShowWithFrame()
    {
        // Arrange
        AboutCommand aboutCommand = new AboutCommand();

        CommandContext mockContext = mock(CommandContext.class);
        SlideViewerFrame mockFrame = mock(SlideViewerFrame.class);
        when(mockContext.getFrame()).thenReturn(mockFrame);

        // Mock static method AboutBox.show
        try (MockedStatic<AboutBox> aboutBoxMock = mockStatic(AboutBox.class))
        {
            // Act
            aboutCommand.execute(mockContext);

            // Assert
            aboutBoxMock.verify(() -> AboutBox.show(mockFrame));
        }
    }
}
