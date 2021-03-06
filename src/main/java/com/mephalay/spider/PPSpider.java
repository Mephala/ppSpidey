package com.mephalay.spider;

import com.mephalay.model.PPCpu;
import com.mephalay.model.PPDataBundle;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Mephalay on 2/10/2016.
 */
public class PPSpider {

    private static PPSpider instance = null;
    private final long WEB_REQUEST_INTERVAL = 2000L;
    private Long lastWebRequest;

    private PPSpider() {
        this.lastWebRequest = System.currentTimeMillis();
    }


    public static synchronized PPSpider getInstance() {
        if (instance == null)
            instance = new PPSpider();
        return instance;
    }

    public PPDataBundle startSpiding(Logger logger, Integer limit) {
        PPDataBundle ppDataBundle = new PPDataBundle();
        List<PPCpu> cpuList = fetchCpuListFromOnlineContent(logger, limit);
        ppDataBundle.setPpCpuList(cpuList);
        return ppDataBundle;
    }

    private List<PPCpu> fetchCpuListFromOnlineContent(Logger logger, Integer limit) {
        try {
            String urlToFetch = "http://pcpartpicker.com/parts/cpu/fetch/";
            List<PPCpu> cpuList = new ArrayList<>();
            JsonResponse jsonResponse = addProductsFromFetchUrl(urlToFetch, cpuList, logger, limit);
            String paging = jsonResponse.getResult().getPaging_row();
            int pageIndex = paging.lastIndexOf("#page=");
            if (pageIndex != -1) {
                paging = paging.substring(pageIndex + 6);
                int quoteIndex = paging.indexOf("\"");
                String pageCount = paging.substring(0, quoteIndex);
                Integer maxPage = Integer.parseInt(pageCount);
                for (int i = 2; i <= maxPage; i++) {
                    if (limit != null && cpuList.size() == limit.intValue())
                        break;
                    urlToFetch = "http://pcpartpicker.com/parts/cpu/fetch/?page=" + i;
                    addProductsFromFetchUrl(urlToFetch, cpuList, logger, limit);
                }
            }
            return cpuList;
        } catch (Throwable t) {
            logger.fatal("Failed to complete spiding", t);
            return null;
        }
    }

    private JsonResponse addProductsFromFetchUrl(String urlToFetch, List<PPCpu> cpuList, Logger logger, Integer limit) throws IOException {
        if (limit != null && cpuList.size() == limit.intValue())
            return null;
        String retval = getContentsOfWebsite(urlToFetch);
        ObjectMapper om = new ObjectMapper();
        JsonResponse jsonResponse = om.readValue(retval, JsonResponse.class);
        String htmlContent = jsonResponse.getResult().getHtml();
        int hrefIndex = htmlContent.indexOf("<a href=\"");
        if (hrefIndex == -1)
            return null;
        htmlContent = htmlContent.substring(hrefIndex);
        hrefIndex = htmlContent.indexOf("<a href=\"");
        while (hrefIndex != -1) {
            PPCpu cpu = new PPCpu();

            if (hrefIndex != -1) {
                htmlContent = htmlContent.substring(hrefIndex + 9);
                int endOfUrlIndex = htmlContent.indexOf("\">");
                String cpuUrl = htmlContent.substring(0, endOfUrlIndex);
                cpu.setDetailUrl(cpuUrl);
            }
            cpu.setGuid(UUID.randomUUID().toString());
            int aEnd = htmlContent.indexOf("\">");
            int atagEnd = htmlContent.indexOf("</a>");
            String cpuName = htmlContent.substring(aEnd + 2, atagEnd);
            htmlContent = htmlContent.substring(atagEnd + 4);
            cpu.setCpuName(cpuName);
            int alignLeftStart = htmlContent.indexOf("left;\">");
            htmlContent = htmlContent.substring(alignLeftStart + 7);
            int tdClose = htmlContent.indexOf("</td>");
            String frequency = htmlContent.substring(0, tdClose);
            htmlContent = htmlContent.substring(tdClose + 5);
            cpu.setCpuFrequency(frequency);
            int centerStart = htmlContent.indexOf("center;\">");
            htmlContent = htmlContent.substring(centerStart + 9);
            int centerEnd = htmlContent.indexOf("</td>");
            String coreCount = htmlContent.substring(0, centerEnd);
            htmlContent = htmlContent.substring(centerEnd + 5);
            cpu.setCpuCoreCount(Integer.parseInt(coreCount));
            alignLeftStart = htmlContent.indexOf("left;\">");
            htmlContent = htmlContent.substring(alignLeftStart + 7);
            tdClose = htmlContent.indexOf("</td>");
            String tdp = htmlContent.substring(0, tdClose);
            htmlContent = htmlContent.substring(tdClose + 5);
            cpu.setCpuTdp(new BigDecimal(tdp.replace("W", "")));
            int cpuEndIndex = htmlContent.indexOf("</tr>");
            htmlContent = htmlContent.substring(cpuEndIndex + 5);
            logger.info("Getting specifications for:" + cpu);
            if (cpu.getCpuName().equalsIgnoreCase("AMD Sempron 145 (OEM/Tray)"))
                System.out.println("Mebug");
            fillInSpecifications(cpu, logger);
            cpuList.add(cpu);
            if (limit != null && cpuList.size() == limit.intValue())
                break;
            hrefIndex = htmlContent.indexOf("<a href=\"");
        }
        return jsonResponse;
    }

    private synchronized String getContentsOfWebsite(String urlToFetch) throws IOException {
        long now = System.currentTimeMillis();
        long differ = now - lastWebRequest;
        while (differ < WEB_REQUEST_INTERVAL) {
            now = System.currentTimeMillis();
            differ = now - lastWebRequest;
        }
        URL website = new URL(urlToFetch);
        URLConnection connection = website.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        connection.getInputStream()));

        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null)
            response.append(inputLine);

        in.close();
        lastWebRequest = System.currentTimeMillis();
        return response.toString();
    }

    private void fillInSpecifications(PPCpu cpu, Logger logger) {
        try {
            String detailUrl = cpu.getDetailUrl();
            if (detailUrl == null || detailUrl.length() == 0)
                return;
            String htmlContent = getContentsOfWebsite("http://pcpartpicker.com" + detailUrl);
            String startText = "<div class=\"col-xs-4\">Manufacturer</div>";
            int start = htmlContent.indexOf(startText);
            if (start != -1) {
                htmlContent = htmlContent.substring(start + startText.length());
                String tmp = "<div class=\"col-xs-8\">";
                htmlContent = htmlContent.substring(htmlContent.indexOf(tmp) + tmp.length());
                tmp = "</div>";
                int index = htmlContent.indexOf(tmp);
                String manufacturer = htmlContent.substring(0, index).trim();
                cpu.setManufacturer(manufacturer);
                tmp = ">Model</div>";
                htmlContent = consumeUntilTag(htmlContent, tmp);
                String model = getNextValue(htmlContent);
                cpu.setModel(model);
                tmp = ">Data Width</div>";
                htmlContent = consumeUntilTag(htmlContent, tmp);
                String dataWidth = getNextValue(htmlContent);
                cpu.setDataWidth(dataWidth);
                tmp = ">Socket</div>";
                htmlContent = consumeUntilTag(htmlContent, tmp);
                String socket = getNextValue(htmlContent);
                cpu.setSocket(socket);
                tmp = ">L1 Cache</div>";
                htmlContent = consumeUntilTag(htmlContent, tmp);
                String L1Cache = getNextValue(htmlContent);
                cpu.setL1Cache(L1Cache);
                tmp = ">L2 Cache</div>";
                htmlContent = consumeUntilTag(htmlContent, tmp);
                String L2Cache = getNextValue(htmlContent);
                cpu.setL2Cache(L2Cache);
                tmp = ">L3 Cache</div>";
                htmlContent = consumeUntilTag(htmlContent, tmp);
                String L3Cache = getNextValue(htmlContent);
                cpu.setL3Cache(L3Cache);
                tmp = ">Lithography</div>";
                htmlContent = consumeUntilTag(htmlContent, tmp);
                String lithography = getNextValue(htmlContent);
                cpu.setLithography(lithography);
                tmp = ">Includes CPU Cooler</div>";
                htmlContent = consumeUntilTag(htmlContent, tmp);
                Boolean hasCpuCooler = !getNextValue(htmlContent).equals("No");
                cpu.setIncludesCpuCooler(hasCpuCooler);
                tmp = ">Hyper-Threading</div>";
                htmlContent = consumeUntilTag(htmlContent, tmp);
                Boolean hasHyperThreading = !getNextValue(htmlContent).equals("No");
                cpu.setIncludesHyperThreading(hasHyperThreading);
                tmp = ">Integrated Graphics</div>";
                htmlContent = consumeUntilTag(htmlContent, tmp);
                String integratedGraphics = getNextValue(htmlContent);
                cpu.setIntegratedGPU(integratedGraphics);
            }
        } catch (Throwable t) {
            logger.error("Failed to fetch specs of cpu:" + cpu, t);
        }
    }

    private String consumeUntilTag(String htmlContent, String tmp) {
        int index;
        index = htmlContent.indexOf(tmp);
        htmlContent = htmlContent.substring(index + tmp.length());
        return htmlContent;
    }

    private String getNextValue(String htmlContent) {
        String tmp;
        int index;
        tmp = "<div class=\"col-xs-8\">";
        htmlContent = htmlContent.substring(htmlContent.indexOf(tmp) + tmp.length());
        tmp = "</div>";
        index = htmlContent.indexOf(tmp);
        return htmlContent.substring(0, index).trim();
    }


}
