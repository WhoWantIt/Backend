package gdg.whowantit.converter;

import gdg.whowantit.dto.PostDto.PostRequestDto;
import gdg.whowantit.dto.PostDto.PostResponseDto;
import gdg.whowantit.dto.beneficiaryDto.BeneficiaryResponseDto;
import gdg.whowantit.dto.volunteerDto.VolunteerResponseDto;
import gdg.whowantit.entity.Post;
import gdg.whowantit.entity.Volunteer;
import gdg.whowantit.util.StringListUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;

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
                .attachedImages(StringListUtil.stringToList(post.getAttachedImages()))
                .build();
    }

    public static Post toPost (PostRequestDto.BeneficiaryPostRequestDto postRequestDto){
        Post post = new Post();
        BeanUtils.copyProperties(postRequestDto, post);
        return post;
    }

    public static Page<PostResponseDto.BeneficiaryPostResponseDto> convertToPostResponseDtoPage(Page<Post> postPage) {
        return postPage.map(PostConverter::toBeneficiaryPostResponseDto);
    }
}
