package DatabaseAccessObjects.QueryObjects;

import java.io.Serializable;

public class AddPublisherAttributes implements Serializable {
    public int publisher_id;
    public String publisher_name;

    public AddPublisherAttributes(){
        publisher_id=0;
        publisher_name=null;
    }
}
