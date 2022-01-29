package com.shortener.controller;

import com.shortener.entity.Url;
import com.shortener.entity.User;
import com.shortener.repo.UrlRepoDAO;
import com.shortener.service.UrlService;
import com.shortener.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Controller
public class web {

    @Autowired
    UrlService urlService;

    @Autowired
    UserService userService;



    @GetMapping("/main")
    public String mainPage(){
        return "mainPage";
    }

    @PostMapping("/main")
    public String addUrl(@RequestParam String link,
                         Map<String, Object> model,
                         Principal principal){
        System.out.println("adding new link for " + link);
        model.put("status", "Successfuly added "+link);
        Url alreadyAdded = urlService.findByUrl(link);
        if (alreadyAdded != null){
            model.put("shortUrl", alreadyAdded.getShortUrl());
            return "mainPage";
        }
        else{
            User author = userService.getUserByLogin(principal.getName());
            Url addedOne = urlService.saveUrl(link);
            addedOne.setUser(author);
            urlService.updateUrl(addedOne);
            System.out.println("Total of " + author.getUrls().size() + " links added by user " + principal.getName());
            System.out.println(author.getUrls());

            model.put("shortUrl", addedOne.getShortUrl());
            return "mainPage";
        }
    }



    @GetMapping("/login")
    public String login(){
        return "login";
    }


    @GetMapping("/*")
    public String response(HttpServletRequest request){
        try{
            Url targetUrl = urlService.findByShortUrl(request.getRequestURI().substring(1));
            return "redirect:" + targetUrl.getOriginalUrl();
        }
        catch (Exception e){
            return "errorPage";
        }
    }

    @GetMapping("/registration")
    public String registerGet(){
        return "registration";
    }

    @PostMapping("/registration")
    public String registerPost(Map<String, Object> model,
                               @RequestBody User user,
                               ServletResponse resp)
    {
        if (userService.isStringOnlyAlphabet(user.getLogin()) &&
                userService.isStringOnlyAlphabet(user.getName()) &&
                userService.isStringOnlyAlphabetAndNumbersAndSymbols(user.getPassword()) &&
                userService.getUserByLogin(user.getLogin()) == null)
        {
            System.out.println("saving new user");
            User us = userService.saveUser(user);
            return "redirect:/main";
        }
        else{
            System.out.println("Error handling");
            if (userService.getUserByLogin(user.getLogin()) != null){
                model.put("status", "Возникла проблема с регистрацией. Возможно, логин уже занят");
            }
            else{
                model.put("status", "Произошла ошибка! Все поля должны состоять из букв!");
            }
            HttpServletResponse response=(HttpServletResponse) resp;
            response.setStatus(500);
            return "registration";
        }
    }

}
