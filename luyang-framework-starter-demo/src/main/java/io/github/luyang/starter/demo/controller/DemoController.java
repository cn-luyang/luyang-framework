package io.github.luyang.starter.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yang.lu
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

	@RequestMapping("/hello")
    public String hello() {
		return "hello world";
	}
}
