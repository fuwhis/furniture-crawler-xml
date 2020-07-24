/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quy.threads;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import quy.crawlers.NoiThat5CCategoryCrawler;

/**
 *
 * @author steve
 */
public class NoiThat5CThread extends BaseThread implements Runnable {

    private String realPath;

    public NoiThat5CThread() {
        this.realPath = realPath;
    }

    @Override
    public void run() {
        try {
            NoiThat5CCategoryCrawler categoryCrawler = new NoiThat5CCategoryCrawler();
                Map<Integer, String> categoryMap = categoryCrawler.getCatalogy();

                // iterate for loop of map list
                
                NoiThat5CThread.sleep(TimeUnit.DAYS.toMillis(7));
                synchronized(BaseThread.getInstance()){
                    while (BaseThread.isSuspended()) {                        
                        BaseThread.getInstance().wait();
                    }
                }
        } catch (Exception e) {
        }
    }
    
    
}
