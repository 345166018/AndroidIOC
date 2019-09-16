package com.hongx.annotation_compiler;

import com.google.auto.service.AutoService;
import com.hongx.annotations.BindView;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.JavaFileObject;

/**
 * 这个类就是APT
 */
@AutoService(Processor.class)
public class AnnotationCompiler extends AbstractProcessor {

    //1.定义一个用于生成文件的对象
    Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
    }

    //2.需要确定当前APT处理所有模块中哪些注解
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new HashSet<>();
        types.add(BindView.class.getCanonicalName());
//        types.add(Override.class.getCanonicalName());
        return types;
    }

    //3.支持的JDK的版本
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    /**
     * 在这个方法中，我们去生成IBinder的实现类
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        //-------------------------注释1 开始------------------//
        //得到程序中所有写了BindView注解的元素的集合
        //类元素（TypeElement)
        //可执行元素(ExecutableElement)
        //属性元素（VariableElement）
        Set<? extends Element> elementsAnnotatedWith = roundEnvironment.getElementsAnnotatedWith(BindView.class);
        //定义一个MAP用来分类
        Map<String, List<VariableElement>> map = new HashMap<>();

        //开始分类存入MAP中
        for (Element element : elementsAnnotatedWith) {
            VariableElement variableElement = (VariableElement) element;
            //获取activity的名字
            String activityName = variableElement.getEnclosingElement().getSimpleName().toString();
            List<VariableElement> elementList = map.get(activityName);
            if (elementList == null) {
                elementList = new ArrayList<>();
                map.put(activityName, elementList);
            }
            elementList.add(variableElement);
        }
        //运行到这就已经完成了分类工作
        //-------------------------注释1结束------------------//

        if (map.size() > 0) {
            //开始写入文件
            Writer writer = null;
            //每一个activity都要生成一个对应的文件
            Iterator<String> iterator = map.keySet().iterator();
            while (iterator.hasNext()) {
                String activityName = iterator.next();
                List<VariableElement> elementList = map.get(activityName);

                //获取包名
                TypeElement enclosingElement = (TypeElement) elementList.get(0).getEnclosingElement();
                String packageName = processingEnv.getElementUtils().getPackageOf(enclosingElement).toString();

                try {
                    //生成文件
                    //包名.MainActivity_ViewBinding
                    JavaFileObject sourceFile = filer.createSourceFile(packageName + "." + activityName + "_ViewBinding");
                    writer = sourceFile.openWriter();
                    //        package com.example.dn_butterknife;
                    writer.write("package " + packageName + ";\n\n");
                    //        import com.example.dn_butterknife.IBinder;
                    writer.write("import " + packageName + ".IBinder;\n\n");
                    //        public class MainActivity_ViewBinding implements IBinder<com.example.dn_butterknife.MainActivity>{
                    writer.write("public class " + activityName + "_ViewBinding implements IBinder<"
                            + packageName + "." + activityName + ">{\n\n");
                    //            @Override
                    writer.write("  @Override\n");
                    //            public void bind(com.example.dn_butterknife.MainActivity target) {
                    writer.write("  public void bind(" + packageName + "." + activityName + " target){\n\n");
                    // target.tvText=(android.widget.TextView)target.findViewById(2131165325);
                    for (VariableElement variableElement : elementList) {
                        //获取控件的名字
                        String variableName = variableElement.getSimpleName().toString();
                        //获取ID
                        int id = variableElement.getAnnotation(BindView.class).value();
                        //获取控件的类型
                        TypeMirror typeMirror = variableElement.asType();
                        writer.write("      target." + variableName + "=(" + typeMirror + ")target.findViewById(" + id + ");\n");

                    }
                    writer.write("\n  }\n\n}");

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (writer != null) {
                        try {
                            writer.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }
        return false;
    }
}









