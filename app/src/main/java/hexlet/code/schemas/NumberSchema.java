package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public final class NumberSchema implements BaseSchema<Number> {

    private final Map<String, Predicate<Object>> checks = new HashMap<>();

    @Override
    public boolean isValid(Number data) {
        return checks.values().stream()
            .allMatch(p -> p.test(data));
    }

    public NumberSchema required() {
        Predicate<Object> check = (obj) -> obj instanceof Number;

        addCheck("required", check);

        return this;
    }

    public NumberSchema positive() {
        Predicate<Object> check = (obj) -> {
            if (obj instanceof Number number) {
                return number.doubleValue() > 0;
            }
            return obj == null;
        };

        addCheck("positive", check);

        return this;
    }

    public NumberSchema range(Number bottom, Number top) {
        Predicate<Object> check = (obj) -> {
            if (obj instanceof Number number) {
                var value = number.doubleValue();
                return value >= bottom.doubleValue() && value <= top.doubleValue();
            }
            return false;
        };

        addCheck("range", check);

        return this;
    }

    private void addCheck(String checkName, Predicate<Object> check) {
        checks.put(checkName, check);
    }

}
