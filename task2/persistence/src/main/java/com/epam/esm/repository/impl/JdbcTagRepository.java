package com.epam.esm.repository.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Implementation of the {@code TagRepository} that uses JDBC.
 *
 * @author Shuleiko Yulia
 */
@Repository
public class JdbcTagRepository implements TagRepository {

    private JdbcTemplate jdbcTemplate;
    private static final String SQL_INSERT_TAG = "INSERT INTO tag (name) VALUES (?)";
    private static final String SQL_DELETE_TAG = "DELETE FROM tag WHERE id = ?";
    private static final String SQL_SELECT_TAG_BY_ID = "SELECT id, name FROM tag WHERE id = ?";
    private static final String SQL_SELECT_TAG_BY_NAME = "SELECT id, name FROM tag WHERE name = ?";
    private static final String SQL_SELECT_TAGS_BY_GIFT_CERTIFICATE_ID = "SELECT tag.id, tag.name " +
            "FROM tag JOIN tag_m2m_gift_certificate " +
            "ON tag.id = tag_m2m_gift_certificate.tag_id " +
            "WHERE tag_m2m_gift_certificate.gift_certificate_id = ?";
    private static final String SQL_DELETE_CONNECTION_BY_GIFT_CERTIFICATE_ID = "DELETE " +
            "FROM tag_m2m_gift_certificate WHERE gift_certificate_id = ?";
    private static final String SQL_DELETE_CONNECTION_BY_TAG_ID = "DELETE " +
            "FROM tag_m2m_gift_certificate WHERE tag_id = ?";
    private static final String SQL_INSERT_TAG_AND_GIFT_CERTIFICATE_CONNECTION = "INSERT " +
            "INTO tag_m2m_gift_certificate (gift_certificate_id, tag_id) VALUES (?, ?)";
    private static final String SQL_SELECT_TAGS_BY_GIFT_CERTIFICATE_ID_AND_TAG_ID = "SELECT tag.id, tag.name " +
            "FROM tag JOIN tag_m2m_gift_certificate " +
            "ON tag.id = tag_m2m_gift_certificate.tag_id " +
            "WHERE tag_m2m_gift_certificate.gift_certificate_id = ? AND tag_m2m_gift_certificate.tag_id = ?";

    /**
     * Setter method of the {@code JdbcTemplate} object.
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
    public void createTag(Tag tag) {
        jdbcTemplate.update(SQL_INSERT_TAG, tag.getName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteTag(long tagId) {
        jdbcTemplate.update(SQL_DELETE_TAG, tagId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Tag> findTagById(long tagId) {
        List<Tag> tags = jdbcTemplate.query(SQL_SELECT_TAG_BY_ID,
                rs -> rs.setLong(1, tagId),
                this::mapTag);
        return tags.isEmpty() ? Optional.empty() : Optional.of(tags.get(0));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Tag> findTagsByGiftCertificateId(long giftCertificateId) {
         List<Tag> tags = jdbcTemplate.query(SQL_SELECT_TAGS_BY_GIFT_CERTIFICATE_ID,
                ps -> ps.setLong(1, giftCertificateId),
                this::mapTag);
         return new HashSet<>(tags);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void disconnectTagsFromGiftCertificate(long giftCertificateId) {
        jdbcTemplate.update(SQL_DELETE_CONNECTION_BY_GIFT_CERTIFICATE_ID, giftCertificateId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Tag> findTagByName(String name) {
        List<Tag> tags = jdbcTemplate.query(SQL_SELECT_TAG_BY_NAME,
                rs -> rs.setString(1, name),
                this::mapTag);
        return tags.isEmpty() ? Optional.empty() : Optional.of(tags.get(0));
    }

    @Override
    public boolean connectionExists(long giftCertificateId, long tagId) {
        List<Tag> tags = jdbcTemplate.query(SQL_SELECT_TAGS_BY_GIFT_CERTIFICATE_ID_AND_TAG_ID,
                ps -> {
                    ps.setLong(1, giftCertificateId);
                    ps.setLong(2, tagId);
                },
                this::mapTag);
        return !tags.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void connectTagsToGiftCertificate(long giftCertificateId, long tagId) {
        jdbcTemplate.update(SQL_INSERT_TAG_AND_GIFT_CERTIFICATE_CONNECTION,
                giftCertificateId,
                tagId);
    }

    @Override
    public void disconnectGiftCertificatesFromTag(long tagId) {
        jdbcTemplate.update(SQL_DELETE_CONNECTION_BY_TAG_ID,tagId);
    }

    /**
     * Maps result set content on the {@code Tag} object.
     *
     * @param rs the {@code ResultSet} object
     * @param row number of the row in table
     * @return the {@code Tag} object
     * @throws SQLException if attempt to get incompatible data from result set
     */
    private Tag mapTag(ResultSet rs, int row) throws SQLException{
        long id = rs.getLong("id");
        String name = rs.getString("name");
        return new Tag(id, name);
    }
}
