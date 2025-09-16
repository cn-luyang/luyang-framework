package io.github.luyang.starter.security.annotation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Anonymous {
}
