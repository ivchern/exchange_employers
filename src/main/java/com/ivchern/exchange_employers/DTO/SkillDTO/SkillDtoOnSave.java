package com.ivchern.exchange_employers.DTO.SkillDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SkillDtoOnSave {
    private String name;
    private String description;
}
