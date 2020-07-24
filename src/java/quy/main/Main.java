/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quy.main;

import java.util.Map;
import quy.crawlers.NhaXinhCategoryCrawler;
import quy.crawlers.NhaXinhSubCategoryCrawler;
import quy.crawlers.NoiThat5CCategoryCrawler;

/**
 *
 * @author steve
 */
public class Main {

    
    public static void main(String[] args) {
        NhaXinhCategoryCrawler pre = new NhaXinhCategoryCrawler();
        Map<Integer, String> link = pre.getCatalogy();
        
        for (Map.Entry<Integer, String> entry : link.entrySet()) {
            NhaXinhSubCategoryCrawler c = new NhaXinhSubCategoryCrawler(entry.getKey(), entry.getValue());
            c.getCatagory();
        }
//        NoiThat5CCategoryCrawler nt = new NoiThat5CCategoryCrawler();
//        nt.getCatalogy();
    }
}
