package com.api.twitchbets;

import java.time.Clock;

import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }
}
