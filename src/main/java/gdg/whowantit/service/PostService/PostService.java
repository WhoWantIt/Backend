package gdg.whowantit.service.PostService;


import gdg.whowantit.dto.PostDto.PostRequestDto;
import gdg.whowantit.dto.PostDto.PostResponseDto;
import gdg.whowantit.entity.ApprovalStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

    PostResponseDto.BeneficiaryPostResponseDto createPost
            (PostRequestDto.BeneficiaryPostRequestDto postRequestDto, List<MultipartFile> images, MultipartFile excelFile);

    PostResponseDto.BeneficiaryPostResponseDto updatePost
            (PostRequestDto.BeneficiaryPostRequestDto postRequestDto, List<MultipartFile> images, MultipartFile excelFile, Long postId);

    PostResponseDto.BeneficiaryPostResponseDto getPostDetail(Long postId);
    void deletePost(Long postId);
    void acceptPost(Long postId);
    void rejectPost(Long postId);
    Page<PostResponseDto.BeneficiaryPostResponseDto> getAllPosts(Pageable pageable);
    Page<PostResponseDto.BeneficiaryPostResponseDto> getPostsByApprovalStatus(ApprovalStatus approvalStatus, Pageable pageable);
    Page<PostResponseDto.BeneficiaryPostResponseDto> getMyPosts(Pageable pageable);
    Page<PostResponseDto.BeneficiaryPostResponseDto> getPostsByYearAndMonth(Long year, Long month, Pageable pageable);

}
