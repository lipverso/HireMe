package org.hire;

import org.hire.client.URLShortenerClient;
import org.hire.domain.entity.URLShortenerEntity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);

        URLShortenerClient client = new URLShortenerClient();

        // Chamada para encurtar uma URL com alias
        String custom_alias = "myAlias";
        String originalUrl = "http://www.example.com";
        String shortenedUrl = client.shortenUrl(custom_alias, originalUrl);
        System.out.println("URL encurtada: " + shortenedUrl);

        // Chamada para encurtar uma URL sem alias
        String alias = null;
        String originalUrl2 = "http://www.example.com";
        String shortenedUrl2 = client.shortenUrl(alias, originalUrl2);
        System.out.println("URL encurtada: " + shortenedUrl2);

        // Chamada para redirecionar para uma URL encurtada
        String redirectUrl = client.redirect(custom_alias);
        System.out.println("Redirecionando para: " + redirectUrl);

        // Chamada para obter as 10 URLs mais acessadas
        /*List<URLShortenerEntity> top10Urls = client.getTop10MostAccessedUrls();
        System.out.println("Top 10 URLs mais acessadas:");
        for (URLShortenerEntity entity : top10Urls) {
            System.out.println(entity.getUrl());
        }*/
    }

}
