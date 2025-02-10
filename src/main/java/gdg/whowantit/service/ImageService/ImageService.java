package gdg.whowantit.service.ImageService;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    String uploadImage(String directory, MultipartFile image);

    String uploadImageForSignUp(String directory, MultipartFile image);

    void deleteImage(String directory, String imageName);

    public List<String> uploadMultipleImages(String directory, List<MultipartFile> images);


    }
