package com.nhlstenden.jabberpoint.menu;

import com.nhlstenden.jabberpoint.*;
import com.nhlstenden.jabberpoint.slide.SlideViewerFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;

public class MenuController extends MenuBar implements ActionListener
{
    // Serial version UID
    @Serial
    private static final long serialVersionUID = 227L;

    // Menu labels
    protected static final String ABOUT = "About";
    protected static final String EXIT = "Exit";
    protected static final String GOTO = "Go to";
    protected static final String NEW = "New";
    protected static final String NEXT = "Next";
    protected static final String OPEN = "Open";
    protected static final String PREV = "Prev";
    protected static final String SAVE = "Save";

    // Class fields
    private final SlideViewerFrame frame;
    private final Presentation presentation;

    /**
     * Constructor
     */
    public MenuController(SlideViewerFrame frame, Presentation presentation)
    {
        this.frame = frame;
        this.presentation = presentation;
    }

    /**
     * Factory method to create appropriate command based on action
     */
    public MenuCommand createCommand(String action)
    {
        return switch (action)
        {
            case OPEN -> new OpenPresentationCommand(presentation, frame);
            case NEW -> new NewPresentationCommand(presentation, frame);
            case SAVE -> new SavePresentationCommand(presentation, frame);
            case EXIT -> new ExitApplicationCommand();
            case NEXT -> new NextSlideCommand(presentation);
            case PREV -> new PreviousSlideCommand(presentation);
            case GOTO -> new GotoSlideCommand(presentation, frame);
            case ABOUT -> new AboutCommand(frame);
            default -> null;
        };
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        MenuCommand command = createCommand(e.getActionCommand());
        if (command != null)
        {
            command.execute();
        }
    }
}