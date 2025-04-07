package edu.ntnu.iir.bidata.teamhome.util;

import java.util.function.Consumer;

/**
 * A utility class that represents an optional value that can be null.
 * This is a simplified version of Java's Optional class, but it allows null values.
 *
 * @param <T> the type of the value
 */
public class NullableOptional<T> {
  private final boolean present;
  private final T value;

  protected NullableOptional(boolean present, T value) {
    this.present = present;
    this.value = value;
  }

  /**
   * Creates a new NullableOptional with the given value.
   *
   * @param value the value to wrap
   * @return a NullableOptional containing the value
   */
  public static <T> NullableOptional<T> of(T value) {
    return new NullableOptional<>(true, value);
  }

  /**
   * Creates a new NullableOptional that is empty.
   *
   * @return an empty NullableOptional
   */
  public static <T> NullableOptional<T> empty() {
    return new NullableOptional<>(false, null);
  }

  /**
   * Returns the value if present, otherwise throws an exception.
   *
   * @return the value
   * @throws NullPointerException if no value is present
   */
  public T get() {
    if (!present) {
      throw new NullPointerException("No value present");
    }
    return value;
  }

  /**
   * Returns {@code true} if there is no value present, otherwise {@code false}.
   *
   * @return {@code true} if there is no value present, otherwise {@code false}
   * @see #isPresent()
   */
  public boolean isEmpty() {
    return !present;
  }

  /**
   * Returns {@code true} if there is a value present, otherwise {@code false}.
   *
   * @return {@code true} if there is a value present, otherwise {@code false}
   * @see #isEmpty()
   */
  public boolean isPresent() {
    return present;
  }

  /**
   * If a value is present, performs the given action with the value,
   * otherwise does nothing.
   *
   * @param action the action to be performed, if a value is present
   * @throws NullPointerException if value is present and the given action is
   *         {@code null}
   */
  public void ifPresent(Consumer<? super T> action) {
    if (present) {
      action.accept(value);
    }
  }
}
