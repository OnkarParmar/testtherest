package com.digishaala.server.rest;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import com.fasterxml.classmate.TypeResolver;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import com.digishaala.server.dto.request.RestPublishRequestDTO;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

	@Autowired
	private TypeResolver typeResolver;

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.digishaala.server.controller"))
				.paths(PathSelectors.regex("/.*")).build().apiInfo(apiInfo()).alternateTypeRules(
						AlternateTypeRules.newRule(
								typeResolver.resolve(HashMap.class, String.class,
										typeResolver.resolve(HashMap.class, String.class,
												typeResolver.resolve(HashMap.class, String.class,
														typeResolver.resolve(List.class,
																RestPublishRequestDTO.class)))),
								typeResolver.resolve(HashMap.class, String.class, WildcardType.class),
								Ordered.HIGHEST_PRECEDENCE));
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Digishaala Server API Documentation").description("Spring Boot REST APIs")
				.contact(new Contact("Onkar Parmar", "https://digishaala.com/", "onkar.parmar@iachieve.in"))
				.license("Apache 2.0").licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html").version("1.0.0")
				.build();
	}
}
