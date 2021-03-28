package com.ecse428.project.unit.controller;

import java.util.Arrays;
import java.util.List;

import com.ecse428.project.auth.UserDetailsServiceImpl;
import com.ecse428.project.controller.CocktailController;
import com.ecse428.project.model.Alcohol;

import com.ecse428.project.model.Cocktail;
import com.ecse428.project.model.Modifier;
import com.ecse428.project.service.IAlcoholService;
import com.ecse428.project.service.ICocktailService;
import com.ecse428.project.service.IModifierService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CocktailController.class)
public class GenerateCocktailControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ICocktailService cocktailService;

    @MockBean
    private IModifierService modifierService;

    @MockBean
    private IAlcoholService alcoholService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @Test
    @WithMockUser
    public void givenInventoryAndPreferences_whenGetCocktails_thenReturnJsonArray() throws Exception {

//        Alcohol vodka = new Alcohol("Vodka");
//        Modifier redBull = new Modifier("RedBull", Modifier.ModifierType.SODA);
//
//        List<Alcohol> alcohols = Arrays.asList(vodka);
//        List<Modifier> modifiers = Arrays.asList(redBull);
//
//        Cocktail vodka_redBull = new Cocktail("Vodka RedBull", alcohols, modifiers);
//        List<Cocktail> allCocktails = Arrays.asList(vodka_redBull);
//
////        given(alcoholService.getAlcohol()).willReturn(alcohols);
////        given(modifierService.getModifiers()).willReturn(modifiers);
//        given(cocktailService.getCocktail()).willReturn(allCocktails);
//
//        mvc.perform(MockMvcRequestBuilders
//                .get("/api/cocktail")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
////                .andExpect(jsonPath("$", hasSize(1)))
////                .andExpect(jsonPath("$[0].name", is(vodka_redBull.getName())));

        String uri_req = "/api/cocktail";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("cName", "");
        map.add("alcohol", "bourbon");
        map.add("modifier", "sugar syrup");
        map.add("tasteType", "");
        map.add("strengthType", "");
        map.add("servingSize", "");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
    }

    @Test
    @WithMockUser
    public void queryCocktailByAlcoholType() throws Exception{
        String uri_req = "/api/cocktail";
        Alcohol vodka = new Alcohol("Vodka");
        Modifier redBull = new Modifier("RedBull", Modifier.ModifierType.SODA);
        List<Alcohol> alcohols = Arrays.asList(vodka);
        List<Modifier> modifiers = Arrays.asList(redBull);
        List<String> tasteType = Arrays.asList("");
        List<String> alcoholList = Arrays.asList("Vodka");
        List<String> modifierList = Arrays.asList("RedBull");
        Cocktail vodka_redBull = new Cocktail("Vodka RedBull", alcohols, modifiers);
        List<Cocktail> allCocktails = Arrays.asList(vodka_redBull);
        
        given(cocktailService.getCocktailByParams("",alcoholList,modifierList,tasteType,"","")).willReturn(allCocktails);
        mvc.perform(MockMvcRequestBuilders.post(uri_req))
                .andExpect(status().isOk());
    }
}