package com.dto.delivery_tour_optimizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:applicationContext.xml") // ✅ Cette annotation suffit
public class DeliveryTourOptimizerApplication {
    public static void main(String[] args) {
        SpringApplication.run(DeliveryTourOptimizerApplication.class, args);
    }
}