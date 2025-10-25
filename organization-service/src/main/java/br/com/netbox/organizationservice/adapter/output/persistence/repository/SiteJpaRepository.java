package br.com.netbox.organizationservice.adapter.output.persistence.repository;

import br.com.netbox.organizationservice.adapter.output.persistence.entity.SiteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Copiado de SiteRepository.java original
@Repository
public interface SiteJpaRepository extends JpaRepository<SiteEntity, Long> {}