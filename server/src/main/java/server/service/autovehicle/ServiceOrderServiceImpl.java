package server.service.autovehicle;

import lib.dto.autovehicle.PartDto;
import lib.dto.autovehicle.ServiceOrderDto;
import lib.dto.autovehicle.Status;
import lib.dto.bill.BillDto;
import lib.dto.bill.TotalPriceDto;
import lib.service.ServiceOrderService;
import server.convert.autovehicle.ServiceOrderConvertor;
import server.dao.impl.autovehicle.PartDaoImpl;
import server.dao.impl.autovehicle.ServiceOrderDaoImpl;
import server.dao.impl.autovehicle.VehicleDaoImpl;
import server.dao.impl.client.ClientDaoImpl;
import server.dao.impl.user.UserDaoImpl;
import server.dao.interfaces.*;
import server.model.autovehicle.ServiceOrder;
import server.model.autovehicle.Vehicle;
import server.model.client.Client;
import server.model.user.User;

import javax.persistence.Persistence;
import java.io.IOException;
import java.io.PrintStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class ServiceOrderServiceImpl extends UnicastRemoteObject implements ServiceOrderService {

    private final ServiceOrderDao serviceOrderDao;
    private final VehicleDao vehicleDao;
    private final UserDao userDao;
    private final ClientDao clientDao;
    private final PartDao partDao;


    public ServiceOrderServiceImpl() throws RemoteException {
        var entityManagerFactory = Persistence.createEntityManagerFactory("serviceAuto");
        var entityManager = entityManagerFactory.createEntityManager();

        serviceOrderDao = new ServiceOrderDaoImpl(entityManager);
        vehicleDao = new VehicleDaoImpl(entityManager);
        userDao = new UserDaoImpl(entityManager);
        clientDao = new ClientDaoImpl(entityManager);
        partDao = new PartDaoImpl(entityManager);
    }

    @Override
    public boolean createServiceOrder(ServiceOrderDto serviceOrderDto) throws RemoteException{
        ServiceOrder serviceOrder = ServiceOrderConvertor.convert(serviceOrderDto);

        Client client = clientDao.findClientById(serviceOrderDto.getClientDto().getId());
        Vehicle vehicle = vehicleDao.findById(serviceOrderDto.getVehicleDtos().getId());

        User user = userDao.findByName(serviceOrderDto.getUserDto().getUserId().getUserName())
                                    .orElseThrow(NoSuchElementException::new);

        serviceOrder.setClient(client);
        serviceOrder.setVehicle(vehicle);
        serviceOrder.setUser(user);

        return serviceOrderDao.createServiceOrder(serviceOrder);
    }

    @Override
    public ServiceOrderDto findById(int id) throws RemoteException{
        return ServiceOrderConvertor
                    .convert(serviceOrderDao.findById(id));
    }

    @Override
    public boolean updateServiceOrder(ServiceOrderDto serviceOrderDto) throws RemoteException{

        ServiceOrder serviceOrder = serviceOrderDao.findById(serviceOrderDto.getId());

        var parts =serviceOrderDto.getPartsIds().stream()
                .map(partDao::findPartById)
                .collect(Collectors.toList());

                serviceOrder.setParts(parts);

              return  serviceOrderDao.updateServiceOrder(serviceOrder);

    }

    @Override
    public void makeBill(List<PartDto> partsDtos, String path, BillDto billDto, TotalPriceDto totalPriceDto) throws RemoteException{
        try(PrintStream ps = new PrintStream(path)) {

            ps.println(billDto);

            for (PartDto part : partsDtos) {
                ps.println(part.toString());
            }

            ps.println(totalPriceDto);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int updateServiceOrderStatus(int orderId, Status status) throws RemoteException{
       return  serviceOrderDao.updateServiceOrderStatus(orderId, status);
    }

    @Override
    public List<Object[]> findAllServiceOrderIdAndStatus() throws RemoteException{
        return serviceOrderDao.findAllServiceOrderIdAndStatus();

    }

    @Override
    public int updateTotalPriceAndStatus(int orderId, double totalPrice, Status status) throws RemoteException {
       return serviceOrderDao.updateTotalPriceAndStatus(orderId, totalPrice, status);
    }

    @Override
    public ServiceOrderDto getPartsAndCarProblems(int id) throws RemoteException{
        serviceOrderDao.detachServiceOrder(id);
        ServiceOrder serviceOrder = serviceOrderDao.getPartsAndCarProblems(id);

        return ServiceOrderConvertor.convert(serviceOrder);

    }

    @Override
    public ServiceOrderDto getParts(int id) throws RemoteException{
        serviceOrderDao.detachServiceOrder(id);
        ServiceOrder serviceOrder = serviceOrderDao.getParts(id);

        return ServiceOrderConvertor.convertNoCarProblemsCollection(serviceOrder);
    }

    @Override
    public ServiceOrderDto findById2(int id) throws RemoteException{

       return ServiceOrderConvertor.convertIds(serviceOrderDao.findById2(id));

    }
}
