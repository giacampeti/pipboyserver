/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.sapienza.softeng.api.withjson.and.dbms;
import com.fasterxml.jackson.jaxrs.json.*;
import java.util.*;
import org.apache.cxf.binding.BindingFactoryManager;
import org.apache.cxf.jaxrs.*;
import org.apache.cxf.jaxrs.lifecycle.*;

/**
 * simple rest server with jaxb
 * @author pablohp
 */
public class Server {

    public static void main(String args[]) throws Exception {

        /* Repository holds Services methods, create instance then set connection */
        JAXRSServerFactoryBean factoryBean = new JAXRSServerFactoryBean();
        factoryBean.setResourceClasses(PipBoyRepository.class);
        PipBoyRepository wr = new PipBoyRepository();
        wr.setConnection();

        /* Repository MUST be a singleton */
        factoryBean.setResourceProvider(new SingletonResourceProvider(wr));

        /* set address to host */
        factoryBean.setAddress("https://localhost:8080/");

        /* set providers */
        List<Object> providers = new ArrayList<Object>();
        providers.add(new JacksonJaxbJsonProvider());
        factoryBean.setProviders(providers);

        /* set binding and cache server */
        BindingFactoryManager manager = factoryBean.getBus().getExtension(BindingFactoryManager.class);
        JAXRSBindingFactory restFactory = new JAXRSBindingFactory();
        restFactory.setBus(factoryBean.getBus());
        manager.registerBindingFactory(JAXRSBindingFactory.JAXRS_BINDING_ID, restFactory);
        org.apache.cxf.endpoint.Server server = factoryBean.create();

        /* just to be sure */
        System.out.println("Server ready...");

        /* keeps running indefinitely, ideally should set timeout */
        while (true) {
        }
    }
}
