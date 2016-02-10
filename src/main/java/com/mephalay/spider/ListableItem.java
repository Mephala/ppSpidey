package com.mephalay.spider;

import java.math.BigDecimal;

/**
 * Created by Mephalay on 2/9/2016.
 */
public class ListableItem {

    private String slug;
    private BigDecimal price;
    private String name;
    private String id;
    private Boolean grouped;

    public ListableItem() {

    }

    @Override
    public String toString() {
        return "ListableItem{" +
                "slug='" + slug + '\'' +
                ", price=" + price +
                ", name='" + name + '\'' +
                ", id=" + id +
                ", grouped=" + grouped +
                '}';
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getGrouped() {
        return grouped;
    }

    public void setGrouped(Boolean grouped) {
        this.grouped = grouped;
    }
}
