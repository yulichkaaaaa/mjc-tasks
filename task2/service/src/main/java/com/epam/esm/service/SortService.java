package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Provides sorting operations.
 *
 * @author Shuleiko Yulia
 */
@Service
public class SortService {

    private static final String CRITERIA_NAME = "name";
    private static final String CRITERIA_CREATE_DATE = "create_date";
    private static final String DIRECTION_DESC = "desc";
    private Map<String, Comparator<GiftCertificateDto>> comparatorMap;

    /**
     * Constructs sort service.
     */
    public SortService() {
        initGiftCertificateComparators();
    }

    /**
     * Initialize map of comparators of gift certificates.
     */
    private void initGiftCertificateComparators() {
        comparatorMap = new HashMap<>();
        comparatorMap.put(CRITERIA_NAME, Comparator.comparing(GiftCertificateDto::getName));
        comparatorMap.put(CRITERIA_CREATE_DATE, Comparator.comparing(GiftCertificateDto::getCreateDate));
    }

    /**
     * Sort list of gift certificates.
     *
     * @param giftCertificates list of gift certificates
     * @param criteria criteria of sorting (name, date)
     * @param direction direction of sorting (asc or desc)
     * @return list of gift certificates
     */
    public List<GiftCertificateDto> sortGiftCertificates(List<GiftCertificateDto> giftCertificates,
                                         String criteria,
                                         String direction) {
        if (Objects.nonNull(criteria) && comparatorMap.containsKey(criteria)) {
            giftCertificates.sort(comparatorMap.get(criteria));
            if (direction.equals(DIRECTION_DESC)) {
                Collections.reverse(giftCertificates);
            }
        }
        return giftCertificates;
    }
}
