package pl.mbcode.nn.bank.integration.nbp;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import pl.mbcode.nn.bank.integration.nbp.model.NbpWrapperDto;

import java.util.List;

@FeignClient(name = "nbpClient", url = "${app.nbp.api-base-url}")
public interface NbpClient {

    @GetMapping(value = "/exchangerates/tables/a?format=json")
    List<NbpWrapperDto> getRates();

}
