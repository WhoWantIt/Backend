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

    @Override
    @Transactional
    public PostResponseDto.BeneficiaryPostResponseDto updatePost
            (PostRequestDto.BeneficiaryPostRequestDto postRequestDto, List<MultipartFile> images, MultipartFile excelFile, Long postId){

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new TempHandler(ErrorStatus.POST_NOT_FOUND)
        );

        post.setTitle(postRequestDto.getTitle());
        post.setContent(postRequestDto.getContent());

        // 기존에 올라온 이미지 삭제
        String attachedImages = post.getAttachedImages();
        String attachedExcelFile = post.getAttachedExcelFile();

        if (!attachedImages.isEmpty()){
            List<String> attachImagesUrls = StringListUtil.stringToList(attachedImages);
            for (String attachImagesUrl : attachImagesUrls) {
                imageService.deleteImage("posts",attachImagesUrl);
            }
        }
        if (!excelFile.isEmpty()){
            imageService.deleteImage("posts",attachedExcelFile);
        }

        // 새로운 이미지 업로드
        if (!images.isEmpty()){
            List<String> newlyAttachedImages = imageService.uploadMultipleImages("posts", images);
            post.setAttachedImages(StringListUtil.listToString(newlyAttachedImages));
        }

        if (!excelFile.isEmpty()){
            String newlyAttachedExcelFile = imageService.uploadImage("posts", excelFile);
            post.setAttachedExcelFile(newlyAttachedExcelFile);
        }

        return PostConverter.toBeneficiaryPostResponseDto(post);
    }

    @Override
    public PostResponseDto.BeneficiaryPostResponseDto getPostDetail(Long postId){
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new TempHandler(ErrorStatus.POST_NOT_FOUND)
        );
        return PostConverter.toBeneficiaryPostResponseDto(post);
    }


}
