import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Represents a mathematical fraction with a numerator and denominator.
 * 
 * @author Marti Lonnemann
 * @version 2.0
 *          Final Project - Batch Calculator
 *          Fall/2023
 */
public class Fraction {
    private long numerator;
    private long denominator;

    /**
     * Constructs a Fraction with the given numerator and denominator.
     *
     * @param numerator   The numerator of the fraction.
     * @param denominator The denominator of the fraction. Cannot be zero.
     * @throws IllegalArgumentException if the denominator is zero.
     */
    public Fraction(long numerator, long denominator) {
        if (denominator == 0) {
            throw new IllegalArgumentException("Denominator cannot be zero.");
        }
        this.numerator = numerator;
        this.denominator = denominator;
        normalize();
    }

    /**
     * Constructs a Fraction with the given numerator and a denominator of 1.
     *
     * @param numerator The numerator of the fraction.
     */
    public Fraction(long numerator) {
        if (denominator == 0) {
            throw new IllegalArgumentException("Denominator cannot be zero.");
        }
        this.numerator = numerator;
        normalize();
    }

    /**
     * Normalizes the fraction by simplifying it and ensuring a positive
     * denominator.
     */
    private void normalize() {
        long gcd = gcd(Math.abs(numerator), Math.abs(denominator));
        numerator /= gcd;
        denominator /= gcd;

        // Ensure the denominator is positive
        if (denominator < 0) {
            numerator *= -1;
            denominator *= -1;
        }
    }

    /**
     * Calculates the greatest common divisor of two numbers.
     *
     * @param a The first number.
     * @param b The second numer.
     * @return The greatest common divisor of a and b.
     */
    private long gcd(long a, long b) {
        while (b != 0) {
            long t = b;
            b = a % b;
            a = t;
        }
        return a;
    }

    /**
     * Adds another fraction to this fraction.
     *
     * @param other The fraction to add.
     * @return A new Fraction representing the sum of this fraction and the other
     *         fraction.
     */
    public Fraction add(Fraction other) {
        long newNumerator = this.numerator * other.denominator + other.numerator * this.denominator;
        long newDenominator = this.denominator * other.denominator;
        return new Fraction(newNumerator, newDenominator);
    }

    /**
     * Subtracts another fraction from this fraction.
     *
     * @param other The fraction to subtract.
     * @return A new Fraction representing the difference of this fraction and the
     *         other fraction.
     */
    public Fraction subtract(Fraction other) {
        long newNumerator = this.numerator * other.denominator - other.numerator * this.denominator;
        long newDenominator = this.denominator * other.denominator;
        return new Fraction(newNumerator, newDenominator);
    }

    /**
     * Multiplies this fraction by another fraction.
     *
     * @param other The fraction to multiply by.
     * @return A new Fraction representing the product of this fraction and the
     *         other fraction.
     */
    public Fraction multiply(Fraction other) {
        return new Fraction(this.numerator * other.numerator, this.denominator * other.denominator);
    }

    /**
     * Divides this fraction by another fraction.
     *
     * @param other The fraction to divide by.
     * @return A new Fraction representing the quotient of this fraction divided by
     *         the other fraction.
     */
    public Fraction divide(Fraction other) {
        return new Fraction(this.numerator * other.denominator, this.denominator * other.numerator);
    }

    /**
     * Converts this fraction to a BigDecimal for more precise calculations.
     *
     * @return A BigDecimal representation of this fraction.
     */
    public BigDecimal toBigDecimal() {
        return new BigDecimal(this.numerator).divide(new BigDecimal(this.denominator), 10, RoundingMode.HALF_UP);
    }

    /**
     * Returns a string representation of the fraction.
     * 
     * @return A string representation of the fraction
     */
    @Override
    public String toString() {
        if (denominator == 1) {
            return String.valueOf(numerator);
        }
        return numerator + "/" + denominator;
    }

}
