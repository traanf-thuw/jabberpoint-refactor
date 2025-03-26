package com.nhlstenden.jabberpoint.menu;

import com.nhlstenden.jabberpoint.menu.MenuCommand;

import java.util.Stack;

public class CommandHistory {
    private Stack<MenuCommand> undoStack = new Stack<>();
    private Stack<MenuCommand> redoStack = new Stack<>();

    

    public void execute(MenuCommand command) {
        command.execute();
        undoStack.push(command);
        redoStack.clear();
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            MenuCommand command = undoStack.pop();
            command.execute();
            redoStack.push(command);
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            MenuCommand command = redoStack.pop();
            command.execute();
            undoStack.push(command);
        }
    }
}
