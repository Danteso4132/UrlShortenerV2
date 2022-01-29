package com.shortener.entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue
    @Column(name = "id", length = 16, unique = true, nullable = false)
    private UUID id;

    //Added by Ilya
    //To not allow to create users with same login
    @Column(unique = true)
    private String login;

    private String password;

    private String email;

    @Column(name = "Enabled", length = 1, nullable = false)
    private boolean enabled;

    private String name;

    private String last_name;

    private String patronymic;

    private Date birthday;

    private Date ban;

    @ManyToOne
    private RoleUser roleAdmin;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Url> urls;




    public String getDotsPassword() {
        return password.replaceAll(".", "*");
    }


    public User(String login, String password){
        this.login = login;
        this.password = password;
    }

    public User(String login, String password, String name, String last_name, String patronymic, Date birthday, Date ban, Boolean enabled) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.last_name = last_name;
        this.patronymic = patronymic;
        this.birthday = birthday;
        this.ban = ban;
        this.enabled = enabled;
    }

    @Override
    public String toString(){
        return this.name;
    }


}