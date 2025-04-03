package com.nhlstenden.jabberpoint.files.saving;

import com.nhlstenden.jabberpoint.Presentation;

import java.io.File;
import java.io.IOException;

public interface SaveStrategy
{
    void savePresentation(Presentation presentation, File file) throws IOException;
}
