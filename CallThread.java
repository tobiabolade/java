import java.util.Scanner;
class CallThread implements Runnable{
    Thread t;
    String Dial;
    CallThread(){
        t = new Thread(this, "Recharge");
        System.out.println("New Thread" + t);
        t.start();
    }

    
    public void run() {
    try {
         System.out.println("Dial (Y)");
         Scanner input = new Scanner(System.in);
         Dial = input.nextLine();
         System.out.println("Processing");
         Thread.sleep(500);
         System.out.println("Recharge Successful");
    }
    catch (InterruptedException e) {
        System.out.println("Recharge interrupted");
        }
      System.out.println("Recharge exited");
    }
}

class Calling{
    public static void main(String args[]){
        String number;
        String Dial;
        
        try {
        Scanner i = new Scanner(System.in);
        System.out.println("Please enter the number");
        number = i.nextLine();
        if (number.length() == 11){
            
                    System.out.println("Dial? (Y)");
                    Scanner in = new Scanner(System.in);
                    Dial = in.nextLine();
                    
                    System.out.println("Dialing " + number);
                    Thread.sleep(1000);
                    System.out.println("Call finished");
         }
        else if (number.length() > 11){
            new CallThread();
        }
        }
            catch(InterruptedException e){
                System.out.println("Call thread interrupted");
            }
        

        }
               
        
    }
    
