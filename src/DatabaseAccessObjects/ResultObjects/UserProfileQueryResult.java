package DatabaseAccessObjects.ResultObjects;

import DatabaseAccessObjects.QueryObjects.Employee;
import DatabaseAccessObjects.QueryObjects.Student;

import java.io.Serializable;

public class UserProfileQueryResult implements Serializable {
    public String user_role;
    public boolean has_pending_library_record;
    public Student student;
    public Employee employee;

   public UserProfileQueryResult(){
       user_role=null;
       has_pending_library_record =false;
       student=null;
       employee=null;
   }
}
