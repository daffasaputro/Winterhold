package com.winterhold.Winterhold.repository;

import com.winterhold.Winterhold.dto.author.AuthorBookDTO;
import com.winterhold.Winterhold.dto.category.CategoryBookDTO;
import com.winterhold.Winterhold.dto.utility.DropdownDTO;
import com.winterhold.Winterhold.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    @Query("""
            SELECT new com.winterhold.Winterhold.dto.author.AuthorBookDTO(boo.title,
                cat.name,
                boo.isBorrowed,
                boo.releaseDate,
                boo.totalPage)
            FROM Book AS boo
            JOIN boo.author AS aut
            JOIN boo.category AS cat
            WHERE aut.id = :authorId
            """)
    public Page<AuthorBookDTO> findByAuthor(@Param("authorId") Integer authorId,
                                            Pageable pageable);

    @Query("""
            SELECT new com.winterhold.Winterhold.dto.category.CategoryBookDTO(boo.code,
                boo.title,
                CONCAT(aut.title, ' ', aut.firstName, ' ', aut.lastName),
                boo.isBorrowed,
                boo.releaseDate,
                boo.totalPage,
                boo.summary)
            FROM Book AS boo
            JOIN boo.author AS aut
            JOIN boo.category AS cat
            WHERE cat.name = :categoryName AND
                (:bookTitle IS NULL OR boo.title LIKE %:bookTitle%) AND 
                (:bookAuthor IS NULL OR CONCAT(aut.firstName, ' ', aut.lastName) LIKE %:bookAuthor%) AND
                (:isAvailable IS NULL OR boo.isBorrowed <> :isAvailable)
            """)
    public Page<CategoryBookDTO> findByCategory(@Param("categoryName") String categoryName,
                                                @Param("bookTitle") String bookTitle,
                                                @Param("bookAuthor") String bookAuthor,
                                                @Param("isAvailable") Boolean isAvailable,
                                                Pageable pageable);

    @Query("""
            SELECT new com.winterhold.Winterhold.dto.utility.DropdownDTO(boo.code,
                boo.title)
            FROM Book AS boo
            WHERE boo.isBorrowed = FALSE
            """)
    public LinkedList<DropdownDTO> dropdown();
}
