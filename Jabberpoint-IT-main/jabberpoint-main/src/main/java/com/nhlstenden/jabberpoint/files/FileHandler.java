package com.nhlstenden.jabberpoint.files;

import com.nhlstenden.jabberpoint.Presentation;
import com.nhlstenden.jabberpoint.files.loading.LoadStrategy;
import com.nhlstenden.jabberpoint.files.saving.SaveStrategy;

import javax.swing.*;
import java.io.File;

/**
 * <p>File handler to load and save presentation.<p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.7 2025/04/02 Thu Tran - Bocheng Peng
 */
public class FileHandler
{
    public void loadFile(Presentation presentation, String fileName)
    {
        try
        {
            File file = new File(fileName);
            if (!file.exists())
            {
                showError("File Not Found", "The file " + fileName + " does not exist.");
                return;
            }

            String fileExtension = extractExtension(fileName);
            LoadStrategy loader = FileStrategyFactory.createLoader(fileExtension);
            loader.loadPresentation(presentation, file);

        } catch (IllegalArgumentException e)
        {
            showError("Unsupported Format", "File format not supported: " + e.getMessage());
        } catch (Exception e)
        {
            showError("Load Error", "Failed to load file: " + e.getMessage());
        }
    }

    public void saveFile(Presentation presentation, String fileName)
    {
        try
        {
            File file = new File(fileName);
            if (!file.exists() && !file.createNewFile())
            {
                showError("IO Error", "Failed to create file: " + fileName);
                return;
            }

            String fileExtension = extractExtension(fileName);
            SaveStrategy saveStrategy = FileStrategyFactory.createSaver(fileExtension);
            saveStrategy.savePresentation(presentation, file);

        } catch (IllegalArgumentException e)
        {
            showError("Unsupported Format", "File format not supported: " + e.getMessage());
        } catch (Exception e)
        {
            showError("Save Error", "Failed to save file: " + e.getMessage());
        }
    }

    // Helper methods
    public String extractExtension(String fileName)
    {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex == -1) throw new IllegalArgumentException("No file extension");
        return fileName.substring(dotIndex + 1).toLowerCase();
    }

    public void showError(String title, String message)
    {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }
}
