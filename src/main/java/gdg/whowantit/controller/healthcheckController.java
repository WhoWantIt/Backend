package gdg.whowantit.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Tag(name = "${swagger.tag.test}")

public class healthcheckController {

    @GetMapping("/")
    public String home() {
        return "Welcome to the homepage!";
    }
    @GetMapping("/health")
    public String healthCheck(){
        return "I'm healthy!";
    }}