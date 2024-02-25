package com.coderscampus.benwoodardassignment14.benwoodardassignment14.web;

import com.coderscampus.benwoodardassignment14.benwoodardassignment14.domain.Channel;
import com.coderscampus.benwoodardassignment14.benwoodardassignment14.domain.User;
import com.coderscampus.benwoodardassignment14.benwoodardassignment14.service.ChannelService;
import com.coderscampus.benwoodardassignment14.benwoodardassignment14.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ChannelController {

    private final ChannelService channelService;
    private final UserService userService;

    @Autowired
    public ChannelController(ChannelService channelService, UserService userService) {
        this.channelService = channelService;
        this.userService = userService;
    }


    @GetMapping("/welcome")
    public String getWelcomePage(ModelMap model, @ModelAttribute User user) {
        if (user.getName() != null) {
            userService.checkForUserGeneralChannel(user);
            model.put("channels", user.getChannels());
            model.put("user", user);
        }
        return "welcome";
    }

    @GetMapping("channels/{channelId}")
    public String getChannel(@PathVariable Long channelId, ModelMap model) {
        if (channelService.findById(channelId) == null) {
            return "redirect:/welcome";
        } else {
            Channel channel = channelService.findById(channelId);
            model.put("channel", channel);
            return "messages";
        }
    }
}
