package com.ecse428.project.unit.controller;

import java.util.*;

import com.ecse428.project.auth.UserDetailsServiceImpl;
import com.ecse428.project.controller.ModifierController;
import com.ecse428.project.controller.UserController;
import com.ecse428.project.model.Modifier;
import com.ecse428.project.model.User;
import com.ecse428.project.model.Alcohol;
import com.ecse428.project.model.Modifier.ModifierType;
import com.ecse428.project.service.IModifierService;
import com.ecse428.project.service.IUserService;

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
    private IUserService userService;

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

    @Test
    @WithMockUser
    public void givenModifiers_whenDeleteModifiers_thenReturnNewJsonArray() throws Exception {
        long num = 100L;
        // Optional<User> user = userRepository.findById(userId);
        Modifier redBull = new Modifier("RedBull", ModifierType.SMOOTHING_AGENT);
        Modifier madiera = new Modifier("Madiera", ModifierType.FORTIFIED_WINE);
        Modifier orangeJuice = new Modifier("OrangeJuice", ModifierType.JUICE);
        Set<Modifier> modifiersInInventory = new HashSet<Modifier>();
        modifiersInInventory.add(redBull);
        modifiersInInventory.add(madiera);
        modifiersInInventory.add(orangeJuice);
        Set<Alcohol> alcoholInInventory = new HashSet<Alcohol>();
        User newUser = new User(num,"newUser@gmail.com","abcdefg", alcoholInInventory, modifiersInInventory);

        given(userService.getModifiersInInventory(num)).willReturn(modifiersInInventory);

        String uri_req = "/api/user/100L/modifier/Madiera";
        mvc.perform(MockMvcRequestBuilders.delete(uri_req))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }
}