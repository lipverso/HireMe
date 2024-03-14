import org.hire.domain.dto.URLShortenerDto;
import org.hire.domain.entity.URLShortenerEntity;
import org.hire.repository.URLShortenerRepository;
import org.hire.service.URLShortenerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

public class URLShortenerServiceTest {


    @Mock
    private URLShortenerRepository repository;

    @InjectMocks
    private URLShortenerService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testEncurtarUrl() {
        String originalURL = "https://www.vale.com.br";
        String alias = "blablueble";
        String shortenedUrl = "http://shortener/blablueble";
        URLShortenerEntity entity = new URLShortenerEntity();
        entity.setAlias(alias);
        entity.setUrl(shortenedUrl);

        when(repository.existsByAlias(alias)).thenReturn(false);
        when(repository.save(ArgumentMatchers.any())).thenReturn(entity);

        URLShortenerDto dto = service.encurtarUrl(alias, originalURL);

        assertNotNull(dto);
        assertEquals(alias, dto.getAlias());
        assertEquals(shortenedUrl, dto.getUrl());
        assertNotNull(dto.getStatisticsDto());
        assertNotNull(dto.getStatisticsDto().getTime_taken());
        verify(repository, times(1)).save(ArgumentMatchers.any());
    }

    @Test
    void testGetOriginalURL() {
        String alias = "blabluble";
        URLShortenerEntity entity = new URLShortenerEntity();
        entity.setAlias(alias);

        when(repository.findByAlias(alias)).thenReturn(entity);

        URLShortenerEntity result = service.getOriginalURL(alias);
        assertNotNull(result);
        assertEquals(alias, result.getAlias());
        verify(repository, times(1)).save(entity);
    }

}
