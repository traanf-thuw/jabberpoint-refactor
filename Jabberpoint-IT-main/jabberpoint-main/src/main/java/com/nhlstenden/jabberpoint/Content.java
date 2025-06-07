package com.nhlstenden.jabberpoint;

/**
 * <p>The base interface for all content types such as {@code Presentation}, {@code Document}, etc.</p>
 * <p>This interface defines the common operations for content navigation, manipulation, and integration
 * with the Visitor design pattern.</p>
 *
 * <p>Core responsibilities include:
 * <ul>
 *   <li>Managing the title and current item index (e.g., slide or page)</li>
 *   <li>Clearing or resetting the content state</li>
 *   <li>Navigation methods: {@code next()} and {@code previous()}</li>
 *   <li>Accepting visitors through the {@code accept()} method</li>
 * </ul></p>
 *
 * <p>The term "show list" refers to the content's primary sequence, such as slides in a presentation
 * or pages in a document.</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.7 2025/04/02 Thu Tran - Bocheng Peng
 */
public interface Content
{
    String getTitle();
    int getShowListSize();
    void setShowListNumber(int number); // For presentation, showlist is the slides, for document it's the page, etc
    void setTitle(String title);
    void clear(); // Reset the content to empty
    void next();
    void previous();

    // Accept method for Visitor pattern
    void accept(ContentVisitor visitor);
}