package com.mephalay.spider;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by Mephalay on 2/9/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ListItems {
    private List<ListableItem> data;

    public ListItems() {

    }

    @Override
    public String toString() {
        return "ListItems{" +
                "data=" + data +
                '}';
    }

    public List<ListableItem> getData() {
        return data;
    }

    public void setData(List<ListableItem> data) {
        this.data = data;
    }
}
