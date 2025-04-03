package com.nhlstenden.jabberpoint.files;

import com.nhlstenden.jabberpoint.files.loading.LoadStrategy;
import com.nhlstenden.jabberpoint.files.loading.XMLLoadStrategy;
import com.nhlstenden.jabberpoint.files.saving.SaveStrategy;
import com.nhlstenden.jabberpoint.files.saving.XMLSaveStrategy;

public class FileStrategyFactory
{
    public static LoadStrategy createLoader(String fileExtension)
    {
        return switch (fileExtension)
        {
            case "xml" -> new XMLLoadStrategy();
            default -> throw new IllegalArgumentException("Unsupported format: " + fileExtension);
        };
    }

    public static SaveStrategy createSaver(String fileExtension)
    {
        return switch (fileExtension)
        {
            case "xml" -> new XMLSaveStrategy();
            default -> throw new IllegalArgumentException("Unsupported format: " + fileExtension);
        };
    }
}
