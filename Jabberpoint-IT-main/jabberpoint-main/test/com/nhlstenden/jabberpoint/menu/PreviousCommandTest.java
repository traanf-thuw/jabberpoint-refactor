package com.nhlstenden.jabberpoint.menu;

import com.nhlstenden.jabberpoint.Presentation;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class PreviousCommandTest
{
    @Test
    void testExecute_callsNextSlide()
    {
        // Arrange
        CommandContext mockContext = mock(CommandContext.class);
        Presentation mockPresentation = mock(Presentation.class);
        when(mockContext.getContent()).thenReturn(mockPresentation);

        PreviousCommand command = new PreviousCommand();

        // Act
        command.execute(mockContext);

        // Assert
        verify(mockPresentation).previous(); // verify nextSlide() was called
    }
}
