package gdg.whowantit.controller;

import gdg.whowantit.apiPayload.ApiResponse;
import gdg.whowantit.dto.PostDto.PostRequestDto;
import gdg.whowantit.dto.PostDto.PostResponseDto;
import gdg.whowantit.dto.volunteerDto.VolunteerRequestDto;
import gdg.whowantit.dto.volunteerDto.VolunteerResponseDto;
import gdg.whowantit.entity.Post;
import gdg.whowantit.service.PostService.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @GetMapping("/{postId}")
    @Operation(summary = "복지시설 게시글 상세 조회", description = "복지시설에서 게시글 상세 조회입니다.")
    public ResponseEntity<ApiResponse<PostResponseDto.BeneficiaryPostResponseDto>> getPostDetail(
            @PathVariable Long postId)
    {

        PostResponseDto.BeneficiaryPostResponseDto postResponseDto =
                postService.getPostDetail(postId);

        return ResponseEntity.ok(ApiResponse.onSuccess(postResponseDto));
    }

    @DeleteMapping("/{postId}")
    @Operation(summary = "복지시설 게시글 삭제", description = "복지시설에서 해당 게시글 삭제 기능입니다.")
    public ResponseEntity<ApiResponse<Void>> deletePost(
            @PathVariable Long postId
    )
    {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("")
    @Operation(summary = "게시글 전체 조회", description = "게시글 전체 조회 기능입니다.")
    public ResponseEntity<ApiResponse<Page<PostResponseDto.BeneficiaryPostResponseDto>>> getAllPosts (
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<PostResponseDto.BeneficiaryPostResponseDto> posts = postService.getAllPosts(pageable);
        return ResponseEntity.ok(ApiResponse.onSuccess(posts));
    }

    @PutMapping("/{postId}/accept")
    @Operation(summary = "관리자 게시글 승인", description = "관리자 게시글 승인 기능입니다.")
    public ResponseEntity<ApiResponse<Void>> acceptPost(@PathVariable Long postId) {
        postService.acceptPost(postId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{postId}/reject")
    @Operation(summary = "관리자 게시글 거부", description = "관리자 게시글 승인 거부 기능입니다.")
    public ResponseEntity<ApiResponse<Void>> rejectPost(@PathVariable Long postId) {
        postService.rejectPost(postId);
        return ResponseEntity.noContent().build();
    }

}
