package pl.mbcode.nn.bank.validation;

import java.util.List;

public interface Validator<T> {

    List<ValidationRule<T>> getRules();

    default void validate(T object) {
        getRules().forEach(rule -> rule.validate(object));
    }

}
