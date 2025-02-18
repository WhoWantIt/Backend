package gdg.whowantit.service.PostService;

import gdg.whowantit.apiPayload.code.status.ErrorStatus;
import gdg.whowantit.apiPayload.exception.handler.TempHandler;
import gdg.whowantit.converter.PostConverter;
import gdg.whowantit.dto.PostDto.PostRequestDto;
import gdg.whowantit.dto.PostDto.PostResponseDto;
import gdg.whowantit.entity.*;
import gdg.whowantit.repository.BeneficiaryRepository;
import gdg.whowantit.repository.PostRepository;
import gdg.whowantit.repository.UserRepository;
import gdg.whowantit.service.ImageService.ImageService;
import gdg.whowantit.util.SecurityUtil;
import gdg.whowantit.util.StringListUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.YearMonth;
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
            (PostRequestDto.BeneficiaryPostRequestDto postRequestDto, List<MultipartFile> images, MultipartFile excelFile) {
        String email = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new TempHandler(ErrorStatus.USER_NOT_FOUND)
        );

        if (user.getRole() != Role.BENEFICIARY) {
            throw new TempHandler(ErrorStatus.FORBIDDEN_POST_ACCESS);
        }

        Post post = PostConverter.toPost(postRequestDto);
        post.setBeneficiary(user.getBeneficiary());
        post.setApprovalStatus(ApprovalStatus.UNDETERMINED);
        post.setIsVerified(Boolean.FALSE);

        if (!images.isEmpty()) {
            List<String> attachedImages = imageService.uploadMultipleImages("posts", images);
            post.setAttachedImages(StringListUtil.listToString(attachedImages));
        }

        if (!excelFile.isEmpty()) {
            String attachedExcelFile = imageService.uploadImage("posts", excelFile);
            post.setAttachedExcelFile(attachedExcelFile);
        }
        Post savedPost = postRepository.save(post);

        return PostConverter.toBeneficiaryPostResponseDto(savedPost);
    }

    @Override
    @Transactional
    public PostResponseDto.BeneficiaryPostResponseDto updatePost
            (PostRequestDto.BeneficiaryPostRequestDto postRequestDto, List<MultipartFile> images, MultipartFile excelFile, Long postId) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new TempHandler(ErrorStatus.POST_NOT_FOUND)
        );

        post.setTitle(postRequestDto.getTitle());
        post.setContent(postRequestDto.getContent());

        // 기존에 올라온 이미지 삭제
        String attachedImages = post.getAttachedImages();
        String attachedExcelFile = post.getAttachedExcelFile();

        if (!attachedImages.isEmpty()) {
            List<String> attachImagesUrls = StringListUtil.stringToList(attachedImages);
            for (String attachImagesUrl : attachImagesUrls) {
                imageService.deleteImage("posts", attachImagesUrl);
            }
        }
        if (!attachedExcelFile.isEmpty()) {
            imageService.deleteImage("posts", attachedExcelFile);
        }

        // 새로운 이미지 업로드
        if (!images.isEmpty()) {
            List<String> newlyAttachedImages = imageService.uploadMultipleImages("posts", images);
            post.setAttachedImages(StringListUtil.listToString(newlyAttachedImages));
        }

        if (!excelFile.isEmpty()) {
            String newlyAttachedExcelFile = imageService.uploadImage("posts", excelFile);
            post.setAttachedExcelFile(newlyAttachedExcelFile);
        }

        return PostConverter.toBeneficiaryPostResponseDto(post);
    }

    @Override
    public PostResponseDto.BeneficiaryPostResponseDto getPostDetail(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new TempHandler(ErrorStatus.POST_NOT_FOUND)
        );
        return PostConverter.toBeneficiaryPostResponseDto(post);
    }

    @Transactional
    @Override
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new TempHandler(ErrorStatus.POST_NOT_FOUND)
        );
        // 기존에 올라온 이미지 삭제
        String attachedImages = post.getAttachedImages();
        String attachedExcelFile = post.getAttachedExcelFile();

        if (!attachedImages.isEmpty()) {
            List<String> attachImagesUrls = StringListUtil.stringToList(attachedImages);
            for (String attachImagesUrl : attachImagesUrls) {
                imageService.deleteImage("posts", attachImagesUrl);
            }
        }
        if (!attachedExcelFile.isEmpty()) {
            imageService.deleteImage("posts", attachedExcelFile);
        }


        postRepository.delete(post);
    }

    public Page<PostResponseDto.BeneficiaryPostResponseDto> getAllPosts(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);
        return PostConverter.convertToPostResponseDtoPage(posts);

    }

    @Transactional
    @Override
    public void acceptPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new TempHandler(ErrorStatus.POST_NOT_FOUND)
        );
        if (post.getApprovalStatus() == ApprovalStatus.APPROVED) {
            throw new TempHandler(ErrorStatus.POST_ALREADY_APPROVED);
        }

        post.setApprovalStatus(ApprovalStatus.APPROVED);
    }

    @Transactional
    @Override
    public void rejectPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new TempHandler(ErrorStatus.POST_NOT_FOUND)
        );
        if (post.getApprovalStatus() == ApprovalStatus.DISAPPROVED) {
            throw new TempHandler(ErrorStatus.POST_ALREADY_DISAPPROVED);
        }
        post.setApprovalStatus(ApprovalStatus.DISAPPROVED);
    }

    @Transactional
    @Override
    public void verifyApprovePost(Long postId)
    {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new TempHandler(ErrorStatus.POST_NOT_FOUND)
        );
        if (post.getIsVerified() == Boolean.TRUE) {
            throw new TempHandler(ErrorStatus.POST_ALREADY_VERIFIED);
        }
        post.setIsVerified(Boolean.TRUE);
    };

    @Transactional
    @Override
    public void verifyRejectPost(Long postId)
    {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new TempHandler(ErrorStatus.POST_NOT_FOUND)
        );
        if (post.getIsVerified() == Boolean.FALSE) {
            throw new TempHandler(ErrorStatus.POST_ALREADY_VERIFY_REJECTED);
        }
        post.setIsVerified(Boolean.FALSE);
    };


    @Override
    public Page<PostResponseDto.BeneficiaryPostResponseDto> getPostsByApprovalStatus
            (ApprovalStatus approvalStatus, Pageable pageable) {
        Page<Post> posts = postRepository.findByApprovalStatus(approvalStatus, pageable);
        return PostConverter.convertToPostResponseDtoPage(posts);

    }

    @Override
    public Page<PostResponseDto.BeneficiaryPostResponseDto> getMyPosts(Pageable pageable) {
        String email = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new TempHandler(ErrorStatus.USER_NOT_FOUND)
        );
        if (user.getRole() != Role.BENEFICIARY) {
            throw new TempHandler(ErrorStatus.FORBIDDEN_POST_ACCESS);
        }
        Page<Post> posts = postRepository.findByBeneficiary(user.getBeneficiary(), pageable);
        return PostConverter.convertToPostResponseDtoPage(posts);
    }

    @Override
    public Page<PostResponseDto.BeneficiaryPostResponseDto> getPostsByYearAndMonth(Long year, Long month, Pageable pageable)
    {
        YearMonth yearMonth = YearMonth.of(year.intValue(), month.intValue());
        LocalDateTime start = yearMonth.atDay(1).atStartOfDay(); // 해당 월의 1일 00:00:00
        LocalDateTime end = yearMonth.atEndOfMonth().atTime(23, 59, 59); // 해당 월의 마지막 날 23:59:59


        Page<Post> posts = postRepository.findByCreatedAtBetween(start, end, pageable);

        return PostConverter.convertToPostResponseDtoPage(posts);
    }

    @Override
    public Page<PostResponseDto.BeneficiaryPostResponseDto> getPostsByNickname(String nickname, Pageable pageable){

        Page<Post> posts = postRepository.findByBeneficiaryNickname(nickname, pageable);
        return PostConverter.convertToPostResponseDtoPage(posts);

    }

}
