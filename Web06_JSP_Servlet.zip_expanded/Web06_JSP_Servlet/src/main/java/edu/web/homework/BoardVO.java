package edu.web.homework;
import java.util.Date;

public class BoardVO {
    private int number;
    private String title;
    private String author;
    private Date date;

    // 생성자
    public BoardVO(int number, String title, String author, Date date) {
        this.number = number;
        this.title = title;
        this.author = author;
        this.date = date;
    }


	// Getter 및 Setter
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    @Override
    public String toString() {
    	return "BoardVO [number=" + number + ", title=" + title + ", author=" + author + ", date=" + date + "]";
    }
}
