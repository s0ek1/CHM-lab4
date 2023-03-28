package main;
import java.util.Arrays;
public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }
    private void run() {
        double[][] A = {{28, 3.8, -32},
                        {2.5, -28, 3.3},
                        {6.5, -7.1, 48}};
        double[] b = {45, 71, 63};
        double eps = 1e-5;
        methodSimpleIter(A,b,eps);
        methodGaussZeydela(A,b,eps);
    }
    private void methodSimpleIter(double[][] A, double[] b, double eps) {
        System.out.println("Метод простої ітерації (Якобі):\n");
        int n = A.length;
        double[] x = new double[n];
        double[] preX;
        int iter = 0;
        double residNorm = Double.MAX_VALUE;
        while (residNorm > eps) {
            preX = x.clone();
            for (int i = 0; i < n; i++) {
                double sum = 0.0;
                for (int j = 0; j < n; j++) {
                    if (i != j) sum += A[i][j] * preX[j];
                }
                x[i] = (b[i] - sum) / A[i][i];
            }
            double[] resid = new double[n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) resid[i] += A[i][j] * x[j];
                resid[i] -= b[i];
            }
            residNorm = 0.0;
            for (int i = 0; i < n; i++) residNorm += resid[i] * resid[i];
            residNorm = Math.sqrt(residNorm);
            iter++;
        }
        for (int i = 0; i < x.length; i++) System.out.println("\tx["+i+"] = "+x[i]);
        System.out.println("\tКоличество итераций: "+ iter+"\n");
    }
        private void methodGaussZeydela(double[][] a, double[] b, double eps) {
            System.out.println("Метод Гауса-Зейделя:\n");
            int n = a.length;
            double[] x = new double[n];
            double[] xNew = new double[n];
            int iter = 0;
            double residNorm = Double.MAX_VALUE;
            while (residNorm > eps) {
                for (int i = 0; i < n; i++) {
                    xNew[i] = b[i];
                    for (int j = 0; j < i; j++) xNew [i] -= a[i][j] * xNew[j];
                    for (int j = i + 1; j < n; j++) xNew[i] -= a[i][j] * x[j];
                    xNew[i] /= a[i][i];
                }
                x = Arrays.copyOf(xNew, n);
                iter++;
                double[] resid = new double[n];
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) resid[i] += a[i][j] * x[j];
                    resid[i] -= b[i];
                }
                residNorm = 0.0;
                for (int i = 0; i < n; i++) residNorm += resid[i] * resid[i];
                residNorm = Math.sqrt(residNorm);
            }
            for (int i = 0; i < x.length; i++) System.out.println("\tx["+i+"] = "+x[i]);
            System.out.println("\tКоличество итераций: "+ iter+"\n");
        }
}