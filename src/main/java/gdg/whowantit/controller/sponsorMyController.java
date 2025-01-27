package gdg.whowantit.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sponsors")
@RequiredArgsConstructor
@Tag(name = "${swagger.tag.my-sponsor}")
public class sponsorMyController {

}
