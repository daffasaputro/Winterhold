package com.winterhold.Winterhold.repository;

import com.winterhold.Winterhold.dto.category.CategoryDataDTO;
import com.winterhold.Winterhold.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, String> {
    @Query("""
            SELECT new com.winterhold.Winterhold.dto.category.CategoryDataDTO(cat.name,
            	cat.floor,
            	cat.isle,
            	cat.bay)
            FROM Category AS cat
            WHERE cat.name LIKE %:categoryName%
            """)
    public Page<CategoryDataDTO> findByName(@Param("categoryName") String categoryName,
                                            Pageable pageable);

    @Query("""
            SELECT COUNT(boo.code)
            FROM Book AS boo
            JOIN boo.category AS cat
            WHERE cat.name = :categoryName
            """)
    public Integer countBookByCategory(@Param("categoryName") String categoryName);
}
