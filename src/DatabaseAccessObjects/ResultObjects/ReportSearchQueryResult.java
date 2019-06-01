package DatabaseAccessObjects.ResultObjects;

import java.io.Serializable;

public class ReportSearchQueryResult  implements Serializable {
    public int report_id;
    public String report_title;
    public int availability_status;
    public int cupboard_no;
    public int shelf_no;

    public ReportSearchQueryResult(){
        report_id=0;
        report_title=null;
        availability_status=0;
        cupboard_no=0;
        shelf_no=0;
    }

    public int getReport_id() {
        return report_id;
    }

    public void setReport_id(int report_id) {
        this.report_id = report_id;
    }

    public String getReport_title() {
        return report_title;
    }

    public void setReport_title(String report_title) {
        this.report_title = report_title;
    }

    public int getAvailability_status() {
        return availability_status;
    }

    public void setAvailability_status(int availability_status) {
        this.availability_status = availability_status;
    }

    public int getCupboard_no() {
        return cupboard_no;
    }

    public void setCupboard_no(int cupboard_no) {
        this.cupboard_no = cupboard_no;
    }

    public int getShelf_no() {
        return shelf_no;
    }

    public void setShelf_no(int shelf_no) {
        this.shelf_no = shelf_no;
    }
    
    
}
