package com.od.cryptography.service;

import com.od.cryptography.errors.ValidationError;
import com.od.cryptography.storage.CryptographyStorage;
import org.springframework.stereotype.Service;


@Service
public class CryptographyService {

    private static final String SPACE = " ";

    public String cesarEncryption(String text, int shift) throws ValidationError {
        String result = "";
        for (int i = 0; i < text.length(); i++) {
          result += CryptographyStorage.resolveShiftValue(String.valueOf(text.charAt(i)), shift);
        }
        return result;
    }

    public String cesarDecryption(String text, int shift) throws ValidationError {
        String result = "";
        for (int i = 0; i < text.length(); i++) {
            result += CryptographyStorage.resolveShiftValue(String.valueOf(text.charAt(i)), -shift);
        }
        return result;
    }

    public String vinegereEncryption(String text, String key) throws ValidationError {
        String preparedKey = prepareKey(text, key);
        String result = "";
        for (int i = 0; i < text.length(); i++) {
            result += CryptographyStorage.resolveShiftValue(String.valueOf(text.charAt(i)),
                    CryptographyStorage.getIndex(String.valueOf(preparedKey.charAt(i))));
        }

        return result;
    }

    public String vinegereDecryption(String text, String key) throws ValidationError {
        String preparedKey = prepareKey(text, key);
        String result = "";
        for (int i = 0; i < text.length(); i++) {
            result += CryptographyStorage.resolveShiftValue(String.valueOf(text.charAt(i)),
                    CryptographyStorage.getAlphabetLenght() - CryptographyStorage.getIndex(String.valueOf(preparedKey.charAt(i))));
        }

        return result;
    }

    private String prepareKey(String text, String key) throws ValidationError {
        if(key.length() > text.length()){
            throw new ValidationError("Klucz jest dłuższy niż text to szyfrowania");
        }

        String result = "";
        int counter = 0;
        for (int i = 0; i < text.length(); i++){
            if(text.charAt(i) == 32){
                result += SPACE;
            } else {
                result += key.charAt(counter);
                counter++;
            }
            if(counter >= key.length()){
                counter = 0;
            }
        }

        return result;
    }
}
