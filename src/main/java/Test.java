import java.util.Calendar;

public class Test {

    public static void main(String[] args) {
        // DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        // System.out.println(df.format(new Date()));

        // String str = "9999-12-31";
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        // LocalDate.parse("9999-12-31", DateTimeFormatter.BASIC_ISO_DATE);
        // System.out.println(new Date(dateTime.toLocalTime().getNano()));
        // System.out.println(Date.valueOf(LocalDate.of(9999, 12, 31)));
        //
        // System.out.println("".replaceAll("/", ","));

        // java.util.Date.from(java.time.LocalDate.parse("1955/09/30",
        // java.time.format.DateTimeFormatter.ofPattern("yyyy/MM/dd")).atStartOfDay(java.time.ZoneId.systemDefault()).toInstant());

        // Random r = new Random();
        // for (int i = 0; i < 10; i++) {
        // // System.out.println((int) (10 * Math.random()));
        // System.out.println(r.nextInt(10));
        // }

        // System.out.println("2345".matches("\\d{4}"));

        System.out.printf("%1$tY/%1$tm/%1$te %1$tH:%1$tM:%1$tS.%1$tL : APP login user %2$s\n/n", Calendar.getInstance(), "troytung");

    }

}
