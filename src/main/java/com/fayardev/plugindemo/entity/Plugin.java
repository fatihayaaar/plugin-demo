package com.fayardev.plugindemo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "plugin")
public class Plugin implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "plugin_code", nullable = false, length = 100, unique = true)
    private String pluginCode;

    @Column(name = "plugin_name", nullable = false, length = 100, unique = true)
    private String pluginName;
}
