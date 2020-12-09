/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package com.teammoeg.elt.util;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

/**
 * A no-op implementation of {@link Capability.IStorage} for capabilities that require custom serialize / deserialization logic
 *
 * @param <T> The capability class
 */
public final class NoopStorage<T> implements Capability.IStorage<T>
{
    @Nullable
    @Override
    public INBT writeNBT(Capability<T> capability, T instance, Direction side)
    {
        throw new UnsupportedOperationException("This storage is non functional. Do not use it.");
    }

    @Override
    public void readNBT(Capability<T> capability, T instance, Direction side, INBT nbt)
    {
        throw new UnsupportedOperationException("This storage is non functional. Do not use it.");
    }
}