package gdg.whowantit.converter;

import gdg.whowantit.dto.PostDto.PostRequestDto;
import gdg.whowantit.dto.PostDto.PostResponseDto;
import gdg.whowantit.dto.beneficiaryDto.BeneficiaryResponseDto;
import gdg.whowantit.entity.Post;
import gdg.whowantit.util.StringListUtil;
import org.springframework.beans.BeanUtils;

public class PostConverter {
    public static BeneficiaryResponseDto.postResponse toPostResponse (Post post){

        return BeneficiaryResponseDto.postResponse.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .beneficiaryId(post.getBeneficiary().getBeneficiaryId())
                .beneficiaryName(post.getBeneficiary().getUser().getName())
                .beneficiaryNickname(post.getBeneficiary().getUser().getNickname())
                .isVerified(post.isVerified())
                .build();
    }

    public static PostResponseDto.BeneficiaryPostResponseDto toBeneficiaryPostResponseDto (Post post){

        return PostResponseDto.BeneficiaryPostResponseDto.builder()
                .postId(post.getPostId())
                .beneficiaryId(post.getBeneficiary().getBeneficiaryId())
                .title(post.getTitle())
                .content(post.getContent())
                .attachedExcelFile(post.getAttachedExcelFile())
                .attachedImage(StringListUtil.stringToList(post.getAttachedImage()))
                .build();
    }

    public static Post toPost (PostRequestDto.BeneficiaryPostRequestDto postRequestDto){
        Post post = new Post();
        BeanUtils.copyProperties(postRequestDto, post);
        return post;
    }
}
