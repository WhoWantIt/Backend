package gdg.whowantit.converter;

import gdg.whowantit.dto.adminDto.AdminResponseDto;
import gdg.whowantit.dto.beneficiaryDto.BeneficiaryResponseDto;
import gdg.whowantit.entity.Beneficiary;

public class BeneficiaryConverter {
    public static BeneficiaryResponseDto.profileResponse toBeneficiaryResponse (Beneficiary beneficiary){
        return BeneficiaryResponseDto.profileResponse.builder()
                .beneficiaryId(beneficiary.getUser().getId())
                .beneficiaryName(beneficiary.getUser().getName())
                .beneficiaryNickname(beneficiary.getUser().getNickname())
                .image(beneficiary.getUser().getImage())
                .email(beneficiary.getUser().getEmail())
                .phoneNumber(beneficiary.getUser().getPhoneNumber())
                .address(beneficiary.getUser().getAddress())
                .info(beneficiary.getInfo())
                .toddler(beneficiary.getToddler())
                .child(beneficiary.getChild())
                .adolescent(beneficiary.getAdolescent())
                .youth(beneficiary.getYouth())
                .build();
    }
    public static AdminResponseDto.beneficiaryResponse toBeneficiaryListResponse (Beneficiary beneficiary){
        return AdminResponseDto.beneficiaryResponse.builder()
                .attachedImage(beneficiary.getUser().getImage())
                .name(beneficiary.getUser().getName())
                .nickname(beneficiary.getUser().getNickname())
                .build();
    }
}
