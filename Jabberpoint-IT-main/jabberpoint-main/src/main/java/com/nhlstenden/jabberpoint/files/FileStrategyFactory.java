package com.nhlstenden.jabberpoint.files;

import com.nhlstenden.jabberpoint.files.loading.LoadStrategy;
import com.nhlstenden.jabberpoint.files.loading.XMLLoadStrategy;
import com.nhlstenden.jabberpoint.files.saving.SaveStrategy;
import com.nhlstenden.jabberpoint.files.saving.XMLSaveStrategy;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>File strategy factory to create the loader and saver.<p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.7 2025/04/02 Thu Tran - Bocheng Peng
 */
public class FileStrategyFactory
{
    private static final Map<String, LoadStrategy> loadStrategyMap = new HashMap<>();
    private static final Map<String, SaveStrategy> saveStrategyMap = new HashMap<>();

    static
    {
        loadStrategyMap.put("xml", new XMLLoadStrategy());
        saveStrategyMap.put("xml", new XMLSaveStrategy());
    }

    public static LoadStrategy createLoader(String fileExtension)
    {
        LoadStrategy strategy = loadStrategyMap.get(fileExtension);
        if (strategy == null)
        {
            throw new IllegalArgumentException("Unsupported format: " + fileExtension);
        }

        return strategy;
    }

    public static SaveStrategy createSaver(String fileExtension)
    {
        SaveStrategy strategy = saveStrategyMap.get(fileExtension);
        if (strategy == null)
        {
            throw new IllegalArgumentException("Unsupported format: " + fileExtension);
        }

        return strategy;
    }
}
