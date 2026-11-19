package com.covoiturage.controller;

import com.covoiturage.dto.ConducteurResponse;
import com.covoiturage.service.ConducteurService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.covoiturage.dto.ConducteurRequest;
import org.springframework.http.MediaType;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.verifyNoInteractions;

@WebMvcTest(ConducteurController.class)
class ConducteurControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    ConducteurService service;






}