package com.giuseppecalvaruso.library365.repositories;

import com.giuseppecalvaruso.library365.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.user_id = :id")
    Optional<User> findIdWithRoles(@Param("id")UUID id);

    @Query(value ="select * FROM users WHERE email= :email", nativeQuery = true )
    Optional<User> findByEmail(@Param("email") String email);

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.email = :email")
    boolean existsEmail(@Param("email") String email);



}
