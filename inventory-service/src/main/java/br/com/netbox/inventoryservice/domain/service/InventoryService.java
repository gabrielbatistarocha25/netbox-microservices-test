package br.com.netbox.inventoryservice.domain.service;

import br.com.netbox.inventoryservice.domain.model.Device;
import br.com.netbox.inventoryservice.domain.model.DeviceModel;
import br.com.netbox.inventoryservice.domain.model.Manufacturer;
import br.com.netbox.inventoryservice.domain.port.input.InventoryUseCase;
import br.com.netbox.inventoryservice.domain.port.output.DeviceModelRepositoryPort;
import br.com.netbox.inventoryservice.domain.port.output.DeviceRepositoryPort;
import br.com.netbox.inventoryservice.domain.port.output.ManufacturerRepositoryPort;
import br.com.netbox.inventoryservice.domain.port.output.OrganizationApiPort;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService implements InventoryUseCase {

    private final DeviceRepositoryPort deviceRepository;
    private final ManufacturerRepositoryPort manufacturerRepository;
    private final DeviceModelRepositoryPort deviceModelRepository;

    // Injeta a porta para a API externa
    private final OrganizationApiPort organizationApiPort;

    public InventoryService(DeviceRepositoryPort deviceRepository, ManufacturerRepositoryPort manufacturerRepository, DeviceModelRepositoryPort deviceModelRepository, OrganizationApiPort organizationApiPort) {
        this.deviceRepository = deviceRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.deviceModelRepository = deviceModelRepository;
        this.organizationApiPort = organizationApiPort;
    }

    @Override
    public Manufacturer createManufacturer(Manufacturer manufacturer) {
        // Lógica do DeviceService original
        return manufacturerRepository.save(manufacturer);
    }

    @Override
    public List<Manufacturer> getAllManufacturers() {
        // Lógica do DeviceService original
        return manufacturerRepository.findAll();
    }

    @Override
    public DeviceModel createDeviceModel(DeviceModel deviceModel) {
        // Lógica do DeviceService original
        // Validação interna (Fabricante está no mesmo serviço)
        manufacturerRepository.findById(deviceModel.getManufacturer().getId())
                .orElseThrow(() -> new EntityNotFoundException("Fabricante com id " + deviceModel.getManufacturer().getId() + " não encontrado."));
        return deviceModelRepository.save(deviceModel);
    }

    @Override
    public List<DeviceModel> getAllDeviceModels() {
        // Lógica do DeviceService original
        return deviceModelRepository.findAll();
    }

    @Override
    public Device createDevice(Device device) {
        // Lógica do DeviceService original

        // 1. Validação externa: Chama o organization-service
        if (device.getSiteId() == null) {
             throw new IllegalArgumentException("O ID do Site é obrigatório.");
        }
        if (!organizationApiPort.siteExists(device.getSiteId())) {
            throw new EntityNotFoundException("Site com id " + device.getSiteId() + " não encontrado.");
        }

        // 2. Validação externa: Chama o organization-service
        if (device.getRackId() != null) {
            if (!organizationApiPort.rackExists(device.getRackId())) {
                throw new EntityNotFoundException("Rack com id " + device.getRackId() + " não encontrado.");
            }
        }

        // 3. Validação interna: (DeviceModel está no mesmo serviço)
        if (device.getDeviceModel() == null || device.getDeviceModel().getId() == null) {
             throw new IllegalArgumentException("O ID do Modelo de Dispositivo é obrigatório.");
        }
        deviceModelRepository.findById(device.getDeviceModel().getId())
                .orElseThrow(() -> new EntityNotFoundException("Modelo de Dispositivo com id " + device.getDeviceModel().getId() + " não encontrado."));

        return deviceRepository.save(device);
    }

    @Override
    public List<Device> getAllDevices() {
        // Lógica do DeviceService original
        return deviceRepository.findAll();
    }

    @Override
    public List<Device> getDevicesBySite(Long siteId) {
        // Lógica do DeviceService original
        // Valida se o site existe antes de buscar
        if (!organizationApiPort.siteExists(siteId)) {
            throw new EntityNotFoundException("Site com id " + siteId + " não encontrado.");
        }
        return deviceRepository.findBySiteId(siteId);
    }
}