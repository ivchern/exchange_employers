package com.ivchern.exchange_employers.Services.Recommendation;

import com.ivchern.exchange_employers.Configuration.RecommendationSystemClient;
import com.ivchern.exchange_employers.Configuration.VariableEnvironment;
import com.ivchern.grpc.Recommendations.*;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RecommendationService {
    private final RecommendationSystemClient recommendationSystemClient;
    private final VariableEnvironment variableEnvironment;

    public RecommendationService(VariableEnvironment variableEnvironment) throws IOException {
        this.variableEnvironment = variableEnvironment;
        recommendationSystemClient = new RecommendationSystemClient(variableEnvironment.getGrpcAddress(), variableEnvironment.getGrpcPort());
    }

    public CardResponse getRecommendation(CardRequest request) {
        return recommendationSystemClient.getRecommendation(request);
    }

    public void shutdown() {
        recommendationSystemClient.shutdown();
    }
}
