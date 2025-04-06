package com.nhlstenden.jabberpoint.menu;

import com.nhlstenden.jabberpoint.Presentation;
import com.nhlstenden.jabberpoint.slide.*;
import com.nhlstenden.jabberpoint.style.DefaultStyle;

import java.io.File;

class NewPresentationCommand implements MenuCommand
{
    private final Presentation presentation;
    private final SlideViewerFrame frame;
    SlideItemFactory factory;

    public NewPresentationCommand(Presentation presentation, SlideViewerFrame parentFrame)
    {
        this.presentation = presentation;
        this.frame = parentFrame;
        this.factory = SlideItemFactory.getInstance();  // Get the factory instance
    }

    @Override
    public void execute()
    {
        this.presentation.clear();

        Slide defaultSlide = new Slide(new DefaultStyle());

        // Create a default text item using the factory
        TextItem defaultTextItem = (TextItem) factory.createSlideItem(SlideItemType.TEXT, 0, "New Slide");
        defaultSlide.addSlideItem(defaultTextItem);

        ButtonItem addTextButton = new ButtonItem(1, "Add Text");
        addTextButton.setActionListener(e -> this.showTextInputDialog(defaultSlide));

        // Image content button
        ButtonItem addImageButton = new ButtonItem(1, "Add Image");
        addImageButton.setActionListener(e -> this.showImageInputDialog(defaultSlide));

        defaultSlide.addSlideItem(addTextButton);
        defaultSlide.addSlideItem(addImageButton);
        this.presentation.append(defaultSlide);
        this.presentation.setSlideNumber(0);
    }

    void showTextInputDialog(Slide targetSlide)
    {
        TextInputDialog dialog = new TextInputDialog(this.frame);
        dialog.setVisible(true);

        String inputText = dialog.getInputText();
        if (inputText != null && !inputText.trim().isEmpty())
        {
            // Use the factory to create the new TextItem
            TextItem newTextItem = (TextItem) this.factory.createSlideItem(SlideItemType.TEXT, 3, inputText.trim());
            targetSlide.addSlideItem(newTextItem);
            this.presentation.refreshView();
        }
    }

    void showImageInputDialog(Slide targetSlide)
    {
        ImageInputDialog dialog = new ImageInputDialog(this.frame);
        dialog.setVisible(true);

        File imageFile = dialog.getSelectedFile();
        if (imageFile != null)
        {
            // Use the factory to create the BitmapItem
            BitmapItem imageItem = (BitmapItem) this.factory.createSlideItem(SlideItemType.BITMAP, 4, imageFile.getAbsolutePath());
            targetSlide.addSlideItem(imageItem);
            this.presentation.refreshView();
        }
    }
}
