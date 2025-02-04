package gdg.whowantit.controller;

import gdg.whowantit.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/beneficiaries")
@RequiredArgsConstructor
@Tag(name = "${swagger.tag.my-beneficiary}")
public class BeneficiaryController {


}
