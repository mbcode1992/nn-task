package pl.mbcode.nn.bank;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
class BankApplicationTests {

    @Test
    @DisplayName("Application should start")
    void applicationStarts() {
        Executable executable = () -> BankApplication.main(new String[]{});
        assertDoesNotThrow(executable);
    }

    @Test
    @DisplayName("Application should create context")
    void contextLoads(ApplicationContext context) {
        assertNotNull(context);
    }

}
