package com.nhlstenden.jabberpoint.files.loading;

import com.nhlstenden.jabberpoint.Content;
import com.nhlstenden.jabberpoint.ContentVisitor;

import java.io.File;

/**
 * <p>The loader for .XML files.<p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.7 2025/04/02 Thu Tran - Bocheng Peng
 */
public class XMLLoadStrategy<T extends Content> implements LoadContentStrategy<T>
{
    @Override
    public void loadContent(T content, File file) throws Exception
    {
        ContentVisitor visitor = new XMLLoadVisitor(file);
        content.accept(visitor);
    }
}

