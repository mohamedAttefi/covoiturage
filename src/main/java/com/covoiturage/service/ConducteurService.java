package com.covoiturage.service;

import com.covoiturage.dto.ConducteurRequest;
import com.covoiturage.dto.ConducteurResponse;
import java.util.List;

public interface ConducteurService {

    ConducteurResponse create(ConducteurRequest request);

    List<ConducteurResponse> findAll();

    ConducteurResponse findById(Long id);

    ConducteurResponse update(Long id, ConducteurRequest request);


}