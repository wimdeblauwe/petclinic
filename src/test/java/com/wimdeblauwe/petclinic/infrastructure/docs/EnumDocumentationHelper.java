package com.wimdeblauwe.petclinic.infrastructure.docs;

import java.util.EnumSet;
import java.util.stream.Collectors;

public final class EnumDocumentationHelper {

  public static <T extends Enum<T>> String commaSeparated(Class<T> enumClass) {
    return EnumSet.allOf(enumClass)
        .stream()
        .map(type -> String.format("`%s`", type))
        .collect(Collectors.joining(", "));
  }

  private EnumDocumentationHelper() {
  }
}
