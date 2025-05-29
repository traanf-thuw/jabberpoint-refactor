package com.nhlstenden.jabberpoint;

public interface Content
{
    String getTitle();
    void setTitle(String title);
    void clear(); // Reset the content to empty

    // Accept method for Visitor pattern
    void accept(ContentVisitor visitor);
}
