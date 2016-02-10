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

/**
 * Created by Mephalay on 2/10/2016.
 */
public class PPSpider {

    private static PPSpider instance = null;

    private PPSpider(){

    }


    public static synchronized PPSpider getInstance(){
        if(instance==null)
            instance = new PPSpider();
        return instance;
    }

    public PPDataBundle startSpiding(Logger logger){
        PPDataBundle ppDataBundle = new PPDataBundle();
        List<PPCpu> cpuList = fetchCpuListFromOnlineContent(logger);
        ppDataBundle.setPpCpuList(cpuList);
        return ppDataBundle;
    }

    public List<PPCpu> fetchCpuListFromOnlineContent(Logger logger) {
        try {
            String urlToFetch = "http://pcpartpicker.com/parts/cpu/fetch/";
            List<PPCpu> cpuList = new ArrayList<>();
            JsonResponse jsonResponse = addProductsFromFetchUrl(urlToFetch, cpuList);
            String paging = jsonResponse.getResult().getPaging_row();
            int pageIndex = paging.lastIndexOf("#page=");
            if (pageIndex != -1) {
                paging = paging.substring(pageIndex + 6);
                int quoteIndex = paging.indexOf("\"");
                String pageCount = paging.substring(0, quoteIndex);
                Integer maxPage = Integer.parseInt(pageCount);
                for (int i = 2; i <= maxPage; i++) {
                    urlToFetch = "http://pcpartpicker.com/parts/cpu/fetch/?page=" + i;
                    addProductsFromFetchUrl(urlToFetch, cpuList);
                }
            }
            return cpuList;
        } catch (Throwable t) {
            logger.fatal("Failed to complete spiding",t);
            return null;
        }
    }

    private JsonResponse addProductsFromFetchUrl(String urlToFetch, List<PPCpu> cpuList) throws IOException {
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
        String retval = response.toString();
        ObjectMapper om = new ObjectMapper();
        JsonResponse jsonResponse = om.readValue(retval, JsonResponse.class);
        String htmlContent = jsonResponse.getResult().getHtml();
        int hrefIndex = htmlContent.indexOf("<a href=\"");
        htmlContent = htmlContent.substring(hrefIndex);

        while (hrefIndex != -1) {
            PPCpu cpu = new PPCpu();
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
            hrefIndex = htmlContent.indexOf("<a href=\"");
            if (hrefIndex != -1)
                htmlContent = htmlContent.substring(hrefIndex + 9);
            cpuList.add(cpu);
        }
        return jsonResponse;
    }


}
