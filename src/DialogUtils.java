import com.intellij.openapi.ui.Messages;

/**
 * Dialog总控
 * Created by wing on 16/7/23.
 */
public class DialogUtils {

    public static void showErrorMessage(String context, String title){
        Messages.showMessageDialog(context,title,Messages.getErrorIcon());
    }
    public static void showMessage(String context, String title){
        Messages.showMessageDialog(context,title,Messages.getInformationIcon());
    }

    public static void showDebugMessage(String context, String title){
        if(true) {
            Messages.showMessageDialog(context, title, Messages.getErrorIcon());
        }
    }
}
