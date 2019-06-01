package DatabaseAccessObjects.ResultObjects;

import java.io.Serializable;

public class BookIssueConstraintsQueryResult implements Serializable {
    public String constraint_type;
    public int constraint_value;

    public BookIssueConstraintsQueryResult(){
        constraint_type=null;
        constraint_value=0;
    }
}
