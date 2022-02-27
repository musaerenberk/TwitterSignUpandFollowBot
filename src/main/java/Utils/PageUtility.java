package Utils;

import org.apache.commons.lang3.RandomStringUtils;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Vector;

public class PageUtility {

    public static String randomTCKN() {
        Vector<Integer> array = new Vector<Integer>();
        Random randomGenerator = new Random();
        array.add(new Integer(1 + randomGenerator.nextInt(9)));
        for (int i = 1; i < 9; i++) array.add(randomGenerator.nextInt(10));
        int t1 = 0;
        for (int i = 0; i < 9; i += 2) t1 += array.elementAt(i);
        int t2 = 0;
        for (int i = 1; i < 8; i += 2) t2 += array.elementAt(i);
        int x = (t1 * 7 - t2) % 10;
        array.add(new Integer(x));
        x = 0;
        for (int i = 0; i < 10; i++) x += array.elementAt(i);
        x = x % 10;
        array.add(new Integer(x));
        String tckn = "";
        for (int i = 0; i < 11; i++) tckn = tckn + Integer.toString(array.elementAt(i));
        return tckn;
    }
    public static String randomVKN(){
        long vkn;
        String vknStr;
        long sum;
        long j;
        long i1;
        long i2;
        String lastDigit;
        vkn = (new Random().nextInt(999999999 - 100000000) + 100000000);
        sum = 0;
        j = 9;
        vknStr = String.valueOf(vkn);
        for (int i = 0; i <= 9; i++) {
            i1 = (Long.valueOf(vknStr) + 9) % 10;
            i2 = (i1 * (2 ^j)) % 9;
            sum += (i1 != 0 && i2 == 0) ? 9 : i2;
            j = j - 1;
        }
        lastDigit=String.valueOf((((sum%10)==0)?0:(10-(sum%10))));
        return lastDigit+vknStr;

    }
    public static String getToday(){

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }
    public static String randomText (int length){
        return RandomStringUtils.randomAlphanumeric(length).toUpperCase();
    }
    public static String randomNumber (int length){
        return RandomStringUtils.randomNumeric(length);
    }

    public static String randomEmail() {
        return randomText(15).toLowerCase() + "@gmail.com";
    }
    public static String randomPhone() {
        Random rad = new Random();
        int Low = 500;
        int High = 560;
        int Result = rad.nextInt(High - Low) + Low;
        Random rad2 = new Random();
        int Low2 = 1000001;
        int High2 = 9999999;
        int Result2 = rad2.nextInt(High2 - Low2) + Low2;
        return String.valueOf(Result)+String.valueOf(Result2);
    }
}
