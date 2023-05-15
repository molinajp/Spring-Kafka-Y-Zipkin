package com.example.repository;

import com.example.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    Optional<CategoryEntity> findById(Long id);

    CategoryEntity findByName(String name);

    List<CategoryEntity> findDistinctByNameContainingIgnoreCase(String name);

    @Query(value = "SELECT COALESCE(max(c.id), 0) FROM pm_categorias c", nativeQuery = true)
    int findIdMax();

}
