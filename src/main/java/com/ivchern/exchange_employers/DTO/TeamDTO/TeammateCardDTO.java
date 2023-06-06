package com.ivchern.exchange_employers.DTO.TeamDTO;

import com.ivchern.exchange_employers.Model.Card.Rank;
import com.ivchern.exchange_employers.Model.Team.Skill;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TeammateCardDTO {
    private String jobTitle;
    private Rank rank;
    private Set<Skill> skills = new HashSet<>();
}
