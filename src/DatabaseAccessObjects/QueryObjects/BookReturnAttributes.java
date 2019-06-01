package DatabaseAccessObjects.QueryObjects;

import java.io.Serializable;

public class BookReturnAttributes implements Serializable {
    public int book_id;
    public String username;
    public boolean is_fine;

    public BookReturnAttributes(){
        book_id=0;
        username=null;
        is_fine=false;
    }
}
