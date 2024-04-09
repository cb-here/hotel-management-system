import java.sql.*;
import java.util.Scanner;
import java.util.Random;

public class test5 {
    private static final String url = "jdbc:mysql://localhost:3306/hotel_db";
    private static final String user = "root";
    private static final String password = "@cbhere_7524";

    public static void main(String[] args) throws InterruptedException{
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded succesfully.");
            Connection con = DriverManager.getConnection(url, user, password);
            Scanner sc = new Scanner(System.in);

            while(true) {
                System.out.println("-----------------------------------");
                System.out.println("Enter your choice: ");
                System.out.println("1. Reverse a Room");
                System.out.println("2. View Reservation");
                System.out.println("3. Delete a Reservation");
                System.out.println("4. Exit");
                System.out.println("-------------------------------------");
                int choice  = sc.nextInt();

                switch(choice){
                    case 1:
                    performInsertion(con, sc);
                    break;
                    case 2:
                    performSelection(con, sc);
                    break;
                    case 3:
                    performDeletion(con, sc);
                    break;
                    case 4:
                    exit();
                    System.exit(0);
                    break;
                    default:
                    System.out.println("Enter a valid choice: ");
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void performInsertion(Connection con, Scanner sc) throws SQLException{
        String sql = "insert into hotel (name, address, room_no) values (?, ?, ?);";
        PreparedStatement ps = con.prepareStatement(sql);
        int room_no = generateRoom();
        String name, address;
        System.out.println("Enter your name: ");
        sc.nextLine();
        name = sc.nextLine();
        System.out.println("Enter your address: ");
        address = sc.nextLine();
        ps.setString(1, name);
        ps.setInt(3, room_no);
        ps.setString(2, address);

        int i = ps.executeUpdate();

        if (i > 0){
            System.out.println("Room Number: " + room_no + " has been reserved for " + name);
        }
        else {
            System.out.println("Something went wrong.");
        }
    }

    private static void performDeletion(Connection con, Scanner sc) throws SQLException, InterruptedException {
        String sql = "delete from hotel where room_no = ?;";
        PreparedStatement ps = con.prepareStatement(sql);
        System.out.println("Enter the your room no: ");
        int room_no = sc.nextInt();
        ps.setInt(1, room_no);

        int i = ps.executeUpdate();

        if(i > 0){
            System.out.print("Deleting");
            int j = 5;
            while (j > 0){
                System.out.print(".");
                Thread.sleep(300);
                j--;
            }
            System.out.println("\nThe room has been deleted.");
        }
        else {
            System.out.println("Something went wrong.");
        }
    }

    private static void performSelection(Connection con, Scanner sc) throws SQLException {
        String sql = "select * from hotel where room_no = ?;";
        System.out.println("Enter your room no: ");
        int room_no = sc.nextInt();

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, room_no);
        ResultSet rs = ps.executeQuery();

        while (rs.next()){
            String name = rs.getString("name");
            int id = rs.getInt("id");
            String date = rs.getString("date_reserved");
            String address = rs.getString("address");
            int room = rs.getInt("room_no");
            System.out.println("**********************************");
            System.out.println("Name: " + name + "\nId: " + id + "\nDate Reserved: " + date + "\nAddress: " + address + "\nRoom No: " + room);
            System.out.println("**********************************");
        }
    }
    
    private static void exit() throws InterruptedException{
        int i = 5;
        System.out.print("Exiting");
        while (i > 0){
            System.out.print(".");
            Thread.sleep(1000);
            i--;
        }   
        System.out.println("\nThanks for using me :)");
    }
    private static int generateRoom() {
        Random rand = new Random();
        return rand.nextInt(100) + 1;
    }
}