package pl.prodzajto.estolowkabackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class EstolowkaBackendApplication {

    public static void main(String[] args) { SpringApplication.run(EstolowkaBackendApplication.class, args); }
}

