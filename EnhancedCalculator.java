import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;

public class EnhancedCalculator {

    private static final Scanner sc = new Scanner(System.in);
    private static final MathContext MC = new MathContext(10, RoundingMode.HALF_UP);

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n====== ENHANCED CONSOLE CALCULATOR ======");
            System.out.println("1. Basic Arithmetic");
            System.out.println("2. Scientific Calculations");
            System.out.println("3. Temperature Conversion");
            System.out.println("4. Currency Conversion");
            System.out.println("5. Expression Evaluation (BODMAS)");
            System.out.println("6. Exit");
            System.out.print("Choose option: ");

            try {
                int choice = sc.nextInt();
                sc.nextLine(); // clear buffer

                switch (choice) {
                    case 1 -> basicArithmetic();
                    case 2 -> scientific();
                    case 3 -> temperature();
                    case 4 -> currency();
                    case 5 -> expressionMode();
                    case 6 -> {
                        System.out.println("Exiting safely.");
                        return;
                    }
                    default -> System.out.println("Invalid choice.");
                }
            } catch (InputMismatchException e) {
                System.out.println("ERROR: Enter a valid number.");
                sc.next();
            }
        }
    }

    /* ---------------- BASIC ARITHMETIC ---------------- */

    private static void basicArithmetic() {
        BigDecimal a = readNumber("Enter first number: ");
        BigDecimal b = readNumber("Enter second number: ");

        System.out.println("1. Add  2. Subtract  3. Multiply  4. Divide");
        System.out.print("Choose: ");
        int op = sc.nextInt();

        try {
            switch (op) {
                case 1 -> System.out.println("Result = " + a.add(b));
                case 2 -> System.out.println("Result = " + a.subtract(b));
                case 3 -> System.out.println("Result = " + a.multiply(b));
                case 4 -> {
                    if (b.compareTo(BigDecimal.ZERO) == 0)
                        throw new ArithmeticException("Division by zero");
                    System.out.println("Result = " + a.divide(b, MC));
                }
                default -> System.out.println("Invalid operation.");
            }
        } catch (ArithmeticException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    /* ---------------- SCIENTIFIC ---------------- */

    private static void scientific() {
        System.out.println("1. Square Root");
        System.out.println("2. Power");
        System.out.print("Choose: ");
        int choice = sc.nextInt();

        try {
            if (choice == 1) {
                BigDecimal n = readNumber("Enter number: ");
                if (n.compareTo(BigDecimal.ZERO) < 0)
                    throw new ArithmeticException("Negative square root");
                System.out.println("Result = " +
                        new BigDecimal(Math.sqrt(n.doubleValue()), MC));
            } else if (choice == 2) {
                BigDecimal base = readNumber("Enter base: ");
                System.out.print("Enter exponent (int): ");
                int exp = sc.nextInt();
                System.out.println("Result = " + base.pow(exp, MC));
            } else {
                System.out.println("Invalid choice.");
            }
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    /* ---------------- TEMPERATURE ---------------- */

    private static void temperature() {
        System.out.println("1. Celsius → Fahrenheit");
        System.out.println("2. Fahrenheit → Celsius");
        System.out.print("Choose: ");
        int ch = sc.nextInt();

        BigDecimal temp = readNumber("Enter temperature: ");

        if (ch == 1) {
            BigDecimal f = temp.multiply(BigDecimal.valueOf(9))
                    .divide(BigDecimal.valueOf(5), MC)
                    .add(BigDecimal.valueOf(32));
            System.out.println("Fahrenheit = " + f);
        } else if (ch == 2) {
            BigDecimal c = temp.subtract(BigDecimal.valueOf(32))
                    .multiply(BigDecimal.valueOf(5))
                    .divide(BigDecimal.valueOf(9), MC);
            System.out.println("Celsius = " + c);
        } else {
            System.out.println("Invalid choice.");
        }
    }

    /* ---------------- CURRENCY ---------------- */

    private static void currency() {
        BigDecimal rate = new BigDecimal("83.25"); // USD → INR (static)
        BigDecimal usd = readNumber("Enter USD: ");
        System.out.println("INR = ₹" + usd.multiply(rate, MC));
    }

    /* ---------------- EXPRESSION PARSER ---------------- */

    private static void expressionMode() {
        System.out.print("Enter expression (e.g. 5 + 3 * 2): ");
        String expr = sc.nextLine();

        try {
            BigDecimal result = evaluateExpression(expr);
            System.out.println("Result = " + result);
        } catch (Exception e) {
            System.out.println("ERROR: Invalid expression.");
        }
    }

    /* -------- SHUNTING YARD + POSTFIX EVALUATION -------- */

    private static BigDecimal evaluateExpression(String expr) {
        List<String> postfix = infixToPostfix(expr);
        Stack<BigDecimal> stack = new Stack<>();

        for (String token : postfix) {
            if (isNumber(token)) {
                stack.push(new BigDecimal(token));
            } else {
                BigDecimal b = stack.pop();
                BigDecimal a = stack.pop();

                switch (token) {
                    case "+" -> stack.push(a.add(b));
                    case "-" -> stack.push(a.subtract(b));
                    case "*" -> stack.push(a.multiply(b));
                    case "/" -> {
                        if (b.compareTo(BigDecimal.ZERO) == 0)
                            throw new ArithmeticException();
                        stack.push(a.divide(b, MC));
                    }
                }
            }
        }
        return stack.pop();
    }

    private static List<String> infixToPostfix(String expr) {
        List<String> output = new ArrayList<>();
        Stack<String> ops = new Stack<>();

        StringTokenizer tokens = new StringTokenizer(expr, "+-*/()", true);

        while (tokens.hasMoreTokens()) {
            String t = tokens.nextToken().trim();
            if (t.isEmpty()) continue;

            if (isNumber(t)) {
                output.add(t);
            } else if ("+-*/".contains(t)) {
                while (!ops.isEmpty() && precedence(ops.peek()) >= precedence(t))
                    output.add(ops.pop());
                ops.push(t);
            } else if (t.equals("(")) {
                ops.push(t);
            } else if (t.equals(")")) {
                while (!ops.peek().equals("("))
                    output.add(ops.pop());
                ops.pop();
            }
        }

        while (!ops.isEmpty())
            output.add(ops.pop());

        return output;
    }

    private static int precedence(String op) {
        return (op.equals("+") || op.equals("-")) ? 1 : 2;
    }

    private static boolean isNumber(String s) {
        try {
            new BigDecimal(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /* ---------------- INPUT SAFETY ---------------- */

    private static BigDecimal readNumber(String msg) {
        System.out.print(msg);
        while (true) {
            try {
                return sc.nextBigDecimal();
            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Enter number: ");
                sc.next();
            }
        }
    }
}
