import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static ComplexMatrix a = null;
    static ComplexMatrix b = null;

    public static void main(String[] args) {
        System.out.println("Числа вводятся так: 3+2i, 1-4i, 5 (без пробелов внутри числа)");

        while (true) {
            System.out.println();
            System.out.println("1 - ввести матрицу A");
            System.out.println("2 - ввести матрицу B");
            System.out.println("3 - A + B");
            System.out.println("4 - A * B");
            System.out.println("5 - A / B");
            System.out.println("6 - транспонировать матрицу");
            System.out.println("7 - определитель матрицы");
            System.out.println("0 - выход");
            System.out.print("Выбор: ");
            String choice = scanner.nextLine().trim();

            try {
                if (choice.equals("1")) {
                    a = readMatrix();
                } else if (choice.equals("2")) {
                    b = readMatrix();
                } else if (choice.equals("3")) {
                    System.out.println(getA().add(getB()));
                } else if (choice.equals("4")) {
                    System.out.println(getA().multiply(getB()));
                } else if (choice.equals("5")) {
                    System.out.println(getA().divide(getB()));
                } else if (choice.equals("6")) {
                    System.out.println(pick().transpose());
                } else if (choice.equals("7")) {
                    System.out.println("Определитель: " + pick().determinant());
                } else if (choice.equals("0")) {
                    break;
                } else {
                    System.out.println("Нет такого пункта меню");
                }
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }

    static ComplexMatrix readMatrix() {
        System.out.print("Число строк: ");
        int rows = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Число столбцов: ");
        int cols = Integer.parseInt(scanner.nextLine().trim());

        ComplexMatrix m = new ComplexMatrix(rows, cols);
        System.out.println("Вводите строки матрицы, числа через пробел:");
        for (int i = 0; i < rows; i++) {
            String[] parts = scanner.nextLine().trim().split("\\s+");
            if (parts.length != cols) {
                throw new IllegalArgumentException("В строке должно быть " + cols + " чисел");
            }
            for (int j = 0; j < cols; j++) {
                m.set(i, j, Complex.parse(parts[j]));
            }
        }
        return m;
    }

    static ComplexMatrix getA() {
        if (a == null) throw new IllegalStateException("Матрица A не задана");
        return a;
    }

    static ComplexMatrix getB() {
        if (b == null) throw new IllegalStateException("Матрица B не задана");
        return b;
    }

    static ComplexMatrix pick() {
        System.out.print("Какая матрица (A или B): ");
        String s = scanner.nextLine().trim().toUpperCase();
        if (s.equals("A")) return getA();
        if (s.equals("B")) return getB();
        throw new IllegalArgumentException("Нужно ввести A или B");
    }
}
