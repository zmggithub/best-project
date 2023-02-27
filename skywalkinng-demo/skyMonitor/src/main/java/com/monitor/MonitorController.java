package com.monitor;

import org.slf4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MonitorController {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(MonitorController.class);

    @GetMapping("/monitor")
    public String monitor() throws Exception{
        log.info("MonitorController - monitor- 进入了 monitor 方法");

        return "MonitorController`s logger !!!!!";
    }
}
