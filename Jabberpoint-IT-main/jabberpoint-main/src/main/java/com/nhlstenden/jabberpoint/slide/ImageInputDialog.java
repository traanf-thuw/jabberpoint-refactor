package com.nhlstenden.jabberpoint.slide;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Dialog to browse and add image to the slide
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.7 2025/04/02 Thu Tran - Bocheng Peng
 */
public class ImageInputDialog extends JDialog
{
    File selectedFile;
    JLabel imagePreview;

    public ImageInputDialog(Frame parent)
    {
        super(parent, "Insert Image", true);
        this.setupUI();
        pack();
        setLocationRelativeTo(parent);
    }

    private void setupUI()
    {
        JPanel contentPane = new JPanel(new BorderLayout(10, 10));
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Preview area
        this.imagePreview = new JLabel("No image selected");
        this.imagePreview.setPreferredSize(new Dimension(400, 300));
        this.imagePreview.setHorizontalAlignment(SwingConstants.CENTER);
        JScrollPane scrollPane = new JScrollPane(this.imagePreview);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        // Control panel
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton browseButton = new JButton("Browse");
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");

        browseButton.addActionListener(e -> browseImage());
        okButton.addActionListener(e -> dispose());
        cancelButton.addActionListener(e ->
        {
            this.selectedFile = null;
            dispose();
        });

        controlPanel.add(browseButton);
        controlPanel.add(okButton);
        controlPanel.add(cancelButton);
        contentPane.add(controlPanel, BorderLayout.SOUTH);

        setContentPane(contentPane);
        getRootPane().setDefaultButton(okButton);
    }

    void browseImage()
    {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter(
                "Image Files", "jpg", "jpeg", "png", "gif", "bmp"
        ));

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
        {
            this.selectedFile = fileChooser.getSelectedFile();
            try
            {
                BufferedImage image = ImageIO.read(this.selectedFile);
                if (image != null)
                {
                    ImageIcon icon = new ImageIcon(image.getScaledInstance(
                            400, 300, Image.SCALE_SMOOTH));
                    this.imagePreview.setIcon(icon);
                    this.imagePreview.setText("");
                }
            } catch (Exception ex)
            {
                JOptionPane.showMessageDialog(this,
                        "Error loading image: " + ex.getMessage(),
                        "Load Error", JOptionPane.ERROR_MESSAGE);
                this.selectedFile = null;
            }
        }
    }

    public File getSelectedFile()
    {
        return this.selectedFile;
    }
}