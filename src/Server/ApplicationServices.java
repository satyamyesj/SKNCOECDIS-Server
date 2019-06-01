package Server;

import DatabaseAccessFunctions.QueryFunctions;
import DatabaseAccessObjects.QueryObjects.*;
import DatabaseAccessObjects.ResultObjects.*;

import java.io.IOException;
import java.util.List;

public class ApplicationServices {

    static public void userCredentialsVerification() throws IOException, ClassNotFoundException {
        ServerThread.printWriter.println("SERVER_READY");
        User user = (User) ServerThread.objIn.readObject();
        boolean successfulSignUp = QueryFunctions.login(user);
        System.out.println("Result: " + successfulSignUp);
        if (successfulSignUp) {
            ServerThread.dataOut.writeBoolean(true);
            user = QueryFunctions.getUserWithPassword(user);
            System.out.println(user.username);
            ServerThread.objOut.writeObject(user);
        } else {
            ServerThread.dataOut.writeBoolean(false);
        }
    }

    static public void returnBookSearchQueryResult() throws IOException, ClassNotFoundException {
        ServerThread.printWriter.println("SERVER_READY");
        BookSearchAttributes book_query = (BookSearchAttributes) ServerThread.objIn.readObject();
        List<BookSearchQueryResult> book_query_result_set = QueryFunctions.searchBook(book_query);
        System.out.println("Result: " + book_query_result_set.size());
        ServerThread.objOut.writeObject(book_query_result_set);
    }

    public static void returnReportSearchQueryResult() throws IOException, ClassNotFoundException {
        ServerThread.printWriter.println("SERVER_READY");
        ReportSearchAttributes report_query = (ReportSearchAttributes) ServerThread.objIn.readObject();
        List<ReportSearchQueryResult> report_query_result_set = QueryFunctions.searchReport(report_query);
        System.out.println("Result:" + report_query_result_set.size());
        ServerThread.objOut.writeObject(report_query_result_set);
    }

    static void returnLibrayContentSearchQueryResult() throws IOException, ClassNotFoundException {
        ServerThread.printWriter.println("SERVER_READY");
        LibraryContentSearchAttributes library_content_query = (LibraryContentSearchAttributes) ServerThread.objIn.readObject();
        List<LibraryContentSearchQueryResult> library_content_query_result_set = QueryFunctions.searchLibraryContent(library_content_query);
        System.out.println("Result: " + library_content_query_result_set.size());
        ServerThread.objOut.writeObject(library_content_query_result_set);
    }

    static void returnLibrayContentFileQueryResult() throws IOException {
        ServerThread.printWriter.println("SERVER_READY");
        int content_id = ServerThread.dataIn.readInt();
        LibrayContentFileQueryResult library_content_file = QueryFunctions.getLibraryContentFile(content_id);
        System.out.println("Result: " + library_content_file.content_file_absolute_path);
        ServerThread.objOut.writeObject(library_content_file);
    }

    public static void returnUserSearchQueryResult() throws IOException, ClassNotFoundException {
        ServerThread.printWriter.println("SERVER_READY");
        UserSearchAttributes user_search_query = (UserSearchAttributes) ServerThread.objIn.readObject();
        List<UserProfileQueryResult> user_search_query_result_set = QueryFunctions.searchUser(user_search_query);
        System.out.println("Result: " + user_search_query_result_set.size());
        ServerThread.objOut.writeObject(user_search_query_result_set);
    }

    static public void returnBookIssueQueryResult() throws IOException, ClassNotFoundException {
        ServerThread.printWriter.println("SERVER_READY");
        BookIssueAttributes book_issue_query = (BookIssueAttributes) ServerThread.objIn.readObject();
        String date_of_return = QueryFunctions.issueBook(book_issue_query);
        System.out.println("Result: " + date_of_return);
        ServerThread.printWriter.println(date_of_return);
    }

    public static void returnReportIssueQueryResult() throws IOException, ClassNotFoundException {
        ServerThread.printWriter.println("SERVER_READY");
        BookIssueAttributes report_issue_query = (BookIssueAttributes) ServerThread.objIn.readObject();
        boolean issued_successfully = QueryFunctions.issueReport(report_issue_query);
        System.out.println("Result: " + issued_successfully);
        ServerThread.dataOut.writeBoolean(issued_successfully);
    }

    static public void returnUserProfile() throws IOException, ClassNotFoundException {
        ServerThread.printWriter.println("SERVER_READY");
        UserProfileRequestAttribute user_profile_query = (UserProfileRequestAttribute) ServerThread.objIn.readObject();
        UserProfileQueryResult user_profile_query_result = QueryFunctions.getUserProfile(user_profile_query);
        System.out.println("Result: " + user_profile_query_result.user_role);
        ServerThread.objOut.writeObject(user_profile_query_result);
    }

    public static void userProfileVerification() throws IOException, ClassNotFoundException {
        ServerThread.printWriter.println("SERVER_READY");
        UserProfileRequestAttribute user_profile_query = (UserProfileRequestAttribute) ServerThread.objIn.readObject();
        boolean verified_successfully = QueryFunctions.verifyUser(user_profile_query);
        System.out.println("Result: " + verified_successfully);
        ServerThread.dataOut.writeBoolean(verified_successfully);
    }

    public static void returnPendingBooksRecord() throws IOException, ClassNotFoundException {
        ServerThread.printWriter.println("SERVER_READY");
        UserProfileRequestAttribute user_profile_query = (UserProfileRequestAttribute) ServerThread.objIn.readObject();
        List<PendingBooksRecordQueryResult> pending_books_record_query_result_set = QueryFunctions.getPendingBooksRecord(user_profile_query);
        System.out.println("Result: " + pending_books_record_query_result_set.size());
        ServerThread.objOut.writeObject(pending_books_record_query_result_set);
    }

    public static void returnPendingReportRecord() throws IOException, ClassNotFoundException {
        ServerThread.printWriter.println("SERVER_READY");
        UserProfileRequestAttribute user_profile_query = (UserProfileRequestAttribute) ServerThread.objIn.readObject();
        List<PendingReportRecordQueryResult> pending_report_record_query_result_set = QueryFunctions.getPendingReportRecord(user_profile_query);
        System.out.println("Result: " + pending_report_record_query_result_set.size());
        ServerThread.objOut.writeObject(pending_report_record_query_result_set);
    }

    public static void returnBookReturnQueryResult() throws IOException, ClassNotFoundException {
        ServerThread.printWriter.println("SERVER_READY");
        BookReturnAttributes book_return_query = (BookReturnAttributes) ServerThread.objIn.readObject();
        boolean returned_successfully = QueryFunctions.returnBook(book_return_query);
        System.out.println("Result: " + returned_successfully);
        ServerThread.dataOut.writeBoolean(returned_successfully);
    }

    public static void returnReportReturnQueryResult() throws IOException, ClassNotFoundException {
        ServerThread.printWriter.println("SERVER_READY");
        ReportReturnAttributes report_return_query = (ReportReturnAttributes) ServerThread.objIn.readObject();
        boolean returned_successfully = QueryFunctions.returnReport(report_return_query);
        System.out.println("Result: " + returned_successfully);
        ServerThread.dataOut.writeBoolean(returned_successfully);
    }

    public static void returnBookRenewQueryResult() throws IOException, ClassNotFoundException {
        ServerThread.printWriter.println("SERVER_READY");
        BookReturnAttributes book_renew_query = (BookReturnAttributes) ServerThread.objIn.readObject();
        String date_of_return;
        date_of_return = QueryFunctions.renewBook(book_renew_query);
        System.out.println("Result: " + date_of_return);
        ServerThread.printWriter.println(date_of_return);
    }

    public static void returnTotalClearedRecordsCount() throws IOException {
        ServerThread.printWriter.println("SERVER_READY");
        int result[] = QueryFunctions.clearBookIssueRecord();
        ServerThread.dataOut.writeInt(result[0]);  //total_cleared_records
        ServerThread.dataOut.writeInt(result[1]); //total_fine_amount
        System.out.println("Result1: " + result[0] + ", " + result[1]);
        int total_cleared_report_records = QueryFunctions.clearReportIssueRecord();
        System.out.println("Result2: " + total_cleared_report_records);
        ServerThread.dataOut.writeInt(total_cleared_report_records);
    }

    public static void returnUsersWithOverDuePendingRecords() throws IOException {
        ServerThread.printWriter.println("SERVER_READY");
        List<UserProfileQueryResult> users_with_pending_records_result_set = QueryFunctions.getUsersWithOverDuePendingRecords();
        System.out.println("Result: " + users_with_pending_records_result_set.size());
        ServerThread.objOut.writeObject(users_with_pending_records_result_set);
    }

    public static void returnUsersWithPendingRecords() throws IOException {
        ServerThread.printWriter.println("SERVER_READY");
        List<UserProfileQueryResult> users_with_pending_records_result_set = QueryFunctions.getUsersWithPendingRecords();
        System.out.println("Result: " + users_with_pending_records_result_set.size());
        ServerThread.objOut.writeObject(users_with_pending_records_result_set);
    }

    public static void returnResetStudentsVerificationResult() throws IOException {
        ServerThread.printWriter.println("SERVER_READY");
        boolean reset_success = QueryFunctions.resetStudentsVerification();
        System.out.println("Result: " + reset_success);
        ServerThread.dataOut.writeBoolean(reset_success);
    }

    public static void returnDeleteUserProfileQueryResult() throws IOException, ClassNotFoundException {
        ServerThread.printWriter.println("SERVER_READY");
        UserProfileRequestAttribute user_query = (UserProfileRequestAttribute) ServerThread.objIn.readObject();
        boolean deleted_successfully = QueryFunctions.deleteUserProfile(user_query);
        System.out.println("Result: " + deleted_successfully);
        ServerThread.dataOut.writeBoolean(deleted_successfully);
    }

    public static void returnGiveClearanceToStudentQueryResult() throws IOException, ClassNotFoundException {
        ServerThread.printWriter.println("SERVER_READY");
        UserProfileRequestAttribute user_profile_query = (UserProfileRequestAttribute) ServerThread.objIn.readObject();
        boolean given_clearance_successfully = QueryFunctions.giveClearanceToStudent(user_profile_query);
        System.out.println("Result: " + given_clearance_successfully);
        ServerThread.dataOut.writeBoolean(given_clearance_successfully);
    }

    public static void returnAlumniStudentsList() throws IOException {
        ServerThread.printWriter.println("SERVER_READY");
        String year = ServerThread.bufferedReader.readLine();
        List<UserProfileQueryResult> alumni_students_list = QueryFunctions.getAlumniListStudentsList(year);
        System.out.println("Result: " + alumni_students_list.size());
        ServerThread.objOut.writeObject(alumni_students_list);
    }

    public static void returnPublishersList() throws IOException {
        ServerThread.printWriter.println("SERVER_READY");
        List<PublisherQueryResult> publishers_list = QueryFunctions.getPublishersList();
        System.out.println("Result: " + publishers_list.size());
        ServerThread.objOut.writeObject(publishers_list);
    }

    public static void returnDomainsList() throws IOException {
        ServerThread.printWriter.println("SERVER_READY");
        List<DomainQueryResult> domains_list = QueryFunctions.getDomainsList();
        System.out.println("Result: " + domains_list.size());
        ServerThread.objOut.writeObject(domains_list);
    }

    public static void returnAccountsList() throws IOException {
        ServerThread.printWriter.println("SERVER_READY");
        List<AccountsQueryResult> accounts_list = QueryFunctions.getAccountsList();
        System.out.println("Result: " + accounts_list.size());
        ServerThread.objOut.writeObject(accounts_list);
    }

    public static void returnSubjectsList() throws IOException {
        ServerThread.printWriter.println("SERVER_READY");
        List<SubjectQueryResult> subjects_list = QueryFunctions.getSubjectsList();
        System.out.println("Result: " + subjects_list.size());
        ServerThread.objOut.writeObject(subjects_list);
    }

    public static void returnBookTitleList() throws IOException {
        ServerThread.printWriter.println("SERVER_READY");
        List<BookTitleQueryResult> book_title_list = QueryFunctions.getBookTitleList();
        System.out.println("Result: " + book_title_list.size());
        ServerThread.objOut.writeObject(book_title_list);
    }

    public static void returnReportTitleList() throws IOException {
        ServerThread.printWriter.println("SERVER_READY");
        List<ReportTitleQueryResult> report_title_list = QueryFunctions.getReportTitleList();
        System.out.println("Result: " + report_title_list.size());
        ServerThread.objOut.writeObject(report_title_list);
    }

    public static void returnAddBookTitleQueryResult() throws IOException, ClassNotFoundException {
        ServerThread.printWriter.println("SERVER_READY");
        AddBookTitleAttributes add_book_query = (AddBookTitleAttributes) ServerThread.objIn.readObject();
        boolean added_successfully = QueryFunctions.addBookTitle(add_book_query);
        System.out.println("Result: " + added_successfully);
        ServerThread.dataOut.writeBoolean(added_successfully);
    }

    public static void returnAddReportTitleQueryResult() throws IOException, ClassNotFoundException {
        ServerThread.printWriter.println("SERVER_READY");
        AddReportTitleAttributes add_report_query = (AddReportTitleAttributes) ServerThread.objIn.readObject();
        boolean added_successfully = QueryFunctions.addReportTitle(add_report_query);
        System.out.println("Result: " + added_successfully);
        ServerThread.dataOut.writeBoolean(added_successfully);
    }

    public static void returnAddDomainQueryResult() throws IOException, ClassNotFoundException {
        ServerThread.printWriter.println("SERVER_READY");
        AddDomainAttributes add_domain_query = (AddDomainAttributes) ServerThread.objIn.readObject();
        boolean added_successfully = QueryFunctions.addDomain(add_domain_query);
        System.out.println("Result: " + added_successfully);
        ServerThread.dataOut.writeBoolean(added_successfully);
    }

    public static void returnAddPublisherQueryResult() throws IOException, ClassNotFoundException {
        ServerThread.printWriter.println("SERVER_READY");
        AddPublisherAttributes add_publisher_query = (AddPublisherAttributes) ServerThread.objIn.readObject();
        boolean added_successfully = QueryFunctions.addPublisher(add_publisher_query);
        System.out.println("Result: " + added_successfully);
        ServerThread.dataOut.writeBoolean(added_successfully);
    }

    public static void returnAddSubjectQueryResult() throws IOException, ClassNotFoundException {
        ServerThread.printWriter.println("SERVER_READY");
        AddSubjectAttributes add_subject_query = (AddSubjectAttributes) ServerThread.objIn.readObject();
        boolean added_successfully = QueryFunctions.addSubject(add_subject_query);
        System.out.println("Result: " + added_successfully);
        ServerThread.dataOut.writeBoolean(added_successfully);
    }

    static void returnAddLibraryContentQueryResult() throws IOException, ClassNotFoundException {
        ServerThread.printWriter.println("SERVER_READY");
        AddLibraryContentAttributes add_library_content_query = (AddLibraryContentAttributes) ServerThread.objIn.readObject();
        boolean added_successfully = QueryFunctions.addLibraryContent(add_library_content_query);
        System.out.println("Result: " + added_successfully);
        ServerThread.dataOut.writeBoolean(added_successfully);
    }

    public static void returnGetBookTitleQueryResult() throws IOException {
        ServerThread.printWriter.println("SERVER_READY");
        int book_id = ServerThread.dataIn.readInt();
        AddBookTitleAttributes book_query_result = QueryFunctions.getBookTitle(book_id);
        System.out.println("Result: " + book_query_result.book_id);
        ServerThread.objOut.writeObject(book_query_result);
    }

    public static void returnGetReportTitleQueryResult() throws IOException {
        ServerThread.printWriter.println("SERVER_READY");
        int report_id = ServerThread.dataIn.readInt();
        AddReportTitleAttributes report_query_result = QueryFunctions.getReportTitle(report_id);
           System.out.println("Result: "+report_query_result.report_id);
        ServerThread.objOut.writeObject(report_query_result);
    }

    public static void returnGetDomainQueryResult() throws IOException {
        ServerThread.printWriter.println("SERVER_READY");
        int domain_id = ServerThread.dataIn.readInt();
        AddDomainAttributes domain_query_result = QueryFunctions.getDomain(domain_id);
           System.out.println("Result: "+domain_query_result.domain_id);
        ServerThread.objOut.writeObject(domain_query_result);
    }

    public static void returnGetPublisherQueryResult() throws IOException {
        ServerThread.printWriter.println("SERVER_READY");
        int publisher_id = ServerThread.dataIn.readInt();
        AddPublisherAttributes publisher_query_result = QueryFunctions.getPublisher(publisher_id);
           System.out.println("Result: "+publisher_query_result.publisher_id);
        ServerThread.objOut.writeObject(publisher_query_result);
    }

    public static void returnGetSubjectQueryResult() throws IOException {
        ServerThread.printWriter.println("SERVER_READY");
        int subject_id = ServerThread.dataIn.readInt();
        AddSubjectAttributes subject_query_result = QueryFunctions.getSubject(subject_id);
           System.out.println("Result: "+subject_query_result.subject_id);
        ServerThread.objOut.writeObject(subject_query_result);
    }

    public static void returnUpdateBookTitleQueryResult() throws IOException, ClassNotFoundException {
        ServerThread.printWriter.println("SERVER_READY");
        AddBookTitleAttributes update_book_query = (AddBookTitleAttributes) ServerThread.objIn.readObject();
        boolean updated_successfully = QueryFunctions.updateBookTitle(update_book_query);
           System.out.println("Result: "+update_book_query.book_id);
        ServerThread.dataOut.writeBoolean(updated_successfully);
    }

    public static void returnUpdateReportTitleQueryResult() throws IOException, ClassNotFoundException {
        ServerThread.printWriter.println("SERVER_READY");
        AddReportTitleAttributes update_report_query = (AddReportTitleAttributes) ServerThread.objIn.readObject();
        boolean updated_successfully = QueryFunctions.updateReportTitle(update_report_query);
           System.out.println("Result: "+updated_successfully);
        ServerThread.dataOut.writeBoolean(updated_successfully);
    }

    public static void returnUpdateDomainQueryResult() throws IOException, ClassNotFoundException {
        ServerThread.printWriter.println("SERVER_READY");
        AddDomainAttributes update_domain_query = (AddDomainAttributes) ServerThread.objIn.readObject();
        boolean updated_successfully = QueryFunctions.updateDomain(update_domain_query);
           System.out.println("Result: "+updated_successfully);
        ServerThread.dataOut.writeBoolean(updated_successfully);
    }

    public static void returnUpdatePublisherQueryResult() throws IOException, ClassNotFoundException {
        ServerThread.printWriter.println("SERVER_READY");
        AddPublisherAttributes update_publisher_query = (AddPublisherAttributes) ServerThread.objIn.readObject();
        boolean updated_successfully = QueryFunctions.updatePublisher(update_publisher_query);
           System.out.println("Result: "+updated_successfully);
        ServerThread.dataOut.writeBoolean(updated_successfully);
    }

    public static void returnUpdateSubjectQueryResult() throws IOException, ClassNotFoundException {
        ServerThread.printWriter.println("SERVER_READY");
        AddSubjectAttributes update_subject_query = (AddSubjectAttributes) ServerThread.objIn.readObject();
        boolean updated_successfully = QueryFunctions.updateSubject(update_subject_query);
           System.out.println("Result: "+updated_successfully);
        ServerThread.dataOut.writeBoolean(updated_successfully);
    }

    public static void returnBookIssueConstraint() throws IOException {
        ServerThread.printWriter.println("SERVER_READY");
        List<BookIssueConstraintsQueryResult> book_issue_constraint_set = QueryFunctions.getBookIssueConstraints();
           System.out.println("Result: "+book_issue_constraint_set.size());
        ServerThread.objOut.writeObject(book_issue_constraint_set);
    }

    public static void returnLibraryContentTypes() throws IOException {
        ServerThread.printWriter.println("SERVER_READY");
        List<LibraryContentTypesQueryResult> library_content_types_list = QueryFunctions.getLibraryContentTypes();
           System.out.println("Result: "+library_content_types_list.size());
        ServerThread.objOut.writeObject(library_content_types_list);
    }

    static void returnExamPatterns() throws IOException {
        ServerThread.printWriter.println("SERVER_READY");
        List<String> exam_patterns_list = QueryFunctions.getExamPatterns();
           System.out.println("Result: "+exam_patterns_list.size());
        ServerThread.objOut.writeObject(exam_patterns_list);
    }

    public static void returnUpdateBookIssueConstraintsResult() throws IOException, ClassNotFoundException {
        ServerThread.printWriter.println("SERVER_READY");
        List<BookIssueConstraintsQueryResult> book_issue_constraint_set = (List<BookIssueConstraintsQueryResult>) ServerThread.objIn.readObject();
        boolean updated_successfully = QueryFunctions.updateBookIssueConstraints(book_issue_constraint_set);
           System.out.println("Result: "+updated_successfully);
        ServerThread.dataOut.writeBoolean(updated_successfully);
    }

    public static void returnWeatherUsernameIsAvailable() throws IOException, ClassNotFoundException {
        ServerThread.printWriter.println("SERVER_READY");
        String input_username = ServerThread.dataIn.readUTF();
        boolean is_available = QueryFunctions.checkWeatherUsernameIsAvailable(input_username);
           System.out.println("Result: "+is_available);
        ServerThread.dataOut.writeBoolean(is_available);
    }

    static void returnStudentProfileRegistrationResult() throws IOException, ClassNotFoundException {
        ServerThread.printWriter.println("SERVER_READY");
        Student student_profile = (Student) ServerThread.objIn.readObject();
        boolean registered_successfully = QueryFunctions.registerStudentProfile(student_profile);
        System.out.println("Result: "+registered_successfully);
        ServerThread.dataOut.writeBoolean(registered_successfully);
    }

    static void returnEmployeeProfileRegistrationResult() throws IOException, ClassNotFoundException {
        ServerThread.printWriter.println("SERVER_READY");
        Employee employee_profile = (Employee) ServerThread.objIn.readObject();
        boolean registered_successfully = QueryFunctions.registerEmployeeProfile(employee_profile);
        System.out.println("Result: "+registered_successfully);
        ServerThread.dataOut.writeBoolean(registered_successfully);
    }

    static void returnUpdateStudentProfileResult() throws IOException, ClassNotFoundException {
        ServerThread.printWriter.println("SERVER_READY");
        Student student_profile = (Student) ServerThread.objIn.readObject();
        boolean updated_successfully = QueryFunctions.updateStudentProfile(student_profile);
        System.out.println("Result: "+updated_successfully);
        ServerThread.dataOut.writeBoolean(updated_successfully);
    }

    static void returnUpdateEmployeeProfileResult() throws IOException, ClassNotFoundException {
        ServerThread.printWriter.println("SERVER_READY");
        Employee employee_profile = (Employee) ServerThread.objIn.readObject();
        boolean updated_successfully = QueryFunctions.updatedEmployeeProfile(employee_profile);
        System.out.println("Result: "+updated_successfully);
        ServerThread.dataOut.writeBoolean(updated_successfully);
    }

    static void returnDeleteLibraryContentResult() throws IOException {
        ServerThread.printWriter.println("SERVER_READY");
        String exam_pattern = ServerThread.dataIn.readUTF();
        System.out.println(exam_pattern);
        boolean deleted_successfully = QueryFunctions.deleteLibraryContent(exam_pattern);
        System.out.println("Result: "+deleted_successfully);
        ServerThread.dataOut.writeBoolean(deleted_successfully);
    }

    static void returnStatistics() throws IOException {
        ServerThread.printWriter.println("SERVER_READY");
        Statistics statistics = QueryFunctions.getStatistics();
        System.out.println("Result: "+statistics.total_books+", "+statistics.total_reports+", "+statistics.total_library_content);
        ServerThread.objOut.writeObject(statistics);
    }

}
