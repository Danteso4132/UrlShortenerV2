package com.shortener.entity;

import lombok.Data;

import javax.persistence.*;
import java.nio.charset.Charset;
import java.util.Random;
import java.util.UUID;


@Entity
@Data
@Table(name="urls")
public class Url {
    @Id
    @GeneratedValue
    @Column(name="id", length=16, unique = true, nullable = false)
    private UUID id;


    @Column(name = "original")
    private String originalUrl;

    @Column(name = "short")
    private String shortUrl;

    @Column(name = "expiration")
    private Integer expirationDate;

    @Column(name = "timesclicked")
    private Integer timesClicked;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;





    public Url() {}

    public Url(String original){
        this.originalUrl = original;
        this.shortUrl = createShortUrl(this.originalUrl);
        this.timesClicked = 0;
        this.expirationDate = 0;
    }


    public String createShortUrl(String originalUrl){
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 5;
        Random random = new Random();
        random.setSeed((long)originalUrl.hashCode());
        String generatedString = random.ints(leftLimit, rightLimit + 1)
                //.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .filter(i -> (i <= 57 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        System.out.println(generatedString);
        return generatedString;
    }


    @Override
    public String toString(){
        return this.originalUrl;
    }
}
