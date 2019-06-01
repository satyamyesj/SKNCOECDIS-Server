package DatabaseAccessObjects.ResultObjects;

import java.io.Serializable;

public class PublisherQueryResult implements Serializable {
    public int publisher_id;
    public String publisher_name;

    public PublisherQueryResult(){
        publisher_id=0;
        publisher_name=null;
    }
}
