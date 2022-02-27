package Utils;

public class OsUtility {

    private static String OS = System.getProperty("os.name").toLowerCase();

    public static String getOsInfo() {
        return OS;
    }

    public static boolean isWindows() {
        return (OS.indexOf("win") >= 0);
    }

    public static boolean isMac() {
        return (OS.indexOf("mac") >= 0);
    }

    public static boolean isUnix() {
        return (OS.indexOf("nux") >= 0);
    }
}