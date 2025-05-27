package com.nhlstenden.jabberpoint.menu;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * <p>Invoker of command pattern, creates different buttons command<p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.7 2025/04/02 Thu Tran - Bocheng Peng
 */
public class MenuController extends MenuBar implements ActionListener
{
    private final CommandContext context;

    public MenuController(CommandContext context)
    {
        this.context = context;
    }

    public MenuCommand createCommand(String action)
    {
        return switch (action)
        {
            case "Open" -> new OpenPresentationCommand();
            case "New" -> new NewPresentationCommand();
            case "Save" -> new SavePresentationCommand();
            case "Exit" -> new ExitApplicationCommand();
            case "Next" -> new NextSlideCommand();
            case "Prev" -> new PreviousSlideCommand();
            case "Go to" -> new GotoSlideCommand();
            case "About" -> new AboutCommand();
            default -> null;
        };
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        MenuCommand command = createCommand(e.getActionCommand());
        if (command != null)
        {
            command.execute(context);
        }
    }
}
