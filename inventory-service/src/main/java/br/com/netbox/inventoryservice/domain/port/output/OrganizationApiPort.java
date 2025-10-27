package br.com.netbox.inventoryservice.domain.port.output;

// Interface para se comunicar com o microserviço externo
public interface OrganizationApiPort {
    boolean siteExists(Long siteId);
    boolean rackExists(Long rackId);
}