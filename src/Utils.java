import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.vfs.VirtualFile;

/**
 * Created by admin on 2017/1/22.
 */
public class Utils {
    /**
     * Get current path by {@link AnActionEvent}.
     * Like this:
     *
     * @param e
     * @param classFullName
     * @return
     */
    public static String getCurrentPath(AnActionEvent e, String classFullName) {
        VirtualFile currentFile = (VirtualFile) DataKeys.VIRTUAL_FILE.getData(e.getDataContext());
        Log.show("In getCurrentPath() - currentFile.getPath():" + currentFile.getPath());
        String path = currentFile.getPath().replace(classFullName + ".java", "");
        return path;
    }

    /**
     * Refresh project ui
     * For file show in idea control
     *
     * @param e
     */
    public static void refreshProject(AnActionEvent e) {
        e.getProject().getBaseDir().refresh(false, true);
    }
}
