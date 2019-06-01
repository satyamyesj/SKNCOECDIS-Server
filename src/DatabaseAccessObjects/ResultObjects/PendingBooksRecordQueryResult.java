package DatabaseAccessObjects.ResultObjects;

import java.io.Serializable;

public class PendingBooksRecordQueryResult implements Serializable {
    public int book_id;
    public String book_title;
    public String date_of_issue;
    public int due_days;
    public int fine_amount;

    public PendingBooksRecordQueryResult(){
       book_id=0;
       book_title=null;
       date_of_issue=null;
       due_days=0;
       fine_amount=0;
    }
}
