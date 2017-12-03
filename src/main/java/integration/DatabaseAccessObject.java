/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integration;

import exceptions.EntityAlreadyExistsException;
import java.util.List;
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
    
    public Currency getCurrency(Long currencyID) {
        Currency currency = em.find(Currency.class, currencyID);
        if (currency == null)
            throw new EntityNotFoundException("Could not find currency with id: " + String.valueOf(currencyID));
        return currency;
    }
    
    public void storeCurrency(Currency currency) throws EntityAlreadyExistsException {
        if (em.createNamedQuery("findCurrencyByName", Currency.class).setParameter("name", currency.getName()).getSingleResult() == null)
            em.persist(currency);
        else
            throw new EntityAlreadyExistsException(currency.getName() + " already exists in the database!");
    }
    
    public List<Currency> getAllCurrencies() {
        return em.createNamedQuery("getAllCurrencies", Currency.class).getResultList();
    }
}
