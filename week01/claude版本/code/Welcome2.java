// Week 01: Welcome2.java -- command-line arguments and printf
public class Welcome2 {
   public static void main(String[] args) {
      if (args.length == 0) {
         System.out.println("Usage: java Welcome2 <name>");
         return;
      }
      String name = args[0];
      System.out.printf("Hello, %s! You passed %d argument(s).%n", name, args.length);
   }
}
