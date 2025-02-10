package gdg.whowantit.service.PostService;


import gdg.whowantit.dto.PostDto.PostRequestDto;
import gdg.whowantit.dto.PostDto.PostResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

    PostResponseDto.BeneficiaryPostResponseDto createPost
            (PostRequestDto.BeneficiaryPostRequestDto postRequestDto, List<MultipartFile> images, MultipartFile excelFile);

    PostResponseDto.BeneficiaryPostResponseDto updatePost
            (PostRequestDto.BeneficiaryPostRequestDto postRequestDto, List<MultipartFile> images, MultipartFile excelFile, Long postId);

    PostResponseDto.BeneficiaryPostResponseDto getPostDetail(Long postId);
    void deletePost(Long postId);
}
