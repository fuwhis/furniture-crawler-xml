/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quy.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author steve
 */
public class CrawlHelper {

    public static boolean isAlphaChar(char x) {
        return (x >= 'a' && x <= 'z');
    }

    public static String getTagName(String content) {
        if (content.charAt(content.length() - 2) == '/') {
            return null;
        }
        String res = "";
        int i = 1;
        if (content.charAt(1) == '/') {
            res = res + '/';
            i++;
        }
        while (isAlphaChar(content.charAt(i))) {
            res = res + content.charAt(i);
            i++;
        }
        if (res.length() == 0 || (res.length() == 1 && res.charAt(0) == '/')) {
            return null;
        }
        return res;
    }

    public static String fixString(String content) {
        List<String> stack = new ArrayList<>();
        List<Integer> li = new ArrayList<>();
        List<String> addtag = new ArrayList<>();

        int sz = content.length();
        int mark[] = new int[sz];
        Arrays.fill(mark, -1);
        int i = 0;
        while (i < content.length()) {
            if (content.charAt(i) == '<') {
                int j = i + 1;
                String tagTmp = "" + content.charAt(i);
                while (j < content.length() && content.charAt(j) != '>') {
                    tagTmp = tagTmp + content.charAt(j);
                    j++;
                }

                int curEnd = j;
                tagTmp = tagTmp + '>';
                i = j + 1;
                String tag = getTagName(tagTmp);

                if (tag != null) {
                    if (tag.charAt(0) != '/') {
                        stack.add(tag);
                        li.add(curEnd);
                    } else {
                        while (stack.size() > 0) {
                            if (stack.get(stack.size() - 1).equals(tag.substring(1))) {
                                stack.remove(stack.size() - 1);
                                li.remove(li.size() - 1);
                                break;
                            } else {
                                addtag.add(stack.get(stack.size() - 1));
                                mark[li.get(li.size() - 1)] = addtag.size() - 1;
                                stack.remove(stack.size() - 1);
                                li.remove(li.size() - 1);
                            }
                        }
                    }
                }
            } else {
                i++;
            }
        }
        while (stack.size() > 0) {
            addtag.add(stack.get(stack.size() - 1));
            mark[li.get(li.size() - 1)] = addtag.size() - 1;
            stack.remove(stack.size() - 1);
            li.remove(li.size() - 1);
        }
        String newContent = "";
        for (int j = 0; j < content.length(); j++) {
            newContent = newContent + content.charAt(j);
            if (mark[j] != -1) {
                newContent = newContent + "</" + addtag.get(mark[j]) + ">";
            }
        }

        return "<root>" + newContent + "</root>";
    }
}
