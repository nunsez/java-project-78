package hexlet.code.schemas;

public interface BaseSchema<T> {

    default boolean isValid(T data) {
        return true;
    }

}
