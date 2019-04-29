package com.excilys.validation;

@FunctionalInterface
public interface IValidator<K> {

  ValidationResult test(K parameter);

  default IValidator<K> and(IValidator<K> chain) {
    return (argument) -> {
      ValidationResult selfResult = this.test(argument);
      return !selfResult.isValid() ? selfResult : chain.test(argument);
    };
  }

  default IValidator<K> or(IValidator<K> chain) {
    return (argument) -> {
      ValidationResult selfResult = this.test(argument);
      return selfResult.isValid() ? selfResult : chain.test(argument);
    };
  }

}
