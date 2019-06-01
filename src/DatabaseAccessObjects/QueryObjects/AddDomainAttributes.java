package DatabaseAccessObjects.QueryObjects;

import java.io.Serializable;

public class AddDomainAttributes implements Serializable {
    public int domain_id;
    public String domain_name;

    public AddDomainAttributes(){
        domain_id=0;
        domain_name=null;
    }

}
