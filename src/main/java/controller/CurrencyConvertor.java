/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import exceptions.EntityAlreadyExistsException;
import integration.DatabaseAccessObject;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import model.Currency;

@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Singleton
public class CurrencyConvertor {

    @EJB DatabaseAccessObject dao;
    
    public void createCurrency(String name, double rate) throws EntityAlreadyExistsException {
        dao.storeCurrency(new Currency(name, rate));
    }
    
    public void deleteCurrency(long id) {
        dao.deleteCurrency(id);
    }
    
    public double convert(long id_from, long id_to, double amount) {
        return dao.getCurrency(id_from).getRate() / dao.getCurrency(id_to).getRate() * amount;
    }
    
    public List<Currency> getCurrencies() {
        return dao.getAllCurrencies();
    }
}
