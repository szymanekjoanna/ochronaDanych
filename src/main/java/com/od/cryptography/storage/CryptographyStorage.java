package com.od.cryptography.storage;

import com.od.cryptography.errors.ValidationError;

import java.util.Arrays;

public final class CryptographyStorage {

    private static final String[] alfabet = {
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
//            "A", "Ą", "B", "C", "Ć", "D", "E", "Ę", "F", "G", "H", "I", "J", "K", "L", "Ł", "M", "N", "Ń", "O", "Ó", "P", "R", "S", "Ś", "T", "U", "W", "Y", "Z", "Ź", "Ż",
//            "a", "ą", "b", "c", "ć", "d", "e", "ę", "f", "g", "h", "i", "j", "k", "l", "ł", "m", "n", "ń", "o", "ó", "p", "r", "s", "ś", "t", "u", "w", "y", "z", "ź", "ż",
//            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"
    };

    private CryptographyStorage() {
    }

    public static String resolveShiftValue(String character, int shift) throws ValidationError {
        if(!Arrays.asList(alfabet).contains(character)){
            return character;
        }

        int length = alfabet.length;
        if(shift > length || -shift > length){
            throw new ValidationError("Przesunięcie jest większe niż długość alfabetu");
        }
        int newIndex = getIndex(character) + shift;
        if(newIndex > length-1){
            newIndex = newIndex - length;
        } else if(newIndex < 0){
            newIndex = newIndex + length;
        }
        return alfabet[newIndex];
    }

    public static int getIndex(String character) {
        return Arrays.asList(alfabet).indexOf(character);
    }

    public static int getAlphabetLenght() {
        return alfabet.length;
    }


}
