package com.nhlstenden.jabberpoint.slide;

import com.nhlstenden.jabberpoint.Content;
import com.nhlstenden.jabberpoint.JabberPoint;
import com.nhlstenden.jabberpoint.KeyController;
import com.nhlstenden.jabberpoint.Presentation;
import com.nhlstenden.jabberpoint.files.FileHandler;
import com.nhlstenden.jabberpoint.menu.CommandContext;
import com.nhlstenden.jabberpoint.menu.MenuController;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.io.Serial;
import javax.swing.JFrame;

/**
 * <p>The application window for a SlideViewerComponent</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.7 2025/04/02 Thu Tran - Bocheng Peng
 */
//public class SlideViewerFrame extends JFrame
//{
//    @Serial
//    private static final long serialVersionUID = 3227L;
//
//    public static final int WIDTH = 1200;
//    public static final int HEIGHT = 800;
//    private static final String FILE = "File";
//    private static final String HELP = "Help";
//    private static final String VIEW = "View";
//
//    public SlideViewerFrame(String title, Presentation presentation)
//    {
//        super(title);
//        SlideViewerComponent slideViewerComponent = new SlideViewerComponent(presentation, this);
//        presentation.setSlideViewComponent(slideViewerComponent);
//        this.setupWindow(slideViewerComponent, presentation);
//    }
//
//    /**
//     * Sets up the GUI.
//     */
//    public void setupWindow(SlideViewerComponent slideViewerComponent, Presentation presentation)
//    {
//        setTitle(JabberPoint.JAB_NAME + " " + JabberPoint.JAB_VERSION);
//
//        addWindowListener(new WindowAdapter()
//        {
//            @Override
//            public void windowClosing(WindowEvent e)
//            {
//                System.exit(0);
//            }
//        });
//
//        getContentPane().add(slideViewerComponent);
//        addKeyListener(new KeyController(presentation));
//
//        // Create and set up MenuBar with CommandContext
//        setMenuBar(createMenuBar(presentation));
//
//        setSize(new Dimension(WIDTH, HEIGHT));
//        setVisible(true);
//    }
//
//    /**
//     * Create and configure MenuBar using CommandContext and MenuController.
//     */
//    public MenuBar createMenuBar(Presentation presentation)
//    {
//        MenuBar menuBar = new MenuBar();
//
//        FileHandler fileHandler = new FileHandler();  // Create once and reuse
//        CommandContext context = new CommandContext(presentation, this, fileHandler);
//
//        MenuController menuController = new MenuController(context);
//
//        // File menu
//        Menu fileMenu = new Menu(FILE);
//        MenuItem openItem = new MenuItem("Open");
//        MenuItem saveItem = new MenuItem("Save");
//        MenuItem newItem = new MenuItem("New");
//        MenuItem exitItem = new MenuItem("Exit");
//
//        // View menu
//        Menu viewMenu = new Menu(VIEW);
//        MenuItem gotoItem = new MenuItem("Go to");
//        MenuItem prevItem = new MenuItem("Prev");
//        MenuItem nextItem = new MenuItem("Next");
//
//        // Help menu
//        Menu helpMenu = new Menu(HELP);
//        MenuItem aboutItem = new MenuItem("About");
//
//        // Bind all menu items to the MenuController
//        openItem.addActionListener(menuController);
//        saveItem.addActionListener(menuController);
//        newItem.addActionListener(menuController);
//        exitItem.addActionListener(menuController);
//        gotoItem.addActionListener(menuController);
//        prevItem.addActionListener(menuController);
//        nextItem.addActionListener(menuController);
//        aboutItem.addActionListener(menuController);
//
//        fileMenu.add(openItem);
//        fileMenu.add(saveItem);
//        fileMenu.add(newItem);
//        fileMenu.addSeparator();
//        fileMenu.add(exitItem);
//
//        viewMenu.add(gotoItem);
//        viewMenu.add(prevItem);
//        viewMenu.add(nextItem);
//
//        helpMenu.add(aboutItem);
//
//        menuBar.add(fileMenu);
//        menuBar.add(viewMenu);
//        menuBar.add(helpMenu);
//
//        return menuBar;
//    }
//}
public class SlideViewerFrame extends JFrame
{
    @Serial
    private static final long serialVersionUID = 3227L;

    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;

    private static final String FILE = "File";
    private static final String HELP = "Help";
    private static final String VIEW = "View";

    public SlideViewerFrame(String title, Content content)
    {
        super(title);

        SlideViewerComponent viewerComponent = new SlideViewerComponent(content, this);

        // Let content accept a visitor for view-related setup
        content.accept(new ContentSetupVisitor(viewerComponent, this));

        setupWindow(viewerComponent);
    }

    public void setupWindow(SlideViewerComponent viewerComponent)
    {
        setTitle(JabberPoint.JAB_NAME + " " + JabberPoint.JAB_VERSION);

        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });

        getContentPane().add(viewerComponent);
        setSize(new Dimension(WIDTH, HEIGHT));
        setVisible(true);
    }

    public MenuBar createMenuBar(Content content)
    {
        MenuBar menuBar = new MenuBar();

        FileHandler fileHandler = new FileHandler();  // Use general content-aware handler
        CommandContext context = new CommandContext(content, this, fileHandler);

        MenuController menuController = new MenuController(context);

        Menu fileMenu = new Menu(FILE);
        Menu viewMenu = new Menu(VIEW);
        Menu helpMenu = new Menu(HELP);

        MenuItem[] fileItems = {
                new MenuItem("Open"),
                new MenuItem("Save"),
                new MenuItem("New"),
                new MenuItem("Exit")
        };

        MenuItem[] viewItems = {
                new MenuItem("Go to"),
                new MenuItem("Prev"),
                new MenuItem("Next")
        };

        MenuItem aboutItem = new MenuItem("About");

        // Add listeners
        for (MenuItem item : fileItems) item.addActionListener(menuController);
        for (MenuItem item : viewItems) item.addActionListener(menuController);
        aboutItem.addActionListener(menuController);

        // Build menus
        for (MenuItem item : fileItems) fileMenu.add(item);
        fileMenu.addSeparator();
        for (MenuItem item : viewItems) viewMenu.add(item);
        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(viewMenu);
        menuBar.add(helpMenu);

        return menuBar;
    }
}
