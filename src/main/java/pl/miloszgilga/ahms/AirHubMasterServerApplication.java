package pl.miloszgilga.ahms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableCaching
@EnableScheduling
@SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement
public class AirHubMasterServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AirHubMasterServerApplication.class, args);
    }
}
