package hexlet.code.schemas;

public class NumberSchema implements Schema<Number> {

    private boolean checkRequired = false;

    private boolean checkPositive = false;

    private boolean checkRange = false;

    private double rangeBottom;

    private double rangeTop;

    public NumberSchema required() {
        checkRequired = true;
        return this;
    }

    public NumberSchema positive() {
        checkPositive = true;
        return this;
    }

    public NumberSchema range(Number rangeBottom, Number rangeTop) {
        this.checkRange = true;
        this.rangeBottom = rangeBottom.doubleValue();
        this.rangeTop = rangeTop.doubleValue();
        return this;
    }

    @Override
    public boolean isValid(Number data) {
        return checkRequired(data)
            && checkPositive(data)
            && checkRange(data);
    }

    private boolean checkRequired(Number data) {
        if (!checkRequired) {
            return true;
        }
        return data != null;
    }

    private boolean checkPositive(Number data) {
        if (!checkPositive) {
            return true;
        }
        if (data == null) {
            return false;
        }
        return data.doubleValue() > 0;
    }

    private boolean checkRange(Number data) {
        if (!checkRange) {
            return true;
        }
        if (data == null) {
            return false;
        }

        var value = data.doubleValue();

        return value >= rangeBottom
            && value <= rangeTop;
    }

}
