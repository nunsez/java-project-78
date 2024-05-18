package hexlet.code.schemas;

public interface Schema<T> {

    default boolean isValid(T data) {
        return true;
    }

}
