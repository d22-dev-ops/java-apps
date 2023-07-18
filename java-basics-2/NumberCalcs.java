public class NumberCalcs {
    public static void main(String[] args) {
        // Declaration of variables
        int num1 = 10;
        int num2 = 20;
        
        // Arithmetic operator
        int sum = num1 + num2;
        System.out.println("The sum is: " + sum);
        
        // Relational operator
        if(num1 > num2) {
            System.out.println(num1 + " is greater than " + num2);
        } else {
            System.out.println(num1 + " is less than " + num2);
        }

        // Arithmetic operators
        int a = 9, b = 4;

        System.out.println("a+b = " + (a + b)); // Addition
        System.out.println("a-b = " + (a - b)); // Subtraction
        System.out.println("a*b = " + (a * b)); // Multiplication
        System.out.println("a/b = " + (a / b)); // Division
        System.out.println("a%b = " + (a % b)); // Modulus (remainder of a/b)


        // Relational operators

        System.out.println("a == b : " + (a == b)); // Equal to
        System.out.println("a != b : " + (a != b)); // Not equal to
        System.out.println("a > b : " + (a > b)); // Greater than
        System.out.println("a < b : " + (a < b)); // Less than
        System.out.println("b >= a : " + (b >= a)); // Greater than or equal to
        System.out.println("b <= a : " + (b <= a)); // Less than or equal to

        // Bitwise Operators

        System.out.println("a&b = " + (a & b)); // Bitwise AND
        System.out.println("a|b = " + (a | b)); // Bitwise OR
        System.out.println("a^b = " + (a ^ b)); // Bitwise XOR
        System.out.println("~a = " + (~a)); // Bitwise Complement
    

        // Logical Operators

        boolean c = true, d = false;

        System.out.println("c && d: " + (c&&d)); // Logical AND
        System.out.println("c || d: " + (c||d)); // Logical OR
        System.out.println("!c: " + !c); // Logical NOT

        // Assignment Operators

        a += 3;  // It means a = a + 3;
        System.out.println("a = " + a);

    }
}

// javac NumberCalcs.java
// java NumberCalcs