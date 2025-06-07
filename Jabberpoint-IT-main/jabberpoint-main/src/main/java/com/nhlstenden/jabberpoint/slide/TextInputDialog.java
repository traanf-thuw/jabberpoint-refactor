package com.nhlstenden.jabberpoint.slide;

import javax.swing.*;
import java.awt.*;

/**
 * Dialog to add text to the slide
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.7 2025/04/02 Thu Tran - Bocheng Peng
 */
public class TextInputDialog extends JDialog
{
    private JTextArea textArea;
    private boolean confirmed = false;

    public TextInputDialog(Frame parent)
    {
        super(parent, "Add Text Content", true);
        this.setupUI();
        pack();
        setLocationRelativeTo(parent);
    }

    private void setupUI()
    {
        JPanel contentPane = new JPanel(new BorderLayout(10, 10));
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Text input area
        this.textArea = new JTextArea(5, 30);
        JScrollPane scrollPane = new JScrollPane(this.textArea);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");

        okButton.addActionListener(e ->
        {
            this.confirmed = true;
            dispose();
        });

        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(contentPane);
        getRootPane().setDefaultButton(okButton);
    }

    public String getInputText()
    {
        return this.confirmed ? this.textArea.getText() : null;
    }
}