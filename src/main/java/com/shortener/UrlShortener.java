package com.shortener;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;


@SpringBootApplication
public class UrlShortener {

    public static void main(String[] args) {
        SpringApplication.run(UrlShortener.class, args);
        System.out.println("Started application");
    }
}