package com.digishaala.server.dto.request;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RestPublishRequestDTO {
	@NotNull
	public String url;

	@NotNull
	public String username;

	@NotNull
	public String password;

	public String library;
}
