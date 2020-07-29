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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.xml.sax.SAXException;
import quy.checkers.CheckWellformUrl;
import quy.entities.Page;
import quy.utils.BaseUtils;
import quy.utils.XMLHelper;

/**
 *
 * @author steve
 */
public class NhaXinhSubCategoryCrawler {
    
    private String content = "";
    private int key;
    private String url;
    private List<Page> listHref; // list get page

    public NhaXinhSubCategoryCrawler(int key, String url) {
        url = BaseUtils.NHAXINH_HOMEPAGE + url;
        this.makeContentWellFrom(url);
        this.key = key;
        this.url = url;
        listHref = new ArrayList<>();
        listHref.add(new Page(url, key));
    }
    
    private void makeContentWellFrom(String url) {
        try {
            this.content = CheckWellformUrl.textWellForm(url);
        } catch (IOException ex) {
            Logger.getLogger(NhaXinhSubCategoryCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(NhaXinhSubCategoryCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(NhaXinhSubCategoryCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Page> getCatagory() {
        XMLStreamReader reader = null;
        InputStream is = null;
        boolean continueConn = true;
        
        try {
            is = new ByteArrayInputStream(content.getBytes(Charset.forName("UTF-8")));
            reader = XMLHelper.parseFileUsingStAXCursor(is);
            while (reader.hasNext()) {
                int currentCursor = reader.next();
                if (currentCursor == XMLStreamConstants.START_ELEMENT) {
                    String tagname = reader.getLocalName();
                    if (tagname.equals("div")) {
                        String attrValue = reader.getAttributeValue("", "id");
                        if (attrValue != null) {
                            if (attrValue.equals("room_left_menu")) {
                                while (continueConn) {
                                    currentCursor = reader.next();
                                    if (currentCursor == XMLStreamConstants.START_ELEMENT) {
                                        tagname = reader.getLocalName();
                                        if (tagname.equals("ul")) {
                                            while (continueConn) {
                                                currentCursor = reader.next();
                                                if (currentCursor == XMLStreamConstants.START_ELEMENT) {
                                                    tagname = reader.getLocalName();
                                                    if (tagname.equals("a")) {
//                                                        attrValue = reader.getAttributeValue("", "href");
//                                                        System.out.println("subCategory: " + attrValue);
//                                                        if (attrValue != null) {
//                                                            // lam gi do chua lam dc
//                                                        }
                                                    }
                                                }
                                                if (currentCursor == XMLStreamConstants.END_ELEMENT) {
                                                    tagname = reader.getLocalName();
                                                    if (tagname.equals("a")) {
                                                        continueConn = false;
                                                    }
                                                }
                                            }
                                        }
                                        if (currentCursor == XMLStreamConstants.START_ELEMENT) {
                                            tagname = reader.getLocalName();
                                            if (tagname.equals("hr")) {
                                                attrValue = reader.getAttributeValue("", "class");
                                                if (attrValue != null) {
                                                    if (attrValue.equals("room_menu_seperate")) {
//                                                        continueConn = true;
                                                        reader.next();
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    if (currentCursor == XMLStreamConstants.END_ELEMENT) {
                                        tagname = reader.getLocalName();
                                        if (tagname.equals("ul")) {
                                            continueConn = false;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (XMLStreamException ex) {
            Logger.getLogger(NhaXinhSubCategoryCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (XMLStreamException ex) {
                Logger.getLogger(NhaXinhSubCategoryCrawler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(NhaXinhSubCategoryCrawler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listHref;
    }
    
    private void addListHref(String href) {
        int count = 0;
        for (int i = 0; i < listHref.size(); i++) {
            String valueInList = listHref.get(i).getHref();
            if (valueInList.equals(href)) {
                count++;
            }
        }
        if (count == 0) {
            listHref.add(new Page(href, key));
        }
    }
}
