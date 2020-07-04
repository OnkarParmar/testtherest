package com.digishaala.server.service;

import java.util.List;

import javax.validation.Valid;

import com.digishaala.server.dto.request.RestPublishRequestDTO;
import com.digishaala.server.exception.CustomMessageException;

public interface RestMasterService {
	String publishRestService(RestPublishRequestDTO restPublishRequestDTO) throws CustomMessageException;
}
