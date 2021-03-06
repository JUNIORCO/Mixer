package com.ecse428.project.controller;

import java.util.List;

import com.ecse428.project.model.Alcohol;
import com.ecse428.project.model.Cocktail;
import com.ecse428.project.model.Modifier;
import com.ecse428.project.service.CocktailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/cocktail")
public class CocktailController {
    private final CocktailService cocktailService;

    @Autowired
    public CocktailController(CocktailService cocktailService) {
        this.cocktailService = cocktailService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Cocktail> getCocktail() {
        return cocktailService.getCocktail();
    }

    // Not needed, left it just in case
    @RequestMapping(value = "/{cocktailName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Cocktail> getCocktailByNameContains(@PathVariable String cocktailName) {
        return cocktailService.getCocktailByNameContains(cocktailName);
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Cocktail> getCocktailByParams(@RequestParam(required = false) String cName, @RequestParam(required = false) List<String> alcohol, @RequestParam(required = false) List<String> modifier,
                                              @RequestParam(required = false) List<String> tasteType, @RequestParam(required = false) String strengthType,
                                              @RequestParam(required = false) String servingSize) {
        return cocktailService.getCocktailByParams(cName, alcohol, modifier, tasteType, strengthType, servingSize);
    }
}
