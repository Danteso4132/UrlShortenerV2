package com.shortener.service;

import com.shortener.entity.RoleUser;
import com.shortener.entity.User;
import com.shortener.repo.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    UserDAO userDAO;

    @Autowired
    private RoleAdminService roleAdminService;

    @Override
    @Transactional
    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    @Override
    @Transactional
    public User saveUser(User man) {
        User user = this.getUserByLogin(man.getLogin());
        if (user == null) {
            this.saveMan(man, "ROLE_USER");
            return this.getUserByLogin(man.getLogin());
        }
        else
        {
            return null;
        }
    }



    @Override
    @Transactional
    public User saveAdmin(User man) {
        User user = this.getUserByLogin(man.getLogin());
        if (user.getId() == null) {
            this.saveMan(man, "ROLE_ADMIN");
            return user;
        }
        else return null;
    }

    @Override
    @Transactional
    public void deleteUserById(UUID id) {
        userDAO.deleteById(id);
    }

    @Override
    @Transactional
    public User getUser(UUID id) {
        Optional<User> optional = userDAO.findById(id);
        return optional.orElse(null);
    }

    @Override
    @Transactional
    public void deleteUser(UUID id) {
        userDAO.deleteById(id);
    }

    @Override
    @Transactional
    public User getUserByLogin(String login) {
        return userDAO.getUserByLogin(login);
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = this.getUserByLogin(login);

        if (user == null)  return null;

        RoleUser roleNames1 = user.getRoleAdmin();

        GrantedAuthority authority = new SimpleGrantedAuthority(roleNames1.getRole());
        List<GrantedAuthority> grantList = new ArrayList<>();
        return new org.springframework.security.core.userdetails.User(user.getLogin(),user.getPassword(), Arrays.asList(authority));
    }

    @Transactional
    void saveMan(User man, String roleAdmin)
    {
        RoleUser role = roleAdminService.findByRole(roleAdmin);
        System.out.println("Saving in man");

        if (role != null)
        {
            userDAO.save(User.builder()
                    .roleAdmin(role)
                    .login(man.getLogin())
                    .password(new BCryptPasswordEncoder().encode(man.getPassword()))
                    .name(man.getName())
                    .enabled(true)
                    .build());
        }
    }

    public boolean isStringOnlyAlphabet(String str)
    {
        return ((str != null)
                && ( str.length() < 25)
                && (!str.equals(""))
                && (str.matches("^[a-zA-Z]*$")));
    }
    public boolean isStringOnlyAlphabetAndNumbersAndSymbols(String str)
    {
        return ((str != null)
                && ( str.length() < 25)
                && (!str.equals(""))
                && (str.matches("[-/@#$%^&_+=()a-zA-Z0-9]+")));
    }
}
