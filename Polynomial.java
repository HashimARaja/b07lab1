import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Polynomial {
    private double[] coefficients;
    private int[] exponents;

    // No-argument constructor that sets the polynomial to zero
    public Polynomial() {
        this.coefficients = new double[]{0};
        this.exponents = new int[]{0};
    }

    // Constructor that takes arrays of doubles as coefficients and ints as exponents
    public Polynomial(double[] coefficients, int[] exponents) {
        if (coefficients.length != exponents.length) {
            throw new IllegalArgumentException("Coefficients and exponents arrays must have the same length");
        }
        this.coefficients = coefficients.clone();
        this.exponents = exponents.clone();
    }

    // Constructor that takes a File object to initialize the polynomial
    public Polynomial(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();
        reader.close();

        List<Double> coeffsList = new ArrayList<>();
        List<Integer> expsList = new ArrayList<>();
        String[] terms = line.split("(?=[-+])"); // Split by + or -

        for (String term : terms) {
            term = term.trim();
            if (term.contains("x")) {
                String[] parts = term.split("x");
                double coeff = parts[0].isEmpty() || parts[0].equals("+") ? 1 : parts[0].equals("-") ? -1 : Double.parseDouble(parts[0]);
                int exp = parts.length > 1 ? Integer.parseInt(parts[1].replace("^", "")) : 1;
                coeffsList.add(coeff);
                expsList.add(exp);
            } else {
                coeffsList.add(Double.parseDouble(term));
                expsList.add(0);
            }
        }

        this.coefficients = coeffsList.stream().mapToDouble(Double::doubleValue).toArray();
        this.exponents = expsList.stream().mapToInt(Integer::intValue).toArray();
    }

    // Method to add two polynomials
    public Polynomial add(Polynomial other) {
        List<Double> resultCoeffs = new ArrayList<>();
        List<Integer> resultExps = new ArrayList<>();

        int i = 0, j = 0;
        while (i < this.coefficients.length && j < other.coefficients.length) {
            if (this.exponents[i] == other.exponents[j]) {
                resultCoeffs.add(this.coefficients[i] + other.coefficients[j]);
                resultExps.add(this.exponents[i]);
                i++;
                j++;
            } else if (this.exponents[i] < other.exponents[j]) {
                resultCoeffs.add(this.coefficients[i]);
                resultExps.add(this.exponents[i]);
                i++;
            } else {
                resultCoeffs.add(other.coefficients[j]);
                resultExps.add(other.exponents[j]);
                j++;
            }
        }

        while (i < this.coefficients.length) {
            resultCoeffs.add(this.coefficients[i]);
            resultExps.add(this.exponents[i]);
            i++;
        }

        while (j < other.coefficients.length) {
            resultCoeffs.add(other.coefficients[j]);
            resultExps.add(other.exponents[j]);
            j++;
        }

        double[] resultCoefficients = resultCoeffs.stream().mapToDouble(Double::doubleValue).toArray();
        int[] resultExponents = resultExps.stream().mapToInt(Integer::intValue).toArray();

        return new Polynomial(resultCoefficients, resultExponents);
    }

    // Method to evaluate the polynomial at a given x value
    public double evaluate(double x) {
        double result = 0;
        for (int i = 0; i < coefficients.length; i++) {
            result += coefficients[i] * Math.pow(x, exponents[i]);
        }
        return result;
    }

    // Method to check if a given value is a root of the polynomial
    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }

    // Method to multiply two polynomials
    public Polynomial multiply(Polynomial other) {
        List<Double> resultCoeffs = new ArrayList<>();
        List<Integer> resultExps = new ArrayList<>();

        for (int i = 0; i < this.coefficients.length; i++) {
            for (int j = 0; j < other.coefficients.length; j++) {
                double coeff = this.coefficients[i] * other.coefficients[j];
                int exp = this.exponents[i] + other.exponents[j];
                int index = resultExps.indexOf(exp);
                if (index != -1) {
                    resultCoeffs.set(index, resultCoeffs.get(index) + coeff);
                } else {
                    resultCoeffs.add(coeff);
                    resultExps.add(exp);
                }
            }
        }

        double[] resultCoefficients = resultCoeffs.stream().mapToDouble(Double::doubleValue).toArray();
        int[] resultExponents = resultExps.stream().mapToInt(Integer::intValue).toArray();

        return new Polynomial(resultCoefficients, resultExponents);
    }

    // Method to save the polynomial to a file
    public void saveToFile(String fileName) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < coefficients.length; i++) {
            if (i > 0 && coefficients[i] > 0) {
                sb.append("+");
            }
            sb.append(coefficients[i]);
            if (exponents[i] != 0) {
                sb.append("x^").append(exponents[i]);
            }
        }

        writer.write(sb.toString());
        writer.close();
    }

    
}
