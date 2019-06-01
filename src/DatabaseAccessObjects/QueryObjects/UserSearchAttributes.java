package DatabaseAccessObjects.QueryObjects;

import java.io.Serializable;

public class UserSearchAttributes implements Serializable {
    public String username;
    public String email;
    public String mobile_no;
    public UserSearchAttributes(){
        username=null;
        email=null;
        mobile_no=null;
    }
}
