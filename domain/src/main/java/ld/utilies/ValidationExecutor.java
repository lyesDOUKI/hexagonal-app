package ld.utilies;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ValidationExecutor {
    /**
     * Pemet de lancer un validteur et de catch éventuellement l'exception et l'ajouter à la liste
     * @param errors
     * @param validator
     * @param validationMethod
     * @param <V>
     */
    public static <V> void collectErrorFromMethod(
            List<RuntimeException> errors,
            V validator,
            Consumer<V> validationMethod
    ) {
        try {
            validationMethod.accept(validator);
        } catch (RuntimeException e) {
            errors.add(e);
        }
    }
    private static <T> boolean validateAny(List<T> items, Predicate<T> predicate) {
        return items.stream()
                .anyMatch(predicate);
    }

    /**
     * permets de lancer un validateur si les predicats sont vérifier
     * @param errors
     * @param validator
     * @param validationMethod
     * @param errorPredicates
     * @param <V>
     */
    @SafeVarargs
    public static <V> void verifyAndValidate(
            List<RuntimeException> errors,
            V validator,
            Consumer<V> validationMethod,
            Predicate<RuntimeException>... errorPredicates
    ) {
        boolean hasAnyError = Arrays.stream(errorPredicates)
                .anyMatch(predicate -> validateAny(errors, predicate));

        if (!hasAnyError) {
            collectErrorFromMethod(errors, validator, validationMethod);
        }
    }
}

