package com.nhlstenden.jabberpoint;

import com.nhlstenden.jabberpoint.style.LevelStyle;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.image.ImageObserver;
import java.util.Collections;
import java.util.List;

/**
 * Text item that can be used as a leaf in the Composite pattern
 * @author [Your Name]
 * @version 1.8 2025/03/26
 */
public class TextItem extends AbstractSlideItem {
	private String text;

	// Default empty constructor
	public TextItem() {
		this(0, "No Text Given");
	}

	// Constructor with level and text
	public TextItem(int level, String text) {
		super(level);
		this.text = text != null ? text : "No Text Given";
	}

	// Constructor with style (for compatibility)
	public TextItem(LevelStyle levelStyle) {
		this(0, "No Text Given");
	}

	// Getter for text
	public String getText() {
		return text;
	}

	// Setter for text
	public void setText(String text) {
		this.text = text != null ? text : "No Text Given";
	}

	@Override
	public void draw(Graphics graphics, Rectangle area, ImageObserver observer) {
		if (text == null || text.isEmpty()) {
			return;
		}

		LevelStyle myStyle = getStyle();

		// Set up font
		graphics.setFont(myStyle.getFont());
		graphics.setColor(myStyle.getColor());

		// Calculate text positioning
		FontMetrics metrics = graphics.getFontMetrics();
		int textWidth = metrics.stringWidth(text);
		int textHeight = metrics.getHeight();

		// Calculate x position with indent
		int x = area.x + (int)(myStyle.getIndent() * area.getWidth() / 100);

		// Vertically center text in the area
		int y = area.y + area.height / 2 + textHeight / 2;

		// Draw the text
		graphics.drawString(text, x, y);
	}

	@Override
	public Rectangle getBoundingBox(Graphics graphics, ImageObserver observer, float scale) {
		if (text == null || text.isEmpty()) {
			return new Rectangle(0, 0, 0, 0);
		}

		LevelStyle myStyle = getStyle();

		// Set up font for measurement
		Font font = new Font(
				myStyle.getFontName(),
				myStyle.getFontStyle(),
				(int)(myStyle.getFontSize() * scale)
		);
		graphics.setFont(font);

		// Calculate text dimensions
		FontMetrics metrics = graphics.getFontMetrics();
		int textWidth = metrics.stringWidth(text);
		int textHeight = metrics.getHeight();

		return new Rectangle(
				(int)(myStyle.getIndent() * scale),
				0,
				(int)(textWidth * scale),
				(int)((myStyle.getLeading() + textHeight) * scale)
		);
	}

	@Override
	public List<AbstractSlideItem> getChildren() {
		// Leaf items have no children
		return Collections.emptyList();
	}

	@Override
	public void addChild(AbstractSlideItem child) {
		// Leaf items cannot have children
		throw new UnsupportedOperationException("TextItem cannot have children");
	}

	@Override
	public void removeChild(AbstractSlideItem child) {
		// Leaf items cannot have children
		throw new UnsupportedOperationException("TextItem cannot have children");
	}

	@Override
	public String toString() {
		return "TextItem[" + getLevel() + "," + text + "]";
	}
}