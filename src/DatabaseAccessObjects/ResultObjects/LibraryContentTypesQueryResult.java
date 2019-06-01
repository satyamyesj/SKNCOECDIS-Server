/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseAccessObjects.ResultObjects;

import java.io.Serializable;

/**
 *
 * @author windows
 */
public class LibraryContentTypesQueryResult implements Serializable{

    public int type_id;
    public String type;

    public LibraryContentTypesQueryResult() {
        type_id = 0;
        type = null;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
}
