package com.digishaala.server.service.impl;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.IOException;

import com.digishaala.server.dto.request.RestPublishRequestDTO;
import com.digishaala.server.rest.RestTemplateService;
import com.digishaala.server.service.RestMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestMasterServiceImpl implements RestMasterService {
    @Autowired
    RestTemplateService restTemplateService;

    @Override
    public String publishRestService(RestPublishRequestDTO restPublishRequestDTO) {
	String response = "Did not run";
        System.out.println(restPublishRequestDTO);
	try {
		if (restPublishRequestDTO.library.equals("httpclient")) {
        		response = restTemplateService.restTemplateAuthToken(restPublishRequestDTO.url, restPublishRequestDTO.username, restPublishRequestDTO.password);
		} else {
        		response = restTemplateService.httpClientAuthToken(restPublishRequestDTO.url, restPublishRequestDTO.username, restPublishRequestDTO.password);
		}
        	System.out.println(response);
		return response;
	} catch (IOException e) {
		return e.getMessage();
	}
    }
}
