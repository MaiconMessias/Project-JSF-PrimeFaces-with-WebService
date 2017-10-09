/*******************************************************************************
 * Procedures that facilitate the creation of a OneMenu that lists the 
 * existing professions.
 ******************************************************************************/
package com.owl.dao.onemenu.interfaceonemenu;

import com.owl.entity.Cargo;

/** OneMenu Office Interface 
 * @version 1.0
 * @author Maicon Messias
 */
public interface OneMenuCargoInterface {
    void editaOneMenu(Cargo cargoFunci);
    void insereOneMenu();
    Cargo salvaOneMenu();
    void cancelaOneMenu();
}
