package server.service.client;

import lib.dto.client.ClientDto;
import lib.service.ClientService;
import server.convert.client.ClientConvertor;
import server.dao.interfaces.ClientDao;
import server.dao.impl.client.ClientDaoImpl;

import javax.persistence.Persistence;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.NoSuchElementException;

public class ClientServiceImpl extends UnicastRemoteObject implements ClientService {

    private final ClientDao clientDao;

    public ClientServiceImpl() throws RemoteException {
        var entityManagerFactory = Persistence.createEntityManagerFactory("serviceAuto");
        var entityManager = entityManagerFactory.createEntityManager();

        clientDao = new ClientDaoImpl(entityManager);
    }

    @Override
    public ClientDto findClientByName(String name) throws RemoteException{

       return clientDao.findByName(name)
                    .map(ClientConvertor::convert)
                    .orElseThrow(NoSuchElementException::new);
    }

}
