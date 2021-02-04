package com.bj25.study.java.processors;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * DTO 클래스를 생성하기 위한 어노테이션입니다.
 * <p>
 * 해당 어노테이션은 class에만 선언이 가능합니다.
 * <p>
 * name 속성을 이용하여 생성할 클래스의 이름을 지정할 수 있습니다. 따로 지정하지 않을경우 기존 클래스이름 + 'DTO'가 DTO 클래스
 * 이름이됩니다.
 * 
 * @author bj25
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface DTO {

    String name() default "";

}
