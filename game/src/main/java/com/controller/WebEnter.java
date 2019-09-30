package com.controller;

import com.dao.PlayerRepository;
import com.dao.UserRepository;
import com.google.common.collect.Lists;
import com.mongoListener.MongoEventListener;
import com.pojo.Player;
import com.service.OnlineService;
import com.template.templates.type.CenterMailType;
import com.util.support.Cat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//import com.config.RedissonConfig;

@RestController("/mail")
@Slf4j
public class WebEnter {
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
	MongoEventListener saveEventListener;
    @Autowired
    private OnlineService onlineService;


    @RequestMapping("/addMail/{type}/{playerIds}/{mailTemplateId}")
    public void test(@PathVariable int type, @PathVariable String playerIds, @PathVariable int mailTemplateId) {
        if (type == CenterMailType.Total) {
            for (Player player : onlineService.getPlayerMap().values()) {
                player.getMailModule().addMail(mailTemplateId);
            }
        } else {
            List<Long> playerLongIds = Lists.newArrayList();

            String[] split = playerIds.split(Cat.comma);
            for (String s : split) {
                playerLongIds.add(Long.parseLong(s));
            }

            for (Player player : onlineService.getPlayerMap().values()) {
                if (playerLongIds.contains(player.getPlayerId())) {
                    player.getMailModule().addMail(mailTemplateId);
                }
            }
        }

    }

}
