/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quy.entities;

/**
 *
 * @author steve
 */
public class Page {
    private String href;
    private int idCatalogy;

    public Page(String href, int idCatalogy) {
        this.href = href;
        this.idCatalogy = idCatalogy;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public int getIdCatalogy() {
        return idCatalogy;
    }

    public void setIdCatalogy(int idCatalogy) {
        this.idCatalogy = idCatalogy;
    }
    
}