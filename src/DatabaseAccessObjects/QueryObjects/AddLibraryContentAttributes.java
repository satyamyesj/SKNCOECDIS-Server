/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseAccessObjects.QueryObjects;

import java.io.File;
import java.io.Serializable;

/**
 *
 * @author windows
 */
public class AddLibraryContentAttributes implements Serializable{
    public int subject_id;
    public String username;
    public int type_id;
    public String content_title;
    public String content_file_absolute_path;

    public AddLibraryContentAttributes() {
        subject_id=0;
        username=null;
        type_id=0;
        content_title=null;
        content_file_absolute_path=null;
    }
    
}
