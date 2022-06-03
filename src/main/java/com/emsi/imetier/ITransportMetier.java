package com.emsi.imetier;

import java.util.List;

import com.emsi.entities.Transport;
import org.springframework.data.domain.Page;


public interface ITransportMetier 
{ 
	public List<Transport> findAllTransports();
	public Page<Transport> findAllTransports(int page, int size ); 
	public Transport getTransport( Long code );   
	public Transport saveTransport(Transport transport);
	public boolean deleteTransport(Long ref);
}
