/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quy.checkers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import quy.crawlers.NhaXinhSubCategoryCrawler;
import quy.utils.TextUtils;

/**
 *
 * @author steve
 */
public class CheckWellformUrl {

    public static String textWellForm(String urlString) throws IOException, ParserConfigurationException, SAXException {
        URL url = new URL(urlString);
        Logger.getLogger(CheckWellformUrl.class.getName()).log(Level.INFO, urlString, "");
        URLConnection connection = url.openConnection();

        connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
        connection.setReadTimeout(8 * 1000);
        connection.setConnectTimeout(8 * 1000);

        String textContent = getStrings(connection.getInputStream());
        textContent = TextUtils.refineHtml(textContent);
        if (checkWellformUrl(textContent)) {
            String content = textContent;
            return textContent;
        }
        return null;
    }

    private static String getStrings(InputStream stream) {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try (
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(stream, StandardCharsets.UTF_8))) {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private static boolean checkWellformUrl(String src) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);
        factory.setNamespaceAware(true);

        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return false;
        }
        builder.setErrorHandler(new ErrorHandler() {
            @Override
            public void warning(SAXParseException exception) throws SAXException {
                System.out.println(exception.getMessage());
            }

            @Override
            public void error(SAXParseException exception) throws SAXException {
                System.out.println(exception.getMessage());
            }

            @Override
            public void fatalError(SAXParseException exception) throws SAXException {
                System.out.println(exception.getMessage());
            }
        });

        return true;
    }
}
