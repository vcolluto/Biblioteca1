package org.java.lessons.biblioteca.repository;

import org.java.lessons.biblioteca.model.Borrowing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowingRepository extends JpaRepository<Borrowing, Integer>{

}
