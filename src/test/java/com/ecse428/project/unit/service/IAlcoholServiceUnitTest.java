package com.ecse428.project.unit.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import com.ecse428.project.model.Alcohol;
import com.ecse428.project.repository.AlcoholRepository;
import com.ecse428.project.service.AlcoholService;
import com.ecse428.project.service.IAlcoholService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
public class IAlcoholServiceUnitTest {

    @TestConfiguration
    static class IAlcoholServiceTestContextConfiguration {

        @Bean
        public AlcoholService alcoholService() {
            return new IAlcoholService();
        }
    }

    @Autowired
    private IAlcoholService alcoholService;

    @MockBean
    private AlcoholRepository alcoholRepository;

    @Before
    public void setUp() {
        Alcohol vodka = new Alcohol("Vodka");

        when(alcoholRepository.findAll()).thenReturn(Arrays.asList(vodka));
    }

    @Test
    public void whenValidName_thenAlcoholShouldBeFound() {
        String name = "Vodka";
        List<Alcohol> alcoholList = alcoholService.getAlcohol();
        boolean found = false;

        for (Alcohol a : alcoholList) {
            if (a.getName().equals(name)) {
                found = true;
                break;
            }
        }

        assertTrue(found);
    }

    @Test
    public void whenInValidName_thenAlcoholShouldBeFound() {
        String name = "Rum";
        List<Alcohol> alcoholList = alcoholService.getAlcohol();
        boolean found = false;

        for (Alcohol a : alcoholList) {
            if (a.getName().equals(name)) {
                found = true;
                break;
            }
        }

        assertFalse(found);
    }
}
