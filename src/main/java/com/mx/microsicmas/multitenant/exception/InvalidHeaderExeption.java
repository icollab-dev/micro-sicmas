package com.mx.microsicmas.multitenant.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "ID Tenant not found in reques Header!")
public class InvalidHeaderExeption extends RuntimeException  {
}
