package com.ecse428.project.unit.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import com.ecse428.project.model.Modifier;
import com.ecse428.project.repository.ModifierRepository;
import com.ecse428.project.service.IModifierService;
import com.ecse428.project.service.ModifierService;
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
public class IModifierServiceUnitTest {

    @TestConfiguration
    static class IModifierServiceTestContextConfiguration {

        @Bean
        public ModifierService modifierService() {
            return new IModifierService();
        }
    }

    @Autowired
    private IModifierService modifierService;

    @MockBean
    private ModifierRepository modifierRepository;

    @Before
    public void setUp() {
        Modifier coke = new Modifier("Cococola", Modifier.ModifierType.SMOOTHING_AGENT);

        when(modifierRepository.findAll()).thenReturn(Arrays.asList(coke));
    }

    @Test
    public void whenValidName_thenAlcoholShouldBeFound() {
        String name = "Cococola";
        List<Modifier> modifierList = modifierService.getModifiers();
        boolean found = false;

        for (Modifier m : modifierList) {
            if (m.getName().equals(name)) {
                found = true;
                break;
            }
        }

        assertTrue(found);
    }

    @Test
    public void whenInValidName_thenAlcoholShouldBeFound() {
        String name = "Coke";
        List<Modifier> modifierList = modifierService.getModifiers();
        boolean found = false;

        for (Modifier m : modifierList) {
            if (m.getName().equals(name)) {
                found = true;
                break;
            }
        }

        assertFalse(found);
    }
}
