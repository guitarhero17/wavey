package com.wavey.api.user.exceptions;

public class FieldCanNotBePatchedException extends RuntimeException{

	public FieldCanNotBePatchedException(String field) {
		super("The field " + field + " can not be patched. Please remove it from the request and try again.");
	}

}
