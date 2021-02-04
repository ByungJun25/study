package com.bj25.study.java.processors;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * DTO의 필드 요소를 마킹하는 어노테이션입니다.
 * <p>
 * DTO 어노테이션이 부여된 클래스 내부에서만 작동합니다.
 * 
 * @author bj25
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.SOURCE)
public @interface DTOProperty {

    String name() default "";

}
