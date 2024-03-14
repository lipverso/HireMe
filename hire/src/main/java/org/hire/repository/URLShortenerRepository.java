package org.hire.repository;

import org.hire.domain.entity.URLShortenerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface URLShortenerRepository extends JpaRepository<URLShortenerEntity, Long> {
    Boolean existsByAlias(String alias);
    URLShortenerEntity findByAlias(String alias);
    List<URLShortenerEntity> findTop10ByOrderByAccessCountDesc();
}
