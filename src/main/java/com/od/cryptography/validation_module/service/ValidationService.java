package com.od.cryptography.validation_module.service;

import com.od.cryptography.common.errors.ValidationError;
import com.od.cryptography.validation_module.model.ValidationResult;
import com.od.cryptography.validation_module.utils.ValidationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ValidationService {

    private final ValidationHelper validationHelper;

    @Autowired
    public ValidationService(ValidationHelper validationHelper) {
        this.validationHelper = validationHelper;
    }

    public ValidationResult validatePesel(String pesel) {
        if (pesel.length() != 11) {
            return new ValidationResult("Niepoprawna długość nr Pesel", false);
        }

//        if (!checkDate(pesel)) {
//            return new ValidationResult("Niepoprawna data urodzenia", false);
//        }

        if (!checkControlSum(pesel)) {
            return new ValidationResult("Niepoprawny pesel, niepoprawna liczba kontrolna", false);
        }

        return new ValidationResult(true);
    }

    private boolean checkDate(String pesel) {
        int day = Integer.parseInt(pesel.substring(0, 2));
        int month = Integer.parseInt(pesel.substring(2, 4));
        if (day < 1 || day > validationHelper.getDaysOfEachMonth().get(month)) {
            return false;
        }
        return true;
    }

    private boolean checkControlSum(String pesel) {
        int[] weights = validationHelper.getPeselNumberWeights();
        int sum = 0;
        for (int i = 0; i < pesel.length() - 1; i++) {
            int val = Integer.parseInt(Character.toString(pesel.charAt(i)));
            sum += val * weights[i];
        }

        sum %= 10;
        sum = 10 - sum;
        sum %= 10;

        return sum == Integer.parseInt(Character.toString(pesel.charAt(10)));
    }

    public ValidationResult calculateLuhna(String luhna) {
        if (luhna.contains("-")) {
            return new ValidationResult(validateLuhna(luhna));
        }
        String controlSum = calculateLuhnaCheckSum(luhna);
        return new ValidationResult(luhna + "-" + controlSum);
    }

    private boolean validateLuhna(String luhna) {
        String[] values = luhna.split("-");
        String luhnaValue = values[0];
        String controlSumValue = values[1];

        return Objects.equals(controlSumValue, calculateLuhnaCheckSum(luhnaValue));
    }

    private String calculateLuhnaCheckSum(String luhna) {
        int sum = 0;
        for (int i = 0; i < luhna.length(); i++) {
            int digit = Integer.parseInt(Character.toString(luhna.charAt(i)));
            int weight = i % 2 == 0 ? 2 : 1;

            int val = digit * weight;
            if (val > 9) {
                int temp = val % 10;
                val = val / 10 + temp;
            }
            sum += val;
        }
        sum %= 10;

        return sum == 0 ? "0" : String.valueOf(10 - sum);
    }

    public ValidationResult calculateCrc(String crcSum, String crcDivider) throws ValidationError {
        String byteArrayRegexp = "^[0-1]*$";
        if (!crcSum.matches(byteArrayRegexp) || !crcDivider.matches(byteArrayRegexp)) {
            throw new ValidationError("Można podać tylko ciąg bitów");
        }

        int i, j, flag = 0;

        int dividend_length = crcSum.length();
        int divisor_length = crcDivider.length();

        int dividend_array[] = new int[dividend_length + divisor_length - 1];
        int send_arr[] = new int[dividend_length + divisor_length - 1];
        int divisor_array[] = new int[divisor_length];

        for (i = 0; i < dividend_length; i++) {
            dividend_array[i] = Integer.parseInt("" + crcSum.charAt(i));
            send_arr[i] = dividend_array[i];
        }

        for (i = dividend_length; i < dividend_array.length; i++) {
            dividend_array[i] = 0;
        }

        for (i = 0; i < divisor_length; i++) {
            divisor_array[i] = Integer.parseInt("" + crcDivider.charAt(i));
        }

        int temp[] = new int[divisor_length];
        for (i = 0; i < dividend_array.length; i++) {
            if (dividend_array[i] == 1) {
                for (j = 0; j < divisor_length; j++) {
                    if (i + j > dividend_array.length - 1) {
                        flag = 1;
                        break;
                    } else {
                        temp[j] = dividend_array[i + j];
                    }
                }

                if (flag == 0) {
                    for (j = 0; j < divisor_length; j++) {
                        if (temp[j] == divisor_array[j]) {
                            dividend_array[i + j] = 0;
                        } else {
                            dividend_array[i + j] = 1;
                        }
                    }
                }
            }
        }

        for (i = dividend_length; i < dividend_array.length; i++) {
            send_arr[i] = dividend_array[i];
        }

        String result = "";
        for (i = 0; i < send_arr.length; i++) {
            result += send_arr[i];
        }
        return new ValidationResult(result);
    }
}
