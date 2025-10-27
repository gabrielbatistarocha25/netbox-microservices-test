package br.com.netbox.inventoryservice.adapter.output.rest;

import br.com.netbox.inventoryservice.domain.port.output.OrganizationApiPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class OrganizationRestAdapter implements OrganizationApiPort {

    private final RestTemplate restTemplate;

    @Value("${organization.service.url}") // Pega a URL do application.properties
    private String organizationServiceUrl;

    public OrganizationRestAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean siteExists(Long siteId) {
        try {
            // Tenta chamar o endpoint GET /api/organization/sites/{id}
            // Usamos /organization/sites/{id} pois foi o que definimos no OrganizationController
            restTemplate.getForEntity(organizationServiceUrl + "/organization/sites/" + siteId, Void.class);
            return true; // Se retornar 200, existe
        } catch (HttpClientErrorException.NotFound e) {
            return false; // Se retornar 404, não existe
        } catch (Exception e) {
            // Qualquer outro erro (ex: serviço offline), assume que falhou
            e.printStackTrace(); // Logar erro
            return false;
        }
    }

    @Override
    public boolean rackExists(Long rackId) {
         try {
            // Tenta chamar o endpoint GET /api/racks/{id}
            // Usamos /racks/{id} pois foi o que definimos no RackController
            restTemplate.getForEntity(organizationServiceUrl + "/racks/" + rackId, Void.class);
            return true;
        } catch (HttpClientErrorException.NotFound e) {
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}