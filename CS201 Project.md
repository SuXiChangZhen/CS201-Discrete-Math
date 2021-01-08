# CS201 Project

## Solve linear congruence equation system with one unknown

**SID: 11812714**      **Name: 张柯远**

---

### 1. linear congruence equation system

​    Let $f_j(x)$ be the polynomial with integral coefficient, then a group of congruence equations with variable x

$$f_j(x) \equiv 0(\mod m_j), \qquad 1 \leq j \leq k  \tag{1}$$

is called **congruence equation system**. If integer c satisfies 

$$f_j(c) \equiv 0(\mod m_j), \qquad 1 \leq j \leq k  \tag{2}$$ 

then c is called the solution to the congruence equation system (1). The next theorem shows the property of  solution to the congruence equation system.

**Theorem 1.1** If c is the solution to the congruence equation system(1), then the congruence class

​	$$ c \mod m, m = [m_1, ..., m_k]$$

any integer in the congruence class is also solution to (1).

**proof:**  

$\forall j, (1 \leq j \leq k)$, let c be the solution.  then $f_j(c) \equiv 0(\mod m_j).$ Because m = $[m_1, ..., m_k]$, then $m_j | m$, $f_j(mk + c) = xm + f_j(c) \equiv f_j(c) \equiv 0(\mod m_j)$. So $f_j(mk + c)$ is also a solution.​

So, in the most case, these solution are seemed to be one solution. Only solutions which are not congruence modulo m are called different solution. 

### 2. The Chinese Remainder Theorem

  First, we will discuss the famous Chinese Remainder Theorem:

**Theorem 2.1(The Chinese Remainder Theorem)**	Let $m_1, ..., m_k$ be pairwise relatively prime positive integers, and $\alpha_1, ..., \alpha_k$ arbitrary integers. Then the system

$$ x \equiv \alpha_j(\mod m_j), \qquad 1 \leq j \leq k\tag{3}$$

has a unique solution. In fact, let 

$$ c = M_1M_1^{-1}\alpha_1 + ... + M_kM_k^{-1}\alpha_k, \tag{4}$$

where $m = m_1...m_k$, $m = m_jM_j (1 \leq j \leq k)$, $M_j^{-1}$ is an integer satisfies

$$ M_jM_j^{-1} \equiv 1 (\mod m_j), \qquad 1 \leq j \leq k \tag{5} $$

i.e. $M_j^{-1}$ is the Inverse of $M_j$ modulo m. And the solution to the (3) is

$$ x \equiv c (\mod m) \tag{6}$$

In the class, the proof to this theorem shows why c is the solution to the system, but does not show why c has the form of (4). So here introduce another proof

**proof:** First, if $x_0$ is the solution to (3), and $x_0^{'}$  is the solution to the system

$$ x \equiv a_j^{'}(\mod m_j), \qquad 1 \leq j \leq k$$

Then, $x_0 + x_0^{'}$ must be the solution to the system 

$$ x \equiv a_j + a_j^{'}(\mod m_j), \qquad 1 \leq j \leq k$$

So, we can construct the solution to the system (3) with the similar method. Let 
$$
a_j^{(i)} = 
\begin{cases}
a_j, \quad i = j \\
0, \quad i \neq j
\end{cases}\tag{7}
$$
for every i$(1\leq i \leq k)$, consider the system
$$
x \equiv a_j^{i}(\mod m_j), \qquad 1 \leq j \leq k \tag{8}
$$
Notice that when $j \neq i, a_j^{(i)} = 0$, so 
$$
x \equiv 0 (\mod M_i)
$$
i.e.
$$
x = M_iy \tag{9
}
$$
substitute  into $i_{th}$ equation
$$
M_iy\equiv a_i(\mod m_i)
$$
According to the property of inverse:
$$
y\equiv M_i^{-1}a_i(\mod m_i)
$$
i.e.
$$
M_iy \equiv M_iM_i^{-1}a_i(\mod m)
$$
With (9)
$$
x \equiv M_iM_i^{-1}a_i(\mod m)
$$
So $M_iM_i^{-1}a_i$ is the solution to the system (8). Then
$$
a_j^{(1)} + a_j^{(2)} + ... + a_j^{(k)} = a_j
$$
so $M_1M_1^{-1}a_1+ ... + M_kM_k^{-1}a_k$ is the solution to the system (3). And in the course, the unique has been proved. $\square$

---

Second, the code implement of Chinese Remain Theorem(CRT).

The algorithm is simple and the key of this algorithm is calculate $M_i^{-1}$.  Similar with extended Euclidean algorithm, here introduces an ancient algorithm: **大衍求一术**

 **大衍求一术** was found in the "数书九章" written by 秦九韶. The original words:

![image-20200607143737005](C:\Users\ibm\AppData\Roaming\Typora\typora-user-images\image-20200607143737005.png)

There is no picture to show the process of this algorithm in the book. The algorithm was separated into two parts: left and right. Left is to calculate the inverse, and right is to do the Euclidean algorithm until the remain is 1.

```java 
// The sample of 大衍求一术    
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
```

Notice that 大衍求一术 can only apply to the equation that a and m are prime. Since in the case of CRT, this is always true. So we can use 大衍求一术 to calculate the inverse.

```java
import java.util.Scanner;

public class CRT {
    public static int[] a;
    public static int[] m;
    public static long[] M;
    public static int n;
    public static long mod = 1;
    public static void main(String[] args) {
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
        // calcualte M_i
        for (int i = 0; i < n; i++) {
            M[i] = mod / m[i];
        }
        long result = 0;
        // calculate inverse modulo m
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
```

---

example: 

solve the congruence equation system:
$$
\begin{cases}
x \equiv 2(\mod 3) \\
x \equiv 3(\mod 5) \\
x \equiv 2(\mod 7)
\end{cases}
$$


![image-20200607153305157](C:\Users\ibm\AppData\Roaming\Typora\typora-user-images\image-20200607153305157.png)

### 2. Back Substitution

Back substitution is also an algorithm to solve the systems of linear congruences.  The main idea of back substitution is keep solving 2 equations, use new variable to replace x until all the equations are solved. 

For example, use back substitution to solve system of linear congruences:
$$
\begin{cases}
x \equiv 2(\mod 3) \\
x \equiv 3(\mod 5) \\
x \equiv 2(\mod 7)
\end{cases}
$$
first, let x = 3k +2, then $3k + 2 \equiv 3(\mod 5)$, solve the equation, $k \equiv 2(\mod 5)$.

 then x = 3(5m+2)+2 = 15m + 8 and $15m + 8 \equiv 2(\mod 7)$, solve the equation, $m \equiv 1 (\mod 7)$, so x = 15(7l + 1) + 8 = 105l + 23.

In this process, the main problem is to solve the equation
$$
ax \equiv b(\mod m)
$$
To solve this equation, it is same to solve the following Diophantine Equation:
$$
ax  = my + b
$$
and $\frac{my + b}{a} = k$, $my + b = ak$, it is same to solve the congruence equation:
$$
my \equiv -b (\mod a)
$$
and if y has solution, then x = $\frac{my + b}{a}$. So a recurrence algorithm can be implemented:

```java
    public static int solve_ce(int a, int b, int mod) {
        if (a == 1)
            return b;
        else {
            return (mod * solve_ce(mod % a, - b % a, a) + b) / a;
        }
    }
```

With this algorithm, back substitution can be implemented:

```java
import java.util.Scanner;

public class BackSubstitution {
    public static int n;
    public static int[] a;
    public static int[] m;
    public static void main(String[] args) {
        //read the coefficient
        Scanner input = new Scanner(System.in);
        n = input.nextInt();
        a = new int[n];
        m = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = input.nextInt();
            m[i] = input.nextInt();
        }
        // c2 is the solution to the system
        int c1 = m[0];
        int c2 = a[0];
        for (int i = 1; i < n; i++) {
            int temp = solve_ce(c1 % m[i], (a[i] - c2) % m[i] , m[i]);
            temp = (temp < 0) ? temp + m[i] : temp;
            c2 += c1 * temp;
            c1 *= m[i];
        }
        System.out.println(c2);
    }

    public static int solve_ce(int a, int b, int mod) {
        if (a == 1)
            return b;
        else {
            return (mod * solve_ce(mod % a, - b % a, a) + b) / a;
        }
    }
}
```

---

Example: 

![image-20200607161144886](C:\Users\ibm\AppData\Roaming\Typora\typora-user-images\image-20200607161144886.png)

### 3. extend system congruences

The CRT has its limit that all the moduli are pairwise relatively prime. Next we will discuss the system that moduli are not pairwise relatively prime. The following theorem gives the relation between these two kinds of system.

**Theorem 3.1** let m = $[m_1, ..., m_k]$, there definitely exists a group of integer $m_1^{'}, ..., m_k^{'}$ satisfies: $m_j^{'}|m_j(1 \leq j \leq k)$, $m_1^{'}, ..., m_k^{'}$ are pairwise relatively prime and $m = m_1^{'}...m_k^{'}$. 

If the system $x \equiv a_j(\mod m_j)(1 \leq j \leq k)$ has solution, then its solution is same with the solution to the system $x \equiv a_j(\mod m_j^{'})$.

A brief constructive proof can be given to this theorem:  prime factor m, let m = $p_1^{a_1}...p_r^{a_r}$. first, for every $m_j$, keep the power of prime in  $m_j$ if its power is same with that of m to get $m_j^{'}$. If p was kept in different $m_i$, then only to keep one and divide out others. In this way, $m_j^{'}(1\leq j \leq k)$ satisfies the requirements. 

For example, solve the equation:
$$
\begin{cases}
x \equiv 8 (\mod 15)\\
x \equiv 2 (\mod 21) 
\end{cases}
$$
$[15, 21] = 105 = 3*5*7$ , $15 = 3 * 5$, $21 = 3 * 7$, keep the 5 in the 15, keep the 7 in the 21, and 15 and 21 both have 3, so only to keep the 3 in one of them. So the equation is equal to 
$$
\begin{cases}
x \equiv 8 (\mod 15)\\
x \equiv 2 (\mod 7) \\ 
\end{cases}
$$
solve the equation by CRT, the solution is 23. 

**code implement**: Use back substitution can solve the system that modulo $m_1, ..., m_k$ are not pairwise relatively prime. The only little difference with the original algorithm is the `solve_ce` method:

```java
    public static int solve_ce(int a, int b, int mod) {
        if (a == 1)
            return b;
        else if(a == 0 && b == 0) {
            // only to return one value
            return 0;
        }
        else {
            return (mod * solve_ce(mod % a, - b % a, a) + b) / a;
        }
    }
```

if $m_i, m_j$ are not pairwise relatively prime, the solution to the equation
$$
ax\equiv b(\mod m)
$$
may be more than 1 when a = b = 0. Since the solution to the system is unique, so only to return one value in the `solve_ce()`

---

example(15 and 21 are not relatively prime):

![image-20200607164324849](C:\Users\ibm\AppData\Roaming\Typora\typora-user-images\image-20200607164324849.png)

**no solution?**  CRT only claim that system with relatively prime moduli always have solution, so if the moduli are not relatively prime, the system may have no solution. 

  In this case, back substitution is still fine to work. No solution to the system is equal to no solution to the equation $ax \equiv b(\mod m)$, so only to modify the `solve_ce()`function.

```java
    public static int solve_ce(int a, int b, int mod) {
        if (a == 1)
            return b;
        else if(a == 0) {
            if (b == 0)
                return 0; // more than one solution
            else { // no solution
                System.out.println("No solution");
                System.exit(1);
                return 0;
            }
        }
        else {
            return (mod * solve_ce(mod % a, - b % a, a) + b) / a;
        }
    }
```

---

test case: 

![image-20200607172607262](C:\Users\ibm\AppData\Roaming\Typora\typora-user-images\image-20200607172607262.png)

### 4. Least Common Multiple of more than 2 numbers

Although we have proved that the solution is unique in the congruence class of m($m = [m_1,...,m_k]$), but the algorithm of CRT and back substitution didn't give the answer to how to calculate m. So in this part we will discuss how to find the LCM of 3 or more numbers.

**Theorem 4.1** $[a_1, ...,a_n]$ = $M/(M/a_1, ..., M/a_n)$, $M = a_1...a_n$

the proof is simple. prime factor $a_1, ..., a_n$, $[a_1, ..., a_n]$, $(M/a_1, ..., M/a_n)$ and $M$,  the relation is:

$\forall p_i$, $p_i$ is a prime factor $M$, suppose $r_1, ..., r_n$ are the power of $p_i$ in the $a_1, ..., a_n$. Then the power in M is equal to $r_1 + ... + r_n$, the power of $p_i$  in $[a_1, ..., a_n]$ is $max(r_i)$ and in $(M/a_1, ..., M/a_n)$ is $min((r_1 + ... + r_n) - r_i) = r_1 + ... + r_n - max(r_i)$, so the sum of the power of $p_i$ is equal to M. So
$$
[a_1, ..., a_n]*(M/a_1, ..., M/a_n) = M \qquad \square
$$
This theorem shows the relation between LCM of n numbers and GCD of n numbers. We can use Euclidean Algorithm to solve GCD:

first, select the smallest number $M/a_i$, use this to mod others, if all the other are 0, GCD = $M/a_i$. Otherwise, keep doing the algorithm.

---

**code implement**

```java
public static int LCM() {
    boolean flag;
    int index = 0;
    int min = M[0];
    while(true) {
        flag = true;
        for (int i = 0; i < n; i++) {
            // find the minimum
            if (min > M[i] && M[i] != 0) {
                min = M[i];
                index = i;
            }
        }
        // mod other number
        for (int i = 0; i < n; i++) {
            if (i != index) {
                M[i] %= min;
                if (M[i] != 0)
                    flag = false;
            }
        }
        // found the gcd
        if (flag) {
            // return the LCM
            return total / min;
        }
    }
}
```

---

test case:

![image-20200607184640154](C:\Users\ibm\AppData\Roaming\Typora\typora-user-images\image-20200607184640154.png)

![image-20200607184655928](C:\Users\ibm\AppData\Roaming\Typora\typora-user-images\image-20200607184655928.png)

### 5. conclusion

This project discusses the solution to the system of congruences which has the form:
$$
x \equiv a_j(\mod m_j) \qquad 1 \leq j\leq k 
$$
And use CRT and Back Substitution to implement the algorithm. During the implementation,  use 大衍求一术, extend Euclidean Algorithm and LCM algorithm.

### 6. final code

1. CRT

   ```java
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
   ```

   

2. Back Substitution

   ```java
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
   ```

### 7. References

1. Slides lecture 08
2. 《初等数论》潘承洞，潘承彪。（第三版）北京大学出版社
3. 王翼勋.从“大衍术”到“大衍求一术”[J].苏州大学学报(自然科学),1990(01):16-18.