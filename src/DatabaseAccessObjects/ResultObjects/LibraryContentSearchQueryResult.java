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
public class LibraryContentSearchQueryResult  implements Serializable{
    public int content_id;
    public SubjectQueryResult subjectQueryResult;
    public String username;
    public int type_id;
    public String content_title;
    public String content_file_extension;
    public long content_file_size;
    public LibraryContentSearchQueryResult(){
        content_id=0;
        subjectQueryResult=null;
        username=null;
        type_id=0;
        content_title=null;
        content_file_extension=null;
        content_file_size=0;
    }

    public int getContent_id() {
        return content_id;
    }

    public void setContent_id(int content_id) {
        this.content_id = content_id;
    }

    public SubjectQueryResult getSubjectQueryResult() {
        return subjectQueryResult;
    }

    public void setSubjectQueryResult(SubjectQueryResult subjectQueryResult) {
        this.subjectQueryResult = subjectQueryResult;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public String getContent_title() {
        return content_title;
    }

    public void setContent_title(String content_title) {
        this.content_title = content_title;
    }

    public long getContent_file_size() {
        return content_file_size;
    }

    public void setContent_file_size(long content_file_size) {
        this.content_file_size = content_file_size;
    }

    public String getContent_file_extension() {
        return content_file_extension;
    }

    public void setContent_file_extension(String content_file_extension) {
        this.content_file_extension = content_file_extension;
    }
}
