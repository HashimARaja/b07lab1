public class Polynomial {
    private double[] coefficients;

    // No-argument constructor that sets the polynomial to zero
    public Polynomial() {
        this.coefficients = new double[]{0};
    }

    // Constructor that takes an array of doubles as coefficients
    public Polynomial(double[] coefficients) {
        this.coefficients = coefficients.clone(); // Use clone to prevent external modification
    }

    // Method to add two polynomials
    public Polynomial add(Polynomial other) {
        int maxLength = Math.max(this.coefficients.length, other.coefficients.length);
        double[] resultCoefficients = new double[maxLength];

        for (int i = 0; i < maxLength; i++) {
            double thisCoeff = i < this.coefficients.length ? this.coefficients[i] : 0;
            double otherCoeff = i < other.coefficients.length ? other.coefficients[i] : 0;
            resultCoefficients[i] = thisCoeff + otherCoeff;
        }

        return new Polynomial(resultCoefficients);
    }

    // Method to evaluate the polynomial at a given x value
    public double evaluate(double x) {
        double result = 0;
        for (int i = 0; i < coefficients.length; i++) {
            result += coefficients[i] * Math.pow(x, i);
        }
        return result;
    }

    // Method to check if a given value is a root of the polynomial
    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }

    // Main method for testing purposes
    public static void main(String[] args) {
        double[] coeffs1 = {6, -2, 0, 5};
        Polynomial p1 = new Polynomial(coeffs1);
        
        double[] coeffs2 = {3, 0, 2};
        Polynomial p2 = new Polynomial(coeffs2);
        
        Polynomial sum = p1.add(p2);
        
        System.out.println("p1 evaluated at x = -1: " + p1.evaluate(-1)); // Output should be 3
        System.out.println("Sum of p1 and p2: ");
        for (double coeff : sum.coefficients) {
            System.out.print(coeff + " ");
        }
        System.out.println("\nDoes p1 have root x = 1? " + p1.hasRoot(1)); // Output should be false
    }
}