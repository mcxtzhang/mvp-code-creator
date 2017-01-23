import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiJavaFile;

/**
 * Created by admin on 2017/1/22.
 */
public class Utils {
    /**
     * Get current path by {@link AnActionEvent}.
     * Like this:"C:/Users/admin/IdeaProjects/Test/src/com/example/helloworld/"
     *
     * @param e
     * @param classFullName
     * @return
     */
    public static String getCurrentPath(AnActionEvent e, String classFullName) {
        VirtualFile currentFile = (VirtualFile) DataKeys.VIRTUAL_FILE.getData(e.getDataContext());
        //C:/Users/admin/IdeaProjects/Test/src/com/example/helloworld/HelloWorldContract.java
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

    /**
     * Get package name of current class.
     * Like this:"com.example.helloworld"
     *
     * @param e
     * @return
     */
    public static String getPkgName(AnActionEvent e) {
        PsiJavaFile javaFile = (PsiJavaFile) e.getData(CommonDataKeys.PSI_FILE);
        return javaFile.getPackageName();
    }
}
