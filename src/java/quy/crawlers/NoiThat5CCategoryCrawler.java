/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quy.crawlers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.xml.sax.SAXException;
import quy.checkers.CheckWellformUrl;
import quy.utils.BaseUtils;
import static quy.utils.BaseUtils.KEY_BED_ROOM;
import static quy.utils.BaseUtils.KEY_DINNER_ROOM;
import static quy.utils.BaseUtils.KEY_HOME_OFFICE;
import static quy.utils.BaseUtils.KEY_LIVING_ROOM;
import quy.utils.XMLHelper;

/**
 *
 * @author steve
 */
public class NoiThat5CCategoryCrawler {

    private String content;

    public NoiThat5CCategoryCrawler() {
        this.makeContentWellFrom();
    }

    private void makeContentWellFrom() {
        try {
            this.content = CheckWellformUrl.textWellForm(BaseUtils.NT5C_HOMEPAGE);
        } catch (IOException ex) {
            Logger.getLogger(NoiThat5CCategoryCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(NoiThat5CCategoryCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(NoiThat5CCategoryCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Map<Integer, String> getCatalogy() {
        Map<Integer, String> results = new HashMap<>();
        XMLStreamReader reader = null;
        InputStream is = null;
        try {
            is = new ByteArrayInputStream(content.getBytes(Charset.forName("UTF-8")));
            reader = XMLHelper.parseFileUsingStAXCursor(is);
            while (reader.hasNext()) {
                int currentCursor = reader.next();
                if (currentCursor == XMLStreamConstants.START_ELEMENT) {
                    String tagName = reader.getLocalName();
                    if (tagName.equals("div")) {
                        String attrValue = reader.getAttributeValue("", "class");
                        if (attrValue != null) {
                            if (attrValue.contains("menu")) {
                                while (reader.hasNext()) {
                                    reader.next();
                                    tagName = reader.getLocalName();
                                    if (tagName.equals("ul")) {
                                        reader.next();
                                        tagName = reader.getLocalName();
                                        if (tagName.equals("li")) {
                                            reader.next();
                                            tagName = reader.getLocalName();
                                            if (tagName.equals("a")) {
                                                attrValue = reader.getAttributeValue("", "class");
                                                if (attrValue != null) {
                                                    if (attrValue.equals("main-menu")) {
                                                        attrValue = reader.getAttributeValue("", "href");
                                                        System.out.println("get Attribute---------" + attrValue);
                                                    }

                                                }
                                            }

                                        }
                                    }
                                }

                            }
                        }

                    }
                }
            }
        } catch (XMLStreamException e) {
            Logger.getLogger(NhaXinhCategoryCrawler.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (Exception e) {
            }
        }
        System.out.println(results);
        return results;
    }
}
