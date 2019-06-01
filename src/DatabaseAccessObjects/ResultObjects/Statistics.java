/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseAccessObjects.ResultObjects;

import java.io.Serializable;

/**
 *
 * @author windows
 */
public class Statistics implements Serializable{
    public int total_library_content;
    public int total_books;
    public int total_reports;
    
    public Statistics(){
        total_library_content=-1;
        total_books=-1;
        total_reports=-1;
    }

    public int getTotal_library_content() {
        return total_library_content;
    }

    public void setTotal_library_content(int total_library_content) {
        this.total_library_content = total_library_content;
    }

    public int getTotal_books() {
        return total_books;
    }

    public void setTotal_books(int total_books) {
        this.total_books = total_books;
    }

    public int getTotal_reports() {
        return total_reports;
    }

    public void setTotal_reports(int total_reports) {
        this.total_reports = total_reports;
    }
    
    
}
