package gdg.whowantit.service.PostService;

import gdg.whowantit.dto.PostDto.ItemDTO;

import java.util.List;

public interface GeminiVisionService {
    boolean verifyItemsWithImage(String imageUrl, List<ItemDTO> donatedItems);



}