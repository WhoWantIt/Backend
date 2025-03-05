package gdg.whowantit.dto.kakaoPayDto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class KakaoPayResponseDto {
    @Data
    public static class KakaoReadyResponse{
        private String tid;
        private String next_redirect_pc_url;
        private String next_redirect_mobile_url;
        private String next_redirect_app_url;
        private String android_app_scheme;
        private String ios_app_scheme;
        private String created_at;
    }

    @Getter
    @Setter
    @ToString
    public static class KakaoApproveResponse {
        private String aid;
        private String tid;
        private String cid;
        private String sid;
        private String partner_order_id;
        private String partner_user_id;
        private String payment_method_type;
        private Amount amount;
        private String item_name;
        private String item_code;
        private int quantity;
        private String created_at;
        private String approved_at;
        private String payload;
    }

    @Getter
    @Setter
    @ToString
    public static class Amount {
        private int total;
        private int tax_free;
        private int tax;
        private int point;
        private int discount;
        private int green_deposit;
    }
}
