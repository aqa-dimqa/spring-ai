package com.example.springai.domain.repository;

import com.example.springai.domain.model.ChatEntity;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChatRepository extends JpaRepository<ChatEntity, UUID> {

    @Nonnull
    Optional<ChatEntity> findById(@Nonnull final UUID chatId);

    @Nonnull
    Optional<ChatEntity> findByUserIdAndId(@Nonnull final UUID userId, @Nonnull final UUID chatId);

    @Nonnull
//    @Query("""
//            SELECT c
//            FROM ChatEntity c
//            WHERE c.userId = :userId
//            AND c.isActive = :isActive
//            ORDER BY c.updatedAt DESC
//            """)
    List<ChatEntity> findAllChatsByUserIdAndIsActiveOrderByUpdatedAtDesc(UUID userId, boolean isActive);

    @Nonnull
//    @Query("""
//            SELECT c
//            FROM ChatEntity c
//            WHERE c.userId = :userId
//            AND c.space = NULL
//            AND c.isActive = :isActive
//            ORDER BY c.updatedAt DESC
//            """)
    List<ChatEntity> findAllChatsByUserIdAndSpaceIsNullAndIsActiveOrderByUpdatedAtDesc(UUID userId, boolean isActive);

    void deleteById(@Nonnull final UUID chatId);

}
