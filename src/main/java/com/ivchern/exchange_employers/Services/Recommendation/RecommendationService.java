package com.ivchern.exchange_employers.Services.Recommendation;

import com.ivchern.exchange_employers.Configuration.RecommendationSystemClient;
import com.ivchern.grpc.Recommendations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecommendationService {
    private final RecommendationSystemClient recommendationSystemClient;

    public RecommendationService() {
        recommendationSystemClient = new RecommendationSystemClient("localhost", 50051);
    }

    public CardResponse getRecommendation(CardRequest request) {
        return recommendationSystemClient.getRecommendation(request);
    }

    public void shutdown() {
        recommendationSystemClient.shutdown();
    }
}
