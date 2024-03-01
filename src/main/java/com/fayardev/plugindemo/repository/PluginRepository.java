package com.fayardev.plugindemo.repository;

import com.fayardev.plugindemo.entity.Plugin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PluginRepository extends JpaRepository<Plugin, Long> {

    Optional<Plugin> findByPluginCode(String pluginCode);
}
