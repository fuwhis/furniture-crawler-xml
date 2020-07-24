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
public class ProductMatch<T> {
    private T data;
    private Double point;
    private String decription;
    
    public ProductMatch(T data, Double point) {
        this.data = data;
        this.point = point;
    }
    
}
