package com.ivchern.exchange_employers.DTO.CardDTO.ResourceDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceDtoOnSave extends ResourceDtoBase {
    @Schema(description = "ID подчиненного", example = "1")
    private Long teammateId;
    @Schema(description = "Номер владельца карточки", example = "1")
    private Long ownerId;

    public ResourceDtoOnSave(String cardTitle, String description, String locationWorked,
                             Date fromFree, Date endFree, long teammateId, long ownerId) {
        super.setCardTitle(cardTitle);
        super.setDescription(description);
        super.setLocationWorked(locationWorked);
        super.setFromFree(fromFree);
        super.setEndFree(endFree);
        this.teammateId = teammateId;
        this.ownerId = ownerId;
    }
}
