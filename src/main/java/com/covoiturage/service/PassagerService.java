package com.covoiturage.service;

import com.covoiturage.dto.PassagerRequest;
import com.covoiturage.dto.PassagerResponse;
import java.util.List;

public interface PassagerService {

    PassagerResponse create(PassagerRequest request);

    List<PassagerResponse> findAll();

    PassagerResponse findById(Long id);




}