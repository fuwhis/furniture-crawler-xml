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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import org.xml.sax.SAXException;
import quy.checkers.CheckWellformUrl;
import quy.utils.BaseUtils.*;
import quy.utils.XMLHelper;

/**
 *
 * @author steve
 */
public class NhaXinhProductCrawler implements Runnable{
    private String content = "";
    private int key;
    private String realPath;

    public NhaXinhProductCrawler(String url, int key, String realPath) {
        this.makeContentWellFrom(url);
        this.key = key;
        this.realPath = realPath;
    }

    private void makeContentWellFrom(String url) {
        try {
            this.content = CheckWellformUrl.textWellForm(url);
        } catch (IOException ex) {
            Logger.getLogger(NhaXinhProductCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(NhaXinhProductCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(NhaXinhProductCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void getProductInCurrentPage() {
        XMLStreamReader reader = null;
        InputStream is = null;
        try {
            is = new ByteArrayInputStream(content.getBytes(Charset.forName("UTF-8")));
            reader = XMLHelper.parseFileUsingStAXCursor(is);
            while (reader.hasNext()) {
                int currentCursor = reader.next();
                if (currentCursor == XMLStreamConstants.START_ELEMENT) {
                    String tagname = reader.getLocalName();
                    if (tagname.equals("a")) {
                        String attrvalue = reader.getAttributeValue("", "class");
                        if (attrvalue != null) {
                            if (attrvalue.equals("thumb-link")) {
                                String url = reader.getAttributeValue("", "href");
                                String name = reader.getAttributeValue("", "title");
                                reader.next();
                                tagname = reader.getLocalName();
                                if (tagname.equals("img")) {
                                    String image = reader.getAttributeValue("", "src");

                                }
                            }
                        }
                    }
                }
            }

        } catch (XMLStreamException ex) {
            Logger.getLogger(NhaXinhProductCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (XMLStreamException ex) {
                Logger.getLogger(NhaXinhProductCrawler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(NhaXinhProductCrawler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void run() {
        getProductInCurrentPage();
    }
}
