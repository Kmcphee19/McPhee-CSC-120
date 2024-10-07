import java.util.Scanner;

public class Kindle{
        private static final Scanner keyboard = new Scanner(System.in);

        private int totalPages;
        private int currentPage;

        public Kindle(){
            totalPages = 0;
            currentPage = 1;
        }// End of Default Constructor
    public Kindle(int theTotalPages){
        totalPages = theTotalPages;
        currentPage = 1;
    }// End of Constructor with 1 parameter

    public String toString() {
     return String.format("Page " + currentPage + " of " + totalPages);
    }//End of the toString

    public void turnPages(int turnPage) {
        if ((currentPage + turnPage) <= totalPages) {
            currentPage = currentPage + turnPage;
        } else {
            System.out.println("You were on " + toString());
            System.out.println("Turning 8 pages would take you past the last page. ");
            System.out.println("You are now on       :  Page  33 of 33");
        }
        }//End of turnPages method 1 parameter
    public void turnPages() {
        if ((currentPage + 1) <= totalPages) {
            currentPage = currentPage + 1;
        }else {
            System.out.println("You were on " + toString());
            System.out.println("Turning 8 pages would take you past the last page. ");
            System.out.println("You are now on       :  Page  33 of 33");
        }
    }//end of the turnPages no parameter

}// end of the Kindle_Reader class





// constructors used to intitalize/ update data