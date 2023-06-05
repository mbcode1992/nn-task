package pl.mbcode.nn.bank.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static java.util.Objects.isNull;

@NoArgsConstructor(access = AccessLevel.NONE)
public class DataUtils {

    public static boolean isBlank(String string) {
        return isNull(string) || string.isBlank();
    }

}
