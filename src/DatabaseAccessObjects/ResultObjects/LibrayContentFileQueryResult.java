/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseAccessObjects.ResultObjects;

import java.io.Serializable;
import java.sql.Blob;

/**
 *
 * @author windows
 */
public class LibrayContentFileQueryResult implements Serializable{
    public String content_title;
    public String content_file_absolute_path;
    public LibrayContentFileQueryResult(){
    content_title=null;
    content_file_absolute_path=null;
    }
}
