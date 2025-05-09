package com.example.ProjectLibrary.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "borrow_records")
public class BorrowRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private LocalDate borrowDate;
    private LocalDate returnDate;
    private boolean returned;

    public BorrowRecord() {}

    public BorrowRecord(Book book, User user, LocalDate borrowDate, boolean returned) {
        this.book = book;
        this.user = user;
        this.borrowDate = borrowDate;
        this.returned = returned;
    }


    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Book getBook() { return book; }

    public void setBook(Book book) { this.book = book; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public LocalDate getBorrowDate() { return borrowDate; }

    public void setBorrowDate(LocalDate borrowDate) { this.borrowDate = borrowDate; }

    public LocalDate getReturnDate() { return returnDate; }

    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }

    public boolean isReturned() { return returned; }

    public void setReturned(boolean returned) { this.returned = returned; }
}
