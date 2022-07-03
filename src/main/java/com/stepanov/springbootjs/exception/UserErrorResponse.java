package com.stepanov.springbootjs.exception;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserErrorResponse {

    private int status;
    private String message;
    private Long timestamp;
}
