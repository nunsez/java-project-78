package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public final class MapSchema implements BaseSchema<Map<?, ?>> {

    private final Map<String, Predicate<Object>> checks = new HashMap<>();

    @Override
    public boolean isValid(Map<?, ?> data) {
        return checks.values().stream()
            .allMatch(p -> p.test(data));
    }

    private HashMap<Object, ?> shape;

    public MapSchema required() {
        Predicate<Object> check = (obj) -> obj instanceof Map;

        addCheck("required", check);

        return this;
    }

    public MapSchema sizeof(int size) {
        Predicate<Object> check = (obj) -> obj instanceof Map map
            && map.size() == size;

        addCheck("sizeof", check);

        return this;
    }

    public MapSchema shape(Map<?, ?> shapeValue) {
        Predicate<Object> check = (obj) -> {
            if (!(shapeValue instanceof Map<?, ?> schemes)) {
                return false;
            }

            if (!(obj instanceof Map<?, ?> data)) {
                return false;
            }

            return schemes.entrySet().stream()
                .allMatch(entry -> checkShape(entry, data));
        };

        addCheck("shape", check);

        return this;
    }

    private boolean checkShape(Map.Entry<?, ?> entry, Map<?, ?> data) {
        var dataValue = data.get(entry.getKey());

        @SuppressWarnings("unchecked")
        var fieldSchema = (BaseSchema<Object>) entry.getValue();

        try {
            return fieldSchema.isValid(dataValue);
        } catch (ClassCastException e) {
            return false;
        }
    }

    private void addCheck(String checkName, Predicate<Object> check) {
        checks.put(checkName, check);
    }

}
