/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quy.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import quy.checkers.XmlSyntaxChecker;

/**
 *
 * @author steve
 */
public class TextUtils {
    public static String refineHtml(String src) {
        src = getBody(src);
        src = removeMiscellaneousTags(src);
        XmlSyntaxChecker xmlSyntaxChecker = new XmlSyntaxChecker();
        src = xmlSyntaxChecker.check(src);
        src = getBody(src);
        return src;
    }

    public static String getBody(String src) {
        String result = src;
        String expression = "<body.*?</body>";
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(result);
        if (matcher.find()) {
            result = matcher.group(0);
        }
        return result;
    }

    public static String removeMiscellaneousTags(String src) {
        String result = src;
        String expression = "<script.*?</script>";
        result = result.replaceAll(expression, "");
        expression = "<!--.*?-->";
        result = result.replaceAll((expression), "");
        expression = "&nbsp;?";
        result = result.replaceAll((expression), "");
        
        expression = "B&W";
        result = result.replaceAll((expression), "BW");
        expression = "&amp;pound;";
        result = result.replaceAll((expression), "euro");
        expression = "&pound;";
        result = result.replaceAll((expression), "euro ");
        expression = "Bowers &amp; Wilkins";
        result = result.replaceAll((expression), "BW");
        expression = "&quote;";
        result = result.replaceAll((expression), "");
        return result;
    }
}
