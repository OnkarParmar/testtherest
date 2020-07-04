package com.digishaala.server.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.digishaala.server.dto.request.RestPublishRequestDTO;
import com.digishaala.server.service.RestMasterService;
import com.digishaala.server.exception.CustomMessageException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/rest")
@Api(value = "Rest Management System", description = "Operations related to the rest")
public class RestMasterController {

	@Autowired
	RestMasterService restService;

	@PostMapping("/publish")
	@ApiOperation(value = "Publish Rest")
	public String publishRest(@Valid @RequestBody RestPublishRequestDTO restPublishRequestDTO) throws CustomMessageException {
		return restService.publishRestService(restPublishRequestDTO);
	}
}
