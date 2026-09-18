// Week 01: Student.java -- a first user-defined class (preview of week 5)
public class Student {
   private String name;
   private int score;

   public Student(String name, int score) {
      this.name = name;
      this.score = score;
   }

   public String getName() { return name; }
   public boolean isPassing() { return score >= 60; }

   public static void main(String[] args) {
      Student a = new Student("Alice", 72);
      Student b = new Student("Bob", 55);
      System.out.println(a.getName() + " passing? " + a.isPassing());
      System.out.println(b.getName() + " passing? " + b.isPassing());
   }
}
