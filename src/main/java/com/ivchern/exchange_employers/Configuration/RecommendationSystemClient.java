package com.ivchern.exchange_employers.Configuration;

import com.ivchern.grpc.Recommendations;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import com.ivchern.grpc.recomendationSystemGrpc;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RecommendationSystemClient {
    private final ManagedChannel channel;
    private final recomendationSystemGrpc.recomendationSystemBlockingStub blockingStub;

    public RecommendationSystemClient(String host, int port) {
        channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        blockingStub = recomendationSystemGrpc.newBlockingStub(channel);
    }

    public void shutdown() {
        channel.shutdown();
    }

    public Recommendations.CardResponse getRecommendation(Recommendations.CardRequest request) {
        return blockingStub.getRecomendation(request);
    }
}
