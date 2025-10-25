package br.com.netbox.organizationservice.adapter.output.persistence.mapper;

import br.com.netbox.organizationservice.adapter.output.persistence.entity.LocationEntity;
import br.com.netbox.organizationservice.domain.model.Location;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class LocationMapper {

    public LocationEntity toEntity(Location model) {
        if (model == null) return null;
        LocationEntity entity = new LocationEntity();
        entity.setId(model.getId());
        entity.setName(model.getName());
        entity.setAddress(model.getAddress());
        // Ignoramos 'sites' na conversão para entidade para evitar loops
        return entity;
    }

    public Location toModel(LocationEntity entity) {
        if (entity == null) return null;
        Location model = new Location();
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setAddress(entity.getAddress());
        if (entity.getSites() != null) {
            // Mapeia os sites (sem recursão infinita)
            model.setSites(entity.getSites().stream()
                .map(siteEntity -> {
                    var siteModel = new SiteMapper().toModel(siteEntity, false); // Evita recursão
                    siteModel.setLocation(model); // Define a referência de volta
                    return siteModel;
                })
                .collect(Collectors.toList()));
        }
        return model;
    }
}