package io.github.benrkia;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringCalculator {

  public static final String COMMA_NEWLINE_REGEX = "[,\n]";

  public int sum(final String input) {
    if (input == null)
      throw new InvalidInputException("Sum does not support null values");

    if (input.isEmpty())
      return 0;

    List<Integer> parsedNumbers = parseNumbers(input);
    ensureAllPositiveNumbers(parsedNumbers);

    return parsedNumbers.stream().mapToInt(i -> i).sum();
  }

  private List<Integer> parseNumbers(final String input) {
    String[] numbers = input.split(COMMA_NEWLINE_REGEX);

    return Stream.of(numbers)
        .map(this::parse)
        .collect(Collectors.toList());
  }

  private void ensureAllPositiveNumbers(final List<Integer> parsedNumbers) {
    List<Integer> negativeNumbers = parsedNumbers.stream()
        .filter(number -> number < 0)
        .collect(Collectors.toList());

    if (!negativeNumbers.isEmpty()) {
      throw new InvalidInputException("Sum does not support negative number " + negativeNumbers);
    }
  }

  private int parse(final String number) {
    try {
      return Integer.parseInt(number);
    } catch (NumberFormatException e) {
      throw new InvalidInputException("Delimited values must be valid numbers");
    }
  }

}
