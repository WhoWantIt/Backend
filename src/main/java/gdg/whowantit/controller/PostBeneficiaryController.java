package gdg.whowantit.controller;

import gdg.whowantit.apiPayload.ApiResponse;
import gdg.whowantit.dto.PostDto.PostRequestDto;
import gdg.whowantit.dto.PostDto.PostResponseDto;
import gdg.whowantit.dto.volunteerDto.VolunteerRequestDto;
import gdg.whowantit.entity.Post;
import gdg.whowantit.service.PostService.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
@Tag(name = "${swagger.tag.post-beneficiary}")
public class PostBeneficiaryController {
    private final PostService postService;

    @PostMapping("")
    @Operation(summary = "복지시설 게시글 생성", description = "복지시설에서 게시글 생성입니다.")
    public ResponseEntity<ApiResponse<PostResponseDto.BeneficiaryPostResponseDto>> createPost(
            @RequestPart("volunteerRequestDto") PostRequestDto.BeneficiaryPostRequestDto postRequestDto,
            @RequestPart(value = "images", required = false) List<MultipartFile> images,
            @RequestPart MultipartFile excelFile)
    {

        PostResponseDto.BeneficiaryPostResponseDto postResponseDto =
                postService.createPost(postRequestDto, images, excelFile);
        return ResponseEntity.ok(ApiResponse.onSuccess(postResponseDto));
    }


    @PutMapping("/{postId}")
    @Operation(summary = "복지시설 게시글 수정", description = "복지시설에서 게시글 수정입니다.")
    public ResponseEntity<ApiResponse<PostResponseDto.BeneficiaryPostResponseDto>> updatePost(
            @RequestPart("volunteerRequestDto") PostRequestDto.BeneficiaryPostRequestDto postRequestDto,
            @RequestPart(value = "images", required = false) List<MultipartFile> images,
            @RequestPart MultipartFile excelFile,
            @PathVariable Long postId)
    {

        PostResponseDto.BeneficiaryPostResponseDto postResponseDto =
                postService.updatePost(postRequestDto, images, excelFile, postId);

        return ResponseEntity.ok(ApiResponse.onSuccess(postResponseDto));
    }

}
