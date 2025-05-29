package com.nhlstenden.jabberpoint.files.saving;

import com.nhlstenden.jabberpoint.*;
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

/**
 * <p>The saver for .XML files.<p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.7 2025/04/02 Thu Tran - Bocheng Peng
 */
public class XMLSavePresentationStrategy implements SaveContentStrategy<Presentation>
{
    @Override
    public void saveContent(Presentation presentation, File file)
    {
        try
        {
            file = ensureFileExtension(file);
            Document doc = createDocumentStructure(presentation);
            writeXmlToFile(doc, file);
        } catch (ParserConfigurationException | TransformerException e)
        {
            showSaveErrorDialog(e.getMessage());
        }
    }

    private File ensureFileExtension(File file)
    {
        if (!file.getName().endsWith(".xml"))
        {
            return new File(file.getParent(), file.getName() + ".xml");
        }

        return file;
    }

    private Document createDocumentStructure(Presentation presentation)
            throws ParserConfigurationException
    {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.newDocument();

        Element root = doc.createElement("presentation");
        doc.appendChild(root);

        addPresentationTitle(presentation, doc, root);
        addAllSlides(presentation, doc, root);

        return doc;
    }

    private void addPresentationTitle(Presentation presentation, Document doc, Element root)
    {
        Element showTitle = doc.createElement("showtitle");
        showTitle.setTextContent(presentation.getTitle());
        root.appendChild(showTitle);
    }

    private void addAllSlides(Presentation presentation, Document doc, Element root)
    {
        for (Slide slide : presentation.getShowList())
        {
            Element xmlSlide = createSlideElement(doc, slide);
            root.appendChild(xmlSlide);
        }
    }

    private Element createSlideElement(Document doc, Slide slide)
    {
        Element xmlSlide = doc.createElement("slide");
        addSlideTitle(doc, slide, xmlSlide);
        addSlideItems(doc, slide, xmlSlide);

        return xmlSlide;
    }

    private void addSlideTitle(Document doc, Slide slide, Element xmlSlide)
    {
        Element titleElement = doc.createElement("title");
        titleElement.setTextContent(slide.getTitle());
        xmlSlide.appendChild(titleElement);
    }

    private void addSlideItems(Document doc, Slide slide, Element xmlSlide)
    {
        for (SlideComponent item : slide.getSlideItems())
        {
            Element xmlItem = createItemElement(doc, item);
            xmlSlide.appendChild(xmlItem);
        }
    }

    private Element createItemElement(Document doc, SlideComponent item)
    {
        Element xmlItem = doc.createElement("item");
        setItemAttributes(xmlItem, item);
        xmlItem.setTextContent(item.getContent());

        return xmlItem;
    }

    private void setItemAttributes(Element xmlItem, SlideComponent item)
    {
        xmlItem.setAttribute("level", String.valueOf(item.getLevel()));
        xmlItem.setAttribute("kind", getItemType(item));
    }

    private String getItemType(SlideComponent item)
    {
        return item instanceof TextItem ? "text" : "image";
    }

    private void writeXmlToFile(Document doc, File file) throws TransformerException
    {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(new DOMSource(doc), new StreamResult(file));
    }

    private void showSaveErrorDialog(String errorMessage)
    {
        JOptionPane.showMessageDialog(
                null,
                "Failed to save XML: " + errorMessage,
                "Save Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}