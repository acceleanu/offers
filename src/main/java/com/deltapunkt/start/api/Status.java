package com.deltapunkt.start.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class Status {

    @RequestMapping(
            path = "/status",
            method = GET
    )
    @ResponseBody
    String status() {
        return "ok";
    }
}
