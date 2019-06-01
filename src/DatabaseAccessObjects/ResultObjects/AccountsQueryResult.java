package DatabaseAccessObjects.ResultObjects;

import java.io.Serializable;

public class AccountsQueryResult implements Serializable {
    public int account_number;
    public String date_of_account;
    public int total_fine_amount;
    public AccountsQueryResult(){
        account_number=0;
        date_of_account=null;
        total_fine_amount=0;
    }
}
