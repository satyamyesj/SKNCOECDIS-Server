package DatabaseAccessObjects.QueryObjects;

import java.io.Serializable;

public class ReportReturnAttributes implements Serializable {
    public String username;
    public int report_id;

    public ReportReturnAttributes(){
        username=null;
        report_id=0;
    }
}
