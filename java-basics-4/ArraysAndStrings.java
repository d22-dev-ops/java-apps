public class ArraysAndStrings {
    public static void main(String[] args) {
    int[] myArray = new int[5]; // Declaration and creation

    // Initialization
    myArray[0] = 10;
    myArray[1] = 20;
    myArray[2] = 30;
    myArray[3] = 40;
    myArray[4] = 50;

    System.out.println(myArray[4]); // Outputs: 30

    // Or you can declare, create, and initialize in one line
    int[] myArray2 = {10, 20, 30, 40, 50};

    System.out.println(myArray2[2]); // Outputs: 30

    String str = "Hello, World!";
        
    System.out.println(str.length());  // Outputs: 13
    System.out.println(str.charAt(4));  // Outputs: 'o'
    System.out.println(str.substring(0, 5));  // Outputs: "Hello"
    System.out.println(str.contains("World"));  // Outputs: true
    System.out.println(str.indexOf("World"));  // Outputs: 7
    System.out.println(str.equals("Hello, World!"));  // Outputs: true

    String[] words = {"Hello", "World", "This", "is", "Java"};
    
    for(String word : words) {
        System.out.println("Word: " + word);
        System.out.println("Length of word: " + word.length());
        if(word.contains("a")) {
            System.out.println("The word contains the letter 'a'");
        }
        System.out.println();
    }

    }
}