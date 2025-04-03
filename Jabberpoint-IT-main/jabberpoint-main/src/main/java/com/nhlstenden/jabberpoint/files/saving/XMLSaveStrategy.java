package com.nhlstenden.jabberpoint.files.saving;

import com.nhlstenden.jabberpoint.*;
import com.nhlstenden.jabberpoint.slide.BitmapItem;
import com.nhlstenden.jabberpoint.slide.Slide;
import com.nhlstenden.jabberpoint.slide.SlideComponent;
import com.nhlstenden.jabberpoint.slide.TextItem;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * <p>The saver for .XML files.<p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman, Rick Vinke
 * @version 1.7 2025/04/02 Thu Tran
 */
public class XMLSaveStrategy implements SaveStrategy
{

    @Override
    public void savePresentation(Presentation presentation, File file)
    {
        try
        {
            // Ensure the file has a .xml extension
            String fileName = file.getName();
            if (!fileName.endsWith(".xml"))
            {
                file = new File(file.getParent(), fileName + ".xml");
            }

            // Create XML document structure
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.newDocument();

            Element root = doc.createElement("presentation");
            doc.appendChild(root);

            // Add presentation title
            Element showTitle = doc.createElement("showtitle");
            showTitle.setTextContent(presentation.getShowTitle());
            root.appendChild(showTitle);

            // Add all slides
            for (Slide slide : presentation.getShowList())
            {
                Element xmlSlide = doc.createElement("slide");
                root.appendChild(xmlSlide);

                // Add slide title
                Element slideTitle = doc.createElement("title");
                slideTitle.setTextContent(slide.getTitle());
                xmlSlide.appendChild(slideTitle);

                // Add slide items (text/image)
                for (SlideComponent item : slide.getSlideItems())
                {
                    Element xmlItem = doc.createElement("item");
                    xmlItem.setAttribute("level", String.valueOf(item.getLevel()));
                    xmlItem.setAttribute("kind", item instanceof TextItem ? "text" : "image");
                    xmlSlide.appendChild(xmlItem);
                }
            }

            // Write XML to file
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(doc), new StreamResult(file));

        } catch (ParserConfigurationException | TransformerException e)
        {
            JOptionPane.showMessageDialog(
                    null,
                    "Failed to save XML: " + e.getMessage(),
                    "Save Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
