package com.ecse428.project.unit.controller;

import java.util.Arrays;
import java.util.List;

import com.ecse428.project.auth.UserDetailsServiceImpl;
import com.ecse428.project.controller.AlcoholController;
import com.ecse428.project.model.Alcohol;
import com.ecse428.project.service.IAlcoholService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AlcoholController.class)
public class AlcoholControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private IAlcoholService service;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @Test
    @WithMockUser
    public void givenAlcohol_whenGetAlcohols_thenReturnJsonArray() throws Exception {

        Alcohol vodka = new Alcohol("Vodka");

        List<Alcohol> allAlcohols = Arrays.asList(vodka);

        given(service.getAlcohol()).willReturn(allAlcohols);

        mvc.perform(MockMvcRequestBuilders
                .get("/api/alcohol")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(vodka.getName())));
    }
}