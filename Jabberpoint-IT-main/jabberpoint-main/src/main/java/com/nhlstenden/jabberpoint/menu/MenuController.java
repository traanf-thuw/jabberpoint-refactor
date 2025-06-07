package com.nhlstenden.jabberpoint.menu;

import com.nhlstenden.jabberpoint.Content;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * <p>Invoker of command pattern, creates different buttons command<p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.7 2025/04/02 Thu Tran - Bocheng Peng
 */
public class MenuController<T extends Content> extends MenuBar implements ActionListener
{
    private final CommandContext<T> context;
    private static final String OPEN = "Open";
    private static final String NEW = "New";
    private static final String SAVE = "Save";
    private static final String EXIT = "Exit";
    private static final String NEXT = "Next";
    private static final String PREV = "Prev";
    private static final String GOTO = "Go to";
    private static final String ABOUT = "About";

    public MenuController(CommandContext<T> context)
    {
        this.context = context;
    }

    public MenuCommand<T> createCommand(String action)
    {
        return switch (action)
        {
            case OPEN -> new OpenContentCommand<T>();
            case NEW -> new NewContentCommand<T>();
            case SAVE -> new SaveContentCommand<T>();
            case EXIT -> new ExitApplicationCommand<T>();
            case NEXT -> new NextCommand<T>();
            case PREV -> new PreviousCommand<T>();
            case GOTO -> new GotoCommand<T>();
            case ABOUT -> new AboutCommand<T>();
            default -> null;
        };
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        MenuCommand<T> command = createCommand(e.getActionCommand());
        if (command != null)
        {
            command.execute(context);
        }
    }
}
