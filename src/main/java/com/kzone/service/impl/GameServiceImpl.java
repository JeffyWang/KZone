package com.kzone.service.impl;

import com.kzone.bean.Game;
import com.kzone.service.GameService;
import org.springframework.stereotype.Service;

/**
 * Created by Jeffy on 2014/6/18 0018.
 */
@Service
public class GameServiceImpl extends CommonServiceImpl<Game> implements GameService {
    @Override
    public void validateInformation(Game game) {

    }
}
