package com.arcbees.gaestudio.server.service;

import java.util.List;

import com.arcbees.gaestudio.shared.dto.entity.AppIdNamespaceDto;

public interface NamespacesService {
    List<AppIdNamespaceDto> getNamespaces();
}
