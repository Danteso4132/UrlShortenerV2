package com.shortener.service;

import com.shortener.entity.User;
import com.shortener.repo.UrlRepoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shortener.entity.Url;

import java.util.UUID;

@Service
public class UrlServiceImpl implements UrlService{
    @Autowired
    UrlRepoDAO urlRepoDAO;


    @Override
    @Transactional
    public Url saveUrl(String original)
    {
        Url url = this.findByUrl(original);
        if (url == null){
            urlRepoDAO.save(new Url(original));
            return this.findByUrl(original);
        }
        else{
            return url;
        }
    }

    @Override
    @Transactional
    public Url updateUrl(Url url){
        Url old = urlRepoDAO.getByUrl(url.getOriginalUrl());
        if (old == null){
            return null;
        }
        else{
            //urlRepoDAO.delete(old);
            urlRepoDAO.save(url);
            return urlRepoDAO.getByUrl(url.getOriginalUrl());
        }
    }


    @Transactional
    public Url findByUrl(String original){
        return urlRepoDAO.getByUrl(original);
    }

    @Transactional
    public Url findByShortUrl(String shortUrl){return urlRepoDAO.getByShortUrl(shortUrl);}
}
