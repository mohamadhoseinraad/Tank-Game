package ir.ac.kntu.logic;

/**
 * @author sina rostami
 *
 */
public class Calculator {

    /**
     * @param operand1 left-handed-side operand.
     * @param operand2 right-handed-side operand.
     * @param operator operator to apply on operands.
     * @return result of the calculation
     */
    public static double calculate(StringBuilder operand1, StringBuilder operand2, StringBuilder operator) {
        switch (Operator.valueOf(operator.toString())) {
            case PLUS:
                return Double.parseDouble(operand1.toString()) + Double.parseDouble(operand2.toString());
            case MINUS:
                return Double.parseDouble(operand1.toString()) - Double.parseDouble(operand2.toString());
            case DIVIDE:
                return Double.parseDouble(operand1.toString()) / Double.parseDouble(operand2.toString());
            case MULTIPLY:
                return Double.parseDouble(operand1.toString()) * Double.parseDouble(operand2.toString());
            case UNKNOWN:
            default:
                return 0;
        }
    }
}
