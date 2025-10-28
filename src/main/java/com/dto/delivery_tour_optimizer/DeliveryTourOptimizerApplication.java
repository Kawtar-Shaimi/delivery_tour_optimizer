package com.dto.delivery_tour_optimizer;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DeliveryTourOptimizerApplication {

    public static void main(String[] args) {
        loadEnv();
        SpringApplication.run(DeliveryTourOptimizerApplication.class, args);
    }

    private static void loadEnv(){
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        dotenv.entries().forEach(entry -> {
            System.setProperty(entry.getKey(), entry.getValue());
        });
    }

}
