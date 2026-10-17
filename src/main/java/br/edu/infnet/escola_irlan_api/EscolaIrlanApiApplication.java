package br.edu.infnet.escola_irlan_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EscolaIrlanApiApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(EscolaIrlanApiApplication.class, args);
    }
}
