package Server;

import DatabaseAccessObjects.QueryObjects.DatabaseConnector;
import RequestAttributes.RequestAttributes;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;

public class ServerThread implements Runnable {

    static public DatabaseConnector databaseConnector;
    private final Socket socket;
    private final Server server;
    static public PrintWriter printWriter;
    static public BufferedReader bufferedReader;
    static public ObjectInputStream objIn;
    static public ObjectOutputStream objOut;
    static public DataInputStream dataIn;
    static public DataOutputStream dataOut;

    ServerThread(Socket socket, Server server) throws IOException {
        this.socket = socket;
        this.server = server;
        ServerThread.printWriter = new PrintWriter(this.socket.getOutputStream(), true);
        ServerThread.bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        ServerThread.objIn = new ObjectInputStream(socket.getInputStream());
        ServerThread.objOut = new ObjectOutputStream(socket.getOutputStream());
        ServerThread.dataIn = new DataInputStream(socket.getInputStream());
        ServerThread.dataOut = new DataOutputStream(socket.getOutputStream());

    }

    public void closeConnection() throws IOException {
        ServerThread.objOut.close();
        ServerThread.objIn.close();
        ServerThread.dataIn.close();
        ServerThread.dataOut.close();
        ServerThread.bufferedReader.close();
        ServerThread.printWriter.close();
        this.socket.close();
    }

    boolean initialiseDatabaseInterface(String interface_type) throws SQLException {
        if (interface_type.equals("LIBRARY_INCHARGE_INTERFACE")) {
            databaseConnector = Server.databaseConnectorForLibraryInchargeInterface;
        } else if (interface_type.equals("STUDENT_AND_EMPLOYEE_INTERFACE")) {
            databaseConnector = Server.databaseConnectorForStudentAndEmployeeInterface;
        }
        if (!databaseConnector.connector.isClosed()) {
            return true;
        } else {
            return true;
        }
    }

    @Override
    public void run() {
        try {
            RequestAttributes requestAttributes;
            requestAttributes = (RequestAttributes) ServerThread.objIn.readObject();

            if (!this.initialiseDatabaseInterface(requestAttributes.interfaceName)) {
                System.out.println("Connection with interface failed !");
            } else {
                String applicationRequest = requestAttributes.requestCode;
                switch (applicationRequest) {
                    case "VERIFY_CREDENTIALS":
                        System.out.println("#executing VERIFY_CREDENTIALS");
                        ApplicationServices.userCredentialsVerification();
                        System.out.println("#returned.");
                        break;
                    case "SEARCH_BOOK":
                        System.out.println("#executing SEARCH_BOOK");
                        ApplicationServices.returnBookSearchQueryResult();
                        System.out.println("#returned.");
                        break;
                    case "SEARCH_REPORT":
                        System.out.println("#executing SEARCH_REPORT");
                        ApplicationServices.returnReportSearchQueryResult();
                        System.out.println("#returned");
                        break;
                    case "SEARCH_LIBRARY_CONTENT":
                        System.out.println("#executing SEARCH_LIBRARY_CONTENT");
                        ApplicationServices.returnLibrayContentSearchQueryResult();
                        System.out.println("#returned");
                        break;
                    case "GET_LIBRARY_CONTENT_FILE":
                        System.out.println("#executing GET_LIBRARY_CONTENT_FILE");
                        ApplicationServices.returnLibrayContentFileQueryResult();
                        System.out.println("#returned");
                        break;
                    case "ISSUE_BOOK":
                        System.out.println("#executing ISSUE_BOOK");
                        ApplicationServices.returnBookIssueQueryResult();
                        System.out.println("#returned.");
                        break;
                    case "ISSUE_REPORT":
                        System.out.println("#executing ISSUE_REPORT");
                        ApplicationServices.returnReportIssueQueryResult();
                        System.out.println("#returned.");
                        break;
                    case "VERIFY_USER_PROFILE":
                        System.out.println("#executing VERIFY_USER_PROFILE");
                        ApplicationServices.userProfileVerification();
                        System.out.println("#returned.");
                        break;
                    case "GET_USER_PROFILE":
                        System.out.println("#executing GET_USER_PROFILE");
                        ApplicationServices.returnUserProfile();
                        System.out.println("#returned.");
                        break;
                    case "GET_PENDING_BOOKS_RECORD":
                        System.out.println("#executing GET_PENDING_BOOKS_RECORD");
                        ApplicationServices.returnPendingBooksRecord();
                        System.out.println("#returned.");
                        break;
                    case "GET_PENDING_REPORT_RECORD":
                        System.out.println("#executing GET_PENDING_REPORT_RECORD");
                        ApplicationServices.returnPendingReportRecord();
                        System.out.println("#returned.");
                        break;
                    case "RETURN_BOOK":
                        System.out.println("#executing RETURN_BOOK");
                        ApplicationServices.returnBookReturnQueryResult();
                        System.out.println("#returned.");
                        break;
                    case "RETURN_REPORT":
                        System.out.println("#executing RETURN_REPORT");
                        ApplicationServices.returnReportReturnQueryResult();
                        System.out.println("#returned.");
                        break;
                    case "RENEW_BOOK":
                        System.out.println("#executing RENEW_BOOK");
                        ApplicationServices.returnBookRenewQueryResult();
                        System.out.println("#returned");
                        break;
                    case "CLEAR_&_ACCOUNT":
                        System.out.println("#executing CLEAR_&_ACCOUNT");
                        ApplicationServices.returnTotalClearedRecordsCount();
                        System.out.println("#returned");
                        break;
                    case "GET_USERS_WITH_OVER_DUE_PENDING_RECORDS":
                        System.out.println("#executing GET_USERS_WITH_OVER_DUE_PENDING_RECORDS");
                        ApplicationServices.returnUsersWithOverDuePendingRecords();
                        System.out.println("#returned");
                        break;
                    case "GET_USERS_WITH_PENDING_RECORDS":
                        System.out.println("#executing GET_USERS_WITH_PENDING_RECORDS");
                        ApplicationServices.returnUsersWithPendingRecords();
                        System.out.println("#returned");
                        break;
                    case "RESET_STUDENTS_VERIFICATION":
                        System.out.println("#executing RESET_STUDENTS_VERIFICATION");
                        ApplicationServices.returnResetStudentsVerificationResult();
                        System.out.println("#returned");
                        break;
                    case "SEARCH_USER":
                        System.out.println("#executing SEARCH_USER");
                        ApplicationServices.returnUserSearchQueryResult();
                        System.out.println("#returned");
                        break;
                    case "DELETE_USER_PROFILE":
                        System.out.println("#executing DELETE_USER_PROFILE");
                        ApplicationServices.returnDeleteUserProfileQueryResult();
                        System.out.println("#returned");
                        break;
                    case "GIVE_CLEARANCE_TO_STUDENT":
                        System.out.println("#executing GIVE_CLEARANCE_TO_STUDENT");
                        ApplicationServices.returnGiveClearanceToStudentQueryResult();
                        System.out.println("#returned");
                        break;
                    case "GET_ALUMNI_STUDENTS_BY_YEAR":
                        System.out.println("#executing GET_ALUMNI_STUDENTS_BY_YEAR");
                        ApplicationServices.returnAlumniStudentsList();
                        System.out.println("#returned");
                        break;
                    case "GET_PUBLISHERS_LIST":
                        System.out.println("#executing GET_PUBLISHERS_LIST");
                        ApplicationServices.returnPublishersList();
                        System.out.println("#returned");
                        break;
                    case "GET_DOMAINS_LIST":
                        System.out.println("#executing GET_DOMAINS_LIST");
                        ApplicationServices.returnDomainsList();
                        System.out.println("#returned");
                        break;
                    case "GET_ACCOUNTS_LIST":
                        System.out.println("#executing GET_ACCOUNTS_LIST");
                        ApplicationServices.returnAccountsList();
                        System.out.println("#returned");
                        break;
                    case "GET_SUBJECTS_LIST":
                        System.out.println("#executing GET_SUBJECTS_LIST");
                        ApplicationServices.returnSubjectsList();
                        System.out.println("#returned");
                        break;
                    case "GET_BOOK_TITLE_LIST":
                        System.out.println("#executing GET_BOOK_TITLE_LIST");
                        ApplicationServices.returnBookTitleList();
                        System.out.println("#returned");
                        break;
                    case "GET_REPORT_TITLE_LIST":
                        System.out.println("#executing GET_REPORT_TITLE_LIST");
                        ApplicationServices.returnReportTitleList();
                        System.out.println("#returned");
                        break;
                    case "ADD_BOOK_TITLE":
                        System.out.println("#executing ADD_BOOK_TITLE");
                        ApplicationServices.returnAddBookTitleQueryResult();
                        System.out.println("#returned");
                        break;
                    case "ADD_REPORT_TITLE":
                        System.out.println("#executing ADD_REPORT_TITLE");
                        ApplicationServices.returnAddReportTitleQueryResult();
                        System.out.println("#returned");
                        break;
                    case "ADD_DOMAIN":
                        System.out.println("#executing ADD_DOMAIN");
                        ApplicationServices.returnAddDomainQueryResult();
                        System.out.println("#returned");
                        break;
                    case "ADD_PUBLISHER":
                        System.out.println("#executing ADD_PUBLISHER");
                        ApplicationServices.returnAddPublisherQueryResult();
                        System.out.println("#returned");
                        break;
                    case "ADD_SUBJECT":
                        System.out.println("#executing ADD_SUBJECT");
                        ApplicationServices.returnAddSubjectQueryResult();
                        System.out.println("#returned");
                        break;
                    case "ADD_LIBRARY_CONTENT":
                        System.out.println("#executing ADD_LIBRARY_CONTENT");
                        ApplicationServices.returnAddLibraryContentQueryResult();
                        System.out.println("#returned");
                        break;
                    case "GET_BOOK_TITLE":
                        System.out.println("#executing GET_BOOK_TITLE");
                        ApplicationServices.returnGetBookTitleQueryResult();
                        System.out.println("#returned");
                        break;
                    case "GET_REPORT_TITLE":
                        System.out.println("#executing GET_REPORT_TITLE");
                        ApplicationServices.returnGetReportTitleQueryResult();
                        System.out.println("#returned");
                        break;
                    case "GET_DOMAIN":
                        System.out.println("#executing GET_DOMAIN");
                        ApplicationServices.returnGetDomainQueryResult();
                        System.out.println("#returned");
                        break;
                    case "GET_PUBLISHER":
                        System.out.println("#executing GET_PUBLISHER");
                        ApplicationServices.returnGetPublisherQueryResult();
                        System.out.println("#returned");
                        break;
                    case "GET_SUBJECT":
                        System.out.println("#executing GET_REPORT_TITLE");
                        ApplicationServices.returnGetSubjectQueryResult();
                        System.out.println("#returned");
                        break;
                    case "UPDATE_BOOK_TITLE":
                        System.out.println("#executing UPDATE_BOOK_TITLE");
                        ApplicationServices.returnUpdateBookTitleQueryResult();
                        System.out.println("#returned");
                        break;
                    case "UPDATE_REPORT_TITLE":
                        System.out.println("#executing UPDATE_REPORT_TITLE");
                        ApplicationServices.returnUpdateReportTitleQueryResult();
                        System.out.println("#returned");
                        break;
                    case "UPDATE_DOMAIN":
                        System.out.println("#executing UPDATE_DOMAIN");
                        ApplicationServices.returnUpdateDomainQueryResult();
                        System.out.println("#returned");
                        break;
                    case "UPDATE_PUBLISHER":
                        System.out.println("#executing UPDATE_PUBLISHER");
                        ApplicationServices.returnUpdatePublisherQueryResult();
                        System.out.println("#returned");
                        break;
                    case "UPDATE_SUBJECT":
                        System.out.println("#executing UPDATE_SUBJECT");
                        ApplicationServices.returnUpdateSubjectQueryResult();
                        System.out.println("#returned");
                        break;
                    case "GET_BOOK_ISSUE_CONSTRAINTS":
                        System.out.println("#executing GET_BOOK_ISSUE_CONSTRAINTS");
                        ApplicationServices.returnBookIssueConstraint();
                        System.out.println("#returned");
                        break;
                    case "GET_LIBRARY_CONTENT_TYPES":
                        System.out.println("#executing GET_LIBRARY_CONTENT_TYPES");
                        ApplicationServices.returnLibraryContentTypes();
                        System.out.println("#returned");
                        break;
                    case "GET_EXAM_PATTERNS":
                        System.out.println("#executing GET_EXAM_PATTERNS");
                        ApplicationServices.returnExamPatterns();
                        System.out.println("#returned");
                        break;
                    case "UPDATE_BOOK_ISSUE_CONSTRAINTS":
                        System.out.println("#executing UPDATE_BOOK_ISSUE_CONSTRAINTS");
                        ApplicationServices.returnUpdateBookIssueConstraintsResult();
                        System.out.println("#returned");
                        break;
                    case "CHECK_USERNAME_IS_AVAILABLE":
                        System.out.println("#executing CHECK_USERNAME_IS_AVAILABLE");
                        ApplicationServices.returnWeatherUsernameIsAvailable();
                        System.out.println("#returned");
                        break;
                    case "REGISTER_STUDENT_PROFILE":
                        System.out.println("#executing REGISTER_STUDENT_PROFILE");
                        ApplicationServices.returnStudentProfileRegistrationResult();
                        System.out.println("#returned");
                        break;
                    case "REGISTER_EMPLOYEE_PROFILE":
                        System.out.println("#executing REGISTER_EMPLOYEE_PROFILE");
                        ApplicationServices.returnEmployeeProfileRegistrationResult();
                        System.out.println("#returned");
                        break;
                    case "UPDATE_STUDENT_PROFILE":
                        System.out.println("#executing UPDATE_STUDENT_PROFILE");
                        ApplicationServices.returnUpdateStudentProfileResult();
                        System.out.println("#returned");
                        break;
                    case "UPDATE_EMPLOYEE_PROFILE":
                        System.out.println("#executing UPDATE_EMPLOYEE_PROFILE");
                        ApplicationServices.returnUpdateEmployeeProfileResult();
                        System.out.println("#returned");
                        break;
                    case "DELETE_LIBRARY_CONTNET":
                        System.out.println("#executing DELETE_LIBRARY_CONTNET");
                        ApplicationServices.returnDeleteLibraryContentResult();
                        System.out.println("#returned");
                        break;
                    case "GET_STATISTICS":
                        System.out.println("#executing GET_STATISTICS");
                        ApplicationServices.returnStatistics();
                        System.out.println("#returned");
                        break;
                    default:
                        System.out.println("#Request not found !");
                }
                System.out.println(socket.getInetAddress() + " disconnected from " + socket.getPort());
                int index = server.connections.indexOf(socket);
                //this.closeConnection();
                server.connections.remove(index);
            }
        } catch (IOException e) {
            System.out.println("$IO exception in " + Thread.currentThread());
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("$SQL exception in " + Thread.currentThread());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("$Class not found exception in " + Thread.currentThread());
            System.out.println();
            e.printStackTrace();
        }
    }
}
