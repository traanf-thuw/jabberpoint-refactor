package com.nhlstenden.jabberpoint.menu;

import static org.mockito.Mockito.*;

import com.nhlstenden.jabberpoint.Presentation;
import org.junit.jupiter.api.Test;

public class NextSlideCommandTest
{
    @Test
    void testExecute_callsNextSlideOnPresentation()
    {
        // Arrange
        Presentation mockPresentation = mock(Presentation.class);
        CommandContext mockContext = mock(CommandContext.class);
        when(mockContext.getPresentation()).thenReturn(mockPresentation);

        NextSlideCommand command = new NextSlideCommand();

        // Act
        command.execute(mockContext);

        // Assert
        verify(mockPresentation, times(1)).nextSlide();
    }
}
