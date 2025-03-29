package com.nhlstenden.jabberpoint.menu;//package com.nhlstenden.jabberpoint;

import com.nhlstenden.jabberpoint.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuController extends MenuBar implements ActionListener
{
	// Serial version UID
	private static final long serialVersionUID = 227L;

	// Menu labels
	private static final String ABOUT = "About";
	private static final String FILE = "File";
	private static final String EXIT = "Exit";
	private static final String GOTO = "Go to";
	private static final String HELP = "Help";
	private static final String NEW = "New";
	private static final String NEXT = "Next";
	private static final String OPEN = "Open";
	private static final String PREV = "Prev";
	private static final String SAVE = "Save";
	private static final String VIEW = "View";

	// Class fields
	private final SlideViewerFrame frame;
	private final Presentation presentation;
	private final CommandHistory commandHistory;

	/**
	 * Constructor
	 */
	public MenuController(SlideViewerFrame frame, Presentation presentation) {
		this.frame = frame;
		this.presentation = presentation;
		this.commandHistory = new CommandHistory();
	}

	public SlideViewerFrame getFrame()
	{
		return this.frame;
	}

	public Presentation getPresentation()
	{
		return this.presentation;
	}

	public CommandHistory getCommandHistory()
	{
		return this.commandHistory;
	}

	/**
	 * Constructor with command history
	 */
	public MenuController(SlideViewerFrame frame, Presentation presentation, CommandHistory commandHistory) {
		this.frame = frame;
		this.presentation = presentation;
		this.commandHistory = commandHistory;
	}

	/**
	 * Factory method to create appropriate command based on action
	 */
	public MenuCommand createCommand(String action) {
        return switch (action)
        {
            case OPEN -> new OpenPresentationCommand(presentation, frame);
            case NEW -> new NewPresentationCommand(presentation);
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
		if (command != null) {
			commandHistory.execute(command);
		}
	}
}