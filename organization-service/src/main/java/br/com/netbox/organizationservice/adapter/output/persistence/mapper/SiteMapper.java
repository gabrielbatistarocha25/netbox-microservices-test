package br.com.netbox.organizationservice.adapter.output.persistence.mapper;

import br.com.netbox.organizationservice.adapter.output.persistence.entity.SiteEntity;
import br.com.netbox.organizationservice.domain.model.Site;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class SiteMapper {

    public SiteEntity toEntity(Site model) {
        if (model == null) return null;
        SiteEntity entity = new SiteEntity();
        entity.setId(model.getId());
        entity.setName(model.getName());
        if (model.getLocation() != null) {
            entity.setLocation(new LocationMapper().toEntity(model.getLocation()));
        }
        // Ignoramos racks aqui
        return entity;
    }

    public Site toModel(SiteEntity entity) {
        return toModel(entity, true); // Por padrão, mapeia recursivamente
    }

    // Criamos este método auxiliar para controlar a recursão
    public Site toModel(SiteEntity entity, boolean mapLocation) {
        if (entity == null) return null;
        Site model = new Site();
        model.setId(entity.getId());
        model.setName(entity.getName());

        if (mapLocation && entity.getLocation() != null) {
            model.setLocation(new LocationMapper().toModel(entity.getLocation()));
        }

        if (entity.getRacks() != null) {
            model.setRacks(entity.getRacks().stream()
                .map(rackEntity -> {
                    var rackModel = new RackMapper().toModel(rackEntity, false); // Evita recursão
                    rackModel.setSite(model); // Define a referência de volta
                    return rackModel;
                })
                .collect(Collectors.toList()));
        }
        return model;
    }
}