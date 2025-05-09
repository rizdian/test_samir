package com.riz.test_samir.web;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties({ "jsonString", "status", "mapper" })
@Data
public class ResponseWrapper<D> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ResponseWrapper.class);

	private ResponseWrapper(D data, ResponseStatus status) {
		Assert.notNull(status, "ResponseStatus cannot be null");
		this.status = status;
		this.code = String.valueOf(this.status.getCode());
		this.message = this.status.getMessage();
		this.data = data;
	}

	private final ObjectMapper mapper = new ObjectMapper();

	private final String code;
	private final String message;
	private final D data;

	@Getter(value = AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE)
	private ResponseStatus status;

	@Setter(value = AccessLevel.PRIVATE)
	private String jsonString;

	public static <D> ResponseWrapper<D> build(D data, ResponseStatus status) {
		return new ResponseWrapper<>(data, status);
	}

}
