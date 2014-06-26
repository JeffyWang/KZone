package com.kzone.rest;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;
import com.kzone.bean.KTV;
import com.kzone.bo.KTVData;
import com.kzone.constants.CommonConstants;
import com.kzone.service.KTVService;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jeffy on 2014/6/7 0007.
 */
@Component
@Path("/import")
public class ImportDataRest {
    Logger log = Logger.getLogger(ImportDataRest.class);
    @Autowired
    private KTVService ktvService;

    @POST
    @Path("/ktv/data/{folder}")
//    @Consumes({ MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_JSON })
    @Produces(MediaType.APPLICATION_JSON)
    public Response importKtvData(@Context HttpServletRequest request, @PathParam("folder") String folder) {
        List<KTV> ktvList = new ArrayList<KTV>();
        try {
            ktvList = readCSV(folder);
            for(KTV ktv : ktvList) {
                ktvService.add(ktv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.ok(ktvList, MediaType.APPLICATION_JSON).build();
    }

    public List readCSV(String folder) throws IOException {
        String folderName = Thread.currentThread().getContextClassLoader().getResource("file").getPath() + "/" + folder;

        System.out.println(folderName);

        File dir=new File(folderName);
        File[] files = dir.listFiles();
        List<KTV> dataList = new ArrayList<KTV>();
        String provinceId = dir.getName();

        for(File file : files) {
            String cityId = file.getName().replace(".csv", "");

            List<KTVData> data = new ArrayList<KTVData>();
            List<KTV> ktvList = new ArrayList<KTV>();
            InputStreamReader isr = new InputStreamReader(FileUtils.openInputStream(file));
            CSVReader reader = new CSVReader(isr);
            ColumnPositionMappingStrategy strat = new ColumnPositionMappingStrategy();
            strat.setType(KTVData.class);
            String[] columns = new String[] {"name", "area", "address", "phoneNumber"}; // the fields to bind do in your JavaBean
            strat.setColumnMapping(columns);
            CsvToBean csv = new CsvToBean();
            data = csv.parse(strat, reader);
            for(KTVData ktvData : data) {
                String districtId = provinceId + "-" + cityId + "-" + ktvData.getArea();
                if(ktvData.getName() != null || !ktvData.getName().equals(""))
                    ktvList.add(new KTV(districtId, ktvData.getName(), ktvData.getAddress(), ktvData.getPhoneNumber()));
            }

            dataList.addAll(ktvList);
        }

        return dataList;
    }

    public static void main(String[] args) throws IOException {
        String path = "C:\\Users\\Administrator\\Desktop\\1";
        File dir=new File(path);
        File[] files = dir.listFiles();
        System.out.println(dir.getName());
//        String s[] = path.split("\\");
//        System.out.println(s.length);

//        for(File file : files) {
//            String cityId = file.getName().replace(".csv", "");
//            System.out.println(cityId);
//
//        CSVReader reader = new CSVReader(new InputStreamReader(FileUtils.openInputStream(file)));
//        ColumnPositionMappingStrategy strat = new ColumnPositionMappingStrategy();
//        strat.setType(KTVData.class);
//        String[] columns = new String[] {"name", "area", "address", "phoneNumber"}; // the fields to bind do in your JavaBean
//        strat.setColumnMapping(columns);
//        CsvToBean csv = new CsvToBean();
//        List list = csv.parse(strat, reader);

//        };
    }
}
