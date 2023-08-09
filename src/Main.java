import java.io.FileWriter;   //https://www.w3schools.com/java/java_files.asp
import java.io.IOException;  //https://www.javatpoint.com/exception-handling-in-java
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * I declare that my work contains no examples of misconduct, such as plagiarism, or collusion.
 * Any code taken from other sources is referenced within my code solution.
 * @author Kavindu sandaruwan. Student ID 20221150 (w1999503).
 */
public class Main {
    //Declaring Attributes.
    public static String[] queueOne = new String[2];       //queue number one -> length of 2.
    public static String[] queueTwo = new String[3];       //queue number two -> length of 3.
    public static String[] queueThree = new String[5];     //queue number three -> length of 5.
    public static String[][] queues = {queueOne, queueTwo, queueThree};   // 2d array for contain all the queues.
    public static  int burgersInStock = 50;          // Starting burger amount.

    public static void main(String[] args) {
        System.out.println("\tFoodies Fave Food center" + "\n" + "_".repeat(32) + "\n");
        boolean shouldContinue = true;        // Boolean value to control the main loop.
        Scanner scanner = new Scanner(System.in);        // Scanner object for taking user inputs.

        while (shouldContinue){
            printMainMenu();
            System.out.print("\n>>>>> Enter your option: ");
            String userOption = scanner.nextLine().toUpperCase();

            if (userOption.equals("999") || userOption.equals("EXT")){
                shouldContinue = false;
                System.out.println("Thank you for using our application....");
            }else{
                switch (userOption) {   //https://docs.oracle.com/en/java/javase/17/language/switch-expressions
                    case "100", "VFQ" -> viewAllQueues();
                    case "101", "VEQ" -> viewEmptyQueues();
                    case "102", "ACQ" -> addCustomerToQueue();
                    case "103", "RCQ" -> removeCustomerFromQueue();
                    case "104", "PCQ" -> removeServedCustomer();
                    case "105", "VCS" -> viewCustomersSortedAlphabeticalOrder();
                    case "106", "SPD" -> storeProgramDataIntoFile();
                    case "107", "LPD" -> loadDataFromTxtFile();
                    case "108", "STK" -> viewRemainingBurgers();
                    case "109", "AFS" -> addBurgerToStock();
                    default -> {
                        System.out.println(">>>>> Invalid input. Please check the main menu" +
                                " and try again.\n");
                        continue;
                    }
                }
                System.out.println(">>>>> Press Enter key to back to the main menu:");
                scanner.nextLine();
                System.out.println("_".repeat(60) + "\n");
            }
        }
    }

    /**
     *This method prints the main menu of the program.
     */
    private static void printMainMenu(){
        System.out.println("""
                100 or VFQ: View all Queues.
                101 or VEQ: View all Empty Queues.
                102 or ACQ: Add customer to a Queue.
                103 or RCQ: Remove a customer from a Queue.
                104 or PCQ: Remove a served customer.
                105 or VCS: View Customers Sorted in alphabetical order.
                106 or SPD: Store Program Data into file.
                107 or LPD: Load Program Data from file.
                108 or STK: View Remaining burgers Stock.
                109 or AFS: Add burgers to Stock.
                999 or EXT: Exit the Program.""");
    }

    /**
     *This method for print all queues.
     * This method used nested for loops for print line by line.
     */
    private static void viewAllQueues(){
        System.out.println("""
               \n *****************
                *    Cashiers   *
                *****************""");
        for (int i = 0; i <= queues[2].length; i++){
            for (var queue : queues){
                try {
                    if (queue[i] == null) System.out.print("   X  ");
                    else System.out.print("   0  ");
                } catch (ArrayIndexOutOfBoundsException aioe){  // If there is no index in array. It prints space.
                    System.out.print("      ");
                }
            }
            System.out.println();
        }
    }
    //https://www.geeksforgeeks.org/difference-between-for-loop-and-enhanced-for-loop-in-java/

    /**
     *This method is for print all the available(empty) positions in each queue.
     */
    private static void viewEmptyQueues(){
        int queueNumber = 1;    // To track which queue we are currently in.
        for (var queue : queues){
            System.out.print("Queue " + queueNumber + ":- ");
            for (int i = 0; i < queue.length; i++){
                if (queue[i] == null) System.out.print(i+1 + ", ");
            }
            queueNumber++;
            System.out.println("\n");
        }
    }

    /**
     *This method for add new customer to the corresponding queue.
     * If there is mo burgers in the stock. It won't add customer until stock updates.
     */
    private static void addCustomerToQueue(){
        if (burgersInStock == 0) {
            System.out.println("""
                    Burger amount is 0......
                    Please add burgers to stock
                    """);
        } else {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.print("\nPlease Enter Queue: ");
                int queue = scanner.nextInt();
                System.out.print("Please Enter Your First Name: ");
                String customerName = scanner.next().toLowerCase();

                switch (queue) {
                    case 1 -> confirmQueuePosition(queues[0], customerName, 1);
                    case 2 -> confirmQueuePosition(queues[1], customerName, 2);
                    case 3 -> confirmQueuePosition(queues[2], customerName, 3);
                    default -> {
                        System.out.println(">>>>> Invalid Queue Detected. Please enter a number" +
                                " between 1 - 3.");
                        addCustomerToQueue();
                    }
                }
            } catch (InputMismatchException ime) {
                System.out.println(">>>>> Characters are not allowed.");
                addCustomerToQueue();    //If inputs are invalid run this method again.
            }
        }
    }

    /**
     *This method is for When a customer adds to the queue, it adds if there are empty slots.
     *Otherwise, a message will be displayed stating that there is no space
     * @param queue Which Queue to be added.
     * @param userName Customer name
     * @param queueNumber To track which queue customer in.
     */
    private static void confirmQueuePosition(String[] queue, String userName, int queueNumber){
        for (int i = 1; i <= queue.length; i++){
            if (queue[i-1] == null){
                queue[i-1] = userName;
                burgersInStock -= 5;
                System.out.println(">>>>> " + userName.substring(0,1).toUpperCase() +
                        userName.substring(1) + " you are in " +
                        i + " position in " + "queue number " + queueNumber + ".\n");
                return;
            }
        }
        System.out.println(">>>>> Sorry " + userName.substring(0,1).toUpperCase() +
                userName.substring(1) + ". No positions available in this queue.\n");
    }

    /**
     *This method is for remove customers from specific location.
     */
    private static void removeCustomerFromQueue(){
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("\nPlease Enter Queue Number: ");
            int queueNumber = scanner.nextInt();
            System.out.print("Please Enter Position of the queue: ");
            int position = scanner.nextInt();
            switch (queueNumber) {
                case 1 -> confirmRemoveFromQueue(queues[0], position);
                case 2 -> confirmRemoveFromQueue(queues[1], position);
                case 3 -> confirmRemoveFromQueue(queues[2], position);
                default -> {
                    System.out.println(">>>>> Invalid Queue Detected.");
                    removeCustomerFromQueue();
                }
            }
        } catch (InputMismatchException ime){
            System.out.println(">>>>> Characters are not allowed.");
            removeCustomerFromQueue();
        }
    }

    /**
     *This method is for confirm remove customer from the specific location.
     * @param queue The queue that the person removing is in.
     * @param position The position that the person removing is in
     * After remove customer. Other customers in the queue shift forward by one position.
     */
    private static void confirmRemoveFromQueue(String[] queue, int position){
        try {
            System.out.println(">>>>> Successfully Removed " + queue[position - 1] +
                    " in " + position + " position.\nAll the customers in the back shift forward by one.\n");
            burgersInStock += 5;
            queue[position - 1] = null;
        } catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Invalid Position.");
            removeCustomerFromQueue();
        }

        // As the position was empty, the customers behind shift forward.
        for (int i = position-1; i < queue.length; i++){
            try {
                queue[i] = queue[i + 1];
            } catch (ArrayIndexOutOfBoundsException ignored){
                queue[i] = null;
            }
        }
    }

    /**
     *This method is for remove all served customers
     * After remove customers. Other customers shift forward by one position.
     */
    private static void removeServedCustomer(){
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Enter the queue number: ");
            int queueNumber = scanner.nextInt();

            switch (queueNumber) {
                case 1 -> confirmRemoveServedCustomer(queueOne);
                case 2 -> confirmRemoveServedCustomer(queueTwo);
                case 3 -> confirmRemoveServedCustomer(queueThree);
                default -> System.out.println("Invalid queue number.\n");
            }
        } catch (InputMismatchException e){
            System.out.println("Characters are not allowed.\n");
        }
    }

    /**
     * @param queue The queue which remove served customer.
     * This method shift value to forward by one and make last element null.
     */
    private static void confirmRemoveServedCustomer(String[] queue){
        for(int i = 0; i < queue.length; i++){
            try {
                queue[i] = queue[i + 1];
            }catch (ArrayIndexOutOfBoundsException e){
                queue[queue.length - 1] = null;
            }
        }
        System.out.println("""
                >>>> Successfully Removed Served Customer.
                All the customers in the back shift forward by one.
                """);
    }

    /**
     *This method is for sort customers name in Alphabetical Order.
     * This method use bubble sort method to sort customers.
     */
    private static void viewCustomersSortedAlphabeticalOrder(){
        int currentQueueNumber = 1;
        int bottom;
        boolean isSorted = true;
        String temp;

        for(String[] queue : queues){
            bottom = queue.length - 2;
            while(isSorted){
                isSorted = false;
                for(int i = 0; i <= bottom; i++){
                    if(queue[i+1] != null){
                        if(queue[i].compareTo(queue[i+1]) > 0){
                            temp = queue[i];
                            queue[i] = queue[i+1];
                            queue[i+1] = temp;
                            isSorted = true;
                        }
                    }
                }
                bottom--;
            }

            System.out.print("Queue : " + currentQueueNumber + "- ");
            currentQueueNumber++;
            for(String name : queue) {
                if(name != null) System.out.print(name + ", ");
            }
            System.out.println();
        }
        System.out.println("\n");
    }

    /**
     *This method is for store customer names, queue and position number to txt file.
     */
    private static void storeProgramDataIntoFile(){
        int currentQueue = 1;
        try {
            FileWriter myFile = new FileWriter("CustomerData.txt", true);
            for (var queue : queues) {
                for (int position = 1; position <= queue.length; position++) {
                    if (queue[position-1] != null) {
                        myFile.write(">>>>> Username: " + queue[position - 1] +
                                ". Queue number: " + currentQueue + "\n");
                    }
                }
                myFile.write("-".repeat(40) + "\n");
                currentQueue++;
            }
            myFile.close();
        } catch (IOException ioe){
            System.out.println(">>>>> An error occurred.");
            ioe.printStackTrace();     //https://www.w3schools.com/java/java_files.asp
        }
        System.out.println("Successfully saved data into txt file.\n");
    }

    /**
     *This method is for print loaded data in text file.
     */
    private static void loadDataFromTxtFile(){
        try {
            File myFile = new File("CustomerData.txt");
            Scanner myReader = new Scanner(myFile);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println(">>>> An error occurred.");
            e.printStackTrace();     //https://www.w3schools.com/java/java_files.asp
        }
    }

    /**
     *This method is for view remaining burger amount in the stock.
     * When burger amount reached 10 it will print warning message.
     */
    private static void viewRemainingBurgers(){
        if (burgersInStock <= 10) System.out.println("!! Warning !!");
        System.out.println(">>>>> There are " + burgersInStock + " remaining in the stock.\n");
    }

    /**
     *This method is for add five burgers in to stock.
     * But maximum burger amount is 50.
     */
    private static void addBurgerToStock(){
        if (burgersInStock >= 50) System.out.println("Maximum Burger amount reached.\n");
        else{burgersInStock += 5;
            System.out.println(">>>>> Five burgers added to the stock.\n");
        }
    }
}