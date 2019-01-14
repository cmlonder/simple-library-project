package com.cemalonder.tutorials.simplelibraryproject.configuration;

import org.mapstruct.ReportingPolicy;

/** Base configuration class for all mappers */
@org.mapstruct.MapperConfig(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public class MapperConfig {}
