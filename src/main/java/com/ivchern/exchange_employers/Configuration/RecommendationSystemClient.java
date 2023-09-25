package com.ivchern.exchange_employers.Configuration;

import com.ivchern.grpc.Recommendations;
import com.ivchern.grpc.recommendationSystemGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;

import javax.net.ssl.SSLException;
import java.io.File;
import java.util.Objects;

@Slf4j
public class RecommendationSystemClient {
    private final ManagedChannel channel;
    private final recommendationSystemGrpc.recommendationSystemBlockingStub blockingStub;
    private final recommendationSystemGrpc.recommendationSystemStub asyncStub;

    public RecommendationSystemClient(String host, int port) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();

//        File certChainFile = new File(Objects.requireNonNull(classLoader.getResource("server.crt")).getFile());

        InputStream certInputStream = classLoader.getResourceAsStream("server.crt");
        if (certInputStream == null) {
            throw new IllegalArgumentException("server.crt не найден в classpath");
        }

        try {
            channel = NettyChannelBuilder.forAddress(host, port)
                    .sslContext(GrpcSslContexts.forClient().trustManager(certInputStream).build())
                    .build();
        } finally {
            certInputStream.close();
        }

        blockingStub = recommendationSystemGrpc.newBlockingStub(channel);
        asyncStub = recommendationSystemGrpc.newStub(channel);
    }

    public void shutdown() {
        channel.shutdown();
    }

    public Recommendations.CardResponse getRecommendation(Recommendations.CardRequest request) {
        try {
            var recommendation =  blockingStub.recommendation(request);
            log.info(recommendation.toString());
            return recommendation;
        } catch (StatusRuntimeException e) {
            log.info("error recommendation", e.getStatus());
            return null;
        }
    }
}
