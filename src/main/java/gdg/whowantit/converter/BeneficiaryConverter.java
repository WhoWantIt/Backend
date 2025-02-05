package gdg.whowantit.converter;

import gdg.whowantit.dto.beneficiaryDto.BeneficiaryResponseDto;
import gdg.whowantit.entity.Beneficiary;

public class BeneficiaryConverter {
    public static BeneficiaryResponseDto.profileResponse toBeneficiaryResponse (Beneficiary beneficiary){
        return BeneficiaryResponseDto.profileResponse.builder()
                .beneficiaryId(beneficiary.getUser().getId())
                .beneficiaryName(beneficiary.getUser().getName())
                .image(beneficiary.getImage())
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
}
