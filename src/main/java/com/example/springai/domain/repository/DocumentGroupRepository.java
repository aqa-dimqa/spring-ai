package com.example.springai.domain.repository;

import com.example.springai.domain.model.DocumentGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface DocumentGroupRepository extends JpaRepository<DocumentGroupEntity, UUID> {

    @Query("SELECT dg FROM DocumentGroupEntity dg WHERE dg.userId = :userId ORDER BY dg.updatedAt DESC")
    List<DocumentGroupEntity> getAllByUserId(UUID userId);

}
