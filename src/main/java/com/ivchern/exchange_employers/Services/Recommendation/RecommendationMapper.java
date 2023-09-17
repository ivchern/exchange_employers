package com.ivchern.exchange_employers.Services.Recommendation;


import com.ivchern.exchange_employers.Model.Card.RequestWorker;
import com.ivchern.exchange_employers.Model.Card.Resource;
import com.ivchern.exchange_employers.Model.Team.Skill;
import com.ivchern.grpc.Recommendations;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RecommendationMapper {

    public Recommendations.CardRequest CreateRequestFromRequestWorker(Long id, List<RequestWorker> requestWorker){
        Recommendations.CardRequest cardRequest;
        List<Recommendations.CardList> matchedCards = new ArrayList<>();
        for (RequestWorker request: requestWorker) {
            Recommendations.CardList matchedCard =
                    Recommendations.CardList.newBuilder()
                            .setId(request.getId()).addAllSkill(request.getSkills().stream()
                                    .map(Skill::getSkill)
                                    .collect(Collectors.toList()))
                            .build();
            matchedCards.add(matchedCard);
        }
        cardRequest = Recommendations.CardRequest.newBuilder().setId(id).addAllMatchedCard(matchedCards).build();
       return cardRequest;
    }
    public Recommendations.CardRequest CreateRequestFromResource(Long id, List<Resource> resources){
        Recommendations.CardRequest cardRequest;
        List<Recommendations.CardList> matchedCards = new ArrayList<>();
        for (Resource resource: resources) {
            Recommendations.CardList matchedCard =
                    Recommendations.CardList.newBuilder()
                            .setId(resource.getId()).addAllSkill(resource.getTeammate().getSkills().stream()
                                    .map(Skill::getSkill)
                                    .collect(Collectors.toList()))
                            .build();
            matchedCards.add(matchedCard);
        }
        cardRequest = Recommendations.CardRequest.newBuilder().setId(id).addAllMatchedCard(matchedCards).build();
        log.info(cardRequest.toString());
        return cardRequest;
    }
}
