package com.emsi.conversions;

import com.emsi.imetier.IClientMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import com.emsi.entities.Client;

public final class ClientConverter implements Converter<String, Client> {

	@Autowired private IClientMetier mc;
    public ClientConverter(IClientMetier mc) {this.mc = mc;} 
    @Override public Client convert(String id) { return mc.getClient(id); }
}