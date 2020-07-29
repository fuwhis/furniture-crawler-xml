/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quy.main;

import java.util.List;
import java.util.Map;
import quy.crawlers.NhaXinhCategoryCrawler;
import quy.crawlers.NhaXinhProductCrawler;
import quy.crawlers.NhaXinhSubCategoryCrawler;
import quy.crawlers.NoiThat5CCategoryCrawler;
import quy.crawlers.NoiThat5CProductCrawler;
import quy.entities.Page;

/**
 *
 * @author steve
 */
public class Main {

    public static void main(String[] args) {
//        NhaXinhCategoryCrawler pre = new NhaXinhCategoryCrawler();
//        NhaXinhProductCrawler pruh = null;
//        Map<Integer, String> link = pre.getCatalogy();
//        List<String> subCateLink = null;
//
//        for (Map.Entry<Integer, String> entry : link.entrySet()) {
//            NhaXinhSubCategoryCrawler c = new NhaXinhSubCategoryCrawler(entry.getKey(), entry.getValue());
//            subCateLink = c.getCatagory();
//            if (subCateLink != null) {
//                for (String page : subCateLink) {
//                    pruh = new NhaXinhProductCrawler(page);
////                    System.out.println(page);
//                    pruh.run();
//                }
//            }
//        }
        NoiThat5CCategoryCrawler nt = new NoiThat5CCategoryCrawler();
        NoiThat5CProductCrawler np = null;
        Map<Integer, String> FiveCProLink = nt.getCatalogy();
        for (String link : FiveCProLink.values()) {
            np = new NoiThat5CProductCrawler(link);
            np.run();
        }
    }

}
