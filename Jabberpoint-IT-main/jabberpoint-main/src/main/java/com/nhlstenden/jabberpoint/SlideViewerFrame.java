package com.nhlstenden.jabberpoint;

import com.nhlstenden.jabberpoint.menu.MenuController;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import javax.swing.JFrame;

/**
 * <p>The applicatiewindow for a slideviewcomponent</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman, Rick Vinke
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 * @version 1.7 2023/01/14 Rick Vinke
 */
public class SlideViewerFrame extends JFrame {
	private static final long serialVersionUID = 3227L;

	public final static int WIDTH = 1200;
	public final static int HEIGHT = 800;
	
	public SlideViewerFrame(String title, Presentation presentation) {
		super(title);
		SlideViewerComponent slideViewerComponent = new SlideViewerComponent(presentation, this);
		presentation.setShowView(slideViewerComponent);
		setupWindow(slideViewerComponent, presentation);
	}

	/**
	 * Sets up the GUI.
	 *
	 * @param slideViewerComponent The view component.
	 * @param presentation The current presentation
	 */
//	public void setupWindow(SlideViewerComponent slideViewerComponent, Presentation presentation) {
//		setTitle(JabberPoint.JAB_NAME + " " + JabberPoint.JAB_VERSION);
//		addWindowListener(new WindowAdapter() {
//				@Override
//				public void windowClosing(WindowEvent e) {
//					System.exit(0);
//				}
//			});
//		getContentPane().add(slideViewerComponent);
//		addKeyListener(new KeyController(presentation)); //Add a controller
//		setMenuBar(new MenuController(this, presentation));	//Add another controller
//		setSize(new Dimension(WIDTH, HEIGHT)); //Same sizes a slide has
//		setVisible(true);
//	}

	public void setupWindow(SlideViewerComponent slideViewerComponent, Presentation presentation) {
		setTitle(JabberPoint.JAB_NAME + " " + JabberPoint.JAB_VERSION);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		getContentPane().add(slideViewerComponent);
		addKeyListener(new KeyController(presentation)); // Add a controller

		// ✅ Create a proper MenuBar
		MenuBar menuBar = createMenuBar(presentation);
		setMenuBar(menuBar);

		setSize(new Dimension(WIDTH, HEIGHT)); // Same sizes a slide has
		setVisible(true);
	}

	// ✅ Properly create and set up the MenuBar
	private MenuBar createMenuBar(Presentation presentation) {
		MenuBar menuBar = new MenuBar();
		MenuController menuController = new MenuController(this, presentation);

		Menu fileMenu = new Menu("File");
		MenuItem openItem = new MenuItem("Open");
		MenuItem saveItem = new MenuItem("Save");
		MenuItem aboutItem = new MenuItem("About");
		MenuItem gotoItem = new MenuItem("Go to");
		MenuItem exitItem = new MenuItem("Exit");

		openItem.addActionListener(menuController);
		saveItem.addActionListener(menuController);
		aboutItem.addActionListener(menuController);
//		gotoItem.setActionCommand("GOTO");
		gotoItem.addActionListener(menuController);
		exitItem.addActionListener(e -> System.exit(0));

		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.add(aboutItem);
		fileMenu.add(gotoItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);

		menuBar.add(fileMenu);
		return menuBar;
	}


	/**
	 * Exists the program.
	 *
	 * @param exitCode The exit code.
	 */
	public void exit(int exitCode) {
		System.exit(exitCode);
	}
}
