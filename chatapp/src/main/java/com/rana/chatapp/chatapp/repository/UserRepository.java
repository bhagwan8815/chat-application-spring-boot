package com.rana.chatapp.chatapp.repository;

import com.rana.chatapp.chatapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public  boolean existsByUserName(String userName);
    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.isOnline  = :isOnline WHERE u.userName = :userName")
    public void updateUserOnlineStatus(@Param("userName") String userName , @Param("isOnline") boolean isOnline);



    public Optional<User> findByUserName(String userName);
}
