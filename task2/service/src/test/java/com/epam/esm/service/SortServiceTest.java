package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SortServiceTest {

    private SortService sortService;
    private List<GiftCertificateDto> giftCertificates;

    @BeforeEach
    public void init() {
        sortService = new SortService();
        initGiftCertificates();
    }

    private void initGiftCertificates() {
        giftCertificates = new ArrayList<>(Arrays.asList(
                new GiftCertificateDto(2, "birthday party",
                        "Organization of birthday party",
                        new BigDecimal("30.10"), 12,
                        LocalDateTime.of(2019, Month.APRIL, 15, 0, 0),
                        LocalDateTime.of(2020, Month.JANUARY, 7, 0, 0),
                        new HashSet<>()),
                new GiftCertificateDto(3, "skydiving",
                        "Try skydiving if you dont afraid!",
                        new BigDecimal("60.70"), 100,
                        LocalDateTime.of(2021, Month.FEBRUARY, 1, 0, 0),
                        LocalDateTime.of(2021, Month.MARCH, 30, 0, 0),
                        new HashSet<>()),
                new GiftCertificateDto(4, "diving",
                        "Dive in the sea with instructor",
                        new BigDecimal("50.60"), 18,
                        LocalDateTime.of(2016, Month.FEBRUARY, 15, 0, 0),
                        LocalDateTime.of(2019, Month.JANUARY, 9, 0, 0),
                        new HashSet<>())));
    }

    @Test
    public void sortGiftCertificateByNameAscTest() {
        List<GiftCertificateDto> result = sortService
                .sortGiftCertificates(giftCertificates, Collections.singletonList("name"), "asc");
        Assertions.assertEquals(2, result.get(0).getId());
        Assertions.assertEquals(4, result.get(1).getId());
        Assertions.assertEquals(3, result.get(2).getId());
    }

    @Test
    public void sortGiftCertificateByNameDescTest() {
        List<GiftCertificateDto> result = sortService
                .sortGiftCertificates(giftCertificates, Collections.singletonList("name"), "desc");
        Assertions.assertEquals(3, result.get(0).getId());
        Assertions.assertEquals(4, result.get(1).getId());
        Assertions.assertEquals(2, result.get(2).getId());
    }

    @Test
    public void sortGiftCertificateByDateAscTest() {
        List<GiftCertificateDto> result = sortService
                .sortGiftCertificates(giftCertificates, Collections.singletonList("create_date"), "asc");
        Assertions.assertEquals(4, result.get(0).getId());
        Assertions.assertEquals(2, result.get(1).getId());
        Assertions.assertEquals(3, result.get(2).getId());
    }

    @Test
    public void sortGiftCertificateByDateDescTest() {
        List<GiftCertificateDto> result = sortService
                .sortGiftCertificates(giftCertificates, Collections.singletonList("create_date"), "desc");
        Assertions.assertEquals(3, result.get(0).getId());
        Assertions.assertEquals(2, result.get(1).getId());
        Assertions.assertEquals(4, result.get(2).getId());
    }

    @Test
    public void sortGiftCertificateByNameAndDateTest() {
        giftCertificates.add(new GiftCertificateDto(5, "diving",
                "See the beauty of the ocean",
                new BigDecimal("50.60"), 18,
                LocalDateTime.of(2014, Month.MARCH, 15, 0, 0),
                LocalDateTime.of(2020, Month.JANUARY, 9, 0, 0),
                new HashSet<>()));
        List<GiftCertificateDto> result = sortService
                .sortGiftCertificates(giftCertificates, new ArrayList<>(Arrays.asList("name", "create_date")), "asc");
        Assertions.assertEquals(2, result.get(0).getId());
        Assertions.assertEquals(5, result.get(1).getId());
        Assertions.assertEquals(4, result.get(2).getId());
        Assertions.assertEquals(3, result.get(3).getId());
    }
}
