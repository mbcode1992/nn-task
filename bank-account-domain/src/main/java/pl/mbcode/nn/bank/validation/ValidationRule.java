package pl.mbcode.nn.bank.validation;

public interface ValidationRule<T> {
    void validate(T input);
}
