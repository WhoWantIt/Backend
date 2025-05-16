package gdg.whowantit.converter;


import gdg.whowantit.dto.PostDto.ItemDTO;
import gdg.whowantit.entity.DonatedItem;

public class DonatedItemConverter {
    public static ItemDTO toItem(DonatedItem donatedItem){

        return ItemDTO.builder()
                .name(donatedItem.getItemName())
                .count(donatedItem.getQuantity())
                .build();
    }


}