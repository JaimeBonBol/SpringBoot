package com.PracticaPropuesta1.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador para verificar palíndormos.
 */
@RestController
public class PalindromeController {

    /**
     * Endpoint para verificar si una palabra es palíndromo o no.
     * @param word La palabra a verificar.
     * @return Un mensaje indicando si la palabra es un palíndormo o no.
     */
    @GetMapping("/validar-palindrome/{word}")
    public String palindorme(@PathVariable String word){
        if (isPalindrome(word)){
            return "La palabra "+ word + " es un palíndromo";
        }else {
            return "La palabra "+ word + " NO es un palíndromo";
        }
    }

    /**
     * Método para verificar si una palabra es un palíndromo.
     * @param word La palabra a verificar.
     * @return true si la palraba es palíndromo o false en caso de que no lo sea.
     */
    public boolean isPalindrome(String word){
        int length = word.length();

        for (int i = 0; i < length / 2; i++) {
            if (word.charAt(i) != word.charAt(length - i - 1)){
                return false;
            }
        }
        return true;
    }

}
