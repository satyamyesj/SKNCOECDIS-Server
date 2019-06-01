package DatabaseAccessObjects.ResultObjects;

import java.io.Serializable;

public class BookTitleQueryResult implements Serializable {
    public int book_id;
    public String book_author;
    public String book_title;
    public int no_of_copies;
    public int available_copies;
    public int publisher_id;
    public String publisher_name;
    public int domain_id;
    public String domain_name;
    public int cupboard_no;
    public int shelf_no;

    public BookTitleQueryResult(){
        book_id=0;
        book_author=null;
        book_title=null;
        no_of_copies=0;
        available_copies=0;
        publisher_id=0;
        publisher_name=null;
        domain_id=0;
        domain_name=null;
        cupboard_no=0;
        shelf_no=0;
    }
}
