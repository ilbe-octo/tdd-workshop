package io.github.benrkia;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class StringCalculatorTest {

  private StringCalculator sut = new StringCalculator();

  @Test
  void should_return_zero_when_given_empty_string() {
    // Act
    int actual = sut.sum("");

    // Assert
    assertEquals(0, actual);
  }

  @Test
  void should_return_value_when_given_single_number() {
    // Act
    int actual = sut.sum("3");

    // Assert
    assertEquals(3, actual);
  }

  @Test
  void should_return_the_sum_given_two_comma_delimited_numbers() {
    // Act
    int actual = sut.sum("130,49");

    // Assert
    assertEquals(179, actual);
  }

  @Test
  void should_return_the_sum_given_two_newline_delimited_numbers() {
    // Act
    int actual = sut.sum("38\n289");

    // Assert
    assertEquals(327, actual);
  }

  @Test
  void should_return_the_sum_given_tree_comma_newline_delimited_numbers() {
    // Act
    int actual = sut.sum("46\n23,89");

    // Assert
    assertEquals(158, actual);
  }

  @Test
  void should_throw_when_provided_null_input() {
    // Assert
    String message = assertThrows(InvalidInputException.class, () -> {
      // Act
      sut.sum(null);
    }).getMessage();

    assertEquals("Sum does not support null values", message);
  }

  @ParameterizedTest
  @MethodSource("invalid_data_provider")
  void should_throw_when_provided_invalid_input_data(final String input) {
    // Assert
    String message = assertThrows(InvalidInputException.class, () -> {
      // Act
      sut.sum(input);
    }).getMessage();

    assertEquals("Delimited values must be valid numbers", message);
  }

  @Test
  void should_throw_when_provided_data_with_negative_numbers() {
    // Assert
    String message = assertThrows(InvalidInputException.class, () -> {
      // Act
      sut.sum("5,-1\n2,-8");
    }).getMessage();

    assertEquals("Sum does not support negative number [-1, -8]", message);
  }

  private static Stream<Arguments> invalid_data_provider() {
    return Stream.of(
        Arguments.of("12\n,30"),
        Arguments.of("12\nabc,30")
    );
  }



}
