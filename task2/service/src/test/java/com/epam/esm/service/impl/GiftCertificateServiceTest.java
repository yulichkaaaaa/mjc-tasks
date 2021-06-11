package com.epam.esm.service.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.converters.GiftCertificateDtoConverter;
import com.epam.esm.dto.converters.TagDtoConverter;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.impl.JdbcGiftCertificateRepository;
import com.epam.esm.repository.impl.JdbcTagRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GiftCertificateServiceTest {

    private GiftCertificateServiceImpl giftCertificateService;
    @Mock
    private GiftCertificateRepository giftCertificateRepository;
    private GiftCertificateDtoConverter giftCertificateDtoConverter;
    @Mock
    private TagRepository tagRepository;
    private TagDtoConverter tagDtoConverter;
    private GiftCertificate giftCertificate;
    private GiftCertificateDto giftCertificateDto;

    @BeforeAll
    public void init(){
        giftCertificateDtoConverter = new GiftCertificateDtoConverter();
        giftCertificateRepository = new JdbcGiftCertificateRepository();
        giftCertificateService = new GiftCertificateServiceImpl();
        tagRepository = new JdbcTagRepository();
        tagDtoConverter = new TagDtoConverter();
        MockitoAnnotations.openMocks(this);
        giftCertificate = new GiftCertificate(1, "present", "some information",
                new BigDecimal("12.32"), 12, LocalDateTime.now(), LocalDateTime.now());
        Set<TagDto> tags = new HashSet<>();
        tags.add(new TagDto("chill"));
        giftCertificateDto = giftCertificateDtoConverter.convertToDto(giftCertificate, tags);
        giftCertificateService.setGiftCertificateRepository(giftCertificateRepository);
        giftCertificateService.setTagDtoConverter(tagDtoConverter);
        giftCertificateService.setGiftCertificateDtoConverter(giftCertificateDtoConverter);
        giftCertificateService.setTagRepository(tagRepository);
    }

    @Test
    public void findGiftCertificateByIdTest(){
        long id = 1;
        Mockito.when(giftCertificateRepository.findGiftCertificateById(id))
                .thenReturn(Optional.of(giftCertificate));
        Assertions.assertEquals(giftCertificateDtoConverter.convertToDto(giftCertificate, new HashSet<>()),
                giftCertificateService.findGiftCertificateById(id));
    }

    @Test
    public void findGiftCertificateByIdNotFoundTest(){
        long id = 100;
        Mockito.when(giftCertificateRepository.findGiftCertificateById(id))
                .thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> giftCertificateService.findGiftCertificateById(id));
    }

    @Test
    public void createGiftCertificateTest(){
        long id = 1;
        Mockito.when(giftCertificateRepository.findGiftCertificateById(id))
                .thenReturn(Optional.empty());
        giftCertificateService.createGiftCertificate(giftCertificateDto);
        Mockito.verify(giftCertificateRepository, Mockito.times(1))
                .createGiftCertificate(giftCertificate);

    }
}
