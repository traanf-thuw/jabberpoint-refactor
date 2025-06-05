package com.nhlstenden.jabberpoint.files;

import com.nhlstenden.jabberpoint.Content;
import com.nhlstenden.jabberpoint.files.loading.LoadContentStrategy;
import com.nhlstenden.jabberpoint.files.loading.XMLLoadStrategy;
import com.nhlstenden.jabberpoint.files.saving.SaveContentStrategy;
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
    private static final Map<String, LoadContentStrategy<? extends Content>> loadStrategyMap = new HashMap<>();
    private static final Map<String, SaveContentStrategy<? extends Content>> saveStrategyMap = new HashMap<>();

    static
    {
        loadStrategyMap.put("xml", new XMLLoadStrategy());
        saveStrategyMap.put("xml", new XMLSaveStrategy());
    }

    @SuppressWarnings("unchecked")
    public static <T extends Content> LoadContentStrategy<T> createLoader(String extension)
    {
        LoadContentStrategy<T> strategy = (LoadContentStrategy<T>) loadStrategyMap.get(extension);
        if (strategy == null)
        {
            throw new IllegalArgumentException("Unsupported format: " + extension);
        }

        return strategy;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Content> SaveContentStrategy<T> createSaver(String extension)
    {
        SaveContentStrategy<T> strategy = (SaveContentStrategy<T>) saveStrategyMap.get(extension);
        if (strategy == null)
        {
            throw new IllegalArgumentException("Unsupported format: " + extension);
        }

        return strategy;
    }
}
