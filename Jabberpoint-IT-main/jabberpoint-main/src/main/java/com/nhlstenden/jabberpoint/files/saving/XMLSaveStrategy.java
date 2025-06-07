package com.nhlstenden.jabberpoint.files.saving;

import com.nhlstenden.jabberpoint.*;
import java.io.File;

/**
 * <p>The saver for .XML files.<p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.7 2025/04/02 Thu Tran - Bocheng Peng
 */

public class XMLSaveStrategy<T extends Content> implements SaveContentStrategy<T>
{
    @Override
    public void saveContent(T content, File file) throws Exception
    {
        file = this.ensureFileExtension(file);
        ContentVisitor visitor = new XMLSaveVisitor(file);
        content.accept(visitor);
    }

    private File ensureFileExtension(File file)
    {
        if (!file.getName().endsWith(".xml"))
        {
            return new File(file.getParent(), file.getName() + ".xml");
        }

        return file;
    }
}