package com.covoiturage.service;

import com.covoiturage.dto.TrajetRequest;
import com.covoiturage.dto.TrajetResponse;
import java.util.List;

public interface TrajetService {

    TrajetResponse create(TrajetRequest request);

    List<TrajetResponse> findAll();




}