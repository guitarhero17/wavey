package com.wavey.api.user.errors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CustomFieldError {
    private String field;
    private String message;
}
