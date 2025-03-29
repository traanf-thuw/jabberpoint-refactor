package com.nhlstenden.jabberpoint.menu;

import com.nhlstenden.jabberpoint.Presentation;
import com.nhlstenden.jabberpoint.SlideViewerFrame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.awt.event.ActionEvent;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MenuControllerTest {

    private SlideViewerFrame mockFrame;
    private Presentation mockPresentation;
    private CommandHistory mockCommandHistory;
    private MenuController menuController;

    @BeforeEach
    void setUp() {
        mockFrame = Mockito.mock(SlideViewerFrame.class);
        mockPresentation = Mockito.mock(Presentation.class);
        mockCommandHistory = Mockito.mock(CommandHistory.class);
        menuController = new MenuController(mockFrame, mockPresentation, mockCommandHistory);
    }

    @Test
    void actionPerformed_openCommand_executesOpenCommand() {
        // Arrange
        ActionEvent openEvent = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Open");
        MenuCommand mockCommand = Mockito.mock(MenuCommand.class);
        MenuController spyController = Mockito.spy(menuController);
        when(spyController.createCommand("Open")).thenReturn(mockCommand);

        // Act
        spyController.actionPerformed(openEvent);

        // Assert
        verify(mockCommandHistory).execute(mockCommand);
    }

    @Test
    void actionPerformed_newCommand_executesNewCommand() {
        // Arrange
        ActionEvent newEvent = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "New");
        MenuCommand mockCommand = Mockito.mock(MenuCommand.class);
        MenuController spyController = Mockito.spy(menuController);
        when(spyController.createCommand("New")).thenReturn(mockCommand);

        // Act
        spyController.actionPerformed(newEvent);

        // Assert
        verify(mockCommandHistory).execute(mockCommand);
    }

    @Test
    void actionPerformed_saveCommand_executesSaveCommand() {
        // Arrange
        ActionEvent saveEvent = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Save");
        MenuCommand mockCommand = Mockito.mock(MenuCommand.class);
        MenuController spyController = Mockito.spy(menuController);
        when(spyController.createCommand("Save")).thenReturn(mockCommand);

        // Act
        spyController.actionPerformed(saveEvent);

        // Assert
        verify(mockCommandHistory).execute(mockCommand);
    }

    @Test
    void actionPerformed_exitCommand_executesExitCommand() {
        // Arrange
        ActionEvent exitEvent = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Exit");
        MenuCommand mockCommand = Mockito.mock(MenuCommand.class);
        MenuController spyController = Mockito.spy(menuController);
        when(spyController.createCommand("Exit")).thenReturn(mockCommand);

        // Act
        spyController.actionPerformed(exitEvent);

        // Assert
        verify(mockCommandHistory).execute(mockCommand);
    }

    @Test
    void actionPerformed_nextCommand_executesNextCommand() {
        // Arrange
        ActionEvent nextEvent = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Next");
        MenuCommand mockCommand = Mockito.mock(MenuCommand.class);
        MenuController spyController = Mockito.spy(menuController);
        when(spyController.createCommand("Next")).thenReturn(mockCommand);

        // Act
        spyController.actionPerformed(nextEvent);

        // Assert
        verify(mockCommandHistory).execute(mockCommand);
    }

    @Test
    void actionPerformed_prevCommand_executesPrevCommand() {
        // Arrange
        ActionEvent prevEvent = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Prev");
        MenuCommand mockCommand = Mockito.mock(MenuCommand.class);
        MenuController spyController = Mockito.spy(menuController);
        when(spyController.createCommand("Prev")).thenReturn(mockCommand);

        // Act
        spyController.actionPerformed(prevEvent);

        // Assert
        verify(mockCommandHistory).execute(mockCommand);
    }

    @Test
    void actionPerformed_gotoCommand_executesGotoCommand() {
        // Arrange
        ActionEvent gotoEvent = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Go to");
        MenuCommand mockCommand = Mockito.mock(MenuCommand.class);
        MenuController spyController = Mockito.spy(menuController);
        when(spyController.createCommand("Go to")).thenReturn(mockCommand);

        // Act
        spyController.actionPerformed(gotoEvent);

        // Assert
        verify(mockCommandHistory).execute(mockCommand);
    }

    @Test
    void actionPerformed_aboutCommand_executesAboutCommand() {
        // Arrange
        ActionEvent aboutEvent = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "About");
        MenuCommand mockCommand = Mockito.mock(MenuCommand.class);
        MenuController spyController = Mockito.spy(menuController);
        when(spyController.createCommand("About")).thenReturn(mockCommand);

        // Act
        spyController.actionPerformed(aboutEvent);

        // Assert
        verify(mockCommandHistory).execute(mockCommand);
    }

    @Test
    void actionPerformed_unknownCommand_doesNothing() {
        // Arrange
        ActionEvent unknownEvent = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Unknown");
        MenuController spyController = Mockito.spy(menuController);

        // Act
        spyController.actionPerformed(unknownEvent);

        // Assert
        verify(mockCommandHistory, never()).execute(any());
    }

    @Test
    void createCommand_openAction_returnsOpenPresentationCommand() {
        // Act
        MenuCommand command = menuController.createCommand("Open");

        // Assert
        assertTrue(command instanceof OpenPresentationCommand);
    }

    @Test
    void createCommand_newAction_returnsNewPresentationCommand() {
        // Act
        MenuCommand command = menuController.createCommand("New");

        // Assert
        assertTrue(command instanceof NewPresentationCommand);
    }

    @Test
    void createCommand_saveAction_returnsSavePresentationCommand() {
        // Act
        MenuCommand command = menuController.createCommand("Save");

        // Assert
        assertTrue(command instanceof SavePresentationCommand);
    }

    @Test
    void createCommand_exitAction_returnsExitApplicationCommand() {
        // Act
        MenuCommand command = menuController.createCommand("Exit");

        // Assert
        assertTrue(command instanceof ExitApplicationCommand);
    }

    @Test
    void createCommand_nextAction_returnsNextSlideCommand() {
        // Act
        MenuCommand command = menuController.createCommand("Next");

        // Assert
        assertTrue(command instanceof NextSlideCommand);
    }

    @Test
    void createCommand_prevAction_returnsPreviousSlideCommand() {
        // Act
        MenuCommand command = menuController.createCommand("Prev");

        // Assert
        assertTrue(command instanceof PreviousSlideCommand);
    }

    @Test
    void createCommand_gotoAction_returnsGotoSlideCommand() {
        // Act
        MenuCommand command = menuController.createCommand("Go to");

        // Assert
        assertTrue(command instanceof GotoSlideCommand);
    }

    @Test
    void createCommand_aboutAction_returnsAboutCommand() {
        // Act
        MenuCommand command = menuController.createCommand("About");

        // Assert
        assertTrue(command instanceof AboutCommand);
    }

    @Test
    void createCommand_unknownAction_returnsNull() {
        // Act
        MenuCommand command = menuController.createCommand("Unknown");

        // Assert
        assertNull(command);
    }
}