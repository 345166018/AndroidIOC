package com.hongx.annotation_compiler;

import com.google.auto.service.AutoService;
import com.hongx.annotations.BindView;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;

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
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;


/**
 * 这个类就是APT
 */
@AutoService(Processor.class)
public class AnnotationCompiler2 extends AbstractProcessor {

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

//            package com.hongx.hxbutterknife;
//
//            import com.hongx.hxbutterknife.IBinder;
//
//            public class MainActivity_ViewBinding implements IBinder<com.hongx.hxbutterknife.MainActivity>{
//
//                @Override
//                public void bind(com.hongx.hxbutterknife.MainActivity target){
//
//                    target.tvText=(android.widget.TextView)target.findViewById(2131165326);
//
//                }
//
//            }

            //每一个activity都要生成一个对应的文件
            Iterator<String> iterator = map.keySet().iterator();
            while (iterator.hasNext()) {
                String activityName = iterator.next();
                List<VariableElement> elementList = map.get(activityName);

                //获取包名
                TypeElement enclosingElement = (TypeElement) elementList.get(0).getEnclosingElement();
                String packageName = processingEnv.getElementUtils().getPackageOf(enclosingElement).toString();


                List<CodeBlock> codeBlockList = new ArrayList<>();

                for (VariableElement variableElement : elementList) {
                    //获取控件的名字
                    String variableName = variableElement.getSimpleName().toString();
                    //获取ID
                    int resourceId = variableElement.getAnnotation(BindView.class).value();
                    //获取控件的类型
                    TypeMirror typeMirror = variableElement.asType();

                    CodeBlock singleCodeBlock = codeBlock(variableName, typeMirror, resourceId);

                    codeBlockList.add(singleCodeBlock);

                }
                CodeBlock allCodeBlock = getAllBlock(codeBlockList);


                /**
                 * $L $S $T $N 都是占位符。
                 *
                 * $L：常量
                 * $S：String类型
                 * $T：变量指定类型，可以通过ClassName来指定外部类名
                 * $N：生成的方法名或者变量名
                 */
                MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("bind")
                        .addModifiers(Modifier.PUBLIC)
                        .returns(void.class)
                        // 重写的方法
                        .addAnnotation(Override.class)
                        .addParameter(ClassName.get(packageName, activityName), "target")
                        .addCode(allCodeBlock);

                ClassName IBinderClass = ClassName.get("com.hongx.hxbutterknife", "IBinder");
                ClassName superinterface = ClassName.bestGuess(ClassName.get(packageName, activityName).toString());

                TypeSpec activityClass = TypeSpec.classBuilder(activityName + "_ViewBinding2")
                        .addModifiers(Modifier.PUBLIC)

                        // 添加接口，ParameterizedTypeName的参数1是接口，参数2是接口的泛型
                        .addSuperinterface(ParameterizedTypeName.get(IBinderClass, superinterface))

                        .addMethod(methodBuilder.build())
                        .build();

                JavaFile javaFile = JavaFile.builder(packageName, activityClass).build();


                try {
                    javaFile.writeTo(filer);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


        }
        return false;
    }

    /**
     * 生成多条代码
     */
    public static CodeBlock getAllBlock(List<CodeBlock> codeBlockList) {

        CodeBlock.Builder codeBlockBuilder = CodeBlock.builder();
        for (CodeBlock codeBlock : codeBlockList) {
            codeBlockBuilder.add(codeBlock);
        }

        return codeBlockBuilder.build();

    }


    public static CodeBlock codeBlock(String variableName, TypeMirror typeMirror, int resourceId) {

        CodeBlock.Builder codeBlockBuilder = CodeBlock.builder();

        CodeBlock codeBlock = CodeBlock.builder()
                .addStatement("$N.$L = ($T)target.findViewById($L)", "target", variableName, typeMirror, resourceId)
                .build();

        codeBlockBuilder.add(codeBlock);

        return codeBlockBuilder.build();
    }

}









