package server.dao.interfaces;

import lib.dto.autovehicle.Status;
import server.model.autovehicle.ServiceOrder;

import java.util.List;

public interface ServiceOrderDao {
    boolean createServiceOrder(ServiceOrder serviceOrder);

    ServiceOrder findById(int id);

    boolean updateServiceOrder(ServiceOrder serviceOrder);

    int updateServiceOrderStatus(int orderId, Status status);

    List<Object[]> findAllServiceOrderIdAndStatus();

    int updateTotalPriceAndStatus(int orderId, double totalPrice, Status status);

    void detachServiceOrder(int id);

    ServiceOrder getPartsAndCarProblems(int id);

    ServiceOrder getParts(int id);

    ServiceOrder findById2(int id);
}
