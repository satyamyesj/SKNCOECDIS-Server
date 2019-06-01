package DatabaseAccessFunctions;

import DatabaseAccessObjects.QueryObjects.*;
import DatabaseAccessObjects.ResultObjects.*;
import Server.ServerThread;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QueryFunctions {

    static public boolean login(User user) {
        try {
            Statement statement = ServerThread.databaseConnector.connector.createStatement();
            String verify_credentials = "call skncoe_computer_department.verify_login_credentials( '" + user.username + "','" + user.password + "',@result,@user_role);";
            String checkResult = "select @result";

            statement.executeQuery(verify_credentials);
            ResultSet resultSet = statement.executeQuery(checkResult);
            resultSet.next();
            boolean isSuccessful = resultSet.getBoolean(1);
            if (isSuccessful) {
                String getRole = "select @user_role";
                resultSet = statement.executeQuery(getRole);
                resultSet.next();
                user.user_role = resultSet.getString(1);
            }
            return isSuccessful;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    static public List<BookSearchQueryResult> searchBook(BookSearchAttributes book_query) {
        try {
            List<BookSearchQueryResult> book_query_result_set = new ArrayList<>();
            String query;
            if (!book_query.subject_abbreviation.equals("%")) {
                query = "select book_id,book_author, book_title, no_of_copies, available_copies, cupboard_no, shelf_no "
                        + "from book_titles "
                        + "where (book_titles.domain_id in (select domain_id from domains where domain_name like '" + book_query.domain_name + "' ))"
                        + "and (book_titles.book_title like '" + book_query.book_title + "' )"
                        + "and ( book_titles.book_author like '" + book_query.book_author + "' )"
                        + "and (book_titles.domain_id in (select domain_id from subjects where subject_abbreviation like '" + book_query.subject_abbreviation + "'));";
            } else {
                query = "select book_id,book_author, book_title, no_of_copies, available_copies, cupboard_no, shelf_no "
                        + "from book_titles "
                        + "where (book_titles.domain_id in (select domain_id from domains where domain_name like '" + book_query.domain_name + "' ))"
                        + "and (book_titles.book_title like '" + book_query.book_title + "' )"
                        + "and ( book_titles.book_author like '" + book_query.book_author + "');";
            }
            Statement statement = ServerThread.databaseConnector.connector.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                BookSearchQueryResult result_tuples = new BookSearchQueryResult();
                result_tuples.book_id = resultSet.getInt("book_id");
                result_tuples.book_author = resultSet.getString("book_author");
                result_tuples.book_title = resultSet.getString("book_title");
                result_tuples.no_of_copies = resultSet.getInt("no_of_copies");
                result_tuples.available_copies = resultSet.getInt("available_copies");
                result_tuples.cupboard_no = resultSet.getInt("cupboard_no");
                result_tuples.shelf_no = resultSet.getInt("shelf_no");
                book_query_result_set.add(result_tuples);
            }
            return book_query_result_set;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<BookSearchQueryResult>();
        }
    }

    public static List<ReportSearchQueryResult> searchReport(ReportSearchAttributes report_query) {
        try {
            List<ReportSearchQueryResult> report_query_result_set = new ArrayList<>();
            String query = "select report_id,report_title,availability_status,cupboard_no,shelf_no  from report_titles where report_title like '" + report_query.report_title + "' and domain_id in (select domain_id from domains where domain_name like '" + report_query.domain_name + "');";
            Statement statement = ServerThread.databaseConnector.connector.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                ReportSearchQueryResult result_tuples = new ReportSearchQueryResult();
                result_tuples.report_id = resultSet.getInt("report_id");
                result_tuples.report_title = resultSet.getString("report_title");
                result_tuples.availability_status = resultSet.getInt("availability_status");
                result_tuples.cupboard_no=resultSet.getInt("cupboard_no");
                result_tuples.shelf_no=resultSet.getInt("shelf_no");
                report_query_result_set.add(result_tuples);
            }
            return report_query_result_set;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<ReportSearchQueryResult>();
        }
    }

    public static List<LibraryContentSearchQueryResult> searchLibraryContent(LibraryContentSearchAttributes library_content_query) {
        try {
            List<LibraryContentSearchQueryResult> content_library_query_result_set = new ArrayList<>();
            String query = "select lc.content_id,lc.type_id,lc.content_title,lc.username,lc.content_file_extension,lc.content_file_size,s.subject_id,s.subject_name,s.semester,s.academic_year,s.exam_pattern,s.domain_id,s.subject_abbreviation from library_content as lc inner join subjects as s on content_title like '" + library_content_query.content_title + "' and s.subject_id in (select subject_id from subjects where (subject_name like '" + library_content_query.subject + "' or subject_abbreviation like '" + library_content_query.subject + "') and exam_pattern like '" + library_content_query.exam_pattern + "') and username like '" + library_content_query.username + "' and type_id like '" + library_content_query.content_type_id + "' and lc.subject_id=s.subject_id;";
            Statement statement = ServerThread.databaseConnector.connector.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                LibraryContentSearchQueryResult result_tuples = new LibraryContentSearchQueryResult();
                result_tuples.content_id = resultSet.getInt(1);
                result_tuples.type_id = resultSet.getInt(2);
                result_tuples.content_title = resultSet.getString(3);
                result_tuples.username = resultSet.getString(4);
                result_tuples.content_file_extension = resultSet.getString(5);
                result_tuples.content_file_size = Long.parseLong(resultSet.getString(6));
                result_tuples.subjectQueryResult = new SubjectQueryResult();
                result_tuples.subjectQueryResult.subject_id = resultSet.getInt(7);
                result_tuples.subjectQueryResult.subject_name = resultSet.getString(8);
                result_tuples.subjectQueryResult.semester = resultSet.getString(9);
                result_tuples.subjectQueryResult.academic_year = resultSet.getString(10);
                result_tuples.subjectQueryResult.exam_pattern = resultSet.getString(11);
                result_tuples.subjectQueryResult.domain_id = resultSet.getInt(12);
                result_tuples.subjectQueryResult.subject_abbreviation = resultSet.getString(13);
                content_library_query_result_set.add(result_tuples);
            }
            return content_library_query_result_set;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<LibraryContentSearchQueryResult>();
        }
    }

    public static LibrayContentFileQueryResult getLibraryContentFile(int content_id) {
        try {
            LibrayContentFileQueryResult library_content_file = new LibrayContentFileQueryResult();
            String query = "select content_title,content_file,content_file_extension from library_content where content_id=" + content_id + ";";
            Statement statement = ServerThread.databaseConnector.connector.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            library_content_file.content_title = resultSet.getString(1);
            Blob content_file_blob = resultSet.getBlob(2);
            String content_file_extension = resultSet.getString(3);
            InputStream inputStream = content_file_blob.getBinaryStream();
            File content_file = new File(library_content_file.content_title + content_file_extension);

            //exporting file to disk from blob
            FileOutputStream fileOutputStream = new FileOutputStream(content_file);
            byte[] bytes = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(bytes)) != -1) {
                // Write file data to Response.
                fileOutputStream.write(bytes);
            }
            fileOutputStream.close();
            inputStream.close();

            library_content_file.content_file_absolute_path = content_file.getAbsolutePath();
            return library_content_file;
        } catch (SQLException e) {
            e.printStackTrace();
            return new LibrayContentFileQueryResult();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(QueryFunctions.class.getName()).log(Level.SEVERE, null, ex);
            return new LibrayContentFileQueryResult();
        } catch (IOException ex) {
            Logger.getLogger(QueryFunctions.class.getName()).log(Level.SEVERE, null, ex);
            return new LibrayContentFileQueryResult();
        }
    }

    public static List<UserProfileQueryResult> searchUser(UserSearchAttributes user_search_query) {
        try {
            List<UserProfileQueryResult> user_search_query_result_set = new ArrayList<>();
            Statement statement = ServerThread.databaseConnector.connector.createStatement();
            ResultSet resultSet = statement.executeQuery("select users.username from users where users.username like '" + user_search_query.username + "'and users.email like '" + user_search_query.email + "'and users.mobile_no like '" + user_search_query.mobile_no + "';");
            while (resultSet.next()) {
                String username = resultSet.getString(1);
                UserProfileRequestAttribute user_query = new UserProfileRequestAttribute();
                user_query.username = username;
                UserProfileQueryResult user_search_query_result = QueryFunctions.getUserProfile(user_query);
                user_search_query_result_set.add(user_search_query_result);
            }
            return user_search_query_result_set;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<UserProfileQueryResult>();
        }
    }

    static public User getUserWithPassword(User user) {
        try {
            Statement statement = ServerThread.databaseConnector.connector.createStatement();
            String get_user = "call skncoe_computer_department.get_user_with_password('" + user.username + "','" + user.password + "', @result,@first_name,@last_name,@email,@mobile_no,@verification_status,@user_role);";
            statement.executeQuery(get_user);
            ResultSet resultSet = statement.executeQuery("select @result");
            resultSet.next();
            if (resultSet.getBoolean(1)) {
                resultSet = statement.executeQuery("select @first_name");
                resultSet.next();
                user.first_name = resultSet.getString(1);
                resultSet = statement.executeQuery("select @last_name");
                resultSet.next();
                user.last_name = resultSet.getString(1);
                resultSet = statement.executeQuery("select @email");
                resultSet.next();
                user.email = resultSet.getString(1);
                resultSet = statement.executeQuery("select @mobile_no");
                resultSet.next();
                user.mobile_no = resultSet.getString(1);
                resultSet = statement.executeQuery("select @verification_status");
                resultSet.next();
                user.verification_status = resultSet.getInt(1);
                resultSet = statement.executeQuery("select @user_role");
                resultSet.next();
                user.user_role = resultSet.getString(1);
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return user; //TODO analyse result of return
        }
    }

    static public User getUserWithoutPassword(User user, DatabaseConnector databaseConnector) {
        try {
            Statement statement = databaseConnector.connector.createStatement();
            String get_user = "call skncoe_computer_department.get_user_without_password('" + user.username + "', @result,@first_name,@last_name,@email,@mobile_no,@verification_status,@user_role);";
            statement.executeQuery(get_user);
            ResultSet resultSet = statement.executeQuery("select @result");
            resultSet.next();
            if (resultSet.getBoolean(1)) {
                resultSet = statement.executeQuery("select @first_name");
                resultSet.next();
                user.first_name = resultSet.getString(1);
                resultSet = statement.executeQuery("select @last_name");
                resultSet.next();
                user.last_name = resultSet.getString(1);
                resultSet = statement.executeQuery("select @email");
                resultSet.next();
                user.email = resultSet.getString(1);
                resultSet = statement.executeQuery("select @mobile_no");
                resultSet.next();
                user.mobile_no = resultSet.getString(1);
                resultSet = statement.executeQuery("select @verification_status");
                resultSet.next();
                user.verification_status = resultSet.getInt(1);
                resultSet = statement.executeQuery("select @user_role");
                resultSet.next();
                user.user_role = resultSet.getString(1);
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return user; //TODO analyse result of return
        }
    }

    static public Student getStudentWithoutPassword(Student student) {
        try {
            Statement statement = ServerThread.databaseConnector.connector.createStatement();
            User user = new User();
            user.username = student.username;
            user = QueryFunctions.getUserWithoutPassword(user, ServerThread.databaseConnector);

            student.username = user.username;
            student.first_name = user.first_name;
            student.last_name = user.last_name;
            student.email = user.email;
            student.mobile_no = user.mobile_no;
            student.verification_status = user.verification_status;

            String get_user = "call skncoe_computer_department.get_student_details('" + student.username + "', @result,@academic_year,@unique_id,@division,@dob, @permanent_address);";
            statement.executeQuery(get_user);
            ResultSet resultSet = statement.executeQuery("select @result");
            resultSet.next();
            if (resultSet.getBoolean(1)) {
                resultSet = statement.executeQuery("select @academic_year");
                resultSet.next();
                student.academic_year = resultSet.getString(1);

                resultSet = statement.executeQuery("select @unique_id");
                resultSet.next();
                student.unique_id = resultSet.getString(1);

                resultSet = statement.executeQuery("select @division");
                resultSet.next();
                student.division = resultSet.getString(1);

                resultSet = statement.executeQuery("select @dob");
                resultSet.next();
                student.dob = resultSet.getString(1);
                if (student.dob.equals("0000-00-00")) {
                    student.dob = "N/A";
                }

                resultSet = statement.executeQuery("select @permanent_address");
                resultSet.next();
                student.permanent_address = resultSet.getString(1);
            }
            return student;
        } catch (SQLException e) {
            e.printStackTrace();
            return student;  //TODO analyse result of return
        }
    }

    static Employee getEmployeeWithoutPassword(Employee employee) {
        try {
            Statement statement = ServerThread.databaseConnector.connector.createStatement();
            User user = new User();
            user.username = employee.username;
            QueryFunctions.getUserWithoutPassword(user, ServerThread.databaseConnector);

            employee.username = user.username;
            employee.first_name = user.first_name;
            employee.last_name = user.last_name;
            employee.email = user.email;
            employee.mobile_no = user.mobile_no;
            employee.verification_status = user.verification_status;

            String get_user = "call skncoe_computer_department.get_employee_details('" + employee.username + "', @result,@employee_no);";
            statement.executeQuery(get_user);
            ResultSet resultSet = statement.executeQuery("select @result");
            resultSet.next();
            if (resultSet.getBoolean(1)) {
                resultSet = statement.executeQuery("select @employee_no");
                resultSet.next();
                employee.employee_no = resultSet.getString(1);
            }
            return employee;
        } catch (SQLException e) {
            e.printStackTrace();
            return employee; //TODO analyse result of return
        }
    }

    static public String issueBook(BookIssueAttributes book_issue_query) {
        try {
            String date_of_return = " ";
            Statement statement = ServerThread.databaseConnector.connector.createStatement();
            statement.executeQuery("call skncoe_computer_department.issue_book('" + book_issue_query.username + "','" + book_issue_query.book_id + "',@result,@date_of_return);");
            ResultSet resultSet = statement.executeQuery("select @result");
            resultSet.next();
            boolean issued_successfully = resultSet.getBoolean(1);
            if (!issued_successfully) {
                date_of_return = "NOT_ISSUED";
            } else {
                resultSet = statement.executeQuery("select @date_of_return");
                resultSet.next();
                date_of_return = resultSet.getString(1);
            }
            return date_of_return;
        } catch (SQLException e) {
            e.printStackTrace();
            return "NOT_ISSUED";
        }
    }

    public static boolean issueReport(BookIssueAttributes report_issue_query) {
        try {
            boolean issued_successfully = false;
            Statement statement = ServerThread.databaseConnector.connector.createStatement();
            statement.executeQuery("call skncoe_computer_department.issue_report('" + report_issue_query.username + "','" + report_issue_query.book_id + "',@result);");
            ResultSet resultSet = statement.executeQuery("select @result");
            resultSet.next();
            return issued_successfully = resultSet.getBoolean(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    static public UserProfileQueryResult getUserProfile(UserProfileRequestAttribute user_profile_query) {
        try {
            Statement statement = ServerThread.databaseConnector.connector.createStatement();
            UserProfileQueryResult user_profile_query_result = new UserProfileQueryResult();
            statement.executeQuery("call skncoe_computer_department.return_user_status('" + user_profile_query.username + "',@result,@user_role,@has_pending_library_record);");
            ResultSet resultSet = statement.executeQuery("select @result");
            resultSet.next();
            if (resultSet.getBoolean(1)) {
                resultSet = statement.executeQuery("select @has_pending_library_record");
                resultSet.next();
                user_profile_query_result.has_pending_library_record = resultSet.getBoolean(1);
                resultSet = statement.executeQuery("select @user_role");
                resultSet.next();
                user_profile_query_result.user_role = resultSet.getString(1);
                if (user_profile_query_result.user_role.equals("S")) {
                    user_profile_query_result.student = new Student();
                    user_profile_query_result.student.username = user_profile_query.username;
                    user_profile_query_result.student = QueryFunctions.getStudentWithoutPassword(user_profile_query_result.student);
                } else if (user_profile_query_result.user_role.equals("E")) {
                    user_profile_query_result.employee = new Employee();
                    user_profile_query_result.employee.username = user_profile_query.username;
                    user_profile_query_result.employee = QueryFunctions.getEmployeeWithoutPassword(user_profile_query_result.employee);
                }
            } else {
                user_profile_query_result.user_role = "NOT_FOUND";
            }
            return user_profile_query_result;
        } catch (SQLException e) {
            e.printStackTrace();
            return new UserProfileQueryResult(); //TODO analyse result of return
        }
    }

    public static boolean verifyUser(UserProfileRequestAttribute user_profile_query) {
        try {
            Statement statement = ServerThread.databaseConnector.connector.createStatement();
            statement.executeQuery("call skncoe_computer_department.verify_user('" + user_profile_query.username + "',@result);");
            ResultSet resultSet = statement.executeQuery("select @result");
            resultSet.next();
            boolean verified_successfully = resultSet.getBoolean(1);
            return verified_successfully;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<PendingBooksRecordQueryResult> getPendingBooksRecord(UserProfileRequestAttribute user_profile_query) {
        try {
            List<PendingBooksRecordQueryResult> pending_records_query_result_set = new ArrayList<>();
            Statement statement = ServerThread.databaseConnector.connector.createStatement();
            ResultSet resultSet = statement.executeQuery("select constraint_value from book_issue_constraints where constraint_type='FINE_PER_DAY';");
            resultSet.next();
            int FINE_PER_DAY = resultSet.getInt(1);
            resultSet = statement.executeQuery("select constraint_value from book_issue_constraints where constraint_type='ALLOWED_NO_OF_DAYS';");
            resultSet.next();
            int ALLOWED_NO_OF_DAYS = resultSet.getInt(1);
            resultSet = statement.executeQuery("select bir.book_id , bt.book_title, date(bir.date_of_issue) ,datediff(date(now()),date(bir.date_of_issue)) from book_issue_record as bir inner join book_titles as bt on bir.username='" + user_profile_query.username + "' and bir.date_of_return is null  and bt.book_id=bir.book_id;");
            while (resultSet.next()) {
                PendingBooksRecordQueryResult pending_query_result = new PendingBooksRecordQueryResult();
                pending_query_result.book_id = resultSet.getInt(1);
                pending_query_result.book_title = resultSet.getString(2);
                pending_query_result.date_of_issue = resultSet.getString(3);
                int total_days_issued = resultSet.getInt(4);
                int due_days = total_days_issued - ALLOWED_NO_OF_DAYS;
                if (due_days > 0) {
                    pending_query_result.due_days = due_days;
                    pending_query_result.fine_amount = due_days * FINE_PER_DAY;
                }
                pending_records_query_result_set.add(pending_query_result);
            }
            return pending_records_query_result_set;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<PendingBooksRecordQueryResult>();
        }
    }

    public static List<PendingReportRecordQueryResult> getPendingReportRecord(UserProfileRequestAttribute user_profile_query) {
        try {
            List<PendingReportRecordQueryResult> pending_report_record_query_result_set = new ArrayList<>();
            Statement statement = ServerThread.databaseConnector.connector.createStatement();
            ResultSet resultSet = statement.executeQuery("select rt.report_id,rt.report_title, date(rir.date_of_issue) from report_titles as rt inner join report_issue_record as rir on rir.username='" + user_profile_query.username + "' and rt.report_id=rir.report_id and rir.date_of_return is null;");
            while (resultSet.next()) {
                PendingReportRecordQueryResult pending_report_query_result = new PendingReportRecordQueryResult();
                pending_report_query_result.report_id = resultSet.getInt(1);
                pending_report_query_result.report_title = resultSet.getString(2);
                pending_report_query_result.date_of_issue = resultSet.getString(3);
                pending_report_record_query_result_set.add(pending_report_query_result);
            }
            return pending_report_record_query_result_set;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<PendingReportRecordQueryResult>();
        }
    }

    public static boolean returnBook(BookReturnAttributes book_return_query) {
        try {
            Statement statement = ServerThread.databaseConnector.connector.createStatement();
            ResultSet resultSet = statement.executeQuery("call skncoe_computer_department.return_book('" + book_return_query.username + "'," + book_return_query.book_id + "," + book_return_query.is_fine + ",@result);");
            resultSet = statement.executeQuery("select @result");
            resultSet.next();
            if (resultSet.getBoolean(1)) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean returnReport(ReportReturnAttributes report_return_query) {
        try {
            Statement statement = ServerThread.databaseConnector.connector.createStatement();
            ResultSet resultSet = statement.executeQuery("call skncoe_computer_department.return_report('" + report_return_query.username + "'," + report_return_query.report_id + ",@result);");
            resultSet = statement.executeQuery("select @result");
            resultSet.next();
            if (resultSet.getBoolean(1)) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String renewBook(BookReturnAttributes book_renew_query) {
        try {
            Statement statement = ServerThread.databaseConnector.connector.createStatement();
            ResultSet resultSet = statement.executeQuery("call skncoe_computer_department.renew_book('" + book_renew_query.username + "'," + book_renew_query.book_id + "," + book_renew_query.is_fine + ",@result,@date_of_return,@resultString);");

            resultSet = statement.executeQuery("select @result");
            resultSet.next();

            resultSet = statement.executeQuery("select @resultString");
            resultSet.next();
            String resultString = resultSet.getString(1);
            if (resultString.equals("RENEWED")) {
                resultSet = statement.executeQuery("select @date_of_return;");
                resultSet.next();
                return resultSet.getString(1);
            } else {
                return resultString;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "NOT_RETURNED";
        }
    }

    static public int[] clearBookIssueRecord() {
        int result[] = new int[2];
        result[0] = 0;
        result[1] = 0;
        try {
            Statement statement = ServerThread.databaseConnector.connector.createStatement();
            ResultSet resultSet = statement.executeQuery("call skncoe_computer_department.clear_book_issue_record(@result,@total_fine_amount,@total_deleted_records);");
            resultSet = statement.executeQuery("select @result");
            resultSet.next();
            if (resultSet.getBoolean(1)) {

                resultSet = statement.executeQuery("select @total_deleted_records;");
                resultSet.next();
                result[0] = resultSet.getInt(1);
                resultSet = statement.executeQuery("select @total_fine_amount");
                resultSet.next();
                result[1] = resultSet.getInt(1);
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return result;
        }
    }

    static public int clearReportIssueRecord() {
        try {
            Statement statement = ServerThread.databaseConnector.connector.createStatement();
            ResultSet resultSet = statement.executeQuery("call skncoe_computer_department.clear_report_issue_record(@result,@total_deleted_records);");
            resultSet = statement.executeQuery("select @result");
            resultSet.next();
            if (resultSet.getBoolean(1)) {
                resultSet = statement.executeQuery("select @total_deleted_records;");
                resultSet.next();
                return resultSet.getInt(1);
            } else {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static List<UserProfileQueryResult> getUsersWithOverDuePendingRecords() {
        try {
            List<UserProfileQueryResult> users_with_pending_records_result_set = new ArrayList<>();
            Statement statement = ServerThread.databaseConnector.connector.createStatement();
            ResultSet resultSet = statement.executeQuery("select constraint_value from book_issue_constraints where constraint_type='ALLOWED_NO_OF_DAYS';");
            resultSet.next();
            int ALLOWED_NO_OF_DAYS = resultSet.getInt(1);
            resultSet = statement.executeQuery("select distinct username from book_issue_record where date_of_return is null and datediff(date(now()),date(date_of_issue))>" + ALLOWED_NO_OF_DAYS + ";");
            while (resultSet.next()) {
                UserProfileRequestAttribute user_profile_query = new UserProfileRequestAttribute();
                user_profile_query.username = resultSet.getString(1);
                UserProfileQueryResult user_profile_query_result = QueryFunctions.getUserProfile(user_profile_query);
                users_with_pending_records_result_set.add(user_profile_query_result);
            }
            return users_with_pending_records_result_set;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<UserProfileQueryResult>();
        }
    }

    public static List<UserProfileQueryResult> getUsersWithPendingRecords() {
        try {
            List<UserProfileQueryResult> users_with_pending_records_result_set = new ArrayList<>();
            Statement statement = ServerThread.databaseConnector.connector.createStatement();

            //ResultSet resultSet = statement.executeQuery("select constraint_value from book_issue_constraints where constraint_type='ALLOWED_NO_OF_DAYS';");
            //resultSet.next();
            //int ALLOWED_NO_OF_DAYS = resultSet.getInt(1);
            ResultSet resultSet = statement.executeQuery("select distinct username from book_issue_record where date_of_return is null;");
            while (resultSet.next()) {
                UserProfileRequestAttribute user_profile_query = new UserProfileRequestAttribute();
                user_profile_query.username = resultSet.getString(1);
                UserProfileQueryResult user_profile_query_result = QueryFunctions.getUserProfile(user_profile_query);
                users_with_pending_records_result_set.add(user_profile_query_result);
            }
            return users_with_pending_records_result_set;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<UserProfileQueryResult>();
        }
    }

    public static boolean resetStudentsVerification() {
        try {
            Statement statement = ServerThread.databaseConnector.connector.createStatement();
            statement.executeQuery("call skncoe_computer_department.refresh_student_data(@result);");
            ResultSet resultSet = statement.executeQuery("select @result");
            resultSet.next();
            return resultSet.getBoolean(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteUserProfile(UserProfileRequestAttribute user_query) {
        try {
            Statement statement = ServerThread.databaseConnector.connector.createStatement();
            statement.executeQuery("call skncoe_computer_department.delete_user_profile('" + user_query.username + "',@result,@has_pending_library_record);");
            ResultSet resultSet = statement.executeQuery("select @result");
            resultSet.next();
            return resultSet.getBoolean(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean giveClearanceToStudent(UserProfileRequestAttribute user_profile_query) {
        try {
            Statement statement = ServerThread.databaseConnector.connector.createStatement();
            statement.executeQuery("call skncoe_computer_department.mark_passout_students_as_alumni('" + user_profile_query.username + "',@result,@has_pending_library_record);");
            ResultSet resultSet = statement.executeQuery("select @result");
            resultSet.next();
            return resultSet.getBoolean(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<UserProfileQueryResult> getAlumniListStudentsList(String year) {
        try {
            List<UserProfileQueryResult> alumni_students_list = new ArrayList<>();
            Statement statement = ServerThread.databaseConnector.connector.createStatement();
            ResultSet resultSet = statement.executeQuery("select students.username from students where students.passing_year='" + year + "' and students.profile_type=2;");
            while (resultSet.next()) {
                UserProfileRequestAttribute user_profile_query = new UserProfileRequestAttribute();
                user_profile_query.username = resultSet.getString(1);
                UserProfileQueryResult user_profile_query_result = QueryFunctions.getUserProfile(user_profile_query);
                alumni_students_list.add(user_profile_query_result);
            }
            return alumni_students_list;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<UserProfileQueryResult>();
        }
    }

    public static List<PublisherQueryResult> getPublishersList() {
        try {
            List<PublisherQueryResult> publishers_list = new ArrayList<>();
            Statement statement = ServerThread.databaseConnector.connector.createStatement();
            ResultSet resultSet = statement.executeQuery("select* from publishers");
            while (resultSet.next()) {
                PublisherQueryResult publisher_result = new PublisherQueryResult();
                publisher_result.publisher_id = resultSet.getInt(1);
                publisher_result.publisher_name = resultSet.getString(2);
                publishers_list.add(publisher_result);
            }
            return publishers_list;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<PublisherQueryResult>();
        }
    }

    public static List<DomainQueryResult> getDomainsList() {
        try {
            List<DomainQueryResult> domains_list = new ArrayList<>();
            Statement statement = ServerThread.databaseConnector.connector.createStatement();
            ResultSet resultSet = statement.executeQuery("select* from domains");
            while (resultSet.next()) {
                DomainQueryResult domain_result = new DomainQueryResult();
                domain_result.domain_id = resultSet.getInt(1);
                domain_result.domain_name = resultSet.getString(2);
                domains_list.add(domain_result);
            }
            return domains_list;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<DomainQueryResult>();
        }
    }

    public static List<AccountsQueryResult> getAccountsList() {
        try {
            List<AccountsQueryResult> accounts_list = new ArrayList<>();
            Statement statement = ServerThread.databaseConnector.connector.createStatement();
            ResultSet resultSet = statement.executeQuery("select* from accounts");
            while (resultSet.next()) {
                AccountsQueryResult account_result = new AccountsQueryResult();
                account_result.account_number = resultSet.getInt(1);
                account_result.date_of_account = resultSet.getString(2);
                account_result.total_fine_amount = resultSet.getInt(3);
                accounts_list.add(account_result);
            }
            return accounts_list;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<AccountsQueryResult>();
        }
    }

    public static List<SubjectQueryResult> getSubjectsList() {
        try {
            List<SubjectQueryResult> subjects_list = new ArrayList<>();
            Statement statement = ServerThread.databaseConnector.connector.createStatement();
            ResultSet resultSet = statement.executeQuery("select* from subjects");
            while (resultSet.next()) {
                SubjectQueryResult subject_result = new SubjectQueryResult();
                subject_result.subject_id = resultSet.getInt(1);
                subject_result.subject_name = resultSet.getString(2);
                subject_result.semester = resultSet.getString(3);
                subject_result.academic_year = resultSet.getString(4);
                subject_result.exam_pattern = resultSet.getString(5);
                subject_result.domain_id = resultSet.getInt(6);
                subject_result.subject_abbreviation = resultSet.getString(7);
                subjects_list.add(subject_result);
            }
            return subjects_list;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<SubjectQueryResult>();
        }
    }

    public static List<BookTitleQueryResult> getBookTitleList() {
        try {
            List<BookTitleQueryResult> book_title_list = new ArrayList<>();
            Statement statement = ServerThread.databaseConnector.connector.createStatement();
            ResultSet resultSet = statement.executeQuery("select* from book_titles");
            while (resultSet.next()) {
                BookTitleQueryResult book_title = new BookTitleQueryResult();
                book_title.book_id = resultSet.getInt(1);
                book_title.book_author = resultSet.getString(2);
                book_title.book_title = resultSet.getString(3);
                book_title.no_of_copies = resultSet.getInt(4);
                book_title.available_copies = resultSet.getInt(5);
                book_title.publisher_id = resultSet.getInt(6);
                book_title.domain_id = resultSet.getInt(7);
                book_title.cupboard_no = resultSet.getInt(8);
                book_title.shelf_no = resultSet.getInt(9);
                book_title_list.add(book_title);
                Statement statementForNameByID = ServerThread.databaseConnector.connector.createStatement();
                ResultSet nameByID = statementForNameByID.executeQuery("select publisher_name from publishers where publisher_id=" + book_title.publisher_id + ";");
                nameByID.next();
                book_title.publisher_name = nameByID.getString(1);
                nameByID = statementForNameByID.executeQuery("select domain_name from domains where domain_id=" + book_title.domain_id + ";");
                nameByID.next();
                book_title.domain_name = nameByID.getString(1);
            }
            return book_title_list;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<BookTitleQueryResult>();
        }
    }

    public static List<ReportTitleQueryResult> getReportTitleList() {
        try {
            List<ReportTitleQueryResult> report_title_list = new ArrayList<>();
            Statement statement = ServerThread.databaseConnector.connector.createStatement();
            ResultSet resultSet = statement.executeQuery("select* from report_titles");
            while (resultSet.next()) {
                ReportTitleQueryResult report_title = new ReportTitleQueryResult();
                report_title.report_id = resultSet.getInt(1);
                report_title.report_title = resultSet.getString(2);
                report_title.domain_id = resultSet.getInt(3);
                report_title.availability_status = resultSet.getInt(4);
                report_title.cupboard_no = resultSet.getInt(5);
                report_title.shelf_no = resultSet.getInt(6);

                Statement statementForNameByID = ServerThread.databaseConnector.connector.createStatement();
                ResultSet nameByID = statementForNameByID.executeQuery("select domain_name from domains where domain_id=" + report_title.domain_id + ";");
                nameByID.next();
                report_title.domain_name = nameByID.getString(1);

                report_title_list.add(report_title);
            }
            return report_title_list;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<ReportTitleQueryResult>();
        }
    }

    public static boolean addBookTitle(AddBookTitleAttributes add_book_query) {
        try {
            Statement statement = ServerThread.databaseConnector.connector.createStatement();
            int result = statement.executeUpdate("insert into book_titles(book_author, book_title, no_of_copies, available_copies, publisher_id, domain_id, cupboard_no, shelf_no) values('" + add_book_query.book_author
                    + "', '" + add_book_query.book_title
                    + "', " + add_book_query.no_of_copies
                    + ", " + add_book_query.available_copies
                    + ", " + add_book_query.publisher_id
                    + ", " + add_book_query.domain_id
                    + "," + add_book_query.cupboard_no
                    + "," + add_book_query.shelf_no + ");");
            if (result == 0) {
                return false;
            } else {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean addReportTitle(AddReportTitleAttributes add_report_query) {
        try {
            Statement statement = ServerThread.databaseConnector.connector.createStatement();
            int result = statement.executeUpdate("insert into report_titles(report_title, domain_id, availability_status, cupboard_no, shelf_no) values('" + add_report_query.report_title
                    + "', " + add_report_query.domain_id
                    + ", " + 1
                    + "," + add_report_query.cupboard_no
                    + "," + add_report_query.shelf_no + ");");
            if (result == 0) {
                return false;
            } else {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean addDomain(AddDomainAttributes add_domain_query) {
        try {
            Statement statement = ServerThread.databaseConnector.connector.createStatement();
            int result = statement.executeUpdate("insert into domains(domain_name) values('" + add_domain_query.domain_name + "');");
            if (result == 0) {
                return false;
            } else {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean addPublisher(AddPublisherAttributes add_publisher_query) {
        try {
            Statement statement = ServerThread.databaseConnector.connector.createStatement();
            int result = statement.executeUpdate("insert into publishers(publisher_name) values('" + add_publisher_query.publisher_name + "');");
            if (result == 0) {
                return false;
            } else {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean addSubject(AddSubjectAttributes add_subject_query) {
        try {
            Statement statement = ServerThread.databaseConnector.connector.createStatement();
            int result = statement.executeUpdate("insert into subjects(subject_name,semester,academic_year,exam_pattern,domain_id,subject_abbreviation) values('" + add_subject_query.subject_name
                    + "','" + add_subject_query.semester
                    + "','" + add_subject_query.academic_year
                    + "','" + add_subject_query.exam_pattern
                    + "'," + add_subject_query.domain_id
                    + ",'" + add_subject_query.subject_abbreviation + "');");
            if (result == 0) {
                return false;
            } else {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean addLibraryContent(AddLibraryContentAttributes add_library_content_query) {
        try {

            Statement statement = ServerThread.databaseConnector.connector.createStatement();
            //statement.execute("SET GLOBAL max_allowed_packet=17825792;");  //17 MB needs SUPER or SYSTEM_VARIABLES_ADMIN privilege
//            String query="insert into library_content(subject_id,username,type_id,content_title,content_file) values('" + add_library_content_query.subject_id
//                    + "','" + add_library_content_query.username
//                    + "','" + add_library_content_query.type_id
//                    + "','" + add_library_content_query.content_title
//                    + "',?);";
            String query = "insert into library_content(subject_id,username,type_id,content_title,content_file,content_file_extension,content_file_size) values(?,?,?,?,?,?,?);";
            PreparedStatement preparedStatement = ServerThread.databaseConnector.connector.prepareStatement(query);
            preparedStatement.setInt(1, add_library_content_query.subject_id);
            preparedStatement.setString(2, add_library_content_query.username);
            preparedStatement.setInt(3, add_library_content_query.type_id);
            preparedStatement.setString(4, add_library_content_query.content_title);
            File content_file = new File(add_library_content_query.content_file_absolute_path);
            InputStream inputStream = new FileInputStream(content_file);
            preparedStatement.setBlob(5, inputStream);
            String file_extension = content_file.getName().substring(content_file.getName().lastIndexOf("."));
            preparedStatement.setString(6, file_extension);
            preparedStatement.setString(7, Long.toString(content_file.length()));
            int result = preparedStatement.executeUpdate();
            try {
                inputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(QueryFunctions.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (result == 0) {
                return false;
            } else {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            Logger
                    .getLogger(QueryFunctions.class
                            .getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static AddBookTitleAttributes getBookTitle(int book_id) {
        AddBookTitleAttributes book_query_result = new AddBookTitleAttributes();
        try {
            Statement statement = ServerThread.databaseConnector.connector.createStatement();
            ResultSet resultSet = statement.executeQuery("select* from book_titles where book_titles.book_id=" + book_id + ";");
            resultSet.next();
            book_query_result.book_id = resultSet.getInt(1);
            book_query_result.book_author = resultSet.getString(2);
            book_query_result.book_title = resultSet.getString(3);
            book_query_result.no_of_copies = resultSet.getInt(4);
            book_query_result.available_copies = resultSet.getInt(5);
            book_query_result.publisher_id = resultSet.getInt(6);
            book_query_result.domain_id = resultSet.getInt(7);
            book_query_result.cupboard_no = resultSet.getInt(8);
            book_query_result.shelf_no = resultSet.getInt(9);
            return book_query_result;
        } catch (SQLException e) {
            e.printStackTrace();
            return book_query_result;
        }
    }

    public static AddReportTitleAttributes getReportTitle(int report_id) {
        AddReportTitleAttributes report_query_result = new AddReportTitleAttributes();
        try {
            Statement statement = ServerThread.databaseConnector.connector.createStatement();
            ResultSet resultSet = statement.executeQuery("select* from report_titles where report_titles.report_id=" + report_id + ";");
            resultSet.next();
            report_query_result.report_id = resultSet.getInt(1);
            report_query_result.report_title = resultSet.getString(2);
            report_query_result.domain_id = resultSet.getInt(3);
            report_query_result.availability_status = resultSet.getInt(4);
            report_query_result.cupboard_no = resultSet.getInt(5);
            report_query_result.shelf_no = resultSet.getInt(6);
            return report_query_result;
        } catch (SQLException e) {
            e.printStackTrace();
            return report_query_result;
        }
    }

    public static AddDomainAttributes getDomain(int domain_id) {
        AddDomainAttributes domain_query_result = new AddDomainAttributes();
        try {
            Statement statement = ServerThread.databaseConnector.connector.createStatement();
            ResultSet resultSet = statement.executeQuery("select* from domains where domains.domain_name =" + domain_id + ";");
            resultSet.next();
            domain_query_result.domain_id = resultSet.getInt(1);
            domain_query_result.domain_name = resultSet.getString(2);
            return domain_query_result;
        } catch (SQLException e) {
            e.printStackTrace();
            return domain_query_result;
        }
    }

    public static AddPublisherAttributes getPublisher(int publisher_id) {
        AddPublisherAttributes publisher_query_result = new AddPublisherAttributes();
        try {
            Statement statement = ServerThread.databaseConnector.connector.createStatement();
            ResultSet resultSet = statement.executeQuery("select* from publishers where publishers.publisher_id=" + publisher_id + ";");
            resultSet.next();
            publisher_query_result.publisher_id = resultSet.getInt(1);
            publisher_query_result.publisher_name = resultSet.getString(2);
            return publisher_query_result;
        } catch (SQLException e) {
            e.printStackTrace();
            return publisher_query_result;
        }
    }

    public static AddSubjectAttributes getSubject(int subject_id) {
        AddSubjectAttributes subject_query_result = new AddSubjectAttributes();
        try {
            Statement statement = ServerThread.databaseConnector.connector.createStatement();
            ResultSet resultSet = statement.executeQuery("select* from subjects where subjects.subject_id=" + subject_id + ";");
            resultSet.next();
            subject_query_result.subject_id = resultSet.getInt(1);
            subject_query_result.subject_name = resultSet.getString(2);
            subject_query_result.semester = resultSet.getString(3);
            subject_query_result.academic_year = resultSet.getString(4);
            subject_query_result.exam_pattern = resultSet.getString(5);
            subject_query_result.domain_id = resultSet.getInt(6);
            subject_query_result.subject_abbreviation = resultSet.getString(7);
            return subject_query_result;
        } catch (SQLException e) {
            e.printStackTrace();
            return subject_query_result;
        }
    }

    public static boolean updateBookTitle(AddBookTitleAttributes update_book_query) {
        try {
            Statement statement = ServerThread.databaseConnector.connector.createStatement();
            statement.executeUpdate("call skncoe_computer_department.update_book_title(" + update_book_query.book_id + ",'" + update_book_query.book_author + "','" + update_book_query.book_title + "'," + update_book_query.no_of_copies + "," + update_book_query.available_copies + "," + update_book_query.publisher_id + "," + update_book_query.domain_id + "," + update_book_query.cupboard_no + "," + update_book_query.shelf_no + ",@result);");
            ResultSet resultSet = statement.executeQuery("select @result");
            resultSet.next();
            return resultSet.getBoolean(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateReportTitle(AddReportTitleAttributes update_report_query) {
        try {
            Statement statement = ServerThread.databaseConnector.connector.createStatement();
            statement.executeUpdate("call skncoe_computer_department.update_report_title(" + update_report_query.report_id + ",'" + update_report_query.report_title + "'," + update_report_query.domain_id + "," + update_report_query.cupboard_no + "," + update_report_query.shelf_no + ",@result);");
            ResultSet resultSet = statement.executeQuery("select @result");
            resultSet.next();
            return resultSet.getBoolean(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateDomain(AddDomainAttributes update_domain_query) {
        try {
            Statement statement = ServerThread.databaseConnector.connector.createStatement();
            int result = statement.executeUpdate("update domains set domains.domain_name='" + update_domain_query.domain_name + "' where domains.domain_id=" + update_domain_query.domain_id + " ;");
            if (result != 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updatePublisher(AddPublisherAttributes update_publisher_query) {
        try {
            Statement statement = ServerThread.databaseConnector.connector.createStatement();
            int result = statement.executeUpdate("update publishers set publishers.publisher_name='" + update_publisher_query.publisher_name + "' where publishers.publisher_id=" + update_publisher_query.publisher_id + " ;");
            if (result != 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateSubject(AddSubjectAttributes update_subject_query) {
        try {
            Statement statement = ServerThread.databaseConnector.connector.createStatement();
            statement.executeUpdate("call skncoe_computer_department.update_subject('" + update_subject_query.subject_name + "'," + update_subject_query.subject_id + ",'" + update_subject_query.semester + "','" + update_subject_query.academic_year + "','" + update_subject_query.exam_pattern + "'," + update_subject_query.domain_id + ",'" + update_subject_query.subject_abbreviation + "',@result);");
            ResultSet resultSet = statement.executeQuery("select @result");
            resultSet.next();
            return resultSet.getBoolean(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<BookIssueConstraintsQueryResult> getBookIssueConstraints() {
        try {
            List<BookIssueConstraintsQueryResult> book_issue_constraints_set = new ArrayList<>();
            Statement statement = ServerThread.databaseConnector.connector.createStatement();
            ResultSet resultSet = statement.executeQuery("select* from book_issue_constraints;");
            while (resultSet.next()) {
                BookIssueConstraintsQueryResult book_issue_constraint = new BookIssueConstraintsQueryResult();
                book_issue_constraint.constraint_type = resultSet.getString(1);
                book_issue_constraint.constraint_value = resultSet.getInt(2);
                book_issue_constraints_set.add(book_issue_constraint);
            }
            return book_issue_constraints_set;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<BookIssueConstraintsQueryResult>();
        }
    }

    public static List<LibraryContentTypesQueryResult> getLibraryContentTypes() {
        try {
            List<LibraryContentTypesQueryResult> library_content_types_list = new ArrayList<>();
            Statement statement = ServerThread.databaseConnector.connector.createStatement();
            ResultSet resultSet = statement.executeQuery("select* from library_content_types;");
            while (resultSet.next()) {
                LibraryContentTypesQueryResult library_content_type = new LibraryContentTypesQueryResult();
                library_content_type.type_id = resultSet.getInt(1);
                library_content_type.type = resultSet.getString(2);
                library_content_types_list.add(library_content_type);
            }
            return library_content_types_list;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<LibraryContentTypesQueryResult>();
        }
    }

    public static List<String> getExamPatterns() {
        try {
            List<String> exam_patterns_list = new ArrayList<>();
            Statement statement = ServerThread.databaseConnector.connector.createStatement();
            ResultSet resultSet = statement.executeQuery("select distinct exam_pattern from subjects");
            while (resultSet.next()) {
                String exam_pattern = resultSet.getString(1);
                exam_patterns_list.add(exam_pattern);
            }
            return exam_patterns_list;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<String>();
        }
    }

    public static boolean updateBookIssueConstraints(List<BookIssueConstraintsQueryResult> book_issue_constraint_set) {
        try {
            Statement statement = ServerThread.databaseConnector.connector.createStatement();
            Iterator iterator = book_issue_constraint_set.iterator();
            boolean updated_successfully = false;
//        #Method to update each row of table non-atomic
//        while (iterator.hasNext()){
//            BookIssueConstraintsQueryResult book_issue_constraint=(BookIssueConstraintsQueryResult) iterator.next();
//             int update_count=statement.executeUpdate("update book_issue_constraints set constraint_value="+book_issue_constraint.constraint_value+" where constraint_type= '"+book_issue_constraint.constraint_type+"';");
//             if(update_count!=0){
//                 updated_successfully=true;
//             }
//        }
            int ALLOWED_NO_OF_DAYS = book_issue_constraint_set.get(0).constraint_value;
            int FINE_PER_DAY = book_issue_constraint_set.get(1).constraint_value;
            int MAX_BOOKS_ALLOWED = book_issue_constraint_set.get(2).constraint_value;

            statement.executeQuery("call skncoe_computer_department.update_book_issue_constraints(" + ALLOWED_NO_OF_DAYS + "," + FINE_PER_DAY + "," + MAX_BOOKS_ALLOWED + ",@result);");
            ResultSet resultSet = statement.executeQuery("select @result");
            resultSet.next();
            updated_successfully = resultSet.getBoolean(1);
            return updated_successfully;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean checkWeatherUsernameIsAvailable(String input_username) {
        try {
            Statement statement = ServerThread.databaseConnector.connector.createStatement();
            ResultSet resultSet = statement.executeQuery("select count(username) from users where username='" + input_username + "';");
            resultSet.next();
            int username_count = resultSet.getInt(1);
            if (username_count == 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean registerStudentProfile(Student student_profile) {
        try {
            Statement statement = ServerThread.databaseConnector.connector.createStatement();
            statement.executeUpdate("call skncoe_computer_department.register_student_profile('" + student_profile.username + "','" + student_profile.first_name + "', '" + student_profile.last_name + "', '" + student_profile.email + "', '" + student_profile.mobile_no + "','" + student_profile.password + "','" + student_profile.academic_year + "','" + student_profile.unique_id + "','" + student_profile.division + "','" + student_profile.dob + "','" + student_profile.permanent_address + "',@result);");
            ResultSet resultSet = statement.executeQuery("select @result");
            resultSet.next();
            int registered_successfully = resultSet.getInt(1);
            if (registered_successfully == 1) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean registerEmployeeProfile(Employee employee_profile) {
        try {
            Statement statement = ServerThread.databaseConnector.connector.createStatement();
            statement.executeUpdate("call skncoe_computer_department.register_employee_profile('" + employee_profile.username + "','" + employee_profile.first_name + "', '" + employee_profile.last_name + "', '" + employee_profile.email + "', '" + employee_profile.mobile_no + "','" + employee_profile.password + "','" + employee_profile.employee_no + "',@result);");
            ResultSet resultSet = statement.executeQuery("select @result");
            resultSet.next();
            int registered_successfully = resultSet.getInt(1);
            if (registered_successfully == 1) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateStudentProfile(Student student_profile) {
        try {
            Statement statement = ServerThread.databaseConnector.connector.createStatement();
            statement.executeUpdate("call skncoe_computer_department.update_student_profile('" + student_profile.username + "','" + student_profile.first_name + "', '" + student_profile.last_name + "', '" + student_profile.email + "', '" + student_profile.mobile_no + "','" + student_profile.academic_year + "','" + student_profile.unique_id + "','" + student_profile.division + "','" + student_profile.dob + "','" + student_profile.permanent_address + "',@result);");
            ResultSet resultSet = statement.executeQuery("select @result");
            resultSet.next();
            int updated_successfully = resultSet.getInt(1);
            if (updated_successfully == 1) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updatedEmployeeProfile(Employee employee_profile) {
        try {
            Statement statement = ServerThread.databaseConnector.connector.createStatement();
            statement.executeUpdate("call skncoe_computer_department.update_employee_profile('" + employee_profile.username + "','" + employee_profile.first_name + "', '" + employee_profile.last_name + "', '" + employee_profile.email + "', '" + employee_profile.mobile_no + "','" + employee_profile.employee_no + "',@result);");
            ResultSet resultSet = statement.executeQuery("select @result");
            resultSet.next();
            int updated_successfully = resultSet.getInt(1);
            if (updated_successfully == 1) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteLibraryContent(String exam_pattern) {
        try {
            Statement statement = ServerThread.databaseConnector.connector.createStatement();
            int result = statement.executeUpdate("delete from library_content where subject_id in (select subject_id from subjects where exam_pattern='" + exam_pattern + "');");

            if (result > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Statistics getStatistics() {
        try {
            Statement statement = ServerThread.databaseConnector.connector.createStatement();
            Statistics statistics = new Statistics();
            ResultSet resultSet = statement.executeQuery("select count(content_id) from library_content");
            resultSet.next();
            statistics.total_library_content = resultSet.getInt(1);
            resultSet = statement.executeQuery("select count(book_id) from book_titles");
            resultSet.next();
            statistics.total_books = resultSet.getInt(1);
            resultSet = statement.executeQuery("select count(report_id) from report_titles");
            resultSet.next();
            statistics.total_reports = resultSet.getInt(1);
            return statistics;
        } catch (SQLException e) {
            e.printStackTrace();
            return new Statistics();
        }
    }
}
