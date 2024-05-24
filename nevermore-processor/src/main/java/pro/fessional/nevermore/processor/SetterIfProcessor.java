package pro.fessional.nevermore.processor;

import pro.fessional.nevermore.setif.SetterIf;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.Set;

/**
 * @author trydofor
 * @since 2024-05-22
 */

@SupportedAnnotationTypes("pro.fessional.nevermore.setif.SetterIf")
public class SetterIfProcessor extends AbstractProcessor {

    private Messager logger;
    private Elements elementUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        logger = processingEnv.getMessager();
        elementUtils = processingEnv.getElementUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(SetterIf.class)) {
            if (element.getKind() == ElementKind.CLASS) {
                TypeElement classElement = (TypeElement) element;
                if (classElement.getEnclosingElement().getKind() == ElementKind.INTERFACE) {
                    generateClass(classElement);
                }
            }
        }
        return true;
    }

    private void generateClass(TypeElement classElement) {
        String className = classElement.getSimpleName() + "SetIf";
        String classPackage = processingEnv.getElementUtils().getPackageOf(classElement).getQualifiedName().toString();
        String fullClassName = classPackage + "." + classElement.getEnclosingElement().getSimpleName() + "." + classElement.getSimpleName();

        String source = "package " + classPackage + ";\n\n" +
                        "public class " + className + " extends " + fullClassName + " {\n" +
                        "    public void setListStrIf(java.util.List<String> listStr, boolean condition) {\n" +
                        "        if (condition) this.listStr = listStr;\n" +
                        "    }\n" +
                        "    // Add more methods here...\n" +
                        "}\n";

        try {
            JavaFileObject file = processingEnv.getFiler().createSourceFile(classPackage + "." + className);
            try (Writer writer = file.openWriter()) {
                writer.write(source);
            }
        } catch (IOException e) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Error generating class: " + e.getMessage());
        }
    }
}
