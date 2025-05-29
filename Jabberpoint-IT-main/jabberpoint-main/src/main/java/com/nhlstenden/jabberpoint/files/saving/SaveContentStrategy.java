package com.nhlstenden.jabberpoint.files.saving;

import com.nhlstenden.jabberpoint.Content;

import java.io.File;
import java.io.IOException;

/**
 * <p>The Strategy interface. It declares a method the context uses to execute a strategy.<p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.7 2025/04/02 Thu Tran - Bocheng Peng
 */
public interface SaveContentStrategy<T extends Content>
{
    void saveContent(T content, File file) throws IOException;
}
