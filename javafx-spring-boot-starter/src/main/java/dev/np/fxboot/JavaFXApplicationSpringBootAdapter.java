package dev.np.fxboot;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static org.springframework.util.ClassUtils.getDefaultClassLoader;

@Slf4j
public abstract class JavaFXApplicationSpringBootAdapter extends Application {
    private ConfigurableApplicationContext context;

    @Override
    public void init() throws ClassNotFoundException {
        Class<?> launcherClass = getLauncherClass();
        log.info("launcher class: {}", launcherClass.getName());
        this.context = new SpringApplicationBuilder()
                .sources(launcherClass)
                .initializers(getInitializer())
                .build()
                .run(getParameters().getRaw().toArray(new String[0]));
    }

    @Override
    public void start(Stage primaryStage) {
        log.info("publishing StageReadyEvent");
        this.context.publishEvent(new StageReadyEvent(primaryStage));
    }

    @Override
    public void stop() {
        this.context.close();
        Platform.exit();
    }

    private ApplicationContextInitializer<GenericApplicationContext> getInitializer() {
        return context -> {
            context.registerBean(Application.class, () -> this);
            context.registerBean(Parameters.class, this::getParameters);
            context.registerBean(HostServices.class, this::getHostServices);
        };
    }


    private Class<?> getLauncherClass() throws ClassNotFoundException {
        if (getParameters().getNamed().containsKey("launcher")) {
            return ClassUtils.forName(getParameters().getNamed().get("launcher"), getDefaultClassLoader());
        } else {
            String[] packages = Arrays.stream(requireNonNull(getDefaultClassLoader()).getDefinedPackages())
                    .map(Package::getName)
                    .toList()
                    .toArray(new String[0]);

            List<BeanDefinition> annotatedClasses = findAnnotatedClasses(SpringBootApplication.class, packages);

            if (annotatedClasses.isEmpty()) {
                throw new UnsupportedOperationException("Unable to find any Spring Boot application class");
            } else if (annotatedClasses.size() > 1) {
                throw new UnsupportedOperationException("Found multiple Spring Boot application classes [" + annotatedClasses.stream().map(BeanDefinition::getBeanClassName).toList() + "]");
            }
            return ClassUtils.forName(requireNonNull(annotatedClasses.getFirst().getBeanClassName()), getDefaultClassLoader());
        }
    }

    private List<BeanDefinition> findAnnotatedClasses(Class<? extends Annotation> annotationType, String... packageNames) {
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AnnotationTypeFilter(annotationType));
        Set<BeanDefinition> definitions = new HashSet<>();
        Arrays.stream(packageNames).forEach(packageName -> definitions.addAll(provider.findCandidateComponents(packageName)));
        return definitions.stream().toList();
    }


    public abstract Parent getRoot();
}
