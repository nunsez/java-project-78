package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;

public final class MapSchema implements BaseSchema<Map<?, ?>> {

    private boolean checkRequired = false;

    private Integer sizeof;

    private HashMap<Object, ?> shape;

    public MapSchema required() {
        checkRequired = true;
        return this;
    }

    public MapSchema sizeof(int size) {
        sizeof = size;
        return this;
    }

    public MapSchema shape(Map<?, ?> schemas) {
        this.shape = new HashMap<>(schemas);
        return this;
    }

    @Override
    public boolean isValid(Map<?, ?> data) {
        return checkRequired(data)
            && checkSizeof(data)
            && checkShape(data);
    }

    private boolean checkRequired(Map<?, ?> data) {
        if (!checkRequired) {
            return true;
        }
        return data != null;
    }

    private boolean checkSizeof(Map<?, ?> data) {
        if (sizeof == null) {
            return true;
        }
        if (data == null) {
            return false;
        }
        return data.size() == sizeof;
    }

    private boolean checkShape(Map<?, ?> data) {
        if (shape == null) {
            return true;
        }
        if (data == null) {
            return false;
        }

        for (var entry : shape.entrySet()) {
            var dataValue = data.get(entry.getKey());

            @SuppressWarnings("unchecked")
            var fieldSchema = (BaseSchema<Object>) entry.getValue();

            try {
                var isFieldValid = fieldSchema.isValid(dataValue);

                if (!isFieldValid) {
                    return false;
                }
            } catch (ClassCastException e) {
                return false;
            }
        }

        return true;
    }

}
