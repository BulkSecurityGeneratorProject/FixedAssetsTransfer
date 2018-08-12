package com.fxt.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.fxt.domain.enumeration.AssetActionType;

import com.fxt.domain.enumeration.TechnicalReview;

/**
 * A FixedAssets.
 */
@Entity
@Table(name = "fixed_assets")
@Document(indexName = "fixedassets")
public class FixedAssets implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "assets_no", nullable = false)
    private Long assets_no;

    @NotNull
    @Column(name = "serial_no", nullable = false)
    private Long serial_no;

    @NotNull
    @Size(min = 10)
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "jhi_date", nullable = false)
    private LocalDate date;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "asset_action_type", nullable = false)
    private AssetActionType asset_action_type;

    @NotNull
    @Size(min = 5)
    @Column(name = "source", nullable = false)
    private String source;

    @NotNull
    @Size(min = 5)
    @Column(name = "destination", nullable = false)
    private String destination;

    @Column(name = "manager_approval")
    private Boolean manager_approval;

    @Column(name = "fixed_assets_manager_approval")
    private Boolean fixed_assets_manager_approval;

    @Enumerated(EnumType.STRING)
    @Column(name = "technical_review")
    private TechnicalReview technical_review;

    @Column(name = "technical_commentary")
    private String technical_commentary;

    @Column(name = "it_asset_previewer")
    private String it_asset_previewer;

    @Column(name = "it_asset_previewer_name")
    private String it_asset_previewer_name;

    @Column(name = "administraitve_affairs_previewer")
    private String administraitve_affairs_previewer;

    @Column(name = "administraitive_affairs_job_name")
    private String administraitive_affairs_job_name;

    @Column(name = "technical_maintenance_commentry")
    private String technical_maintenance_commentry;

    @Column(name = "technical_maintenance_previewer")
    private String technical_maintenance_previewer;

    @Column(name = "technical_maintenance_job_name")
    private String technical_maintenance_job_name;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAssets_no() {
        return assets_no;
    }

    public FixedAssets assets_no(Long assets_no) {
        this.assets_no = assets_no;
        return this;
    }

    public void setAssets_no(Long assets_no) {
        this.assets_no = assets_no;
    }

    public Long getSerial_no() {
        return serial_no;
    }

    public FixedAssets serial_no(Long serial_no) {
        this.serial_no = serial_no;
        return this;
    }

    public void setSerial_no(Long serial_no) {
        this.serial_no = serial_no;
    }

    public String getDescription() {
        return description;
    }

    public FixedAssets description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public FixedAssets date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public AssetActionType getAsset_action_type() {
        return asset_action_type;
    }

    public FixedAssets asset_action_type(AssetActionType asset_action_type) {
        this.asset_action_type = asset_action_type;
        return this;
    }

    public void setAsset_action_type(AssetActionType asset_action_type) {
        this.asset_action_type = asset_action_type;
    }

    public String getSource() {
        return source;
    }

    public FixedAssets source(String source) {
        this.source = source;
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public FixedAssets destination(String destination) {
        this.destination = destination;
        return this;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Boolean isManager_approval() {
        return manager_approval;
    }

    public FixedAssets manager_approval(Boolean manager_approval) {
        this.manager_approval = manager_approval;
        return this;
    }

    public void setManager_approval(Boolean manager_approval) {
        this.manager_approval = manager_approval;
    }

    public Boolean isFixed_assets_manager_approval() {
        return fixed_assets_manager_approval;
    }

    public FixedAssets fixed_assets_manager_approval(Boolean fixed_assets_manager_approval) {
        this.fixed_assets_manager_approval = fixed_assets_manager_approval;
        return this;
    }

    public void setFixed_assets_manager_approval(Boolean fixed_assets_manager_approval) {
        this.fixed_assets_manager_approval = fixed_assets_manager_approval;
    }

    public TechnicalReview getTechnical_review() {
        return technical_review;
    }

    public FixedAssets technical_review(TechnicalReview technical_review) {
        this.technical_review = technical_review;
        return this;
    }

    public void setTechnical_review(TechnicalReview technical_review) {
        this.technical_review = technical_review;
    }

    public String getTechnical_commentary() {
        return technical_commentary;
    }

    public FixedAssets technical_commentary(String technical_commentary) {
        this.technical_commentary = technical_commentary;
        return this;
    }

    public void setTechnical_commentary(String technical_commentary) {
        this.technical_commentary = technical_commentary;
    }

    public String getIt_asset_previewer() {
        return it_asset_previewer;
    }

    public FixedAssets it_asset_previewer(String it_asset_previewer) {
        this.it_asset_previewer = it_asset_previewer;
        return this;
    }

    public void setIt_asset_previewer(String it_asset_previewer) {
        this.it_asset_previewer = it_asset_previewer;
    }

    public String getIt_asset_previewer_name() {
        return it_asset_previewer_name;
    }

    public FixedAssets it_asset_previewer_name(String it_asset_previewer_name) {
        this.it_asset_previewer_name = it_asset_previewer_name;
        return this;
    }

    public void setIt_asset_previewer_name(String it_asset_previewer_name) {
        this.it_asset_previewer_name = it_asset_previewer_name;
    }

    public String getAdministraitve_affairs_previewer() {
        return administraitve_affairs_previewer;
    }

    public FixedAssets administraitve_affairs_previewer(String administraitve_affairs_previewer) {
        this.administraitve_affairs_previewer = administraitve_affairs_previewer;
        return this;
    }

    public void setAdministraitve_affairs_previewer(String administraitve_affairs_previewer) {
        this.administraitve_affairs_previewer = administraitve_affairs_previewer;
    }

    public String getAdministraitive_affairs_job_name() {
        return administraitive_affairs_job_name;
    }

    public FixedAssets administraitive_affairs_job_name(String administraitive_affairs_job_name) {
        this.administraitive_affairs_job_name = administraitive_affairs_job_name;
        return this;
    }

    public void setAdministraitive_affairs_job_name(String administraitive_affairs_job_name) {
        this.administraitive_affairs_job_name = administraitive_affairs_job_name;
    }

    public String getTechnical_maintenance_commentry() {
        return technical_maintenance_commentry;
    }

    public FixedAssets technical_maintenance_commentry(String technical_maintenance_commentry) {
        this.technical_maintenance_commentry = technical_maintenance_commentry;
        return this;
    }

    public void setTechnical_maintenance_commentry(String technical_maintenance_commentry) {
        this.technical_maintenance_commentry = technical_maintenance_commentry;
    }

    public String getTechnical_maintenance_previewer() {
        return technical_maintenance_previewer;
    }

    public FixedAssets technical_maintenance_previewer(String technical_maintenance_previewer) {
        this.technical_maintenance_previewer = technical_maintenance_previewer;
        return this;
    }

    public void setTechnical_maintenance_previewer(String technical_maintenance_previewer) {
        this.technical_maintenance_previewer = technical_maintenance_previewer;
    }

    public String getTechnical_maintenance_job_name() {
        return technical_maintenance_job_name;
    }

    public FixedAssets technical_maintenance_job_name(String technical_maintenance_job_name) {
        this.technical_maintenance_job_name = technical_maintenance_job_name;
        return this;
    }

    public void setTechnical_maintenance_job_name(String technical_maintenance_job_name) {
        this.technical_maintenance_job_name = technical_maintenance_job_name;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FixedAssets fixedAssets = (FixedAssets) o;
        if (fixedAssets.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fixedAssets.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FixedAssets{" +
            "id=" + getId() +
            ", assets_no=" + getAssets_no() +
            ", serial_no=" + getSerial_no() +
            ", description='" + getDescription() + "'" +
            ", date='" + getDate() + "'" +
            ", asset_action_type='" + getAsset_action_type() + "'" +
            ", source='" + getSource() + "'" +
            ", destination='" + getDestination() + "'" +
            ", manager_approval='" + isManager_approval() + "'" +
            ", fixed_assets_manager_approval='" + isFixed_assets_manager_approval() + "'" +
            ", technical_review='" + getTechnical_review() + "'" +
            ", technical_commentary='" + getTechnical_commentary() + "'" +
            ", it_asset_previewer='" + getIt_asset_previewer() + "'" +
            ", it_asset_previewer_name='" + getIt_asset_previewer_name() + "'" +
            ", administraitve_affairs_previewer='" + getAdministraitve_affairs_previewer() + "'" +
            ", administraitive_affairs_job_name='" + getAdministraitive_affairs_job_name() + "'" +
            ", technical_maintenance_commentry='" + getTechnical_maintenance_commentry() + "'" +
            ", technical_maintenance_previewer='" + getTechnical_maintenance_previewer() + "'" +
            ", technical_maintenance_job_name='" + getTechnical_maintenance_job_name() + "'" +
            "}";
    }
}
