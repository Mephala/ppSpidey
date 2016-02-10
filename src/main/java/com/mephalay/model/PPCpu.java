package com.mephalay.model;

import java.math.BigDecimal;

/**
 * Created by Mephalay on 2/9/2016.
 */
public class PPCpu {


    private String guid;

    private String cpuName;

    private String cpuFrequency;

    private Integer cpuCoreCount;

    private BigDecimal cpuTdp;

    private Integer cpuRatedCount;

    private BigDecimal cpuAverageRating;

    private BigDecimal cpuCheapestPrice;

    public BigDecimal getCpuCheapestPrice() {
        return cpuCheapestPrice;
    }

    public void setCpuCheapestPrice(BigDecimal cpuCheapestPrice) {
        this.cpuCheapestPrice = cpuCheapestPrice;
    }

    public Integer getCpuRatedCount() {
        return cpuRatedCount;
    }

    public void setCpuRatedCount(Integer cpuRatedCount) {
        this.cpuRatedCount = cpuRatedCount;
    }

    public BigDecimal getCpuAverageRating() {
        return cpuAverageRating;
    }

    public void setCpuAverageRating(BigDecimal cpuAverageRating) {
        this.cpuAverageRating = cpuAverageRating;
    }

    public BigDecimal getCpuTdp() {
        return cpuTdp;
    }

    public void setCpuTdp(BigDecimal cpuTdp) {
        this.cpuTdp = cpuTdp;
    }

    public Integer getCpuCoreCount() {
        return cpuCoreCount;
    }

    public void setCpuCoreCount(Integer cpuCoreCount) {
        this.cpuCoreCount = cpuCoreCount;
    }

    public String getCpuFrequency() {
        return cpuFrequency;
    }

    public void setCpuFrequency(String cpuFrequency) {
        this.cpuFrequency = cpuFrequency;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getCpuName() {
        return cpuName;
    }

    public void setCpuName(String cpuName) {
        this.cpuName = cpuName;
    }
}
