package DatabaseAccessObjects.QueryObjects;

import java.io.Serializable;

public class BookSearchAttributes implements Serializable {
    public String book_author;
    public String book_title;
    public String subject_abbreviation;
    public String domain_name;

    public BookSearchAttributes(){
        book_title=null;
        book_author=null;
        subject_abbreviation=null;
        domain_name=null;
    }
}
