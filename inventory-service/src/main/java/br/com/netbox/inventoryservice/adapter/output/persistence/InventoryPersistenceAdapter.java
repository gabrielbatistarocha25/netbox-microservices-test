package br.com.netbox.inventoryservice.adapter.output.persistence;

import br.com.netbox.inventoryservice.adapter.output.persistence.entity.DeviceEntity;
import br.com.netbox.inventoryservice.adapter.output.persistence.entity.DeviceModelEntity;
import br.com.netbox.inventoryservice.adapter.output.persistence.entity.ManufacturerEntity;
import br.com.netbox.inventoryservice.adapter.output.persistence.mapper.DeviceMapper;
import br.com.netbox.inventoryservice.adapter.output.persistence.mapper.DeviceModelMapper;
import br.com.netbox.inventoryservice.adapter.output.persistence.mapper.ManufacturerMapper;
import br.com.netbox.inventoryservice.adapter.output.persistence.repository.DeviceJpaRepository;
import br.com.netbox.inventoryservice.adapter.output.persistence.repository.DeviceModelJpaRepository;
import br.com.netbox.inventoryservice.adapter.output.persistence.repository.ManufacturerJpaRepository;
import br.com.netbox.inventoryservice.domain.model.Device;
import br.com.netbox.inventoryservice.domain.model.DeviceModel;
import br.com.netbox.inventoryservice.domain.model.Manufacturer;
import br.com.netbox.inventoryservice.domain.port.output.DeviceModelRepositoryPort;
import br.com.netbox.inventoryservice.domain.port.output.DeviceRepositoryPort;
import br.com.netbox.inventoryservice.domain.port.output.ManufacturerRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class InventoryPersistenceAdapter implements ManufacturerRepositoryPort, DeviceModelRepositoryPort, DeviceRepositoryPort {

    private final ManufacturerJpaRepository manufacturerJpaRepository;
    private final DeviceModelJpaRepository deviceModelJpaRepository;
    private final DeviceJpaRepository deviceJpaRepository;

    private final ManufacturerMapper manufacturerMapper;
    private final DeviceModelMapper deviceModelMapper;
    private final DeviceMapper deviceMapper;

    public InventoryPersistenceAdapter(ManufacturerJpaRepository manufacturerJpaRepository, DeviceModelJpaRepository deviceModelJpaRepository, DeviceJpaRepository deviceJpaRepository, ManufacturerMapper manufacturerMapper, DeviceModelMapper deviceModelMapper, DeviceMapper deviceMapper) {
        this.manufacturerJpaRepository = manufacturerJpaRepository;
        this.deviceModelJpaRepository = deviceModelJpaRepository;
        this.deviceJpaRepository = deviceJpaRepository;
        this.manufacturerMapper = manufacturerMapper;
        this.deviceModelMapper = deviceModelMapper;
        this.deviceMapper = deviceMapper;
    }

    @Override
    public Manufacturer save(Manufacturer manufacturer) {
        ManufacturerEntity entity = manufacturerMapper.toEntity(manufacturer);
        return manufacturerMapper.toModel(manufacturerJpaRepository.save(entity));
    }

    @Override
    public List<Manufacturer> findAll() {
        return manufacturerJpaRepository.findAll().stream()
                .map(manufacturerMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Manufacturer> findById(Long id) {
        return manufacturerJpaRepository.findById(id).map(manufacturerMapper::toModel);
    }

    @Override
    public DeviceModel save(DeviceModel deviceModel) {
        DeviceModelEntity entity = deviceModelMapper.toEntity(deviceModel);
        if (entity.getManufacturer() != null && entity.getManufacturer().getId() != null) {
            manufacturerJpaRepository.findById(entity.getManufacturer().getId())
                .ifPresent(entity::setManufacturer);
        }
        return deviceModelMapper.toModel(deviceModelJpaRepository.save(entity));
    }

    @Override
    public List<DeviceModel> findAllDeviceModels() { 
        return deviceModelJpaRepository.findAll().stream()
                .map(deviceModelMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<DeviceModel> findDeviceModelById(Long id) { 
        return deviceModelJpaRepository.findById(id).map(deviceModelMapper::toModel);
    }

    @Override
    public Device save(Device device) {
        DeviceEntity entity = deviceMapper.toEntity(device);
        if (entity.getDeviceModel() != null && entity.getDeviceModel().getId() != null) {
            deviceModelJpaRepository.findById(entity.getDeviceModel().getId())
                .ifPresent(entity::setDeviceModel);
        }
        return deviceMapper.toModel(deviceJpaRepository.save(entity));
    }

    @Override
    public List<Device> findAllDevices() { 
        return deviceJpaRepository.findAll().stream()
                .map(deviceMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Device> findDeviceById(Long id) {
        return deviceJpaRepository.findById(id).map(deviceMapper::toModel);
    }

    @Override
    public List<Device> findBySiteId(Long siteId) { 
        return deviceJpaRepository.findBySiteId(siteId).stream()
                .map(deviceMapper::toModel)
                .collect(Collectors.toList());
    }
}