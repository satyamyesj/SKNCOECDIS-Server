package RequestAttributes;

import java.io.Serializable;

public class RequestAttributes implements Serializable {
    public  String interfaceName;
    public  String requestCode;
    public RequestAttributes(){
        interfaceName=null;
        requestCode=null;
    }
}
