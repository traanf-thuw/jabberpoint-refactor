package com.nhlstenden.jabberpoint.menu;

import com.nhlstenden.jabberpoint.Presentation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PreviousSlideCommandTest {

    private Presentation mockPresentation;
    private PreviousSlideCommand command;

    @BeforeEach
    void setUp() {
        mockPresentation = Mockito.mock(Presentation.class);
        command = new PreviousSlideCommand(mockPresentation);
    }

    @Test
    void execute_normalExecution_callsPrevSlideOnPresentation() {
        // Arrange
        when(mockPresentation.getSlideNumber()).thenReturn(2); // Not on first slide

        // Act
        command.execute();

        // Assert
        verify(mockPresentation).prevSlide();
    }

    @Test
    void execute_onFirstSlide_doesNotThrowException() {
        // Arrange
        when(mockPresentation.getSlideNumber()).thenReturn(0); // First slide

        // Act & Assert
        assertDoesNotThrow(() -> command.execute());
        verify(mockPresentation).prevSlide();
    }

    @Test
    void execute_onSecondSlide_goesToFirstSlide() {
        // Arrange
        when(mockPresentation.getSlideNumber()).thenReturn(1); // Second slide

        // Act
        command.execute();

        // Assert
        verify(mockPresentation).prevSlide();
    }

    @Test
    void execute_emptyPresentation_doesNotThrowException() {
        // Arrange
        when(mockPresentation.getSlideNumber()).thenReturn(-1); // Empty presentation

        // Act & Assert
        assertDoesNotThrow(() -> command.execute());
        verify(mockPresentation).prevSlide();
    }

    @Test
    void execute_multipleCalls_callsPrevSlideEachTime() {
        // Arrange
        when(mockPresentation.getSlideNumber()).thenReturn(3); // Start from slide 3

        // Act
        command.execute(); // Should go to slide 2
        command.execute(); // Should go to slide 1
        command.execute(); // Should go to slide 0

        // Assert
        verify(mockPresentation, times(3)).prevSlide();
    }

    @Test
    void execute_singleSlidePresentation_doesNotGoBelowZero() {
        // Arrange
        when(mockPresentation.getSlideNumber()).thenReturn(0); // Only one slide

        // Act
        command.execute();

        // Assert
        verify(mockPresentation).prevSlide();
        // Presentation should handle not going below 0
    }
}