package com.nhlstenden.jabberpoint.files;

import com.nhlstenden.jabberpoint.Content;
import com.nhlstenden.jabberpoint.files.loading.LoadContentStrategy;
import com.nhlstenden.jabberpoint.files.saving.SaveContentStrategy;

import javax.swing.*;
import java.io.File;

/**
 * <p>File handler to load and save presentation.<p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.7 2025/04/02 Thu Tran - Bocheng Peng
 */
public class FileHandler<T extends Content>
{
    public void loadFile(T content, String fileName)
    {
        try
        {
            File file = new File(fileName);
            if (!file.exists())
            {
                showError("File Not Found", "The file " + fileName + " does not exist.");
                return;
            }

            String extension = extractExtension(fileName);
            LoadContentStrategy<T> loader = FileStrategyFactory.createLoader(extension);
            loader.loadContent(content, file);
        }
        catch (IllegalArgumentException e)
        {
            showError("Unsupported Format", "File format not supported: " + e.getMessage());
        }
        catch (Exception e)
        {
            showError("Load Error", "Failed to load file: " + e.getMessage());
        }
    }

    public void saveFile(T content, String fileName)
    {
        try
        {
            File file = new File(fileName);
            if (!file.exists() && !file.createNewFile())
            {
                showError("IO Error", "Failed to create file: " + fileName);
                return;
            }

            String extension = extractExtension(fileName);
            SaveContentStrategy<T> saver = FileStrategyFactory.createSaver(extension);
            saver.saveContent(content, file);

        }
        catch (IllegalArgumentException e)
        {
            showError("Unsupported Format", "File format not supported: " + e.getMessage());
        }
        catch (Exception e)
        {
            showError("Save Error", "Failed to save file: " + e.getMessage());
        }
    }

    private String extractExtension(String fileName)
    {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex == -1) throw new IllegalArgumentException("No file extension");
        return fileName.substring(dotIndex + 1).toLowerCase();
    }

    private void showError(String title, String message)
    {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }
}