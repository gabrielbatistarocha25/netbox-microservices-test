package br.com.netbox.organizationservice.adapter.output.persistence;

import br.com.netbox.organizationservice.adapter.output.persistence.entity.LocationEntity;
import br.com.netbox.organizationservice.adapter.output.persistence.entity.RackEntity;
import br.com.netbox.organizationservice.adapter.output.persistence.entity.SiteEntity;
import br.com.netbox.organizationservice.adapter.output.persistence.mapper.LocationMapper;
import br.com.netbox.organizationservice.adapter.output.persistence.mapper.RackMapper;
import br.com.netbox.organizationservice.adapter.output.persistence.mapper.SiteMapper;
import br.com.netbox.organizationservice.adapter.output.persistence.repository.LocationJpaRepository;
import br.com.netbox.organizationservice.adapter.output.persistence.repository.RackJpaRepository;
import br.com.netbox.organizationservice.adapter.output.persistence.repository.SiteJpaRepository;
import br.com.netbox.organizationservice.domain.model.Location;
import br.com.netbox.organizationservice.domain.model.Rack;
import br.com.netbox.organizationservice.domain.model.Site;
import br.com.netbox.organizationservice.domain.port.output.LocationRepositoryPort;
import br.com.netbox.organizationservice.domain.port.output.RackRepositoryPort;
import br.com.netbox.organizationservice.domain.port.output.SiteRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component // Esta é a implementação da Porta de Saída
public class OrganizationPersistenceAdapter implements LocationRepositoryPort, SiteRepositoryPort, RackRepositoryPort {

    private final LocationJpaRepository locationJpaRepository;
    private final SiteJpaRepository siteJpaRepository;
    private final RackJpaRepository rackJpaRepository;
    private final LocationMapper locationMapper;
    private final SiteMapper siteMapper;
    private final RackMapper rackMapper;

    public OrganizationPersistenceAdapter(LocationJpaRepository locationJpaRepository, SiteJpaRepository siteJpaRepository, RackJpaRepository rackJpaRepository, LocationMapper locationMapper, SiteMapper siteMapper, RackMapper rackMapper) {
        this.locationJpaRepository = locationJpaRepository;
        this.siteJpaRepository = siteJpaRepository;
        this.rackJpaRepository = rackJpaRepository;
        this.locationMapper = locationMapper;
        this.siteMapper = siteMapper;
        this.rackMapper = rackMapper;
    }

    // --- LocationRepositoryPort ---
    @Override
    public Location save(Location location) {
        LocationEntity entity = locationMapper.toEntity(location);
        LocationEntity savedEntity = locationJpaRepository.save(entity);
        return locationMapper.toModel(savedEntity);
    }

    @Override
    public List<Location> findAll() {
        return locationJpaRepository.findAll().stream()
                .map(locationMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Location> findById(Long id) {
        return locationJpaRepository.findById(id).map(locationMapper::toModel);
    }

    @Override
    public boolean existsById(Long id) {
        return locationJpaRepository.existsById(id);
    }


    // --- SiteRepositoryPort ---
    @Override
    public Site save(Site site) {
        SiteEntity entity = siteMapper.toEntity(site);
        // Garante que a localização (se existir) está gerenciada pelo JPA
        if (entity.getLocation() != null && entity.getLocation().getId() != null) {
            locationJpaRepository.findById(entity.getLocation().getId())
                .ifPresent(entity::setLocation);
        }
        SiteEntity savedEntity = siteJpaRepository.save(entity);
        return siteMapper.toModel(savedEntity);
    }

    @Override
    public List<Site> findAll() {
        return siteJpaRepository.findAll().stream()
                .map(siteMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Site> findById(Long id) {
        return siteJpaRepository.findById(id).map(siteMapper::toModel);
    }

    // --- RackRepositoryPort ---
    @Override
    public Rack save(Rack rack) {
        RackEntity entity = rackMapper.toEntity(rack);
        // Garante que o site (se existir) está gerenciado pelo JPA
        if (entity.getSite() != null && entity.getSite().getId() != null) {
            siteJpaRepository.findById(entity.getSite().getId())
                .ifPresent(entity::setSite);
        }
        RackEntity savedEntity = rackJpaRepository.save(entity);
        return rackMapper.toModel(savedEntity);
    }

    // findAll para Rack
    @Override
    public List<Rack> findAll() {
        return rackJpaRepository.findAll().stream()
                .map(rackMapper::toModel)
                .collect(Collectors.toList());
    }

    // findById para Rack
    @Override
    public Optional<Rack> findById(Long id) {
        return rackJpaRepository.findById(id).map(rackMapper::toModel);
    }
}