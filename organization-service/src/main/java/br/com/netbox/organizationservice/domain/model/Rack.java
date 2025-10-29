package br.com.netbox.organizationservice.domain.model;

public class Rack {
    private Long id;
    private String name;
    private int uHeight;
    private Site site;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getuHeight() { return uHeight; }
    public void setuHeight(int uHeight) { this.uHeight = uHeight; }
    public Site getSite() { return site; }
    public void setSite(Site site) { this.site = site; }
}