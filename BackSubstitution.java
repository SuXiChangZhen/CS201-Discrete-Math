import java.util.Scanner;

public class BackSubstitution {
    public static int n;
    public static int[] a;
    public static int[] m;
    public static int[] M;
    public static int total = 1;
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        n = input.nextInt();
        a = new int[n];
        m = new int[n];
        M = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = input.nextInt();
            m[i] = input.nextInt();
            total *= m[i];
        }
        for (int i = 0; i < n; i++) {
            M[i] = total / m[i];
        }
        int c1 = m[0];
        int c2 = a[0];
        for (int i = 1; i < n; i++) {
            int temp = solve_ce(c1 % m[i], (a[i] - c2) % m[i] , m[i]);
            temp = (temp < 0) ? temp + m[i] : temp;
            c2 += c1 * temp;
            c1 *= m[i];
        }
        int lcm = LCM();
        System.out.printf("The solution is: %d + %dk\n", c2, lcm);
    }

    public static int solve_ce(int a, int b, int mod) {
        if (a == 1)
            return b;
        else if(a == 0) {
            if (b == 0)
                return 0;
            else {
                System.out.println("No solution");
                System.exit(1);
                return 0;
            }
        }
        else {
            return (mod * solve_ce(mod % a, - b % a, a) + b) / a;
        }
    }
    public static int LCM() {
        boolean flag;
        int index = 0;
        int min = M[0];
        while(true) {
            flag = true;
            for (int i = 0; i < n; i++) {
                if (min > M[i] && M[i] != 0) {
                    min = M[i];
                    index = i;
                }
            }
            for (int i = 0; i < n; i++) {
                if (i != index) {
                    M[i] %= min;
                    if (M[i] != 0)
                        flag = false;
                }
            }
            if (flag) {
                return total / min;
            }
        }
    }
}