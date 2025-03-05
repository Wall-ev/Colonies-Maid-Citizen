package com.github.wallev.coloniesmaidcitizen.handler;

import com.minecolonies.api.colony.ICitizenData;

import java.util.UUID;

public record CitizenMaidData(ICitizenData citizenData, UUID citizenUuid, String modelId, boolean enableModelRender) {
}
