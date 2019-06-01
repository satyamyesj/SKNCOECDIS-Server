package DatabaseAccessObjects.ResultObjects;

import java.io.Serializable;

public class DomainQueryResult implements Serializable {
    public int domain_id;
    public String domain_name;

    public DomainQueryResult(){
        domain_id=0;
        domain_name=null;
    }

    public int getDomain_id() {
        return domain_id;
    }

    public void setDomain_id(int domain_id) {
        this.domain_id = domain_id;
    }

    public String getDomain_name() {
        return domain_name;
    }

    public void setDomain_name(String domain_name) {
        this.domain_name = domain_name;
    }
    
}
