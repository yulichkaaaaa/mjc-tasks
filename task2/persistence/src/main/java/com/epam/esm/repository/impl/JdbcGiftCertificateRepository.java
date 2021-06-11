package com.epam.esm.repository.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.repository.GiftCertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class JdbcGiftCertificateRepository implements GiftCertificateRepository {

    private JdbcTemplate jdbcTemplate;
    private static final String SQL_INSERT_GIFT_CERTIFICATE = "INSERT INTO gift_certificate " +
            "(name, description, price, duration, create_date, last_update_date) " +
            "VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_GIFT_CERTIFICATE = "UPDATE gift_certificate " +
            "SET name = COALESCE (?, name), " +
            "description = COALESCE (?, description), " +
            "price = COALESCE (?, price), " +
            "duration = COALESCE (?, duration), " +
            "create_date = COALESCE (?, create_date), " +
            "last_update_date = ? " +
            "WHERE id = ?";
    private static final String SQL_DELETE_GIFT_CERTIFICATE = "DELETE FROM gift_certificate WHERE id = ?";
    private static final String SQL_SELECT_GIFT_CERTIFICATE_BY_ID = "SELECT id, name, description, price, " +
            "duration, create_date, last_update_date " +
            "FROM gift_certificate WHERE id = ?";
    private static final String SQL_SELECT_GIFT_CERTIFICATES_BY_TAG_NAME = "SELECT gift_certificate.id, " +
            "gift_certificate.name, gift_certificate.description, gift_certificate.price, gift_certificate.duration, " +
            "gift_certificate.create_date, gift_certificate.last_update_date " +
            "FROM gift_certificate " +
            "JOIN tag_m2m_gift_certificate " +
            "ON gift_certificate.id = tag_m2m_gift_certificate.gift_certificate_id " +
            "JOIN tag ON tag_m2m_gift_certificate.tag_id = tag.id " +
            "WHERE tag.name LIKE ?";
    private static final String SQL_SELECT_GIFT_CERTIFICATES_BY_NAME_AND_DESCRIPTION = "SELECT id, name, " +
            "description, price, duration, create_date, last_update_date  " +
            "FROM gift_certificate " +
            "WHERE name LIKE CONCAT('%', IFNULL(?, name), '%') " +
            "AND description LIKE CONCAT('%', IFNULL(?, description), '%')";
    private static final String SQL_SELECT_ALL_GIFT_CERTIFICATES = "SELECT id, name, " +
            "description, price, duration, create_date, last_update_date  " +
            "FROM gift_certificate";

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
    public long createGiftCertificate(GiftCertificate giftCertificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(SQL_INSERT_GIFT_CERTIFICATE, new String[]{"id"});
            ps.setString(1, giftCertificate.getName());
            ps.setString(2, giftCertificate.getDescription());
            ps.setBigDecimal(3, giftCertificate.getPrice());
            ps.setInt(4, giftCertificate.getDuration());
            ps.setTimestamp(5, Timestamp.valueOf(giftCertificate.getCreateDate()));
            ps.setTimestamp(6, Timestamp.valueOf(giftCertificate.getLastUpdateDate()));
            return ps;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateGiftCertificate(GiftCertificate giftCertificate) {
        LocalDateTime createDate = giftCertificate.getCreateDate();
        jdbcTemplate.update(SQL_UPDATE_GIFT_CERTIFICATE,
                giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(),
                giftCertificate.getDuration(),
                Objects.isNull(createDate) ? null : Timestamp.valueOf(giftCertificate.getCreateDate()),
                Timestamp.valueOf(giftCertificate.getLastUpdateDate()),
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
        List<GiftCertificate> giftCertificates = jdbcTemplate.query(SQL_SELECT_GIFT_CERTIFICATE_BY_ID,
                rs -> rs.setLong(1, giftCertificateId),
                this::mapGiftCertificate);
        return giftCertificates.isEmpty() ? Optional.empty() : Optional.of(giftCertificates.get(0));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GiftCertificate> findGiftCertificateByTagName(String tagName) {
        return jdbcTemplate.query(SQL_SELECT_GIFT_CERTIFICATES_BY_TAG_NAME,
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

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GiftCertificate> findAllGiftCertificates() {
        return jdbcTemplate.query(SQL_SELECT_ALL_GIFT_CERTIFICATES, this::mapGiftCertificate);
    }

    /**
     * Maps result set content on the {@code GiftCertificate} object.
     *
     * @param rs the {@code ResultSet} object
     * @param row number of the row in table
     * @return the {@code GiftCertificate} object
     * @throws SQLException if attempt to get incompatible data from result set
     */
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
