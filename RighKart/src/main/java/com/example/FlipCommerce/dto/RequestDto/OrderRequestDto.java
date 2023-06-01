package com.example.FlipCommerce.dto.RequestDto;

import com.example.FlipCommerce.Enum.CardType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class OrderRequestDto {

    String emailId;

    int productId;
    CardType cardType;
    String cardNo;

    int cvv;

    int requiredQuantity;
}
