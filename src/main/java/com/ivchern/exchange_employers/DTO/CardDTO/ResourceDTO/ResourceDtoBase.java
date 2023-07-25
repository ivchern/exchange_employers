package com.ivchern.exchange_employers.DTO.CardDTO.ResourceDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceDtoBase {
    @Schema(description = "Заголовок карточки", example = "Kotlin developer")
    private String cardTitle;
    @Schema(description = "Оисание", example = "Kotlin developer desc.... Middle. ASAP")
    private String description;
    @Schema(description = "Локация", example = "Из дома")
    private String locationWorked;
    @Schema(description = "Свободен с", example = "2024-01-01", type = "string")
    private Date fromFree;
    @Schema(description = "Свободен до", example = "2025-01-01", type = "string")
    private Date endFree;
}
