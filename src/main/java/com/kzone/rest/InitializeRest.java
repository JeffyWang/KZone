package com.kzone.rest;

import com.kzone.bo.ErrorMessage;
import com.kzone.constants.CommonConstants;
import com.kzone.constants.ErrorCode;
import com.kzone.constants.ParamsConstants;
import com.kzone.service.InitializeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Created by Jeffy on 2014/6/19 0019.
 */
@Component
@Path("/init")
public class InitializeRest {
    Logger log = Logger.getLogger(InitializeRest.class);

    @Autowired
    private InitializeService initializeService;
    @Value("#{kzoneConfig['initialize.name']}")
    private String initializeName;
    @Value("#{kzoneConfig['initialize.password']}")
    private String initializePassword;

    @POST
    @Path("/{name}/{password}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addComment(@PathParam(ParamsConstants.PARAM_NAME) String name, @PathParam(ParamsConstants.PARAM_PASSWORD) String password) {
        if(!name.equals(initializeName) || !password.equals(initializePassword)) {
            return Response.ok(new ErrorMessage(ErrorCode.INITIALIZE_ERR_CODE, ErrorCode.INITIALIZE_ERR_MSG),MediaType.APPLICATION_JSON).status(500).build();
        }

        String sqlPath = Thread.currentThread().getContextClassLoader().getResource("sql").getPath();
        System.out.println(sqlPath);
        File dir=new File(sqlPath);
        File[] files = dir.listFiles();
        for(File file : files) {
            try {
                if (file.isFile() && file.exists()) { //判断文件是否存在
                    InputStreamReader read = new InputStreamReader(new FileInputStream(file), CommonConstants.ENCODE);//考虑到编码格式
                    BufferedReader bufferedReader = new BufferedReader(read);
                    String sql = null;
                    String sqlString = "";
                    while ((sql = bufferedReader.readLine()) != null) {
                        sqlString += sql;
                    }
                    log.debug(sqlString);
                    initializeService.initialize(sqlString);
                    read.close();
                }
            } catch (Exception e) {
                log.warn(e);
                return Response.ok(new ErrorMessage(ErrorCode.INITIALIZE_ERR_CODE, ErrorCode.INITIALIZE_ERR_MSG), MediaType.APPLICATION_JSON).status(500).build();
            }
        }

        return Response.ok().build();
    }
}
