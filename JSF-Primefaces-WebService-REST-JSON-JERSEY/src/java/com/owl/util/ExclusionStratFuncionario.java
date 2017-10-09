/*******************************************************************************
 * Class responsible for eliminating the cross reference so that it is possible 
 * to use the Gson lib. Exclusion Strategy.
 ******************************************************************************/
package com.owl.util;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.owl.entity.ContatoFuncionario;
import com.owl.entity.Funcionario;
import com.owl.entity.Usuario;
import com.owl.entity.Venda;

/** Exclusion Strategy of Employee
 * @version 1.0
 * @author Maicon Messias
 */
public class ExclusionStratFuncionario implements ExclusionStrategy {

    @Override
    public boolean shouldSkipClass(Class<?> type) {
        return false;
    }

    @Override
    public boolean shouldSkipField(FieldAttributes f) {
//        return (f.getDeclaringClass() == Option.class && f.getName().equals("poll"))||
//            (f.getDeclaringClass() == Country.class && f.getName().equals("name"));
        return (f.getDeclaringClass() == ContatoFuncionario.class && f.getName().equals("funci")) ||
                (f.getDeclaringClass() == Venda.class && f.getName().equals("funci")) ||
                (f.getDeclaringClass() == Usuario.class && f.getName().equals("funci")) ||
                (f.getDeclaringClass() == Funcionario.class && f.getName().equals("cargo"));
    }

    
}
