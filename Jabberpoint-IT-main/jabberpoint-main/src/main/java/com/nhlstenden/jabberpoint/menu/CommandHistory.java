package com.nhlstenden.jabberpoint.menu;

import java.util.Stack;

public class CommandHistory
{
    private Stack<MenuCommand> undoStack = new Stack<>();
    private Stack<MenuCommand> redoStack = new Stack<>();

    public Stack<MenuCommand> getUndoStack()
    {
        return this.undoStack;
    }

    public void setUndoStack(Stack<MenuCommand> undoStack)
    {
        this.undoStack = undoStack;
    }

    public Stack<MenuCommand> getRedoStack()
    {
        return this.redoStack;
    }

    public void setRedoStack(Stack<MenuCommand> redoStack)
    {
        this.redoStack = redoStack;
    }

    public void execute(MenuCommand command)
    {
        command.execute();
        undoStack.push(command);
        redoStack.clear();
    }

    public void undo()
    {
        if (!undoStack.isEmpty())
        {
            MenuCommand command = undoStack.pop();
            command.execute();
            redoStack.push(command);
        }
    }

    public void redo()
    {
        if (!redoStack.isEmpty())
        {
            MenuCommand command = redoStack.pop();
            command.execute();
            undoStack.push(command);
        }
    }
}
