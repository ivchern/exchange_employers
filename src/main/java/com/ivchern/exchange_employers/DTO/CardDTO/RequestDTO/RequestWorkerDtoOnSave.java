package com.ivchern.exchange_employers.DTO.CardDTO.RequestDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestWorkerDtoOnSave {
    @Schema(description = "Заголовок карточки", example = "Kotlin developer. Middle. ASAP")
    String jobTitle;
    @Schema(description = "Проект на который требуется разработчик", example = "Синий банк. Смарт")
    String projectName;
    @Schema(description = "Уровень разработчика", example = "Middle")
    String rank;
    @Schema(description = "Описания", example = "Атмосфера стартапа")
    String description;
    @Schema(description = "Место работы", example = "Удаленная работа")
    String locationWorked;
    @Schema(description = "Требуется до", example = "2024-01-01", type = "string")
    Date needBefore;
    @Schema(description = "Необходимость интерью у заказчика", example = "true")
    boolean isInterviewNeeded;
    @Schema(description = "Требуемые скиллы", type = "array", example = "[\"Java\", \"Kafka\", \"Maven\"]")
    private Set<String> skills;
    @Schema(description = "Владелец карточки", example = "2")
    private Long ownerId;
}
