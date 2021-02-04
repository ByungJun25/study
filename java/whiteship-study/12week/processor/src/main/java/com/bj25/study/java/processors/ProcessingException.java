package com.bj25.study.java.processors;

import javax.lang.model.element.Element;

/**
 * <p>
 * 에러 메세지용 Exception입니다.
 * 
 * @author bj25
 */
public class ProcessingException extends Exception {

    private static final long serialVersionUID = 8518668935383933518L;

    private Element element;

    public ProcessingException(Element element) {
        this.element = element;
    }

    public ProcessingException(Element element, String message, Object... args) {
        super(String.format(message, args));
        this.element = element;
    }

    public ProcessingException(String message, Element element) {
        super(message);
        this.element = element;
    }

    public ProcessingException(Throwable cause, Element element) {
        super(cause);
        this.element = element;
    }

    public ProcessingException(String message, Throwable cause, Element element) {
        super(message, cause);
        this.element = element;
    }

    public ProcessingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace,
            Element element) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.element = element;
    }

    public Element getElement() {
        return this.element;
    }

}
