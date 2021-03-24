package com.example.javademo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@SpringBootApplication
public class HttpClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(HttpClientApplication.class, args);
    }

    @RestController
    public static class PingRestController {
        private final HttpClient httpClient = HttpClient.create().wiretap(true);

//        @PostMapping
//        public void warmUp() {
//            httpClient.warmup().block();
//        }

        @GetMapping
        Mono<String> ping() {
            var start = System.currentTimeMillis();
            return httpClient.baseUrl("http://github.com").get().response()
                    .map(response -> (System.currentTimeMillis() - start) + "ms");
        }
    }
}
