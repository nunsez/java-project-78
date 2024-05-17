package hexlet.code.schemas;

public class NumberSchema {

    private boolean checkRequired = false;

    private boolean checkPositive = false;

    private boolean checkRange = false;

    private int rangeBottom;

    private int rangeTop;

    public NumberSchema required() {
        checkRequired = true;
        return this;
    }

    public NumberSchema positive() {
        checkPositive = true;
        return this;
    }

    public NumberSchema range(int rangeBottom, int rangeTop) {
        this.checkRange = true;
        this.rangeBottom = rangeBottom;
        this.rangeTop = rangeTop;
        return this;
    }

    public boolean isValid(Integer data) {
        return checkRequired(data)
            && checkPositive(data)
            && checkRange(data);
    }

    private boolean checkRequired(Integer data) {
        if (!checkRequired) {
            return true;
        }
        return data != null;
    }

    private boolean checkPositive(Integer data) {
        if (!checkPositive) {
            return true;
        }
        if (data == null) {
            return false;
        }
        return data > 0;
    }

    private boolean checkRange(Integer data) {
        if (!checkRange) {
            return true;
        }
        if (data == null) {
            return false;
        }
        return data >= rangeBottom
            && data <= rangeTop;
    }

}
