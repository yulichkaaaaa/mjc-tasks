package com.epam.esm.repository.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.repository.GiftCertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcGiftCertificateRepository implements GiftCertificateRepository {

    private JdbcTemplate jdbcTemplate;
    private static final String SQL_INSERT_GIFT_CERTIFICATE = "INSERT INTO gift_certificate " +
            "(name, description, price, duration, create_date, last_update_date) " +
            "VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_GIFT_CERTIFICATE = "UPDATE gift_certificate " +
            "SET name = ?, description = ?, price = ?, duration = ?, create_date = ?, last_update_date = ? " +
            "WHERE id = ?";
    private static final String SQL_DELETE_GIFT_CERTIFICATE = "DELETE FROM gift_certificate WHERE id = ?";
    private static final String SQL_SELECT_GIFT_CERTIFICATE_BY_ID = "SELECT id, name, description, price, " +
            "duration, create_date, last_update_date " +
            "FROM gift_certificate WHERE id = ?";
    private static final String SQL_SELECT_GIFT_CERTIFICATES_BY_TAG_NAMES = "SELECT gift_certificate.id, " +
            "gift_certificate.name, gift_certificate.price, gift_certificate.duration, " +
            "gift_certificate.create_date, gift_certificate.last_update_date " +
            "FROM gift_certificate " +
            "JOIN tag_m2m_gift_certificate " +
            "ON gift_certificate.id = tag_m2m_gift_certificate.gift_certificate_id " +
            "JOIN tag ON tag_m2m_gift_certificate.tag_id = tag.id" +
            "WHERE tag.name LIKE ?";
    private static final String SQL_SELECT_GIFT_CERTIFICATES_BY_NAME_AND_DESCRIPTION = "SELECT id, name, " +
            "description, price, duration, create_date, last_update_date  " +
            "FROM gift_certificate " +
            "WHERE name LIKE CONCAT('%', IFNULL(?, name), '%') " +
            "AND description LIKE CONCAT('%', IFNULL(?, description), '%')";

    /**
     * Setter method of {@code JdbcTemplate} object.
     *
     * @param jdbcTemplate the {@code JdbcTemplate} object
     */
    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createGiftCertificate(GiftCertificate giftCertificate) {
        jdbcTemplate.update(SQL_INSERT_GIFT_CERTIFICATE,
                giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(),
                giftCertificate.getDuration(),
                giftCertificate.getCreateDate(),
                giftCertificate.getLastUpdateDate());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateGiftCertificate(GiftCertificate giftCertificate) {
        jdbcTemplate.update(SQL_UPDATE_GIFT_CERTIFICATE,
                giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(),
                giftCertificate.getDuration(),
                giftCertificate.getCreateDate(),
                giftCertificate.getLastUpdateDate(),
                giftCertificate.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteGiftCertificate(long giftCertificateId) {
        jdbcTemplate.update(SQL_DELETE_GIFT_CERTIFICATE, giftCertificateId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<GiftCertificate> findGiftCertificateById(long giftCertificateId) {
        GiftCertificate giftCertificate = jdbcTemplate.queryForObject(SQL_SELECT_GIFT_CERTIFICATE_BY_ID,
                this::mapGiftCertificate,
                giftCertificateId);
        return Optional.ofNullable(giftCertificate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GiftCertificate> findGiftCertificateByTagName(String tagName) {
        return jdbcTemplate.query(SQL_SELECT_GIFT_CERTIFICATES_BY_TAG_NAMES,
                ps -> ps.setString(1, tagName),
                this::mapGiftCertificate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GiftCertificate> findGiftCertificatesByNameAndDescription(String name, String description) {
        return jdbcTemplate.query(SQL_SELECT_GIFT_CERTIFICATES_BY_NAME_AND_DESCRIPTION,
                ps -> {
                    ps.setString(1, name);
                    ps.setString(2, description);
                },
                this::mapGiftCertificate);
    }

    private GiftCertificate mapGiftCertificate(ResultSet rs, int row) throws SQLException {
        long id = rs.getLong("id");
        String name = rs.getString("name");
        String description = rs.getString("description");
        BigDecimal price = rs.getBigDecimal("price");
        int duration = rs.getInt("duration");
        LocalDateTime createDate = rs.getTimestamp("create_date").toLocalDateTime();
        LocalDateTime lastUpdateDate = rs.getTimestamp("last_update_date").toLocalDateTime();
        return new GiftCertificate(id, name, description, price, duration, createDate, lastUpdateDate);
    }
}
