package com.nhlstenden.jabberpoint.files.loading;

import com.nhlstenden.jabberpoint.Presentation;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public interface LoadStrategy
{
    void loadPresentation(Presentation presentation, File file) throws IOException, ParserConfigurationException, SAXException;
}
