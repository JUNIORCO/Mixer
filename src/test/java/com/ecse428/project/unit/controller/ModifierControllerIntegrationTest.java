package com.ecse428.project.unit.controller;

import java.util.Arrays;
import java.util.List;

import com.ecse428.project.auth.UserDetailsServiceImpl;
import com.ecse428.project.controller.ModifierController;
import com.ecse428.project.model.Modifier;
import com.ecse428.project.model.Modifier.ModifierType;
import com.ecse428.project.service.IModifierService;

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
@WebMvcTest(ModifierController.class)
public class ModifierControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private IModifierService service;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @Test
    @WithMockUser
    public void givenModifiers_whenGetModifiers_thenReturnJsonArray() throws Exception {

        Modifier redBull = new Modifier("Red Bull", ModifierType.SMOOTHING_AGENT);

        List<Modifier> allModifiers = Arrays.asList(redBull);

        given(service.getModifiers()).willReturn(allModifiers);

        mvc.perform(MockMvcRequestBuilders
                .get("/api/modifier")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(redBull.getName())));
    }
}