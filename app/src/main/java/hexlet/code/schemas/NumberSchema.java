package hexlet.code.schemas;

public class NumberSchema implements BaseSchema<Number> {

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

    public NumberSchema range(Number bottom, Number top) {
        this.checkRange = true;
        this.rangeBottom = bottom.doubleValue();
        this.rangeTop = top.doubleValue();
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
