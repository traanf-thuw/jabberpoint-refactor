package com.nhlstenden.jabberpoint;

public interface ContentVisitor
{
    void visitPresentation(Presentation presentation);

    // A visitor pattern to allow operations to be performed on different content types without instanceof for example document or spreadsheet
//    T visitTextDocument(TextDocument textDocument);
//    T visitSpreadsheet(Spreadsheet spreadsheet);
}