package DatabaseAccessObjects.QueryObjects;

import java.io.Serializable;

public class BookIssueAttributes implements Serializable {
    public String username;
    public String book_id;

    public BookIssueAttributes() {
        username = null;
        book_id = null;
    }
}
