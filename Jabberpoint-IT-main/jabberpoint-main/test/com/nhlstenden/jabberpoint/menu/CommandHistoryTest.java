package com.nhlstenden.jabberpoint.menu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommandHistoryTest {

    private CommandHistory commandHistory;
    private MenuCommand mockCommand1;
    private MenuCommand mockCommand2;

    @BeforeEach
    void setUp() {
        commandHistory = new CommandHistory();
        mockCommand1 = mock(MenuCommand.class);
        mockCommand2 = mock(MenuCommand.class);
    }

    @Test
    void execute_withNewCommand_addsCommandToUndoStack() {
        commandHistory.execute(mockCommand1);

        verify(mockCommand1).execute();
        assertEquals(1, commandHistory.getUndoStack().size());
        assertEquals(0, commandHistory.getRedoStack().size());
    }

    @Test
    void execute_withNewCommand_clearsRedoStack() {
        // First execute and undo to populate redo stack
        commandHistory.execute(mockCommand1);
        commandHistory.undo();

        // Now execute new command
        commandHistory.execute(mockCommand2);

        assertEquals(1, commandHistory.getUndoStack().size());
        assertEquals(0, commandHistory.getRedoStack().size());
    }

    @Test
    void undo_withEmptyStack_doesNothing() {
        assertDoesNotThrow(() -> commandHistory.undo());
        assertEquals(0, commandHistory.getUndoStack().size());
    }

    @Test
    void undo_withCommandsInStack_executesAndMovesCommandToRedoStack() {
        commandHistory.execute(mockCommand1);
        commandHistory.undo();

        verify(mockCommand1, times(2)).execute();
        assertEquals(0, commandHistory.getUndoStack().size());
        assertEquals(1, commandHistory.getRedoStack().size());
    }

    @Test
    void redo_withEmptyStack_doesNothing() {
        assertDoesNotThrow(() -> commandHistory.redo());
        assertEquals(0, commandHistory.getRedoStack().size());
    }

    @Test
    void redo_withCommandsInRedoStack_executesAndMovesCommandToUndoStack() {
        commandHistory.execute(mockCommand1);
        commandHistory.undo();
        commandHistory.redo();

        verify(mockCommand1, times(3)).execute();
        assertEquals(1, commandHistory.getUndoStack().size());
        assertEquals(0, commandHistory.getRedoStack().size());
    }

    @Test
    void executeThenUndoThenRedo_completesFullCycle() {
        commandHistory.execute(mockCommand1);
        commandHistory.undo();
        commandHistory.redo();

        verify(mockCommand1, times(3)).execute();
        assertEquals(1, commandHistory.getUndoStack().size());
        assertEquals(0, commandHistory.getRedoStack().size());
    }

    @Test
    void multipleUndosAndRedos_handlesCorrectly() {
        commandHistory.execute(mockCommand1);
        commandHistory.execute(mockCommand2);

        commandHistory.undo();
        commandHistory.undo();
        commandHistory.redo();
        commandHistory.redo();

        verify(mockCommand1, times(3)).execute();
        verify(mockCommand2, times(3)).execute();
        assertEquals(2, commandHistory.getUndoStack().size());
        assertEquals(0, commandHistory.getRedoStack().size());
    }
}