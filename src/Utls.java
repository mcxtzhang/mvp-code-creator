import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.vfs.VirtualFile;

/**
 * Created by admin on 2017/1/22.
 */
public class Utls {
    public static String getCurrentPath(AnActionEvent e, String classFullName) {

        VirtualFile currentFile = DataKeys.VIRTUAL_FILE.getData(e.getDataContext());
        System.out.println(currentFile.getPath());
        String path = currentFile.getPath().replace(classFullName + ".java", "");
        return path;
    }

    public static void refreshProject(AnActionEvent e) {
        e.getProject().getBaseDir().refresh(false, true);
    }
}
