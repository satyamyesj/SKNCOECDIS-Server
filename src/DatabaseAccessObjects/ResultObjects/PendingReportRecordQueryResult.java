package DatabaseAccessObjects.ResultObjects;

import java.io.Serializable;

public class PendingReportRecordQueryResult  implements Serializable {
    public int report_id;
    public String report_title;
    public String date_of_issue;

    public PendingReportRecordQueryResult(){
        report_id=0;
        report_title=null;
        date_of_issue=null;
    }
}
