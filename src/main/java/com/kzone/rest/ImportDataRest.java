package com.kzone.rest;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;
import com.kzone.bean.DpKTV;
import com.kzone.bean.KTV;
import com.kzone.bo.KTVData;
import com.kzone.constants.CommonConstants;
import com.kzone.dao.DpKTVDao;
import com.kzone.service.DistrictService;
import com.kzone.service.DpKTVService;
import com.kzone.service.KTVService;
import com.kzone.util.PoiAnalysisUtil;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jeffy on 2014/6/7 0007.
 */
@Component
@Path("/import")
public class ImportDataRest {
    Logger log = Logger.getLogger(ImportDataRest.class);
    @Autowired
    private KTVService ktvService;
    @Autowired
    private DpKTVService dpKTVService;
    @Autowired
    private DistrictService districtService;

    @POST
    @Path("/ktv/data/{folder}")
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

    @POST
    @Path("/ktv/data")
    @Produces(MediaType.APPLICATION_JSON)
    public Response importData() throws Exception {
        List<KTV> ktvList = new ArrayList<KTV>();
        List<DpKTV> dpKTVList = dpKTVService.getList();


        for(DpKTV dpKTV : dpKTVList) {
            Map<String, Double> coordinateFrame = null;
            String districtId = "";
            try {
                coordinateFrame = PoiAnalysisUtil.analysis(dpKTV.getDatapoi());
                String areaId = districtService.getAreaId(dpKTV.getRegion()).getAreaId();
                String cityId = districtService.getAreaId(dpKTV.getRegion()).getReference();
                String provinceId = districtService.getCity(cityId).getReference();
                districtId = provinceId + "-" + cityId + "-" + areaId;
                KTV ktv = new KTV();
                ktv.setName(dpKTV.getShopname());
                ktv.setAddress(dpKTV.getShopadd());
                ktv.setPhoneNumber(dpKTV.getBookingtel());
                ktv.setPrice(dpKTV.getPrice());
                ktv.setBusinessId(dpKTV.getBusinessid());
                ktv.setBusinessArea(dpKTV.getBusinessarea());
                ktv.setLatitude(coordinateFrame.get("lat").toString());
                ktv.setLongitude(coordinateFrame.get("lng").toString());
                ktv.setDistrictId(districtId);
                ktvService.add(ktv);
                ktvList.add(ktv);
            } catch (Exception e) {

            }
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

}
