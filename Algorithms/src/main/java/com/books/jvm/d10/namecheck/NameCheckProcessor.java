package com.books.jvm.d10.namecheck;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Set;

/**
 * Page 320
 *
 * Javac编译器在持续注解处理器代码时要调用该process()方法
 *
 * Created by Administrator on 2017/5/18 0018.
 */
@SupportedAnnotationTypes("*") // 代表这个注解处理器对哪些注解感兴趣
@SupportedSourceVersion(SourceVersion.RELEASE_6) // 表示这个注解处理器可以处理哪些版本的Java代码
public class NameCheckProcessor extends AbstractProcessor {

    private NameChecker nameChecker;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        nameChecker = new NameChecker(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        if (!roundEnv.processingOver()) {
            for (Element element : roundEnv.getRootElements()) {
                nameChecker.checkNames(element);
            }
        }

        // 返回false，表示这个注解处理器没有改变语法树的内容
        // 如果返回true，则表示修改了代码，会构造一个新的JavaCompiler实例来编译修改后的代码
        return false;
    }
}
