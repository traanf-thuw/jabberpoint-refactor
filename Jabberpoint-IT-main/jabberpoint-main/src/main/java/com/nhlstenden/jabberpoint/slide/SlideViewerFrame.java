package com.nhlstenden.jabberpoint.slide;

import com.nhlstenden.jabberpoint.JabberPoint;
import com.nhlstenden.jabberpoint.KeyController;
import com.nhlstenden.jabberpoint.Presentation;
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
public class SlideViewerFrame extends JFrame
{
    @Serial
	private static final long serialVersionUID = 3227L;

    public final static int WIDTH = 1200;
    public final static int HEIGHT = 800;
    private static final String FILE = "File";
    private static final String HELP = "Help";
    private static final String VIEW = "View";

    public SlideViewerFrame(String title, Presentation presentation)
    {
        super(title);
        SlideViewerComponent slideViewerComponent = new SlideViewerComponent(presentation, this);
        presentation.setSlideViewComponent(slideViewerComponent);
        this.setupWindow(slideViewerComponent, presentation);
    }

    /**
     * Sets up the GUI.
     *
     * @param slideViewerComponent The view component.
     * @param presentation         The current presentation
     */
    public void setupWindow(SlideViewerComponent slideViewerComponent, Presentation presentation)
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

        getContentPane().add(slideViewerComponent);
        addKeyListener(new KeyController(presentation)); // Add a controller

        // Create a proper MenuBar
        MenuBar menuBar = createMenuBar(presentation);
        setMenuBar(menuBar);

        setSize(new Dimension(WIDTH, HEIGHT)); // Same sizes a slide has
        setVisible(true);
    }

    // Properly create and set up the MenuBar
    public MenuBar createMenuBar(Presentation presentation)
    {
        MenuBar menuBar = new MenuBar();
        MenuController menuController = new MenuController(this, presentation);

        Menu fileMenu = new Menu(FILE);
        MenuItem openItem = new MenuItem("Open");
        MenuItem saveItem = new MenuItem("Save");
        MenuItem newItem = new MenuItem("New");
        MenuItem exitItem = new MenuItem("Exit");

        Menu viewMenu = new Menu(VIEW);
        MenuItem gotoItem = new MenuItem("Go to");
        MenuItem previtem = new MenuItem("Prev");
        MenuItem nextItem = new MenuItem("Next");

		Menu helpMenu = new Menu(HELP);
		MenuItem aboutItem = new MenuItem("About");

        openItem.addActionListener(menuController);
        saveItem.addActionListener(menuController);
        aboutItem.addActionListener(menuController);
        gotoItem.addActionListener(menuController);
        previtem.addActionListener(menuController);
        nextItem.addActionListener(menuController);
        newItem.addActionListener(menuController);
        exitItem.addActionListener(e -> System.exit(0));

        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(newItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        viewMenu.add(gotoItem);
        viewMenu.add(previtem);
        viewMenu.add(nextItem);

		helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(viewMenu);
		menuBar.add(helpMenu);

        return menuBar;
    }
}
