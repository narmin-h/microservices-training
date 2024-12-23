package az.kapitalbank.observability.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@Slf4j
@RestController
public class TestController {

    @GetMapping
    public String testTracing() {
        log.info("Testing distributed tracing");
        return "Test!";
    }
}
