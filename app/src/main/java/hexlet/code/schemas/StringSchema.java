package hexlet.code.schemas;

public class StringSchema {

    private boolean checkRequired = false;

    private Integer minLength;

    private String contains;

    public StringSchema required() {
        checkRequired = true;
        return this;
    }

    public StringSchema minLength(int value) {
        minLength = value;
        return this;
    }

    public StringSchema contains(String contains) {
        this.contains = contains;
        return this;
    }

    public boolean isValid(String data) {
        return checkRequired(data)
            && checkMinLength(data)
            && checkContains(data);
    }

    private boolean checkRequired(String data) {
        if (!checkRequired) {
            return true;
        }
        if (data == null) {
            return false;
        }
        return !data.isBlank();
    }

    private boolean checkMinLength(String data) {
        if (minLength == null) {
            return true;
        }
        if (data == null) {
            return false;
        }
        return data.length() >= minLength;
    }

    private boolean checkContains(String data) {
        if (contains == null) {
            return true;
        }
        if (data == null) {
            return false;
        }
        return data.contains(contains);
    }

}
