package com.wavey.api.user.web.errors;

import com.wavey.api.user.errors.CustomFieldError;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.FieldError;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class FieldErrorResponse {
    private List<CustomFieldError> fieldErrors;
}
