package pl.mbcode.nn.bank.config;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import pl.mbcode.nn.bank.integration.nbp.NbpClient;

@Configuration
@EnableScheduling
@EnableFeignClients(clients = {
        NbpClient.class
})
@ImportAutoConfiguration({FeignAutoConfiguration.class})
class ApplicationConfig {
}
