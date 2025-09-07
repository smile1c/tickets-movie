import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

/**
 * PjMovie
 */

public class PjMovie {
    static int NumOfMovie = 6 ;
    static int NumOfSeat = 96;  // เพิ่มที่นั่ง
    static int MaxNumOfUser = 100;
    static String Seat[][] = new String[NumOfSeat][NumOfMovie]; 
    static Movie [] movies = new Movie[NumOfMovie];
    static Tickets [] tickets = new Tickets[MaxNumOfUser];
    static Scanner sc = new Scanner(System.in);
    static String UserName = "";
    static String UserPhoneNum = "";
    static String UserSeatNum = "";
    static int chkMovieSelect = 0;
    public static void main(String[] args) {
        AddMovie(); // method เพิ่มหนัง
        AddTestUser(); // method เพิ่มตัวอย่างตั๋ว
        // bubbleSortByUserId(); // method bubbleSort ตั๋ว
        
        while (true) {
          MovieMainMenu();
          System.out.print("Select : ");
          String chk = sc.nextLine();
            if (chk.equals("1") || chk.equalsIgnoreCase("Book movie tickets")) { //------------------------------------------- 1.Book movie tickets                
                ShowMovie();
                String Umovie = SelectMovie();
                if(Umovie != ""){ // Umovie หนังที่เลือก , UserName ชื่อ , UserPhoneNum เบอร์ , UserSeatNum เบอร์ที่นั่ง , ToDayDate วันที่
                    System.out.println("----> "+Umovie );
                    EnterUserDetail();
                    ShowAllSeat();
                    SelectSeat();

                    while (true) {
                        
                    
                    int number = Integer.parseInt(UserSeatNum);
                    if (number <= NumOfSeat && Seat[number-1][chkMovieSelect-1] != "full") {
                        Date currentDate = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                        String ToDayDate = sdf.format(currentDate);
                        CreateUserTicket(Umovie , UserName ,  UserPhoneNum , UserSeatNum , ToDayDate);

                        Seat[number-1][chkMovieSelect-1] = "full";
                        // System.out.print("|"+(number-1)+"|"+(chkMovieSelect-1)+"|"+Seat[number-1][chkMovieSelect-1]);             
                        System.out.println("\t!!! Successfully booked movie tickets !!!");break;
      
                    }
                    else {
                        System.out.println("Error : Seats are not available or not available...");
                        SelectSeat();
                    }
                    }             
                }               
            }
            else if (chk.equals("2") || chk.equalsIgnoreCase("Check your movie tickets")) {//--------------------------------- 2.Check your movie tickets
                bubbleSortByUserId(); // method bubbleSort ตั๋ว                
                System.out.print("Enter Ticket ID : ");
                String targetUserId = sc.nextLine();
                int index = binarySearch(tickets, targetUserId);

                // Print the result
                if (index >= 0) {
                    System.out.println("  Ticket found! "
                    +"\n---------------------------Your Ticket---------------------------"
                    +"\n\tName : " + tickets[index].getUserName()          
                    +"\n\tTicket ID : "+ tickets[index].getUserId()
                    +"\n\tDate : "+ tickets[index].getDate()
                    +"\n\tSeat Number : "+ tickets[index].getSeatNumber()
                    +"\n\tMovie : "+ tickets[index].getMovieName()
                    +"\n\tPhone Number : "+ tickets[index].getUserPhoneNumber()
                    +"\n-----------------------------------------------------------------"
                    );
                } else {
                    System.out.println("User not found.");
                }
            }
            else if(chk.equals("000") || chk.equalsIgnoreCase("debug")){
                // bubbleSortByUserId(); // method bubbleSort ตั๋ว
                for (int i = 0; i < MaxNumOfUser; i++) {
                    if (tickets[i] != null) {
                        System.err.println(tickets[i].toString());
                    }
                }
            }
            else if (chk.equals("3") || chk.equalsIgnoreCase("Exit")) { //---------------------------------------------------- 3.Exit
                System.out.println("-= [Exit] =-");break;
            }
            else {
                System.out.println("Error : Enter 1,2,3 please...");
            }
        }             
    }//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------End Main method
    
    public static void CreateUserTicket(String Umovie , String UserName , String UserPhoneNum , String UserSeatNum , String ToDayDate){
        for (int i = 0; i < MaxNumOfUser; i++) {
            if (tickets[i] == null) {
                int length = 10; // specify the length of the random string
                String randomString = generateRandomString(length);
                tickets[i] = new Tickets(randomString, UserName, UserPhoneNum, ToDayDate, Umovie, UserSeatNum, null);            
                    System.out.println(
                    "\n---------------------------Your Ticket---------------------------"
                    +"\n\tName : " + tickets[i].getUserName()          
                    +"\n\tTicket ID : "+ tickets[i].getUserId()
                    +"\n\tDate : "+ tickets[i].getDate()
                    +"\n\tSeat Number : "+ tickets[i].getSeatNumber()
                    +"\n\tMovie : "+ tickets[i].getMovieName()
                    +"\n\tPhone Number : "+ tickets[i].getUserPhoneNumber()
                    +"\n-----------------------------------------------------------------"
                    );
                break;
            }
        }
    }//---------------------------End CreateUserTicket method

    public static void SelectSeat(){
        do{try{
        System.out.print("Select seat number : ");
        UserSeatNum = sc.nextLine();
        int seatNum = Integer.parseInt(UserSeatNum);

        // If successful, it's a number
        System.out.println("Number " + seatNum + " is your seat.");
        }catch (NumberFormatException e) {
            // If parsing fails, it's not a number
            System.out.println("Please enter a valid number");
            UserSeatNum = "null"; // Set to null to continue the loop
        }
        }while(UserSeatNum == "null");

    }//---------------------------End SelectSeat method
    
    public static void EnterUserDetail(){
        System.out.print("Enter Name : ");
        UserName = sc.nextLine();
        System.out.print("Enter Phone Number : ");
        UserPhoneNum = sc.nextLine();
    }//---------------------------End EnterUserDetail method

    public static String SelectMovie(){
          System.out.print("Select : ");
          String chk = sc.nextLine();

          chkMovieSelect = Integer.parseInt(chk);
            int chkError=0;
            String UserSelect = "";
            for (int i = 0; i < NumOfMovie; i++) {
                if (chk.equalsIgnoreCase(movies[i].getName()) || chk.equalsIgnoreCase(movies[i].getMovieID()) ) {   
                    UserSelect = movies[i].getName();chkError++;              
                }

            }
            if (chkError==0) {
                System.out.println("Error : Enter 1-"+NumOfMovie+" please...");
            }
            return UserSelect;
    }//---------------------------End SelectMovie method


    
    public static void MovieMainMenu(){
        System.out.println("----------------------------Main Menu----------------------------\n"+
        "\t1.Book movie tickets\n\t2.Check your movie tickets\n\t3.Exit\n"+
        "-----------------------------------------------------------------");
    }//---------------------------End MovieMainMenu method


    public static void ShowAllSeat(){
        System.out.print("-----|--------------------[ Screen ]--------------------|-----\n\n    ");

        for (int i = 0; i < Seat.length; i++) {
            if (i != 0 && i%16 == 0) {
                System.out.println();
            }
            if (Seat[i][chkMovieSelect-1]==null) {

                System.out.print("["+(i+1)+"]");
            }
            else{
                System.out.print("[--]");
            }
        }
        System.out.println("\n\n\n---|Door|---------------------------------------------|Door|---");
    }//---------------------------End ShowAllSeat method
    
    public static void AddMovie() {
        movies[0] = new Movie("1", "The Matrix", "A sci-fi action film");
        movies[1] = new Movie("2", "Inception", "A mind-bending thriller");
        movies[2] = new Movie("3", "Jurassic Park", "An adventure in a dinosaur-filled island");
        movies[3] = new Movie("4", "The Shawshank Redemption", "A drama about hope and redemption");
        movies[4] = new Movie("5", "Pulp Fiction", "A nonlinear crime film");
        movies[5] = new Movie("6", "Pulp Fiction 2", "A nonlinear crime film");
    }//---------------------------End AddMovie method

        public static void AddTestUser() {
        tickets[0] = new Tickets("cfrpjgrgjd", "John Doe", "555-1234", "2023-01-01", "Movie A", "1", null);
        tickets[1] = new Tickets("aEubmo2u0I", "Tim Oneo", "555-1234", "2023-01-01", "Movie B", "12", null);
        tickets[2] = new Tickets("bDP7LK21J8", "Jack Doorsun", "555-1234", "2023-01-01", "Movie C", "29", null);
    }//---------------------------End AddTestUser method


        public static void ShowMovie() {
        System.out.println("---------------------------Select Movie--------------------------\n");
        for (int i = 0; i < NumOfMovie; i++) {
            String Mn = movies[i].getName();
            String Md = movies[i].getMovieDetails();
            System.out.println("\t"+(i+1)+"."+Mn+ "\n\t- Details : "+Md+"\n");
        }
        System.out.println("-----------------------------------------------------------------\n");
 
    }//---------------------------End ShowMovie method

        private static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder randomStringBuilder = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            randomStringBuilder.append(randomChar);
        }
        return randomStringBuilder.toString();
    }


    // Bubble sort by userId
    public static void bubbleSortByUserId() {
        int n = tickets.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (tickets[j] != null && tickets[j + 1] != null) {
                    if (tickets[j].getUserId().compareTo(tickets[j + 1].getUserId()) > 0) {
                        Tickets temp = tickets[j];
                        tickets[j] = tickets[j + 1];
                        tickets[j + 1] = temp;
                    }
                }
            }
        }
    }//---------------------------End bubbleSortByUserId method


    private static int binarySearch(Tickets[] tickets, String targetUserId) {
        int left = 0, right = tickets.length - 1;
    
        while (left <= right) {
            int mid = left + (right - left) / 2;
    
            Tickets midTicket = tickets[mid];
            if (midTicket == null) {
                int nextNonNull = findNextNonNull(tickets, mid + 1, right);
                if (nextNonNull == -1) {
                    right = mid - 1;
                } else {
                    left = nextNonNull;
                }
                continue;
            }  
            String midUserId = midTicket.getUserId();
    
            if (midUserId != null && midUserId.equals(targetUserId)) {
                return mid; // User found
            } else if (midUserId != null && midUserId.compareTo(targetUserId) < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }  
        return -1; // User not found
    }
    private static int findNextNonNull(Tickets[] tickets, int start, int end) {
        for (int i = start; i <= end; i++) {
            if (tickets[i] != null) {
                return i;
            }
        }
        return -1;
    }
}//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------End Class