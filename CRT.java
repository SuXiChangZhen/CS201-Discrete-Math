import java.util.Scanner;

public class CRT {
    public static int[] a;
    public static int[] m;
    public static long[] M;
    public static int n;
    public static long mod = 1;
    public static void main(String[] args) {
        System.out.println("Enter the number of equations:");
        Scanner input = new Scanner(System.in);
        n = input.nextInt();
        a = new int[n];
        m = new int[n];
        M = new long[n];
        for (int i = 0; i < n; i++) {
            a[i] = input.nextInt();
            m[i] = input.nextInt();
            mod *= m[i];
        }
        for (int i = 0; i < n; i++) {
            M[i] = mod / m[i];
        }
        long result = 0;
        for (int i = 0; i < n; i++) {
            result += M[i] * inverse(M[i], m[i]) * a[i];
            result %= mod;
        }
        System.out.print(result);


    }
    public static long inverse(long num, long mod) {
        long k0 = 0;
        long k1 = 1;
        long result = 0;
        long r0 = mod;
        long r1 = num;
        long r2 = 0;
        long q = 0;
        if (num == 1)
            return 1;
        while (r2 != 1) {
            q = r0 / r1;
            r2 = r0 % r1;
            r0 = r1;
            r1 = r2;
            result = k0 - q * k1;
            k0 = k1;
            k1 = result;
        }
        if (result > 0)
            return result;
        else
            return result + mod;
    }
}
