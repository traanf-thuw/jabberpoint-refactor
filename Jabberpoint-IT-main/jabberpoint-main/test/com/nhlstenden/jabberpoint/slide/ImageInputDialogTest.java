package com.nhlstenden.jabberpoint.slide;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.function.Supplier;
import javax.swing.ImageIcon;
import org.junit.jupiter.api.Test;

public class ImageInputDialogTest {
    @Test
    void testBrowseImage_withNoFileSelected() {
        ImageInputDialog dialog = new ImageInputDialog(null);

        dialog.browseImage();

        // selectedFile should remain null
        assertNull(dialog.getSelectedFile());

        // icon and text remain unchanged or default
        assertNull(dialog.imagePreview.getIcon());
        assertEquals("No image selected", dialog.imagePreview.getText());
    }
}
