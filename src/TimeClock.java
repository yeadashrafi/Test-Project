import java.time.Duration;
import java.time.LocalTime;
import java.util.*;

public class TimeClock {

    private static long totalLunchTime = 0L;
    private static long totalBreakTime = 0L;
    private static HashMap<LocalTime, String> log = new HashMap<>();

    public static void main(String[] args) {
        //creating employee ids manually
        final List<Integer> employeeIDs = new ArrayList<>();
        for (int i = 101; i <= 110; i++) {
            employeeIDs.add(i);
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your valid employee ID to start the application: ");

        final Employee employee1 = new Employee();

        while (true) {
            int empId = scanner.nextInt();
            if (employeeIDs.contains(empId)) {
                System.out.println("Welcome to the application: Employee " + empId);
                employee1.setId(empId);
                break;
            } else {
                System.out.println("Invalid ID. Please enter your ID again: ");
            }
        }

        System.out.println("Do you want to start your shift (Y/N): ");
        Scanner scanner2 = new Scanner(System.in);
        String userInput = scanner2.nextLine();

        LocalTime startTime;

        if (userInput.equals("Y")) {
            //employee1.setActive(true);
            startTime = LocalTime.now();
            addLog(log, "Shift Start", startTime);
            userActivity(employee1, startTime);
            System.out.println("Your total lunch hour for today was: " + timeFormatter(totalLunchTime));
            System.out.println("Your total break hour for today was: " + timeFormatter(totalBreakTime));
            System.out.println("---------------------------");
            System.out.println("Please see shift data log for employee: " +employee1.getId());
            System.out.println("---------------------------");
            //adding TreeMap so that shift data can be sorted
            TreeMap<LocalTime, String> sorted = new TreeMap<>();
            sorted.putAll(log);
            for (LocalTime key : sorted.keySet()) {
                System.out.println(sorted.get(key) + " = " + key);
            }
            System.out.println("---------------------------");
        } else if (userInput.equals("N")) {
            System.out.println("Exiting application... Thank you!");
        }
    }

    public static void mainMenu(){
        //Main menu to choose from
        System.out.println("Please choose from following option: ");
        System.out.println("1: end your shift ");
        System.out.println("2: go to lunch ");
        System.out.println("3: go to break ");
    }

    public static void subMenu(String option){
        //Sub menu during lunch and break
        System.out.println("Please choose from following option: ");
        System.out.println("1: end your " + option);
        System.out.println("2: end your shift ");
    }

    public static void userActivity(Employee emp, LocalTime startTime){
        mainMenu();
        Scanner scanner = new Scanner(System.in);
        String option = scanner.nextLine();
        switch (option) {
            case "1":
                endShift(emp, startTime);
                break;
            case "2":
                lunch(emp, startTime);
                break;
            case "3":
                workBreak(emp, startTime);
                break;
        }
    }

    public static void endShift(Employee emp, LocalTime startTime){
        //emp.setActive(false);
        LocalTime endTime = LocalTime.now();
        addLog(log, "Shift End", endTime);
        long diff = Duration.between(startTime, endTime).toMillis();
        System.out.println("Thank you for using the application.");
        System.out.println("---------------------------");
        System.out.println("Your total logOn hour for today was: " + timeFormatter(diff));
    }

    public static void lunch(Employee emp, LocalTime startTime){
        boolean inLunch = true;
        LocalTime lunchStart = LocalTime.now();
        addLog(log, "Lunch Start", lunchStart);
        subMenu("lunch");
        Scanner scanner = new Scanner(System.in);
        while(true) {
            String option = scanner.nextLine();
            if (option.equals("1")) {
                inLunch = false;
                LocalTime lunchEnd = LocalTime.now();
                addLog(log, "Lunch End", lunchEnd);
                long diff = Duration.between(lunchStart, lunchEnd).toMillis();
                System.out.println("Your lunch time was: " + timeFormatter(diff));
                userActivity(emp, startTime);
                totalLunchTime = totalLunchTime + diff;
                break;
            } else {
                System.out.println("You cannot end your shift during lunch! You must end your lunch first to end your shift");
                System.out.println("Please choose option 1 to end lunch: ");
                System.out.println("1: end your lunch ");
            }
        }
    }

    public static void workBreak(Employee emp, LocalTime startTime){
        boolean inBreak = true;
        LocalTime breakStart = LocalTime.now();
        addLog(log, "Break Start", breakStart);
        subMenu("break");
        Scanner scanner = new Scanner(System.in);
        while(true){
            String option = scanner.nextLine();
            if(option.equals("1")){
                inBreak = false;
                LocalTime breakEnd = LocalTime.now();
                addLog(log, "Break End", breakEnd);
                long diff = Duration.between(breakStart, breakEnd).toMillis();
                System.out.println("Your break time was: " +timeFormatter(diff));
                //timeFormatter(diff);
                userActivity(emp, startTime);
                totalBreakTime = totalBreakTime + diff;
                break;
            }
            else{
                System.out.println("You cannot end your shift during break! You must end you break first to end your shift");
                System.out.println("Please choose option 1 to end break: ");
                System.out.println("1: end your break");
            }
        }
    }

    public static String timeFormatter(long millis){
        int seconds = (int) (millis / 1000) % 60 ;
        int minutes = (int) ((millis / (1000*60)) % 60);
        int hours   = (int) ((millis / (1000*60*60)) % 24);
        return("Hours: "+hours + " "+ "Minutes: "+minutes + " "+ "Seconds: "+seconds);
    }

    public static void addLog(HashMap map, String activity, LocalTime time){
        map.put(time, activity);
    }





}
