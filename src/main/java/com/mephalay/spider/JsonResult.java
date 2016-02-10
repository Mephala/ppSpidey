package com.mephalay.spider;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.math.BigDecimal;

/**
 * Created by Mephalay on 2/9/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonResult {
    private String paging_row;
    private BigDecimal lowest_price;
    private String html;
    private BigDecimal highest_price;
    private ListItems data;
    private BigDecimal alert_price;

    public JsonResult() {

    }

    public String getPaging_row() {
        return paging_row;
    }

    public void setPaging_row(String paging_row) {
        this.paging_row = paging_row;
    }

    public BigDecimal getLowest_price() {
        return lowest_price;
    }

    public void setLowest_price(BigDecimal lowest_price) {
        this.lowest_price = lowest_price;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public BigDecimal getHighest_price() {
        return highest_price;
    }

    public void setHighest_price(BigDecimal highest_price) {
        this.highest_price = highest_price;
    }

    public ListItems getData() {
        return data;
    }

    public void setData(ListItems data) {
        this.data = data;
    }

    public BigDecimal getAlert_price() {
        return alert_price;
    }

    public void setAlert_price(BigDecimal alert_price) {
        this.alert_price = alert_price;
    }
}
