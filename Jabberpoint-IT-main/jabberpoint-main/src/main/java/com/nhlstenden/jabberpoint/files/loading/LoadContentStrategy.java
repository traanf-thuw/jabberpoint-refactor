package com.nhlstenden.jabberpoint.files.loading;

import com.nhlstenden.jabberpoint.Content;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * <p>The Strategy interface. It declares a method the context uses to execute a strategy.<p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.7 2025/04/02 Thu Tran - Bocheng Peng
 */
public interface LoadContentStrategy<T extends Content>
{
    void loadContent(T content, File file) throws Exception;
}