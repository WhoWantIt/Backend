package gdg.whowantit.service.ImageService;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    String uploadImage (String directory, MultipartFile image);
}
