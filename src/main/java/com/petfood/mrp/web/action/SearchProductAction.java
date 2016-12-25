package com.petfood.mrp.web.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.petfood.mrp.model.Customer;
import com.petfood.mrp.model.Product;
import com.petfood.mrp.web.manager.CustomerManager;
import com.petfood.mrp.web.manager.ProductManager;

@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller("web.searchProductAction")
public class SearchProductAction extends AbstractAction {

    @Autowired
    private CustomerManager customerManager;

    @Autowired
    private ProductManager productManager;

    private List<Customer> customers;
    private List<Product> products;
    private String cusCode;
    private String proName;

    @Override
    public String execute() {
        customers = customerManager.getAllCustomers();
        return SUCCESS;
    }

    public String search() {
        products = productManager.searchProduct(cusCode, proName);
        return execute();
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public List<Product> getProducts() {
        return products;
    }

    public String getCusCode() {
        return cusCode;
    }

    public void setCusCode(String cusCode) {
        this.cusCode = cusCode;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

}
