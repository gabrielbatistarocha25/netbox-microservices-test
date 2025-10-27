package br.com.netbox.inventoryservice.domain.port.output;

import br.com.netbox.inventoryservice.domain.model.DeviceModel;
import java.util.List;
import java.util.Optional;

public interface DeviceModelRepositoryPort {
    DeviceModel save(DeviceModel deviceModel);
    List<DeviceModel> findAll();
    Optional<DeviceModel> findById(Long id);
}