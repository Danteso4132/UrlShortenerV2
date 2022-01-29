package com.shortener.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.shortener.entity.Url;

@Repository
public interface UrlRepoDAO extends JpaRepository<Url, Long>{
    @Query(value = "select * from urls where urls.original = :original", nativeQuery = true)
    Url getByUrl(@Param("original") String original);

    @Query(value = "select * from urls where urls.short = :shortUrl", nativeQuery = true)
    Url getByShortUrl(@Param("shortUrl") String shortUrl);
}