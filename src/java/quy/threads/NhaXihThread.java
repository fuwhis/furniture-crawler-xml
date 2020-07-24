/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quy.threads;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import quy.crawlers.NhaXinhCategoryCrawler;
import quy.crawlers.NhaXinhSubCategoryCrawler;

/**
 *
 * @author steve
 */
public class NhaXihThread extends BaseThread implements Runnable {

    private String realPath;

    public NhaXihThread() {
        this.realPath = realPath;
    }

    @Override
    public void run() {
        while (true) {
            try {
                NhaXinhCategoryCrawler categoryCrawler = new NhaXinhCategoryCrawler();
                Map<Integer, String> categoryMap = categoryCrawler.getCatalogy();

                for (Map.Entry<Integer, String> cate : categoryMap.entrySet()) {
//                    NhaXinhSubCategoryCrawler subCrawler = new NhaXinhSubCategoryCrawler(cate.getValue(), cate.getKey());
//
//                    List<Page> listHref = subCrawler.getCatalogy();
//                    for (Page abuPage : listHref) {
//                        Thread productEachPage = new Thread(new NhaXinhProductCrawler(abuPage.getHref(), abuPage.getIdCatalogy(), realPath));
//                        productEachPage.start();
//                    }
//
                }

                NhaXihThread.sleep(TimeUnit.DAYS.toMillis(7));
                synchronized (BaseThread.getInstance()) {
                    while (BaseThread.isSuspended()) {
                        BaseThread.getInstance().wait();
                    }
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(NhaXihThread.class.getName()).log(Level.SEVERE, null, ex);

            }
        }
    }
}
