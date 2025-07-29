package com.wimdeblauwe.petclinic.infrastructure.error;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

public final class ErrorDocumentation {

  private ErrorDocumentation() {
  }

  public static ResponseFieldsSnippet responseFieldsSnippet() {
    return responseFields(
        ErrorDocumentation.codeField(),
        ErrorDocumentation.messageField()
    );
  }

  public static ResponseFieldsSnippet responseFieldsSnippetWithFieldErrors() {
    return responseFields(
        ErrorDocumentation.codeField(),
        ErrorDocumentation.messageField(),
        ErrorDocumentation.fieldErrorsField()
    ).andWithPrefix("fieldErrors[].",
                    ErrorDocumentation.codeField(),
                    ErrorDocumentation.messageField(),
                    ErrorDocumentation.propertyField(),
                    ErrorDocumentation.pathField(),
                    ErrorDocumentation.rejectedValueField());
  }

  public static FieldDescriptor codeField() {
    return fieldWithPath("code").description("Constant that indicates the problem. It is intended to be human and machine readable.");
  }

  public static FieldDescriptor messageField() {
    return fieldWithPath("message").description("Human-readable information about the problem.");
  }

  public static FieldDescriptor fieldErrorsField() {
    return fieldWithPath("fieldErrors").description("Detailed information on the fields in the request that failed the validation.");
  }

  public static FieldDescriptor propertyField() {
    return fieldWithPath("property").description("Name of the property that has a validation error.");
  }

  public static FieldDescriptor pathField() {
    return fieldWithPath("path").description("The full path of the property that has a validation error.");
  }

  public static FieldDescriptor rejectedValueField() {
    return fieldWithPath("rejectedValue")
        .description("The actual value that was present in the request. This type of field can be `null`, a number, a boolean or a string.")
        .optional() //rejectedValue is always present, but can have `null` value, so we need to mark it optional for Spring REST Docs.
        .type(JsonFieldType.VARIES);
  }
}
