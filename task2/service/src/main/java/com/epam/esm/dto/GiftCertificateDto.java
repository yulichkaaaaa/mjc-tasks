package com.epam.esm.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class GiftCertificateDto {

    private long id;

    @NotNull
    @Size(max = 40)
    private String name;

    @NotNull
    @Size(max = 200)
    private String description;

    @NotNull
    private BigDecimal price;

    @NotNull
    private int duration;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    private Set<TagDto> tags;

    public GiftCertificateDto(long id, String name, String description,
                              BigDecimal price, int duration, LocalDateTime createDate,
                              LocalDateTime lastUpdateDate, Set<TagDto> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.tags = new HashSet<>();
        this.tags.addAll(tags);
    }

    public GiftCertificateDto(String name, String description, BigDecimal price,
                              int duration, LocalDateTime createDate,
                              LocalDateTime lastUpdateDate, Set<TagDto> tags) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.tags = new HashSet<>();
        this.tags.addAll(tags);
    }

    public GiftCertificateDto() {
        tags = new HashSet<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Set<TagDto> getTags() {
        return new HashSet<>(tags);
    }

    public void addTag(TagDto tagDto){
        tags.add(tagDto);
    }

    public void addTags(Set<TagDto> tags){
        this.tags.addAll(tags);
    }

    public void removeTag(TagDto tagDto){
        tags.remove(tagDto);
    }
}
