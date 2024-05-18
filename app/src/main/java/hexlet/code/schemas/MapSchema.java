package hexlet.code.schemas;

import java.util.Map;

public class MapSchema {

    private boolean checkRequired = false;

    private Integer sizeof;

    public MapSchema required() {
        checkRequired = true;
        return this;
    }

    public MapSchema sizeof(int size) {
        sizeof = size;
        return this;
    }

    public boolean isValid(Map<?, ?> data) {
        return checkRequired(data)
            && checkSizeof(data);
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

}
