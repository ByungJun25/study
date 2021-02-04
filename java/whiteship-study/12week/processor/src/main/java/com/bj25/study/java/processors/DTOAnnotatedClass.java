package com.bj25.study.java.processors;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;

import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 * DTO 어노테이션이 가진 클래스의 정보를 가진 데이터 객체입니다. 이 객체에 저장된 정보를 활용하여 추후 DTO 클래스를 생성합니다.
 * 
 * @author bj25
 */
public class DTOAnnotatedClass {

    private final String POSTFIX = "DTO";
    private PackageElement packageElement;
    private TypeElement annotatedClassElement;
    private Set<DTOPropertyAnnotatedClass> dtoPropertiesElements;
    // 클래스 전체이름
    private String qualifiedClassName;
    // 클래스 이름
    private String simpleClassName;
    // 어노테이션이 주어진 DTO 생성용 이름
    private String name;

    /**
     * 패키지 요소와 어노테이션을 가진 클래스 요소를 받습니다.
     * 
     * @param packageElement
     * @param annotatedClassElement
     */
    public DTOAnnotatedClass(PackageElement packageElement, TypeElement annotatedClassElement) {
        if (annotatedClassElement == null) {
            throw new IllegalArgumentException("The element is required!");
        }

        this.packageElement = packageElement;
        this.annotatedClassElement = annotatedClassElement;
        this.initClassName();
        this.initAnnotationInfo();
        this.initFields();
    }

    /**
     * 클래스 이름에 대한 정보를 수집합니다.
     */
    private void initClassName() {
        try {
            this.qualifiedClassName = this.annotatedClassElement.getQualifiedName().toString();
            this.simpleClassName = this.annotatedClassElement.getSimpleName().toString();
        } catch (MirroredTypeException mte) {
            DeclaredType classTypeMirror = (DeclaredType) mte.getTypeMirror();
            TypeElement classTypeElement = (TypeElement) classTypeMirror.asElement();
            this.qualifiedClassName = classTypeElement.getQualifiedName().toString();
            this.simpleClassName = classTypeElement.getSimpleName().toString();
        }
    }

    /**
     * 어노테이션에 주어진 정보를 수집합니다.
     */
    private void initAnnotationInfo() {
        DTO annotation = this.annotatedClassElement.getAnnotation(DTO.class);
        this.name = annotation.name();
        if (StringUtils.isBlank(this.name)) {
            this.name = this.generateDefaultName();
        }
    }

    /**
     * DTO 어노테이션의 name 요소에 값이 없을 경우, 기존 클래스 이름 + 'DTO'가 이름이 됩니다.
     * 
     * @return {@code [ClassName]DTO}
     */
    private String generateDefaultName() {
        return this.simpleClassName + this.POSTFIX;
    }

    /**
     * DTOProperty 어노테이션이 붙은 필드에 대한 정보를 수집하는 함수입니다.
     * 
     */
    private void initFields() {
        // 클래스 내부 모든 요소를 가져옵니다.
        List<?> elements = annotatedClassElement.getEnclosedElements();

        if (!elements.isEmpty()) {
            // 내부 요소중 필드영역의 요소만 필터링합니다.
            List<VariableElement> variableElements = elements.stream().filter(e -> e instanceof VariableElement)
                    .map(e -> (VariableElement) e).collect(Collectors.toList());

            // 필터 요소중 DTOProperty 어노테이션이 붙은 필드의 정보를 수집합니다.
            this.dtoPropertiesElements = variableElements.stream()
                    .filter(ve -> ve.getAnnotation(DTOProperty.class) != null)
                    .map(ve -> new DTOPropertyAnnotatedClass(ve)).collect(Collectors.toSet());
        }
    }

    /**
     * 
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * 
     * @return package name
     */
    public String getPackage() {
        return this.packageElement.getQualifiedName().toString();
    }

    /**
     * 
     * @return QFCN
     */
    public String getQualifiedClassName() {
        return this.qualifiedClassName;
    }

    /**
     * 
     * @return className
     */
    public String getSimpleClassName() {
        return this.simpleClassName;
    }

    /**
     * 
     * @return TypeElement
     */
    public TypeElement getTypeElement() {
        return this.annotatedClassElement;
    }

    /**
     * 해당 클래스 내부의 DTOProperty를 가진 요소들에 대한 정보를 반환합니다.
     * 
     * @return Set<DTOPropertyAnnotatedClass>
     */
    public Set<DTOPropertyAnnotatedClass> getDtoPropertiesElements() {
        return this.dtoPropertiesElements;
    }

}
