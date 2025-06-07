package com.nhlstenden.jabberpoint.slide;

import com.nhlstenden.jabberpoint.Content;
import com.nhlstenden.jabberpoint.JabberPoint;
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

    public <T extends Content> MenuBar createMenuBar(T content)
    {
        MenuBar menuBar = new MenuBar();

        FileHandler<T> fileHandler = new FileHandler<>();
        CommandContext<T> context = new CommandContext<>(content, this, fileHandler);

        MenuController<T> menuController = new MenuController<>(context);

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
