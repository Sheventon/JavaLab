import com.google.auto.service.AutoService;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@AutoService(Processor.class)
@SupportedAnnotationTypes(value = {"HtmlForm"})
public class FreemarkerHtmlProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        // получить типы с аннотаций HtmlForm
        Set<? extends Element> annotatedFormElements = roundEnv.getElementsAnnotatedWith(HtmlForm.class);
        for (Element element : annotatedFormElements) {
            // получаем полный путь для генерации html
            String path = FreemarkerHtmlProcessor.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            // User.class -> User.html
            path = path.substring(1) + element.getSimpleName().toString() + ".html";
            Path out = Paths.get(path);

            Configuration configuration = new Configuration(Configuration.VERSION_2_3_30);
            configuration.setDefaultEncoding("UTF-8");
            try {
                configuration.setTemplateLoader(new FileTemplateLoader(new File("src/main/resources")));
                Template template = configuration.getTemplate("freemarker_form.ftlh");

                Map<String, Object> attributes = new HashMap<>();
                List<Input> inputs = new ArrayList<>();
                HtmlForm formAnnotation = element.getAnnotation(HtmlForm.class);
                Form form = new Form(formAnnotation.action(), formAnnotation.method());
                //attributes.put("action", formAnnotation.action());
                //attributes.put("method", formAnnotation.method());
                attributes.put("form", form);

                List<? extends Element> enclosedElements = element.getEnclosedElements();
                for (Element elem : enclosedElements) {
                    if (elem.getKind().isField()) {
                        List<? extends AnnotationMirror> annotationMirrors = elem.getAnnotationMirrors();
                        for (AnnotationMirror annotationMirror : annotationMirrors) {
                            Element annotationElement = annotationMirror.getAnnotationType().asElement();
                            if (annotationElement.getSimpleName().contentEquals("HtmlInput")) {
                                HtmlInput inputAnnotation = elem.getAnnotation(HtmlInput.class);
                                Input input = new Input(inputAnnotation.type(), inputAnnotation.name(), inputAnnotation.placeholder());
                                inputs.add(input);
                            }
                        }
                    }
                }
                attributes.put("inputs", inputs);
                BufferedWriter writer = new BufferedWriter(new FileWriter(out.toFile()));
                template.process(attributes, writer);
                writer.close();
            } catch (IOException | TemplateException e) {
                throw new IllegalStateException(e);
            }
        }
        return true;
    }
}
