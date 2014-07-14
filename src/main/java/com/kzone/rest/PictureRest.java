package com.kzone.rest;

import com.kzone.bean.Game;
import com.kzone.bean.Information;
import com.kzone.bean.KTV;
import com.kzone.bean.User;
import com.kzone.bo.ErrorMessage;
import com.kzone.constants.CommonConstants;
import com.kzone.constants.ErrorCode;
import com.kzone.constants.HTTPConstants;
import com.kzone.constants.ParamsConstants;
import com.kzone.service.*;
import com.kzone.util.Pinyin4jUtil;
import com.upyun.service.FileBucketService;
import com.upyun.service.PicBucketService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Jeffy on 2014/5/19 0019.
 */
@Component
@Path("/picture")
public class PictureRest {
    Logger log = Logger.getLogger(PictureRest.class);
    @Autowired
    private KTVService ktvService;
    @Autowired
    private InformationService informationService;
    @Autowired
    private GameService gameService;
    @Autowired
    private FileService fileService;
    @Autowired
    private PicBucketService picBucketService;
    @Autowired
    private FileBucketService fileBucketService;
    @Autowired
    private UserService userService;

    @POST
    @Path("/ktv/{areaId}/{ktvId}")
    @Consumes({ MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_JSON })
    @Produces(MediaType.APPLICATION_JSON)
    public Response addKTVPicture(@Context HttpServletRequest request,@PathParam(ParamsConstants.DISTRICT_AREA_ID) int areaId, @PathParam(ParamsConstants.PARAM_KTV_ID) int ktvId) {
        String picture = "";
        KTV ktv = null;
        String pictureName = String.valueOf(System.currentTimeMillis());
        String uploadPicPath = HTTPConstants.HTTP_PATH_SEPARATOR + CommonConstants.PICTURE_TYPE_KTV + HTTPConstants.HTTP_PATH_SEPARATOR + areaId + HTTPConstants.HTTP_PATH_SEPARATOR + ktvId + HTTPConstants.HTTP_PATH_SEPARATOR + System.currentTimeMillis();
        System.out.println(uploadPicPath);
        MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);
        MultipartFile file = multipartRequest.getFile("Filedata");

        try {
            ktv =  ktvService.get(ktvId);
            pictureName = String.valueOf(ktvId);
            InputStream input = file.getInputStream();
            String tmpPicturePath = fileService.addFile(input, pictureName, CommonConstants.CONTENT_TYPE);
            log.debug("tmp picture path : " + tmpPicturePath);

            String pictureUrl = picBucketService.addBigPicture(tmpPicturePath, uploadPicPath);
            log.debug("The KTV picture URL is {" + pictureUrl + "}");
//            picBucketService.addMiddlePicture(tmpPicturePath, uploadPicPath);
//            picBucketService.addBigPicture(tmpPicturePath, uploadPicPath);

            if(ktv.getPictures() != null) {
                picture = ktv.getPictures() + "," + pictureUrl;
            } else {
                picture = pictureUrl;
            }

            ktv.setPictures(picture);
            ktvService.update(ktv);

            input.close();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.ok(new ErrorMessage(ErrorCode.ADD_PICTURE_ERR_CODE, ErrorCode.ADD_PICTURE_ERR_MSG), MediaType.APPLICATION_JSON).build();
        } finally {
            fileService.deleteFile(pictureName,  CommonConstants.CONTENT_TYPE);
        }

        return Response.ok(ktv, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/information/{informationId}")
    @Consumes({ MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_JSON })
    @Produces(MediaType.APPLICATION_JSON)
    public Response addInformationPicture(@Context HttpServletRequest request, @PathParam(ParamsConstants.PARAM_INFORMATION_ID) int informationId) {
        String picture = "";
        Information information = null;
        String pictureName =  String.valueOf(System.currentTimeMillis());
        String uploadPicPath = HTTPConstants.HTTP_PATH_SEPARATOR + CommonConstants.PICTURE_TYPE_INFORMATION + HTTPConstants.HTTP_PATH_SEPARATOR + informationId + HTTPConstants.HTTP_PATH_SEPARATOR + System.currentTimeMillis();
        Map<String, String> pictureResult = new HashMap<String, String>();

        MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);
        MultipartFile file = multipartRequest.getFile("Filedata");

        try {
            information = informationService.get(informationId);
            pictureName = String.valueOf(informationId);
            InputStream input = file.getInputStream();
            String tmpPicturePath = fileService.addFile(input, pictureName, CommonConstants.CONTENT_TYPE);
            log.debug("tmp picture path : " + tmpPicturePath);

            String pictureUrl = picBucketService.addBigPicture(tmpPicturePath, uploadPicPath);
            log.debug("The information picture URL is {" + pictureUrl + "}");
//            picBucketService.addMiddlePicture(tmpPicturePath, uploadPicPath);
//            picBucketService.addBigPicture(tmpPicturePath, uploadPicPath);
            pictureResult.put(CommonConstants.PICTURE_URL, pictureUrl);

            picture = pictureUrl;
            information.setPicture(picture);
            informationService.update(information);

            input.close();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.ok(new ErrorMessage(ErrorCode.ADD_PICTURE_ERR_CODE, ErrorCode.ADD_PICTURE_ERR_MSG), MediaType.APPLICATION_JSON).build();
        } finally {
            fileService.deleteFile(pictureName,  CommonConstants.CONTENT_TYPE);
        }

        return Response.ok(pictureResult, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/game/{gameId}")
    @Consumes({ MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_JSON })
    @Produces(MediaType.APPLICATION_JSON)
    public Response addGamePicture(@Context HttpServletRequest request, @PathParam(ParamsConstants.PARAM_GAME_ID) int gameId) {
        String picture = "";
        Game game = null;
        String pictureName =  String.valueOf(System.currentTimeMillis());
        String uploadPicPath = HTTPConstants.HTTP_PATH_SEPARATOR + CommonConstants.PICTURE_TYPE_GAME + HTTPConstants.HTTP_PATH_SEPARATOR + gameId + HTTPConstants.HTTP_PATH_SEPARATOR + System.currentTimeMillis();
        Map<String, String> pictureResult = new HashMap<String, String>();

        MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);
        MultipartFile file = multipartRequest.getFile("Filedata");

        try {
            game = gameService.get(gameId);
            InputStream input = file.getInputStream();
            String tmpPicturePath = fileService.addFile(input, pictureName, CommonConstants.CONTENT_TYPE);
            log.debug("tmp picture path : " + tmpPicturePath);

            String pictureUrl = picBucketService.addBigPicture(tmpPicturePath, uploadPicPath);
//            String pictureUrl = picBucketService.addSamplePicture(tmpPicturePath, uploadPicPath);
            log.debug("The game picture URL is {" + pictureUrl + "}");
//            picBucketService.addMiddlePicture(tmpPicturePath, uploadPicPath);
//            picBucketService.addBigPicture(tmpPicturePath, uploadPicPath);
            pictureResult.put(CommonConstants.PICTURE_URL, pictureUrl);

            System.out.println(pictureUrl);
            picture = pictureUrl;
            game.setPicture(picture);
            gameService.update(game);

            input.close();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.ok(new ErrorMessage(ErrorCode.ADD_PICTURE_ERR_CODE, ErrorCode.ADD_PICTURE_ERR_MSG), MediaType.APPLICATION_JSON).build();
        } finally {
            fileService.deleteFile(pictureName, CommonConstants.CONTENT_TYPE);
        }

        return Response.ok(pictureResult, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/user/{userId}")
    @Consumes({ MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_JSON })
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUserPicture(@Context HttpServletRequest request, @PathParam(ParamsConstants.PARAM_USER_ID) int userId) {
        String picture = "";
        User user = null;
        String pictureName =  String.valueOf(System.currentTimeMillis());
        String uploadPicPath = HTTPConstants.HTTP_PATH_SEPARATOR + CommonConstants.PICTURE_TYPE_GAME + HTTPConstants.HTTP_PATH_SEPARATOR + userId + HTTPConstants.HTTP_PATH_SEPARATOR + System.currentTimeMillis();
        Map<String, String> pictureResult = new HashMap<String, String>();

        MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);
        MultipartFile file = multipartRequest.getFile("Filedata");

        try {
            user = userService.get(userId);
            InputStream input = file.getInputStream();
            String tmpPicturePath = fileService.addFile(input, pictureName, CommonConstants.CONTENT_TYPE);
            log.debug("tmp picture path : " + tmpPicturePath);

            String pictureUrl = picBucketService.addMiddlePicture(tmpPicturePath, uploadPicPath);
            log.debug("The game picture URL is {" + pictureUrl + "}");
            pictureResult.put(CommonConstants.PICTURE_URL, pictureUrl);

            picture = pictureUrl;
            user.setPicture(picture);
            userService.update(user);

            input.close();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.ok(new ErrorMessage(ErrorCode.ADD_PICTURE_ERR_CODE, ErrorCode.ADD_PICTURE_ERR_MSG), MediaType.APPLICATION_JSON).build();
        } finally {
            fileService.deleteFile(pictureName, CommonConstants.CONTENT_TYPE);
        }

        return Response.ok(pictureResult, MediaType.APPLICATION_JSON).build();
    }

    @DELETE
    @Path("/{type}/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePictures(@PathParam(ParamsConstants.PARAM_TYPE) String type, @PathParam(ParamsConstants.PARAM_ID) int id) {
        try {
            String dirPath = HTTPConstants.HTTP_PATH_SEPARATOR + type + HTTPConstants.HTTP_PATH_SEPARATOR + id;
            log.debug("Delete a " + type + " dir that the path is {" + dirPath + "}");
            List<String> fileNameList = fileBucketService.readDir(dirPath);
            if(fileNameList.size() == 0) {
                fileBucketService.deleteDir(dirPath);
            } else {
                for(String fileName : fileNameList) {
                    String filePath = dirPath + HTTPConstants.HTTP_PATH_SEPARATOR + fileName;
                    log.debug("Delete a " + type + " file that the path is {" + filePath + "}");
                    fileBucketService.deleteFile(filePath);
                }
                fileBucketService.deleteDir(dirPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.ok(new ErrorMessage(ErrorCode.DELETE_PICTURE_ERR_CODE, ErrorCode.DELETE_PICTURE_ERR_MSG), MediaType.APPLICATION_JSON).build();
        }

        return Response.ok(new com.kzone.bo.Response(), MediaType.APPLICATION_JSON).build();
    }

    @DELETE
    @Path("/ktv/{areaId}/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteKTVPictures (@PathParam(ParamsConstants.DISTRICT_AREA_ID) String areaId, @PathParam(ParamsConstants.PARAM_ID) int id) {
        try {
            String dirPath = HTTPConstants.HTTP_PATH_SEPARATOR + CommonConstants.PICTURE_TYPE_KTV + HTTPConstants.HTTP_PATH_SEPARATOR + areaId + HTTPConstants.HTTP_PATH_SEPARATOR + id;
            List<String> fileNameList = fileBucketService.readDir(dirPath);
            log.debug("Delete a ktv dir that the path is {" + dirPath + "}");
            if(fileNameList.size() == 0) {
                fileBucketService.deleteDir(dirPath);
            } else {
                for(String fileName : fileNameList) {
                    String filePath = dirPath + HTTPConstants.HTTP_PATH_SEPARATOR + fileName;
                    log.debug("Delete a ktv file that the path is {" + filePath + "}");
                    fileBucketService.deleteFile(filePath);
                }
                fileBucketService.deleteDir(dirPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.ok(new ErrorMessage(ErrorCode.DELETE_PICTURE_ERR_CODE, ErrorCode.DELETE_PICTURE_ERR_MSG), MediaType.APPLICATION_JSON).build();
        }

        return Response.ok(new com.kzone.bo.Response(), MediaType.APPLICATION_JSON).build();
    }
}
