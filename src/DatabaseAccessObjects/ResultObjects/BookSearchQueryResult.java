package DatabaseAccessObjects.ResultObjects;

import java.io.Serializable;


public class BookSearchQueryResult implements Serializable {
        public int book_id;
        public String book_author;
        public String book_title;
        public int no_of_copies;
        public int available_copies;
        public int cupboard_no;
        public int shelf_no;

        public BookSearchQueryResult(){
                book_id=0;
                book_author=null;
                book_author=null;
                book_title=null;
                no_of_copies=0;
                available_copies=0;
                cupboard_no=0;
                shelf_no=0;
        }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getBook_author() {
        return book_author;
    }

    public void setBook_author(String book_author) {
        this.book_author = book_author;
    }

    public String getBook_title() {
        return book_title;
    }

    public void setBook_title(String book_title) {
        this.book_title = book_title;
    }

    public int getNo_of_copies() {
        return no_of_copies;
    }

    public void setNo_of_copies(int no_of_copies) {
        this.no_of_copies = no_of_copies;
    }

    public int getAvailable_copies() {
        return available_copies;
    }

    public void setAvailable_copies(int available_copies) {
        this.available_copies = available_copies;
    }

    public int getCupboard_no() {
        return cupboard_no;
    }

    public void setCupboard_no(int cupboard_no) {
        this.cupboard_no = cupboard_no;
    }

    public int getShelf_no() {
        return shelf_no;
    }

    public void setShelf_no(int shelf_no) {
        this.shelf_no = shelf_no;
    }

}
