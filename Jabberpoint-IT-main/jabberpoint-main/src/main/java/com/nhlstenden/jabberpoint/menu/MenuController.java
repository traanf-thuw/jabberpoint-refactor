package com.nhlstenden.jabberpoint.menu;//package com.nhlstenden.jabberpoint;

import com.nhlstenden.jabberpoint.*;
import com.nhlstenden.jabberpoint.slide.SlideViewerFrame;

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
    public MenuController(SlideViewerFrame frame, Presentation presentation)
    {
        this.frame = frame;
        this.presentation = presentation;
        this.commandHistory = new CommandHistory();
    }

    /**
     * Constructor with command history
     */
    public MenuController(SlideViewerFrame frame, Presentation presentation, CommandHistory commandHistory)
    {
        this.frame = frame;
        this.presentation = presentation;
        this.commandHistory = commandHistory;
    }

    /**
     * Creates the menubar with menus and menu items
     */
//	public JMenuBar getMenuBar() {
//		JMenuBar menuBar = new JMenuBar();
//
//		JMenu fileMenu = mkMenu(FILE);
//		fileMenu.add(mkMenuItem(OPEN));
//		fileMenu.add(mkMenuItem(NEW));
//		fileMenu.add(mkMenuItem(SAVE));
//		fileMenu.add(mkMenuItem(EXIT));
//		menuBar.add(fileMenu);
//
//		JMenu viewMenu = mkMenu(VIEW);
//		viewMenu.add(mkMenuItem(NEXT));
//		viewMenu.add(mkMenuItem(PREV));
//		viewMenu.add(mkMenuItem(GOTO));
//		menuBar.add(viewMenu);
//
//		JMenu helpMenu = mkMenu(HELP);
//		helpMenu.add(mkMenuItem(ABOUT));
//		menuBar.add(helpMenu);
//
//		return menuBar;
//	}

//	/**
//	 * Creates a menu
//	 */
//	public JMenu mkMenu(String name) {
//		JMenu menu = new JMenu(name);
//		return menu;
//	}
//
//	/**
//	 * Creates a menu item and adds action listener
//	 */
//	public JMenuItem mkMenuItem(String name) {
//		JMenuItem menuItem = new JMenuItem(name);
//		menuItem.addActionListener(this);
//		return menuItem;
//	}

//	/**
//	 * Creates open menu item with a specific menu
//	 */
//	public JMenuItem mkOpenMenuItem(JMenu fileMenu) {
//		JMenuItem menuItem = new JMenuItem(OPEN);
//		menuItem.addActionListener(this);
//		fileMenu.add(menuItem);
//		return menuItem;
//	}
//
//	/**
//	 * Creates save menu item with a specific menu
//	 */
//	public JMenuItem mkSaveMenuItem(JMenu fileMenu) {
//		JMenuItem menuItem = new JMenuItem(SAVE);
//		menuItem.addActionListener(this);
//		fileMenu.add(menuItem);
//		return menuItem;
//	}

//	public JMenuItem mkGotoMenuItem(JMenu fileMenu) {
//		JMenuItem menuItem = new JMenuItem(GOTO);
//		menuItem.addActionListener(this);
//		fileMenu.add(menuItem);
//		return menuItem;
//	}

    /**
     * Creates save menu item with a specific menu
     */
//	public JMenuItem mkAboutMenuItem(JMenu fileMenu) {
//		JMenuItem menuItem = new JMenuItem(ABOUT);
//		menuItem.addActionListener(this);
//		fileMenu.add(menuItem);
//		return menuItem;
//	}

    /**
     * Factory method to create appropriate command based on action
     */
    public MenuCommand createCommand(String action)
    {
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
        if (command != null)
        {
            commandHistory.execute(command);
        }
    }
}