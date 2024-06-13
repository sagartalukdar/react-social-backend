package com.react.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDetails {

	private String message;
	private String Error;
	private LocalDateTime timeStamp;
}
