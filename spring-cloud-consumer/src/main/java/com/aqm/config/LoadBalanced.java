package com.aqm.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.WeightedResponseTimeRule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadBalanced {

    @Bean
    public IRule iRule() {
        return new WeightedResponseTimeRule();
        // return new RandomRule();
    }
}