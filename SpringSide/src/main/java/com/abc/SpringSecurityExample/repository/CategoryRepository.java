package com.abc.SpringSecurityExample.repository;

import com.abc.SpringSecurityExample.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    Page<Category> findByParentIsNull(Pageable pageable);

    @EntityGraph(attributePaths = {"parent"})
    Page<Category> findByParentId(Long parentId, Pageable pageable);

    @EntityGraph(attributePaths = {"parent"})
    Page<Category> findAll(Pageable pageable);


    @Query(value = """ 
            Select * from categories where lower(name) like lower(concat('%', :category ,'%') )
            """,
            nativeQuery = true)
    List<Category> searchByName(@Param("category") String name);
}
