public class GreetingApp {
    public static void main(String[] args) {
        // 固定案例使參數、回傳值與輸出可以分別核對。
        String first = buildGreeting("Ada");
        System.out.println(first);
        System.out.println(buildGreeting("Grace"));
        System.out.println(buildGreeting("Ada Lovelace"));
    }

    static String buildGreeting(String name) {
        // 保持計算與顯示分離，呼叫端才能選擇主控台或 UI。
        return "Hello, " + name + "!";
    }
}
