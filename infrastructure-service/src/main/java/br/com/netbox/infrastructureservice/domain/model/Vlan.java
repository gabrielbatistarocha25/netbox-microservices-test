package br.com.netbox.infrastructureservice.domain.model;

// POJO Puro
public class Vlan {
    private Long id;
    private Integer vlanId;
    private String name;

    // ID para o domínio externo
    private Long siteId;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Integer getVlanId() { return vlanId; }
    public void setVlanId(Integer vlanId) { this.vlanId = vlanId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Long getSiteId() { return siteId; }
    public void setSiteId(Long siteId) { this.siteId = siteId; }
}