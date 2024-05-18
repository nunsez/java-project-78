package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public final class StringSchema implements BaseSchema<String> {

    private final Map<String, Predicate<Object>> checks = new HashMap<>();

    @Override
    public boolean isValid(String data) {
        return checks.values().stream()
            .allMatch(p -> p.test(data));
    }

    public StringSchema required() {
        Predicate<Object> check = (obj) -> obj instanceof String str
            && !str.isBlank();

        addCheck("required", check);

        return this;
    }

    public StringSchema minLength(int value) {
        Predicate<Object> check = (obj) -> obj instanceof String str
            && str.length() >= value;

        addCheck("minLength", check);

        return this;
    }

    public StringSchema contains(String substring) {
        Predicate<Object> check = (obj) -> {
            if (substring == null) {
                return true;
            }
            if (obj instanceof String str) {
                return str.contains(substring);
            }
            return false;
        };

        addCheck("contains", check);

        return this;
    }

    private void addCheck(String checkName, Predicate<Object> check) {
        checks.put(checkName, check);
    }

}
