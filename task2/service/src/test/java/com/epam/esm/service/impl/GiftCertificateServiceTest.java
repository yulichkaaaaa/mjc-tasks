package com.epam.esm.service.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.converters.GiftCertificateDtoConverter;
import com.epam.esm.dto.converters.TagDtoConverter;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityNotExistException;
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
import java.time.Month;
import java.util.Optional;
import java.util.Set;
import java.util.HashSet;

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
    public void init() {
        giftCertificateService = new GiftCertificateServiceImpl();
        setConverters();
        setRepositories();
        giftCertificate = new GiftCertificate(1, "present", "some information",
                new BigDecimal("12.32"), 12,
                LocalDateTime.of(2019, Month.APRIL, 15, 0, 0),
                LocalDateTime.now());
        Set<TagDto> tags = new HashSet<>();
        tags.add(new TagDto("chill"));
        giftCertificateDto = giftCertificateDtoConverter.convertToDto(giftCertificate, tags);
    }

    private void setConverters() {
        giftCertificateDtoConverter = new GiftCertificateDtoConverter();
        tagDtoConverter = new TagDtoConverter();
        giftCertificateService.setTagDtoConverter(tagDtoConverter);
        giftCertificateService.setGiftCertificateDtoConverter(giftCertificateDtoConverter);
    }

    private void setRepositories() {
        giftCertificateRepository = new JdbcGiftCertificateRepository();
        tagRepository = new JdbcTagRepository();
        MockitoAnnotations.openMocks(this);
        giftCertificateService.setGiftCertificateRepository(giftCertificateRepository);
        giftCertificateService.setTagRepository(tagRepository);
    }

    @Test
    public void findGiftCertificateByIdTest() {
        long id = 1;
        Mockito.when(giftCertificateRepository.findGiftCertificateById(id))
                .thenReturn(Optional.of(giftCertificate));
        Assertions.assertEquals(giftCertificateDtoConverter.convertToDto(giftCertificate, new HashSet<>()),
                giftCertificateService.findGiftCertificateById(id));
    }

    @Test
    public void findGiftCertificateByIdNotFoundTest() {
        long id = 100;
        Mockito.when(giftCertificateRepository.findGiftCertificateById(id))
                .thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> giftCertificateService.findGiftCertificateById(id));
    }

    @Test
    public void createGiftCertificateTest() {
        Tag tag = tagDtoConverter.convertToEntity(giftCertificateDto.getTag(0));
        Mockito.when(tagRepository.findTagByName(tag.getName()))
                .thenReturn(Optional.of(tag));
        giftCertificateService.createGiftCertificate(giftCertificateDto);
        Mockito.verify(giftCertificateRepository, Mockito.times(1))
                .createGiftCertificate(Mockito.any());
    }

    @Test
    public void updateGiftCertificateTest() {
        Mockito.when(giftCertificateRepository.findGiftCertificateById(giftCertificateDto.getId()))
                .thenReturn(Optional.of(giftCertificate));
        Tag tag = tagDtoConverter.convertToEntity(giftCertificateDto.getTag(0));
        Mockito.when(tagRepository.findTagByName(tag.getName()))
                .thenReturn(Optional.of(tag));
        giftCertificateService.updateGiftCertificate(giftCertificateDto);
        Mockito.verify(giftCertificateRepository, Mockito.times(1))
                .updateGiftCertificate(Mockito.any());
    }

    @Test
    public void updateGiftCertificateNotExistTest() {
        Mockito.when(giftCertificateRepository.findGiftCertificateById(giftCertificateDto.getId()))
                .thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotExistException.class,
                () -> giftCertificateService.updateGiftCertificate(giftCertificateDto));
    }

    @Test
    public void deleteGiftCertificateTest() {
        long id = giftCertificate.getId();
        Mockito.when(giftCertificateRepository.findGiftCertificateById(id))
                .thenReturn(Optional.of(giftCertificate));
        giftCertificateService.deleteGiftCertificateById(id);
        Mockito.verify(giftCertificateRepository, Mockito.times(1))
                .deleteGiftCertificate(id);
    }

    @Test
    public void deleteGiftCertificateBotExistTest() {
        long id = 100;
        Mockito.when(giftCertificateRepository.findGiftCertificateById(id))
                .thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotExistException.class,
                () -> giftCertificateService.deleteGiftCertificateById(id));

    }

}
