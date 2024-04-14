package com.StreamlineLearn.UserManagement.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD) // Use METHOD to apply to methods only
public @interface IsAuthorizedToUpdateAdministrative {
}
