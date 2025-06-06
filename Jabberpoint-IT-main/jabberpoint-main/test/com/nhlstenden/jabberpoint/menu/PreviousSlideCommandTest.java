package com.nhlstenden.jabberpoint.menu;

import com.nhlstenden.jabberpoint.Presentation;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class PreviousSlideCommandTest
{
    @Test
    void testExecute_callsNextSlide()
    {
        // Arrange
        CommandContext mockContext = mock(CommandContext.class);
        Presentation mockPresentation = mock(Presentation.class);
        when(mockContext.getPresentation()).thenReturn(mockPresentation);

        PreviousSlideCommand command = new PreviousSlideCommand();

        // Act
        command.execute(mockContext);

        // Assert
        verify(mockPresentation).nextSlide(); // verify nextSlide() was called
    }
}
