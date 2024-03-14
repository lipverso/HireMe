package org.hire.service;

import lombok.RequiredArgsConstructor;
import org.hire.domain.dto.StatisticsDto;
import org.hire.domain.dto.URLShortenerDto;
import org.hire.domain.entity.URLShortenerEntity;
import org.hire.repository.URLShortenerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class URLShortenerService {
    private final URLShortenerRepository repository;

    private static final Logger LOGGER = LoggerFactory.getLogger(URLShortenerService.class);

    public URLShortenerDto encurtarUrl(String alias, String urlOriginal) {

        long inicioTempo = System.currentTimeMillis();
        String hashUrlEncurtada = alias;

        if (Objects.isNull(alias) || alias.isEmpty()){
            hashUrlEncurtada = encurtarUrl(urlOriginal);
        }

        long fimTempo = System.currentTimeMillis();
        double tempoDecorrido = fimTempo - inicioTempo;

        URLShortenerEntity shorter = new URLShortenerEntity();
        shorter.setAlias(hashUrlEncurtada);

        if (repository.existsByAlias(shorter.getAlias())){
            throw new RuntimeException("Alias já cadastrado");
        }

        shorter.setUrl(getUrlEncurtada(hashUrlEncurtada));

        shorter = repository.save(shorter);

        StatisticsDto statisticsDto = new StatisticsDto();
        String tempoDecorridoFormatado = String.format("%.2f ms", tempoDecorrido);
        statisticsDto.setTime_taken(tempoDecorridoFormatado);

        URLShortenerDto dto = new URLShortenerDto();
        dto.setStatisticsDto(statisticsDto);
        dto.setAlias(shorter.getAlias());
        dto.setUrl(shorter.getUrl());

        return dto;
    }
    public String getUrlEncurtada(String hashUrlEncurtada){
        return "http://shortener/" + hashUrlEncurtada;
    }
    public static String encurtarUrl(String originalURL) {
        LOGGER.info("Encurtando URL {}", originalURL);
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] hashBytes = digest.digest(originalURL.getBytes());

            String base64Hash = Base64.getUrlEncoder().encodeToString(hashBytes);

            String shortenedURL = base64Hash.replaceAll("[^a-zA-Z0-9]", "").substring(0, 5);

            return shortenedURL;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public URLShortenerEntity getOriginalURL(String alias) {
        URLShortenerEntity entity = repository.findByAlias(alias);
        if (Objects.nonNull(entity)){
            entity.setAccessCount(entity.getAccessCount() + 1);
            entity = repository.save(entity);
        }
        return entity != null ? entity : null;
    }

    public List<URLShortenerEntity> findTop10MostAccessedUrls() {
        return repository.findTop10ByOrderByAccessCountDesc();
    }
}
