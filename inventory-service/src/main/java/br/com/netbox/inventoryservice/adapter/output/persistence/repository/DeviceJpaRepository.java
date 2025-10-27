package br.com.netbox.inventoryservice.adapter.output.persistence.repository;

import br.com.netbox.inventoryservice.adapter.output.persistence.entity.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

// Baseado no DeviceRepository.java original
@Repository
public interface DeviceJpaRepository extends JpaRepository<DeviceEntity, Long> {

    // Agora buscamos pelo ID do site, não pelo objeto
    List<DeviceEntity> findBySiteId(Long siteId);
}