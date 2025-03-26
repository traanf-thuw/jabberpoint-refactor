package com.nhlstenden.jabberpoint.files;

import com.nhlstenden.jabberpoint.Presentation;
import com.nhlstenden.jabberpoint.files.loading.FileLoader;
import com.nhlstenden.jabberpoint.files.loading.XMLLoader;
import com.nhlstenden.jabberpoint.files.saving.FileSaver;
import com.nhlstenden.jabberpoint.files.saving.XMLSaver;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/** <p>Handles saving and loading of files.<p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman, Rick Vinke
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 * @version 1.7 2023/01/14 Rick Vinke
 */
public class FileHandler {

    private final Set<FileLoader> fileLoaders = new HashSet<>();
    private final Set<FileSaver> fileSavers = new HashSet<>();

    public FileHandler(){
        loadLoaders();
        loadSavers();
    }

    public void loadFile(Presentation presentation, String fileName){
        try
        {
            File file = new File(fileName);
            if (!file.exists())
            {
                JOptionPane.showMessageDialog(null,
                        "The file " + fileName + " does not exist.", "IO Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Correct way to extract the file extension
            String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

            for (FileLoader loader : fileLoaders)
            {
                if (fileExtension.equals(loader.getExtension()))
                {
                    try
                    {
                        loader.loadPresentation(presentation, file);
                        return;
                    } catch (RuntimeException e)
                    {
                        JOptionPane.showMessageDialog(null,
                                "Error parsing " + fileExtension + " file: " + e.getMessage(),
                                "Parsing Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Unexpected error loading file: " + e.getMessage(),
                    "Load Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void saveFile(Presentation presentation, String fileName){
        File file = new File(fileName);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null,
                        e.getMessage(), "IO Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        String fileExtension = String.valueOf(Optional.of(fileName)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(fileName.lastIndexOf(".") + 1)));

        for(FileSaver saver : fileSavers){
            if(fileExtension.equals(saver.getExtension())){
                try {
                    saver.savePresentation(presentation, file);
                    return;
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "File save error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        }

        JOptionPane.showMessageDialog(null,
                "The " + fileExtension + " extension is not supported for saving.", "Unsupported file", JOptionPane.ERROR_MESSAGE);
    }

    private void loadLoaders() {
        fileLoaders.add(new XMLLoader());  // Supports XML files
    }

    private void loadSavers(){
        fileSavers.add(new XMLSaver());
    }

    public Set<FileLoader> getFileLoaders() {
        return fileLoaders;
    }

    public Set<FileSaver> getFileSavers() {
        return fileSavers;
    }
}
