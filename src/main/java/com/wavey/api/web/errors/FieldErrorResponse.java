package com.wavey.api.web.errors;

import com.wavey.api.errors.CustomFieldError;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class FieldErrorResponse {
    private List<CustomFieldError> fieldErrors;
}
