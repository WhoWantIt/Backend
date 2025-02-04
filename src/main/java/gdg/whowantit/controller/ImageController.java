package gdg.whowantit.controller;

import gdg.whowantit.apiPayload.ApiResponse;
import gdg.whowantit.service.ImageService.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
@Tag(name = "${swagger.tag.test}")
public class ImageController {
    private final ImageService imageService;
    @PostMapping(value="/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary="이미지 업로드 API",
            description = "이미지 업로드 API")
    public ApiResponse<String> uploadImage(@RequestParam("imageFile") MultipartFile image){
        String imageUrl = imageService.uploadImage("image", image);
        return ApiResponse.onSuccess(imageUrl);
    }
}
