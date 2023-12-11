import java.math.BigDecimal;

/**
 * The MathValue class represents a mathematical value that can be expressed
 * either as a fraction or as a decimal. This class encapsulates a Fraction
 * object and a BigDecimal object, along with an enum ValueType to indicate the
 * current representation of the value.
 * 
 * @author Marti Lonnemann
 * @version 2.0
 *          Final Project - Batch Calculator
 *          Fall/2023
 */
public class MathValue {
    private Fraction fractionValue;
    private BigDecimal decimalValue;
    private ValueType type;

    static final int DECIMAL_PLACE_MAXIMUM = 10;

    /**
     * Enum for representing the type of the mathematical value, either FRACTION or
     * DECIMAL.
     */
    public enum ValueType {
        FRACTION, DECIMAL
    }

    /**
     * Constructs a MathValue object from a Fraction.
     * The decimal representation is computed from the fraction.
     * 
     * @param fraction The fraction to be represented.
     */
    public MathValue(Fraction fraction) {
        this.fractionValue = fraction;
        this.decimalValue = this.fractionValue.toBigDecimal();
        this.type = ValueType.FRACTION;
    }

    /**
     * Constructs a MathValue object from a BigDecimal.
     * The fraction representation is not computed in this constructor.
     * 
     * @param decimal The decimal value to be represented.
     */
    public MathValue(BigDecimal decimal) {
        this.decimalValue = decimal;
        this.fractionValue = null; // not computed
        this.type = ValueType.DECIMAL;
    }

    /**
     * Constructs a MathValue object from a string representation of a number.
     * The method determines whether to treat the input as a decimal or a fraction
     * based on the location of a decimal point and the number of decimal places.
     * ex. "3.99" => 399/100 (type Fraction)
     * or "3.9999999999999999999" => 3.9999999999999999999 (type BigDecimal)
     * 
     * @param number The string representation of the number.
     */
    public MathValue(String number) {
        if (number.contains(".")) {
            int decimalPlaces = number.length() - number.indexOf('.') - 1;
            if (decimalPlaces > DECIMAL_PLACE_MAXIMUM) {
                this.decimalValue = new BigDecimal(number);
                this.type = ValueType.DECIMAL;
                return;
            }
            long denom = (long) Math.pow(10, decimalPlaces);
            long num = Long.parseLong(number.replace(".", ""));
            this.fractionValue = new Fraction(num, denom);
        } else {
            this.fractionValue = new Fraction(Long.parseLong(number), 1);
        }
        this.type = ValueType.FRACTION;
    }

    /**
     * Functional interface for fraction operations.
     */
    @FunctionalInterface
    interface DecimalOperation {
        BigDecimal apply(BigDecimal a, BigDecimal b);
    }

    /**
     * Functional interface for fraction operations.
     */
    @FunctionalInterface
    interface FractionOperation {
        Fraction apply(Fraction a, Fraction b);
    }

    /**
     * Applies the specified operation to this MathValue and another MathValue.
     * The operation is applied based on the type (decimal or fraction) of the
     * values.
     * 
     * @param other      The other MathValue to operate with.
     * @param decimalOp  The operation to be applied if the values are decimals.
     * @param fractionOp The operation to be applied if the values are fractions.
     * @return A new MathValue representing the result of the operation.
     */
    public MathValue applyOperation(MathValue other, DecimalOperation decimalOp, FractionOperation fractionOp) {
        if (this.type == ValueType.DECIMAL || other.type == ValueType.DECIMAL) {
            return new MathValue(decimalOp.apply(this.getDecimal(), other.getDecimal()));
        }
        return new MathValue(fractionOp.apply(this.fractionValue, other.fractionValue));
    }

    /**
     * Retrieves the decimal representation of this MathValue.
     * If the current type is FRACTION, it converts the fraction to a BigDecimal.
     * 
     * @return The BigDecimal representation of this MathValue.
     */
    public BigDecimal getDecimal() {
        return decimalValue == null ? fractionValue.toBigDecimal() : decimalValue;
    }

    /**
     * Adds another MathValue to this MathValue and returns the result.
     * 
     * @param other The MathValue to be added.
     * @return A new MathValue representing the sum.
     */
    
    public MathValue add(MathValue other) {
        return applyOperation(other, BigDecimal::add, Fraction::add);
    }

    /**
     * Subtracts another MathValue from this MathValue and returns the result.
     * 
     * @param other The MathValue to be subtracted.
     * @return A new MathValue representing the difference.
     */
    public MathValue subtract(MathValue other) {
        return applyOperation(other, BigDecimal::subtract, Fraction::subtract);
    }

    /**
     * Multiplies another MathValue from this MathValue and returns the result.
     * 
     * @param other The MathValue to be multiplied.
     * @return A new MathValue representing the product.
     */
    public MathValue multiply(MathValue other) {
        return applyOperation(other, BigDecimal::multiply, Fraction::multiply);
    }

    /**
     * Divides another MathValue from this MathValue and returns the result.
     * 
     * @param other The MathValue to be divided.
     * @return A new MathValue representing the quotient.
     */
    public MathValue divide(MathValue other) {
        return applyOperation(other, BigDecimal::divide, Fraction::divide);
    }

    /**
     * A string representation of the MathValue determined by its type, DECIMAL or
     * FRACTION
     * 
     * @return A string representation of the MathValue
     */
    @Override
    public String toString() {
        switch (this.type) {
            case DECIMAL:
                return this.decimalValue.stripTrailingZeros().toString();
            case FRACTION:
                return this.fractionValue.toString();
            default:
                return null;
        }
    }

}
