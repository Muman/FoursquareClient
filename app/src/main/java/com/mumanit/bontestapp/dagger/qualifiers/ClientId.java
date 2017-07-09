package com.mumanit.bontestapp.dagger.qualifiers;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by pmuciek on 7/8/17.
 */

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ClientId {
}
