package gdg.whowantit.service.PostService;

import gdg.whowantit.apiPayload.code.status.ErrorStatus;
import gdg.whowantit.apiPayload.exception.handler.TempHandler;
import gdg.whowantit.converter.PostConverter;
import gdg.whowantit.dto.PostDto.PostRequestDto;
import gdg.whowantit.dto.PostDto.PostResponseDto;
import gdg.whowantit.entity.Beneficiary;
import gdg.whowantit.entity.Post;
import gdg.whowantit.entity.Role;
import gdg.whowantit.entity.User;
import gdg.whowantit.repository.BeneficiaryRepository;
import gdg.whowantit.repository.PostRepository;
import gdg.whowantit.repository.UserRepository;
import gdg.whowantit.service.ImageService.ImageService;
import gdg.whowantit.util.SecurityUtil;
import gdg.whowantit.util.StringListUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final BeneficiaryRepository beneficiaryRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;
    private final PostRepository postRepository;

    @Transactional
    @Override
    public PostResponseDto.BeneficiaryPostResponseDto createPost
            (PostRequestDto.BeneficiaryPostRequestDto postRequestDto, List<MultipartFile> images, MultipartFile excelFile){
        String email = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(email).orElseThrow(
                ()-> new TempHandler(ErrorStatus.USER_NOT_FOUND)
        );

        if (user.getRole() != Role.BENEFICIARY){
            throw new TempHandler(ErrorStatus.FORBIDDEN_POST_ACCESS);
        }

        Post post = PostConverter.toPost(postRequestDto);
        post.setBeneficiary(user.getBeneficiary());
        post.setVerified(false);

        if (!images.isEmpty()){
            List<String> attachedImages = imageService.uploadMultipleImages("posts", images);
            post.setAttachedImages(StringListUtil.listToString(attachedImages));
        }

        if (!excelFile.isEmpty()){
            String attachedExcelFile = imageService.uploadImage("posts", excelFile);
            post.setAttachedExcelFile(attachedExcelFile);
        }
        Post savedPost = postRepository.save(post);

        return PostConverter.toBeneficiaryPostResponseDto(savedPost);
    }

}
