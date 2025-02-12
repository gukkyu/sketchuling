package com.sketchuling.channel;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/channel")
public class ChannelController {

    @GetMapping("")
    public String channel(){
        return "channel/channel";
    }

    @GetMapping("/specific")
    public String specific(){
        return "channel/specific";
    }

    @GetMapping("/chat")
    public String chat(){
        return "channel/chat";
    }
}
