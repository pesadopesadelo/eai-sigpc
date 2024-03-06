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
public enum BalanceSheetType {

    Monthly("Monthly"),
    Quarter("Quarter");
	//Flow("Flow"), // Fluxo
	//Budget("budget"); //Orcamental

    private final String value;

    private BalanceSheetType(String value) {
        this.value = value;
    }

    private String getValue() {
        return value;
    }
}
