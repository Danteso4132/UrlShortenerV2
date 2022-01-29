package com.shortener.repo;

import com.shortener.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface UserDAO extends JpaRepository<User, UUID> {

    @Query(value = "select * from user where user.login = :login", nativeQuery = true)
    User getUserByLogin(@Param("login") String login);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE user " +
            "SET user.name= :name, user.last_name= :lastName " +
            "WHERE user.login = :login", nativeQuery = true)
    void updateUser(@Param("login") String login,
                    @Param("name") String name,
                    @Param("lastName") String lastName);


    @Query("SELECT u FROM User u WHERE (:login is null or u.login = :login) and" +
            " (:password is null or u.password = :password) and " +
            " (:email is null or u.email = :email)")
    Set<User> findByParameters(@Param("login") String login,
                               @Param("password") String password,
                               @Param("email") String email);


    Optional<User> findByLogin(String login);
}
