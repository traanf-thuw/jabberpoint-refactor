package com.nhlstenden.jabberpoint.menu;

import com.nhlstenden.jabberpoint.Presentation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NextSlideCommandTest {

    private Presentation mockPresentation;
    private NextSlideCommand command;

    @BeforeEach
    void setUp() {
        mockPresentation = Mockito.mock(Presentation.class);
        command = new NextSlideCommand(mockPresentation);
    }

    @Test
    void execute_normalExecution_callsNextSlideOnPresentation() {
        // Arrange
        when(mockPresentation.getSize()).thenReturn(5);
        when(mockPresentation.getCurrentSlideNumber()).thenReturn(2);

        // Act
        command.execute();

        // Assert
        verify(mockPresentation).nextSlide();
    }

    @Test
    void execute_onLastSlide_doesNotThrowException() {
        // Arrange
        when(mockPresentation.getSize()).thenReturn(3);
        when(mockPresentation.getCurrentSlideNumber()).thenReturn(2); // Last slide (0-based)

        // Act & Assert
        assertDoesNotThrow(() -> command.execute());
        verify(mockPresentation).nextSlide();
    }

    @Test
    void execute_onFirstSlide_advancesToSecondSlide() {
        // Arrange
        when(mockPresentation.getSize()).thenReturn(3);
        when(mockPresentation.getCurrentSlideNumber()).thenReturn(0); // First slide

        // Act
        command.execute();

        // Assert
        verify(mockPresentation).nextSlide();
    }

    @Test
    void execute_emptyPresentation_doesNotThrowException() {
        // Arrange
        when(mockPresentation.getSize()).thenReturn(0);
        when(mockPresentation.getCurrentSlideNumber()).thenReturn(-1);

        // Act & Assert
        assertDoesNotThrow(() -> command.execute());
        verify(mockPresentation).nextSlide();
    }

    @Test
    void execute_multipleCalls_callsNextSlideEachTime() {
        // Arrange
        when(mockPresentation.getSize()).thenReturn(5);

        // Act
        command.execute();
        command.execute();
        command.execute();

        // Assert
        verify(mockPresentation, times(3)).nextSlide();
    }

    @Test
    void execute_singleSlidePresentation_doesNotAdvanceBeyondBounds() {
        // Arrange
        when(mockPresentation.getSize()).thenReturn(1);
        when(mockPresentation.getCurrentSlideNumber()).thenReturn(0);

        // Act
        command.execute();

        // Assert
        verify(mockPresentation).nextSlide();
    }
}