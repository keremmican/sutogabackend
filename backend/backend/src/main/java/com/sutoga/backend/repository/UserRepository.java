package com.sutoga.backend.repository;

import com.sutoga.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Query(value = "SELECT * FROM user WHERE id NOT IN (:excludedIds) ORDER BY RAND() LIMIT :limitCount", nativeQuery = true)
    List<User> findRandomUsersExcludingIds(@Param("excludedIds") List<Long> excludedIds, @Param("limitCount") int limitCount);

    @Query(value = "SELECT * FROM user WHERE id NOT IN (:excludedIds) ORDER BY RAND() LIMIT 1", nativeQuery = true)
    User findRandomUserExcludingIds(@Param("excludedIds") List<Long> excludedIds);

    Optional<User> findByEmail(String email);
    User findBySteamId(Long steamId);
    List<User> findByUsernameIgnoreCaseContaining(String query);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
