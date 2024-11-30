import java.math.BigDecimal;
import java.math.MathContext;

public class ArithmeticCoding {

    static class Segment {
        BigDecimal left, right;
        public Segment(BigDecimal left, BigDecimal right) {
            this.left = left;
            this.right = right;
        }
    }

    public static Segment[] defineSegments(char[] letters, double[] probabilities) {
        Segment[] segments = new Segment[letters.length];
        BigDecimal left = BigDecimal.ZERO;
        for (int i = 0; i < letters.length; i++) {
            BigDecimal probability = BigDecimal.valueOf(probabilities[i]);
            segments[i] = new Segment(left, left.add(probability));
            left = left.add(probability);
        }
        return segments;
    }

    public static BigDecimal arithmeticCoding(char[] letters, double[] probabilities, String input) {
        Segment[] segments = defineSegments(letters, probabilities);
        BigDecimal left = BigDecimal.ZERO, right = BigDecimal.ONE;
        MathContext mc = new MathContext(50);

        for (int step = 0; step < input.length(); step++) {
            char symbol = input.charAt(step);
            int index = -1;
            for (int i = 0; i < letters.length; i++) {
                if (letters[i] == symbol) {
                    index = i;
                    break;
                }
            }

            if (index == -1) {
                throw new IllegalArgumentException("Символ " + symbol + " отсутствует в алфавите.");
            }

            Segment segment = segments[index];
            BigDecimal range = right.subtract(left);
            BigDecimal newLeft = left.add(range.multiply(segment.left, mc));
            BigDecimal newRight = left.add(range.multiply(segment.right, mc));

            System.out.printf("'%c':   %s,   %s\n", symbol, newLeft.toPlainString(), newRight.toPlainString());

            left = newLeft;
            right = newRight;
        }

        return left.add(right).divide(BigDecimal.valueOf(2), mc);
    }

    public static void main(String[] args) {
        char[] letters = {'п', 'л', 'а', 'т', 'о', 'н', 'в', 'р', 'м', 'и', 'к', 'ч'};
        double[] probabilities = {1.0 / 28, 1.0 / 28, 2.0 / 28, 2.0 / 28, 5.0 / 28, 2.0 / 28,
                3.0 / 28, 2.0 / 28, 1.0 / 28, 2.0 / 28, 1.0 / 28, 1.0 / 28};
        String input = "платоновроманвикторович";
        arithmeticCoding(letters, probabilities, input);
    }
}
