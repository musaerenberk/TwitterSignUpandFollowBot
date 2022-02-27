package Test;

import Listener.Listener;
import Pages.TwitterSignUpAndFollow;
import bsh.BshMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import java.awt.*;

@Listeners({Listener.class})
public class TwitterTest extends TwitterSignUpAndFollow {
    private BshMethod method;

        @Test
        public void IssueTestA() throws InterruptedException, AWTException {
            logger = extent.startTest("Test Name : Twitter Signup And Follow");
            Run();
    }


}
