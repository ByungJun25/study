package com.bj25.study.java.processors;

import javax.lang.model.element.VariableElement;

import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 * DTOProperty 어노테이션이 붙은 필드의 정보를 수집하는 클래스입니다.
 * 
 * @author bj25
 */
public class DTOPropertyAnnotatedClass {

    private VariableElement annotatedVariableElement;
    private String fieldOriginalName;
    private String name;

    public DTOPropertyAnnotatedClass(VariableElement annotatedVariableElement) {
        if (annotatedVariableElement == null) {
            throw new IllegalArgumentException("The element is required!");
        }

        this.annotatedVariableElement = annotatedVariableElement;
        this.fieldOriginalName = this.annotatedVariableElement.getSimpleName().toString();
        DTOProperty annotation = this.annotatedVariableElement.getAnnotation(DTOProperty.class);
        this.name = annotation.name();
        if (StringUtils.isBlank(this.name)) {
            this.name = this.fieldOriginalName;
        }
    }

    public String getName() {
        return this.name;
    }

    public String getFieldName() {
        return this.fieldOriginalName;
    }

    public VariableElement getVariableElement() {
        return this.annotatedVariableElement;
    }

}
