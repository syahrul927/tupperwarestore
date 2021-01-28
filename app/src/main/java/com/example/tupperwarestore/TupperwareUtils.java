package com.example.tupperwarestore;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class TupperwareUtils {

    public static  String moneyFormatter(Double money){

        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);
        return kursIndonesia.format(money);
    }
}
