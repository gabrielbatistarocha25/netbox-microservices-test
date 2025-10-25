package br.com.netbox.organizationservice.domain.model;

import java.util.List;

// POJO puro - Sem anotações JPA ou Jackson
public class Location {
    private Long id;
    private String name;
    private String address;
    private List<Site> sites;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public List<Site> getSites() { return sites; }
    public void setSites(List<Site> sites) { this.sites = sites; }
}