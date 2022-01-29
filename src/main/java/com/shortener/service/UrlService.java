package com.shortener.service;

import com.shortener.entity.Url;
import com.shortener.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.UUID;


public interface UrlService {

    Url saveUrl(String url);

    Url findByUrl(String url);

    Url findByShortUrl(String shortUrl);

    Url updateUrl(Url url);
}
