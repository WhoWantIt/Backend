package gdg.whowantit.service.ImageService;

import gdg.whowantit.apiPayload.code.status.ErrorStatus;
import gdg.whowantit.apiPayload.exception.handler.TempHandler;
import gdg.whowantit.aws.s3.AmazonS3Manager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService{
    private final AmazonS3Manager s3Manager;

    @Override
    @Transactional
    public String uploadImage (String directory, MultipartFile image){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            throw new TempHandler(ErrorStatus.TOKEN_EXPIRED);
        }

        return s3Manager.uploadFile(directory, image);

    }

    // ✅ 여러 개의 이미지 업로드 기능 추가
    public List<String> uploadMultipleImages(String directory, List<MultipartFile> images) {
        return s3Manager.uploadMultipleFiles(directory, images);
    }


    public String uploadImageForSignUp (String directory, MultipartFile image){
        return s3Manager.uploadFile(directory, image);
    }

    public void deleteImage(String directory, String imageName){
        s3Manager.deleteFile(directory, imageName);
    }
}
