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
    private String detailUrl;
    private String manufacturer;
    private String model;
    private String dataWidth;
    private String socket;
    private String L1Cache;
    private String L2Cache;
    private String L3Cache;
    private String lithography;
    private Boolean includesCpuCooler;
    private Boolean includesHyperThreading;
    private String integratedGPU;

    @Override
    public String toString() {
        return "PPCpu{" +
                "cpuName='" + cpuName + '\'' +
                ", model='" + model + '\'' +
                ", socket='" + socket + '\'' +
                '}';
    }

    public String getL2Cache() {
        return L2Cache;
    }

    public void setL2Cache(String l2Cache) {
        L2Cache = l2Cache;
    }

    public String getL3Cache() {
        return L3Cache;
    }

    public void setL3Cache(String l3Cache) {
        L3Cache = l3Cache;
    }

    public String getLithography() {
        return lithography;
    }

    public void setLithography(String lithography) {
        this.lithography = lithography;
    }

    public Boolean getIncludesCpuCooler() {
        return includesCpuCooler;
    }

    public void setIncludesCpuCooler(Boolean includesCpuCooler) {
        this.includesCpuCooler = includesCpuCooler;
    }

    public Boolean getIncludesHyperThreading() {
        return includesHyperThreading;
    }

    public void setIncludesHyperThreading(Boolean includesHyperThreading) {
        this.includesHyperThreading = includesHyperThreading;
    }

    public String getIntegratedGPU() {
        return integratedGPU;
    }

    public void setIntegratedGPU(String integratedGPU) {
        this.integratedGPU = integratedGPU;
    }

    public String getL1Cache() {
        return L1Cache;
    }

    public void setL1Cache(String l1Cache) {
        L1Cache = l1Cache;
    }

    public String getSocket() {
        return socket;
    }

    public void setSocket(String socket) {
        this.socket = socket;
    }

    public String getDataWidth() {
        return dataWidth;
    }

    public void setDataWidth(String dataWidth) {
        this.dataWidth = dataWidth;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

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
