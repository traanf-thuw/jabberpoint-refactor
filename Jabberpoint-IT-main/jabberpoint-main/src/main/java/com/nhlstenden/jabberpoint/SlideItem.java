package com.nhlstenden.jabberpoint;

import com.nhlstenden.jabberpoint.style.LevelStyle;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.image.ImageObserver;

/** <p>The abstract class for items on a slide.<p>
 * <p>All SlideItems have drawing capabilities.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman, Rick Vinke
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 * @version 1.7 2023/01/14 Rick Vinke
 */
public abstract class SlideItem {
	private final int level; //The level of the SlideItem

	public SlideItem(int lev) {
		level = lev;
	}

	public SlideItem() {
		this(0);
	}

	public int getLevel() {
		return level;
	}

	public abstract Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale, LevelStyle style);

	/**
	 * Draws this SlideItem.
	 *
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @param scale The scale of the SlideItem.
	 * @param g The Graphics object.
	 * @param style The style for the SlideItem.
	 * @param observer The observer for loading the SlideItem.
	 */
	public abstract void draw(int x, int y, float scale, Graphics g, LevelStyle style, ImageObserver observer);
}
