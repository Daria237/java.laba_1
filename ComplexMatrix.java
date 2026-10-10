public class ComplexMatrix {
    private final int rows;
    private final int cols;
    private final Complex[][] data;

    public ComplexMatrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.data = new Complex[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] = new Complex(0, 0);
            }
        }
    }

    public void set(int i, int j, Complex value) {
        data[i][j] = value;
    }

    public ComplexMatrix add(ComplexMatrix other) {
        if (rows != other.rows || cols != other.cols) {
            throw new IllegalArgumentException("Для сложения размеры матриц должны совпадать");
        }
        ComplexMatrix result = new ComplexMatrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.data[i][j] = data[i][j].add(other.data[i][j]);
            }
        }
        return result;
    }

    public ComplexMatrix multiply(ComplexMatrix other) {
        if (cols != other.rows) {
            throw new IllegalArgumentException(
                    "Для умножения число столбцов первой матрицы должно равняться числу строк второй");
        }
        ComplexMatrix result = new ComplexMatrix(rows, other.cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < other.cols; j++) {
                Complex sum = new Complex(0, 0);
                for (int k = 0; k < cols; k++) {
                    sum = sum.add(data[i][k].multiply(other.data[k][j]));
                }
                result.data[i][j] = sum;
            }
        }
        return result;
    }

    public ComplexMatrix transpose() {
        ComplexMatrix result = new ComplexMatrix(cols, rows);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.data[j][i] = data[i][j];
            }
        }
        return result;
    }

    private ComplexMatrix minor(int row, int col) {
        ComplexMatrix result = new ComplexMatrix(rows - 1, cols - 1);
        int ri = 0;
        for (int i = 0; i < rows; i++) {
            if (i == row) continue;
            int rj = 0;
            for (int j = 0; j < cols; j++) {
                if (j == col) continue;
                result.data[ri][rj] = data[i][j];
                rj++;
            }
            ri++;
        }
        return result;
    }

    public Complex determinant() {
        if (rows != cols) {
            throw new IllegalArgumentException("Определитель есть только у квадратной матрицы");
        }
        if (rows == 1) {
            return data[0][0];
        }
        Complex det = new Complex(0, 0);
        for (int j = 0; j < cols; j++) {
            Complex term = data[0][j].multiply(minor(0, j).determinant());
            if (j % 2 == 0) {
                det = det.add(term);
            } else {
                det = det.subtract(term);
            }
        }
        return det;
    }

    public ComplexMatrix inverse() {
        Complex det = determinant();
        if (det.isZero()) {
            throw new ArithmeticException("Определитель равен нулю, обратной матрицы нет");
        }
        ComplexMatrix result = new ComplexMatrix(rows, cols);
        if (rows == 1) {
            result.data[0][0] = new Complex(1, 0).divide(data[0][0]);
            return result;
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Complex cofactor = minor(i, j).determinant();
                if ((i + j) % 2 == 1) {
                    cofactor = new Complex(0, 0).subtract(cofactor);
                }
                result.data[j][i] = cofactor.divide(det);
            }
        }
        return result;
    }

    public ComplexMatrix divide(ComplexMatrix other) {
        if (other.rows != other.cols) {
            throw new IllegalArgumentException("Делить можно только на квадратную матрицу");
        }
        return multiply(other.inverse());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sb.append(data[i][j]).append("\t");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
