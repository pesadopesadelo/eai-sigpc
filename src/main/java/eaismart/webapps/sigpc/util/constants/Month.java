/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eaismart.webapps.sigpc.util.constants;

/**
 *
 * @author elton
 */
public enum Month {

    JANUARY("Diario_Jan"),
    FEBRUARY("Diario_Fev"),
    MARCH("Diario_Mar"),
    APRIL("Diario_Abr"),
    MAY("Diario_Mai"),
    JUNE("Diario_Jun"),
    JULY("Diario_Jul"),
    AUGUST("Diario_Ago"),
    SEPTEMBER("Diario_Set"),
    OCTOBER("Diario_Out"),
    NOVEMBER("Diario_Nov"),
    DECEMBER("Diario_Dez");

    private final String value;

    private Month(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
    
    public static Month getMonthByValue(String value) {
    	for(Month month : Month.values()) {
    		if(month.getValue().equals(value)) 
    			return month;
    	}
    	return null; 
    }
    
    public static Month returnMonth(Month month) {

        if (month.equals(Month.JANUARY)) {
            return Month.DECEMBER;
        } else if (month.equals(Month.FEBRUARY)) {
            return Month.JANUARY;
        } else if (month.equals(Month.MARCH)) {
            return Month.FEBRUARY;
        } else if (month.equals(Month.APRIL)) {
            return Month.MARCH;
        } else if (month.equals(Month.MAY)) {
            return Month.APRIL;
        } else if (month.equals(Month.JUNE)) {
            return Month.MAY;
        } else if (month.equals(Month.JULY)) {
            return Month.JUNE;
        } else if (month.equals(Month.AUGUST)) {
            return Month.JULY;
        } else if (month.equals(Month.SEPTEMBER)) {
            return Month.AUGUST;
        } else if (month.equals(Month.OCTOBER)) {
            return Month.SEPTEMBER;
        } else if (month.equals(Month.NOVEMBER)) {
            return Month.OCTOBER;
        } else if (month.equals(Month.DECEMBER)) {
            return Month.NOVEMBER;
        }
        return null;
    }
    
    public static void main(String[] args) {
    	Month MES_ACTUAL = Month.DECEMBER; 
    	Month MES_ANTERIOR = MES_ACTUAL == Month.JANUARY ? Month.DECEMBER : Month.values()[MES_ACTUAL.ordinal() - 1]; 
		System.out.println(MES_ANTERIOR);
	}
}
