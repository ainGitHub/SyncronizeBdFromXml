import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Main {

    static final Logger rootLogger = LogManager.getRootLogger();

    public static void main(String[] args) {
        System.out.println("Hello world");
        //System.out.println(args[0] + " " + args[1]);
        rootLogger.info("Test working");
    }
}
