package gdg.whowantit.converter;

import gdg.whowantit.dto.beneficiaryDto.BeneficiaryResponseDto;
import gdg.whowantit.entity.Post;

public class PostConverter {
    public static BeneficiaryResponseDto.postResponse toPostResponse (Post post){

        return BeneficiaryResponseDto.postResponse.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .beneficiaryId(post.getBeneficiary().getBeneficiaryId())
                .beneficiaryName(post.getBeneficiary().getUser().getNickname())
                .isVerified(post.isVerified())
                .build();
    }
}
