package com.nhlstenden.jabberpoint.menu;

import static org.mockito.Mockito.*;

import com.nhlstenden.jabberpoint.Presentation;
import org.junit.jupiter.api.Test;

public class NextCommandTest
{
    @Test
    void testExecute_callsNextSlideOnPresentation()
    {
        // Arrange
        Presentation mockPresentation = mock(Presentation.class);
        CommandContext mockContext = mock(CommandContext.class);
        when(mockContext.getContent()).thenReturn(mockPresentation);

        NextCommand command = new NextCommand();

        // Act
        command.execute(mockContext);

        // Assert
        verify(mockPresentation, times(1)).next();
    }
}
