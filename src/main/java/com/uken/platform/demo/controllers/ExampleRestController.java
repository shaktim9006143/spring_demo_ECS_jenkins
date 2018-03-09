package com.uken.platform.demo.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(
  value = "demo",
  description =
      "A sample controller that shows the use of custom metrics and application properties"
)
public class ExampleRestController {

  static final Logger logger = LoggerFactory.getLogger(ExampleRestController.class);

  @Value("${uken.hello_response}")
  private String response;

  @Autowired private CounterService counterService;

  @Autowired private GaugeService gaugeService;

  @RequestMapping(value = "/hello", method = RequestMethod.GET)
  @ApiOperation(value = "hellp", notes = "return a response driven by application properties")
  public String hello() {
    logger.info("/hello called");

    gaugeService.submit("demo.hello.gauge", new Random().nextDouble());
    counterService.increment("demo.hello.counter");
    return "Hi " + response + "!";
  }
}
