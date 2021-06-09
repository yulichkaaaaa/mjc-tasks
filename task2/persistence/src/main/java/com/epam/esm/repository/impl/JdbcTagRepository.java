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
    private static final String SQL_SELECT_TAGS_BY_GIFT_CERTIFICATES_ID = "SELECT tag.id, tag.name " +
            "FROM tag JOIN tag_m2m_gift_certificate " +
            "ON tag.id = tag_m2m_gift_certificate.tag_id " +
            "WHERE tag_m2m_gift_certificate.gift_certificate_id = ?";
    private static final String SQL_DELETE_TAG_AND_GIFT_CERTIFICATE_CONNECTION = "DELETE " +
            "FROM tag_m2m_gift_certificate " +
            "WHERE tag_id = ? AND gift_certificate_id = ?";


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
        Tag tag = jdbcTemplate.queryForObject(SQL_SELECT_TAG_BY_ID, this::mapTag, tagId);
        return Optional.ofNullable(tag);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Tag> findTagsByGiftCertificateId(long giftCertificateId) {
         List<Tag> tags = jdbcTemplate.query(SQL_SELECT_TAGS_BY_GIFT_CERTIFICATES_ID,
                ps -> ps.setLong(1, giftCertificateId),
                this::mapTag);
         return new HashSet<>(tags);
    }

    @Override
    public void disconnectTag(long tagId, long giftCertificateId) {
        jdbcTemplate.update(SQL_DELETE_TAG_AND_GIFT_CERTIFICATE_CONNECTION,
                tagId,
                giftCertificateId);
    }

    /**
     *
     *
     * @param rs the {@code ResultSet} object
     * @param row number of the row in table
     * @return the {@code Tag} object
     * @throws SQLException if attempt to get incompatible data from result set
     */
    private Tag mapTag(ResultSet rs, int row) throws SQLException {
        long id = rs.getLong("id");
        String name = rs.getString("name");
        return new Tag(id, name);
    }
}
