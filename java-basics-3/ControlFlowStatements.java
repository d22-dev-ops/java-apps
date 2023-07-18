public class ControlFlowStatements {
    public static void main(String[] args) {

// if / else
int x = 10;
if(x > 0) {
    System.out.println("x is positive");
} else {
    System.out.println("x is non-positive");
}

// switch
int day = 3;
switch (day) {
    case 1:
        System.out.println("Monday");
        break;
    case 2:
        System.out.println("Tuesday");
        break;
    case 3:
        System.out.println("Wednesday");
        break;
    // ...
    default:
        System.out.println("Invalid day");
}

// for
for(int q = 0; q < 5; q++) {
    System.out.println(q);
}

// while
int u = 0;
while(u < 5) {
    System.out.println(u);
    u++;
}


// so-while
int p = 0;
do {
    System.out.println(p);
    p++;
} while(p < 5);

// combined
        for(int i = 0; i < 5; i++) {
            if(i % 2 == 0) {
                System.out.println(i + " is even");
            } else {
                System.out.println(i + " is odd");
            }
        }
        
        int i = 0;
        while(i < 3) {
            System.out.println("While loop count: " + i);
            i++;
        }
        
        i = 0;
        do {
            System.out.println("Do-while loop count: " + i);
            i++;
        } while(i < 3);

    }
}