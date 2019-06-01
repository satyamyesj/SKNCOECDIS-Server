/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseAccessObjects.QueryObjects;

import java.io.Serializable;

/**
 *
 * @author windows
 */
public class LibraryContentSearchAttributes implements Serializable{
    public String content_title;
    public String subject;
    public String username;
    public String exam_pattern;
    public String content_type_id;
    
    public void LibraryContentSearchAttributes(){
        content_title=null;
        subject=null;
        username=null;
        exam_pattern=null;
        content_type_id=null;
    }

}
