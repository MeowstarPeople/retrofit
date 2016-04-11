package com.yong.rest;

import com.yong.rest.Dto.Dto;

public class RestBean<T> extends Dto {

	private static final long serialVersionUID = 7647095138431194179L;
	public int showapi_res_code;

	public String showapi_res_error;

	public T showapi_res_body;

	@Override
	public String toString() {
		return "Res [showapi_res_code=" + showapi_res_code
				+ ", showapi_res_error=" + showapi_res_error
				+ ", showapi_res_body=" + showapi_res_body + "]";
	}



}
