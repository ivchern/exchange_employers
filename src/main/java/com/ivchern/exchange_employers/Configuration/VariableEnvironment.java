package com.ivchern.exchange_employers.Configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VariableEnvironment {
    @Value("${grpc.port}")
    private int grpcPort;
    @Value("${grpc.address}")
    private String grpcAddress;
}
