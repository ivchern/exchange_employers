package com.ivchern.exchange_employers.Services.Recommendation;

import com.ivchern.exchange_employers.Configuration.RecommendationSystemClient;
import com.ivchern.grpc.Recommendations.*;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLException;
import java.io.IOException;

@Service
public class RecommendationService {
    private final RecommendationSystemClient recommendationSystemClient;

    public RecommendationService() throws IOException {
        recommendationSystemClient = new RecommendationSystemClient("127.0.0.1", 50051);
    }

    public CardResponse getRecommendation(CardRequest request) {
        return recommendationSystemClient.getRecommendation(request);
    }

    public void shutdown() {
        recommendationSystemClient.shutdown();
    }
}
