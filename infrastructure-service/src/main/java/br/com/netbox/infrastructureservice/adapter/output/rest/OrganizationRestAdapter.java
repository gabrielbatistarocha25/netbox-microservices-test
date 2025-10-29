package br.com.netbox.infrastructureservice.adapter.output.rest;

import br.com.netbox.infrastructureservice.domain.port.output.OrganizationApiPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class OrganizationRestAdapter implements OrganizationApiPort {

    private final RestTemplate restTemplate;

    @Value("${organization.service.url}")
    private String organizationServiceUrl;

    public OrganizationRestAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean siteExists(Long siteId) {
         try {
            restTemplate.getForEntity(organizationServiceUrl + "/organization/sites/" + siteId, Void.class);
            return true;
        } catch (HttpClientErrorException.NotFound e) {
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}