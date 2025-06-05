package com.nhlstenden.jabberpoint;

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
