package com.kzone.rest;

import com.kzone.bean.Game;
import com.kzone.bean.Information;
import com.kzone.bo.ErrorMessage;
import com.kzone.constants.CommonConstants;
import com.kzone.constants.ErrorCode;
import com.kzone.constants.ParamsConstants;
import com.kzone.service.GameService;
import com.kzone.util.EncodingUtil;
import com.kzone.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jeffy on 2014/6/18 0018.
 */
@Component
@Path("/game")
public class GameRest {
    Logger log = Logger.getLogger(InformationRest.class);
    @Autowired
    private GameService gameService;

    @GET
    @Path("/info/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGame(@PathParam(ParamsConstants.PARAM_ID) int id) {
        Game game = null;

        try {
            game = gameService.get(id);
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.GET_GAME_ERR_CODE, ErrorCode.GET_GAME_ERR_MSG),MediaType.APPLICATION_JSON).status(500).build();
        }

        log.debug("Get a " + game.toString());
        return Response.ok(game, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/info")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGamesPage(@QueryParam(ParamsConstants.PAGE_PARAMS_OFFSET) int offset, @QueryParam(ParamsConstants.PAGE_PARAMS_LENGTH) int length,
                                        @QueryParam(ParamsConstants.PAGE_PARAMS_ORDER_DESC) String orderDesc,@QueryParam(ParamsConstants.PARAM_GAME_NAME) String name) {
        List<Game> gamePageList = null;

        Map<String, String> likeCondition = new HashMap<String, String>();
        Map<String, String> equalCondition = new HashMap<String, String>();

        if (name != null
                && !CommonConstants.NULL_STRING.equals(name)
                && !CommonConstants.NULL.equals(name))
            likeCondition.put(ParamsConstants.PARAM_GAME_NAME, name);

        try {
            gamePageList = gameService.getListForPage(Game.class, offset, length,orderDesc, equalCondition, likeCondition);
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.GET_GAME_LIST_ERR_CODE, ErrorCode.GET_GAME_LIST_ERR_MSG),MediaType.APPLICATION_JSON).status(500).build();
        }

        log.debug("Get games pages success.");
        return Response.ok(gamePageList, MediaType.APPLICATION_JSON).build();
    }

    @PUT
    @Path("/info")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateGame(@RequestBody String body) {
        Game game = null;
        String encode = EncodingUtil.getEncoding(body);

        try {
            body = new String(body.getBytes(encode), CommonConstants.ENCODE);
            game = StringUtil.jsonStringToObject(body, Game.class);
            String gameDetails = StringUtil.stringToHtml(game.getName(), game.getGame());
            game.setGame(gameDetails);
            gameService.update(game);
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.ADD_GAME_ERR_CODE, ErrorCode.ADD_GAME_ERR_MSG),MediaType.APPLICATION_JSON).status(500).build();
        }

        log.debug("Update a " + game.toString());
        return Response.ok(game, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/info")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addGame(@RequestBody String body) {
        Game game = new Game("game"," ");

        try {
            gameService.add(game);
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.ADD_GAME_ERR_CODE, ErrorCode.ADD_GAME_ERR_MSG),MediaType.APPLICATION_JSON).status(500).build();
        }

        log.debug("Add a " + game.toString());
        return Response.ok(game, MediaType.APPLICATION_JSON).build();
    }

    @DELETE
    @Path("/info/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteGame(@PathParam("id") int id) {
        Game game = null;

        try {
            game = gameService.get(id);
            gameService.delete(game);
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.DELETE_GAME_ERR_CODE, ErrorCode.DELETE_GAME_ERR_MSG),MediaType.APPLICATION_JSON).status(500).build();
        }

        log.debug("Delete a " + game.toString());
        return Response.ok(game, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGameCount( @QueryParam(ParamsConstants.PARAM_GAME_NAME) String name) {
        Map<String, Integer> countMap = new HashMap<String, Integer>();
        int gameCount = 0;

        Map<String, String> likeCondition = new HashMap<String, String>();
        Map<String, String> equalCondition = new HashMap<String, String>();

        if (name != null
                && !CommonConstants.NULL_STRING.equals(name)
                && !CommonConstants.NULL.equals(name))
            likeCondition.put(ParamsConstants.PARAM_GAME_NAME, name);

        try {
            gameCount = (int) gameService.getListCount(equalCondition, likeCondition);
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.COUNT_GAME_ERR_CODE, ErrorCode.COUNT_GAME_ERR_MSG),MediaType.APPLICATION_JSON).status(500).build();
        }

        countMap.put(ParamsConstants.PAGE_DATA_COUNT, gameCount);
        log.debug("Get the games' count is {" + gameCount + "}");
        return Response.ok(countMap, MediaType.APPLICATION_JSON).build();
    }
}
