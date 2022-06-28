package net.ashwork.mc.multiplatform.platform.core.util;

import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 * Represents a function that accepts one argument and produces a not-null result.
 *
 * <p>This is a functional interface whose functional method is {@link #apply(Object)}.
 *
 * @param <T> the type of the input to the function
 * @param <R> the type of the result of the function
 */
@FunctionalInterface
public interface NotNullFunction<T, R> extends Function<T, R> {

    /**
     * Applies this function to the given argument.
     *
     * @param t the function argument
     * @return the function result
     * @throws NullPointerException if the result is {@code null}
     */
    default @NotNull(exception = NullPointerException.class) R checkedApply(T t) {
        return this.apply(t);
    }

    @Override
    default <V> NotNullFunction<V, R> compose(@NotNull Function<? super V, ? extends T> before) {
        return (V v) -> apply(before.apply(v));
    }

    @Override
    default <V> NotNullFunction<T, V> andThen(@NotNull Function<? super R, ? extends V> after) {
        return (T t) -> after.apply(apply(t));
    }

    /**
     * Returns a function that always returns its input argument.
     *
     * @param <T> the type of the input and output objects to the function
     * @return a function that always returns its input argument
     */
    static <T> NotNullFunction<T, T> identity() {
        return t -> t;
    }
}
