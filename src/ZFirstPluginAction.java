import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.psi.PsiJavaFile;
import com.intellij.ui.JBColor;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import javax.lang.model.element.Modifier;

/**
 * Created by admin on 2017/1/20.
 */
public class ZFirstPluginAction extends AnAction {

    private Editor mEditor;

    //current file content
    private String mContent;

    private void init(AnActionEvent e) {
        mEditor = (Editor) e.getData(PlatformDataKeys.EDITOR);
        mContent = mEditor.getDocument().getText();
        Log.show("mContent:" + mContent);
    }


    @Override
    public void actionPerformed(AnActionEvent e) {
        init(e);
        String[] words = mContent.split(" ");
        //find out file name (CLassName)
        for (String word : words) {
            if (word.contains("Contract")) {
                //For example: word: CreateGroupContract ,moduleName: CreateGroup
                String moduleName = word.substring(0, word.indexOf("Contract"));
                Log.show("word:" + word + ", moduleName:" + moduleName);
                String currentPath = Utils.getCurrentPath(e, word);
                Log.show("get currentPath:" + currentPath);

                //test for fill contract file
                int lastIndex = mContent.lastIndexOf("}");
                mContent = mContent.substring(0, lastIndex);
                DialogUtils.showDebugMessage(mContent, "debug");


                //Way 1 :hard code way
/*                String content = mContent + "public interface " + "View{\n}\n\n"
                        + "public interface " + "Presenter{\n}\n\n"
                        + "public interface " + "Model{\n}\n\n"
                        + "\n}";*/

                //Way 2 : use JavaPoet(Recommend)
                ClassName interfaceIBaseView = ClassName.get("cn.com.anlaiye.mvp", "IBaseView");
                TypeSpec interfaceIView = TypeSpec.interfaceBuilder("IView")
                        .addSuperinterface(interfaceIBaseView)
                        .build();

                mContent = mContent + interfaceIView.toString() + "}";
                //wirte in runWriteAction
                WriteCommandAction.runWriteCommandAction(mEditor.getProject(),
                        new Runnable() {
                            @Override
                            public void run() {
                                mEditor.getDocument().setText(mContent);
                            }
                        });


                PsiJavaFile javaFile = (PsiJavaFile) e.getData(CommonDataKeys.PSI_FILE);
                String packageName = javaFile.getPackageName();

                System.out.println("current package name is :" + packageName);


                //test for create file

                MethodSpec methodSpec = MethodSpec.methodBuilder("main")
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                        .returns(void.class)
                        .addParameter(String[].class, "args")
                        .addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!")
                        .build();

                TypeSpec Presenter = TypeSpec.classBuilder("className" + "Presenter2")
                        .addModifiers(Modifier.PUBLIC)
                        .addMethod(methodSpec)
                        .build();

                JavaFile outP = JavaFile.builder(packageName, Presenter)
                        .build();
                try {
                    System.out.println("????????");
                    outP.writeTo(new File("C:/Users/admin/IdeaProjects/Test/src"));
                    System.out.println("!!!!!!!!!");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                Utils.refreshProject(e);


            }


            //System.out.println(word);
        }



/*        final Editor mEditor = e.getData(PlatformDataKeys.EDITOR);
        if (null == mEditor) {
            return;
        }
        SelectionModel model = mEditor.getSelectionModel();
        final String selectedText = model.getSelectedText();
        if (TextUtils.isEmpty(selectedText)) {
            return;
        }

        //showDialog("测试", "测试内容");
        showPopupBalloon(mEditor, "测试咯");*/

        Project project = e.getProject();

        //▲input dialog
/*
        String s = Messages.showInputDialog(project, "Input package name", "Hello", Messages.getQuestionIcon());
        Messages.showMessageDialog(project, "Hello " + s + "!", "Welcome", Messages.getInformationIcon());*/


//create file by psi

        /*ApplicationManager.getApplication().invokeLater(new Runnable() {
            @Override
            public void run() {
                new WriteCommandAction(project) {
                    @Override
                    protected void run(Result result) throws Throwable {
                        //writing to file
                        PsiDirectory baseDir = PsiDirectoryFactory.getInstance(project).createDirectory(project.getBaseDir());
                        System.out.println("baseDir:" + baseDir);
                        String moduleName = "testnewdir";
                        //moduleName = baseDir.toString() + File.separator + moduleName;
                        PsiDirectory subDir = baseDir.findSubdirectory(moduleName);
                        System.out.println("subDir:" + subDir + ",moduleName:" + moduleName);
                        boolean isNotExist = subDir == null;
                        if (isNotExist) {
                            System.out.println("subDir:" + subDir);
                            subDir = baseDir.createSubdirectory(moduleName);
                            System.out.println("subDir:" + subDir);
                        }

                        PsiClass clazz = JavaDirectoryService.getInstance().createClass(subDir, "testJava");

                        //PsiPackage psiPackage = JavaPsiFacade.getInstance(project).findPackage();

                        ((PsiJavaFile) clazz.getContainingFile()).setPackageName(*//*psiPackage.getName()*//*"testnewdir");

                    }
                }.execute();
            }
        });*/


    }

    private void showDialog(String title, String content) {
        Messages.showMessageDialog(content, "title", Messages.getInformationIcon());
    }


    private void showPopupBalloon(final Editor editor, final String result) {
        ApplicationManager.getApplication().invokeLater(new Runnable() {
            public void run() {
                JBPopupFactory factory = JBPopupFactory.getInstance();
                factory.createHtmlTextBalloonBuilder(result, null, new JBColor(new Color(186, 238, 186), new Color(73, 117, 73)), null)
                        .setFadeoutTime(5000)
                        .createBalloon()
                        .show(factory.guessBestPopupLocation(editor), Balloon.Position.below);
            }
        });
    }
}
