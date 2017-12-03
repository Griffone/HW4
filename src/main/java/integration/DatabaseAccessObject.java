/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integration;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import model.Currency;

@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Stateless
public class DatabaseAccessObject {
    @PersistenceContext(unitName = "conversionsPU")
    private EntityManager em;
    
    public Currency getCurrency(String currencyName) {
        Currency currency = em.createNamedQuery("findCurrencyByName", Currency.class).setParameter("name", currencyName).getSingleResult();
        if (currency == null)
            throw new EntityNotFoundException("Could not find \"" + currencyName + "\" currency!");
        return currency;
    }
    
    public void storeCurrency(Currency currency) {
        em.persist(currency);
    }
}
