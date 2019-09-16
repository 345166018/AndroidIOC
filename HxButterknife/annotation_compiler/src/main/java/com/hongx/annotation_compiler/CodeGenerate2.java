package com.hongx.annotation_compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;

import javax.lang.model.element.Modifier;

/**
 * @author: fuchenming
 * @create: 2019-09-10 14:13
 */
public class CodeGenerate2 {





    public static void main(String[] args) {

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



//        JavaFile javaFile = JavaFile.builder("com.hongx.hxbutterknife", clazz).build();
//        System.out.println(javaFile.toString());


        ClassName activity = ClassName.get("android.app", "Activity");

        TypeSpec.Builder mainActivityBuilder = TypeSpec.classBuilder("MainActivity")
                .addModifiers(Modifier.PUBLIC)
                .superclass(activity);

        ClassName override = ClassName.get("java.lang", "Override");

        ClassName bundle = ClassName.get("android.os", "Bundle");

        ClassName nullable = ClassName.get("android.support.annotation", "Nullable");

        ParameterSpec savedInstanceState = ParameterSpec.builder(bundle, "savedInstanceState")
                .addAnnotation(nullable)
                .build();

        MethodSpec onCreate = MethodSpec.methodBuilder("onCreate")
                .addAnnotation(override)
                .addModifiers(Modifier.PROTECTED)
                .addParameter(savedInstanceState)
                .addStatement("super.onCreate(savedInstanceState)")
                .addStatement("setContentView(R.layout.activity_main)")
                .build();

        TypeSpec mainActivity = mainActivityBuilder.addMethod(onCreate)
                .build();

        JavaFile file = JavaFile.builder("com.test", mainActivity).build();

        try {
            file.writeTo(System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
