package com.bj25.study.java.processors;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO 어노테이션을 가진 클래스의 DTO 클래스를 생성해주는 annotation processor 입니다.
 * 
 * @author bj25
 */
@SupportedAnnotationTypes({ "com.bj25.study.java.processors.DTO" })
@AutoService(Processor.class)
public class DTOProcessor extends AbstractProcessor {

    private Types typeUtils;
    private Elements elementUtils;
    private Filer filer;
    private Messager messager;

    /**
     * ProcessingEnvironment로부터 필요한 유틸성 객체를 받습니다.
     */
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        this.typeUtils = processingEnv.getTypeUtils();
        this.elementUtils = processingEnv.getElementUtils();
        this.filer = processingEnv.getFiler();
        this.messager = processingEnv.getMessager();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    /**
     * DTO 어노테이션을 가진 클래스를 수집하여 해당 클래스에 대한 DTO 클래스를 만드는 코드입니다.
     * 
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        try {
            // 모든 @DTO가 붙은 element를 순회합니다.
            for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(DTO.class)) {
                // class 타입의 element인지 확인합니다. 이러한 이유는 class에 한정해서 사용하도록 하기 위함입니다.
                if (annotatedElement.getKind() != ElementKind.CLASS) {
                    throw new ProcessingException(annotatedElement, "Only classes can be annotated with @%s",
                            DTO.class.getSimpleName());
                }

                // 클래스인것을 알기때문에 TypeElement로 캐스팅이 가능합니다.
                TypeElement typeElement = (TypeElement) annotatedElement;
                // 패키지 정보를 수집합니다.
                PackageElement packageElement = this.elementUtils.getPackageOf(typeElement);
                // DTO 어노테이션을 가진 클래스에 대한 정보를 추출합니다.
                DTOAnnotatedClass annotatedClass = new DTOAnnotatedClass(packageElement, typeElement);
                // DTO 클래스 코드를 생성합니다.
                this.generateDTO(annotatedClass);
            }
        } catch (ProcessingException e) {
            this.error(e.getElement(), e.getMessage());
        } catch (IOException e) {
            this.error(null, e.getMessage());
        } catch (Exception e) {
            this.error(null, e.getMessage());
        }

        return true;
    }

    /**
     * 에러메시지 출력용 함수입니다.
     * 
     * @param e
     * @param msg
     * @param args
     */
    private void error(Element e, String msg, Object... args) {
        this.messager.printMessage(Diagnostic.Kind.ERROR, String.format(msg, args), e);
    }

    /**
     * DTO 클래스 코드를 생성하는 함수입니다. 대상이 되는 클래스 정보를 받습니다.
     * 
     * @param annotatedClass
     * @throws IOException
     */
    private void generateDTO(DTOAnnotatedClass annotatedClass) throws IOException {
        TypeSpec dto = this.generateDTOClass(annotatedClass);

        JavaFile javaFile = JavaFile.builder(annotatedClass.getPackage(), dto).build();

        javaFile.writeTo(this.filer);
    }

    /**
     * JavaPoet를 이용하여 DTO 클래스에 대한 정보를 생성합니다.
     * 
     * @param annotatedClass
     * @return
     */
    private TypeSpec generateDTOClass(DTOAnnotatedClass annotatedClass) {
        Set<DTOPropertyAnnotatedClass> fieldInfos = annotatedClass.getDtoPropertiesElements();

        Set<FieldSpec> fields = this.generateFields(fieldInfos);
        MethodSpec constructor = this.generateConstructor(fieldInfos);

        return TypeSpec.classBuilder(annotatedClass.getName()).addModifiers(Modifier.PUBLIC).addFields(fields)
                .addAnnotation(Getter.class).addAnnotation(NoArgsConstructor.class).addMethod(constructor).build();
    }

    /**
     * JavaPoet를 이용하여 DTO 클래스의 필드들에 대한 정보를 생성합니다.
     * 
     * @param fieldInfos
     * @return
     */
    private Set<FieldSpec> generateFields(Set<DTOPropertyAnnotatedClass> fieldInfos) {
        return fieldInfos.stream()
                .map(f -> FieldSpec
                        .builder(TypeName.get(f.getVariableElement().asType()), f.getName(), Modifier.PRIVATE).build())
                .collect(Collectors.toSet());
    }

    /**
     * JavaPoet를 이용하여 DTO 클래스의 생성자 정보를 생성합니다.
     * 
     * @param fieldInfos
     * @return
     */
    private MethodSpec generateConstructor(Set<DTOPropertyAnnotatedClass> fieldInfos) {
        Set<ParameterSpec> constructorParameterSpecs = this.generateConstructorParameters(fieldInfos);
        CodeBlock constructorCodeBlock = this.generateConstructorCodes(fieldInfos);

        return MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC).addParameters(constructorParameterSpecs)
                .addAnnotation(Builder.class).addCode(constructorCodeBlock).build();
    }

    /**
     * DTO 클래스의 생성자내에서 사용될 코드 정보를 생성합니다.
     * 
     * @param fieldInfos
     * @return
     */
    private CodeBlock generateConstructorCodes(Set<DTOPropertyAnnotatedClass> fieldInfos) {
        List<String> constructorCommands = fieldInfos.stream().map(f -> f.getName())
                .map(name -> "this." + name + " = " + name).collect(Collectors.toList());

        com.squareup.javapoet.CodeBlock.Builder codeBlockBuilder = CodeBlock.builder();

        constructorCommands.forEach(c -> codeBlockBuilder.addStatement(c));

        return codeBlockBuilder.build();
    }

    /**
     * DTO 클래스의 생성자에 사용될 파라미터 정보를 생성합니다.
     * 
     * @param fieldInfos
     * @return
     */
    private Set<ParameterSpec> generateConstructorParameters(Set<DTOPropertyAnnotatedClass> fieldInfos) {
        return fieldInfos.stream()
                .map(f -> ParameterSpec.builder(TypeName.get(f.getVariableElement().asType()), f.getName()).build())
                .collect(Collectors.toSet());
    }

}