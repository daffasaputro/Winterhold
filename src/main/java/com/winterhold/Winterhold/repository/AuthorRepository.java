package com.winterhold.Winterhold.repository;

import com.winterhold.Winterhold.dto.author.AuthorDataDTO;
import com.winterhold.Winterhold.dto.utility.DropdownDTO;
import com.winterhold.Winterhold.entity.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
    @Query("""
            SELECT new com.winterhold.Winterhold.dto.author.AuthorDataDTO(aut.id,
                CONCAT(aut.title, ' ', aut.firstName, ' ', aut.lastName),
                aut.birthDate,
                aut.deceasedDate,
                aut.education)
            FROM Author AS aut
            WHERE aut.firstName LIKE %:authorName% OR aut.lastName LIKE %:authorName%
            """)
    public Page<AuthorDataDTO> findByName(@Param("authorName") String authorName,
                                          Pageable pageable);

    @Query("""
            SELECT new com.winterhold.Winterhold.dto.utility.DropdownDTO(aut.id,
                CONCAT(aut.title, ' ', aut.firstName, ' ', aut.lastName))
            FROM Author AS aut
            """)
    public LinkedList<DropdownDTO> dropdown();
}
