package com.kzone.service;

import com.kzone.bean.Game;

/**
 * Created by Jeffy on 2014/6/18 0018.
 */
public interface GameService extends CommonService<Game> {
    public void validateInformation(Game game);
}
