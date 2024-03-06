/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eaismart.util.generator.entity;

/**
 *
 * @author elton
 */
public abstract class GenericEntityFileGenerator {
    
    protected StringBuilder content;
    
    public GenericEntityFileGenerator() {
        content = new StringBuilder();
    }
    
    public StringBuilder getContent() {
        return content;
    } 
    
}
