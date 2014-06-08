package com.kzone.rest;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;
import com.kzone.bean.TestBean;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by jeffy on 2014/6/7 0007.
 */
@Component
@Path("/import")
public class ImportDataRest {
    @POST
    @Path("/ktv/data")
//    @Consumes({ MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_JSON })
    @Produces(MediaType.APPLICATION_JSON)
    public Response importKtvData(@Context HttpServletRequest request) {
        List csv = null;
        try {
            csv = readCSV("a");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Response.ok(csv, MediaType.APPLICATION_JSON).build();
    }

    public List readCSV(String f) throws IOException {
        CSVReader reader = new CSVReader(new InputStreamReader(FileUtils.openInputStream(new File("C:\\Users\\jeffy\\Desktop\\test.csv"))));
        ColumnPositionMappingStrategy strat = new ColumnPositionMappingStrategy();
        strat.setType(TestBean.class);
        String[] columns = new String[] {"name", "address"}; // the fields to bind do in your JavaBean
        strat.setColumnMapping(columns);
        CsvToBean csv = new CsvToBean();
        List list = csv.parse(strat, reader);

        return list;
    }
}
