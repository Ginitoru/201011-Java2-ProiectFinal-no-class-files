package server.dao.interfaces;

import server.model.autovehicle.Vehicle;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface VehicleDao {

    boolean createVehicle(Vehicle vehicle);

    Vehicle findById(int id);

    Optional<Vehicle> findBySerialNumber(String serialNumber);

    Collection<Vehicle> findAllVehicles();

    List<Object[]> findVehicleWithClient(String serialNumber);
}
