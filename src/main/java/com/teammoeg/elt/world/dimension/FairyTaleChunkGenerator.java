package com.teammoeg.elt.world.dimension;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teammoeg.elt.ELT;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.WorldGenRegion;
import net.minecraft.world.gen.feature.structure.StructureManager;

import java.util.function.Supplier;

public class FairyTaleChunkGenerator extends ChunkGenerator {
    protected final Supplier<DimensionSettings> settings;

    public static void registerChunkgenerator() {
        Registry.register(Registry.CHUNK_GENERATOR, new ResourceLocation(ELT.MOD_ID, "chunk_generator"), FairyTaleChunkGenerator.CODEC);
    }

    public static final Codec<FairyTaleChunkGenerator> CODEC = RecordCodecBuilder.create((instance_) -> {
        return instance_.group(BiomeProvider.CODEC.fieldOf("biome_source").forGetter((ftChunkGenerator2_) -> {
            return ftChunkGenerator2_.biomeSource;
        }), DimensionSettings.CODEC.fieldOf("settings").forGetter((ftChunkGenerator_) -> {
            return ftChunkGenerator_.settings;
        })).apply(instance_, instance_.stable(FairyTaleChunkGenerator::new));
    });

    public FairyTaleChunkGenerator(BiomeProvider biomeProvider, Supplier<DimensionSettings> supplier_) {
        this(biomeProvider, biomeProvider, supplier_);
    }

    public FairyTaleChunkGenerator(BiomeProvider biomeProvider_, BiomeProvider biomeProvider1_, Supplier<DimensionSettings> supplier_) {
        super(biomeProvider_, supplier_.get().structureSettings());
        DimensionSettings dimensionsettings = supplier_.get();
        this.settings = supplier_;

    }

    @Override
    protected Codec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    @Override
    public ChunkGenerator withSeed(long long_) {
        return new FairyTaleChunkGenerator(this.biomeSource.withSeed(long_), this.settings);
    }

    @Override
    public void buildSurfaceAndBedrock(WorldGenRegion worldGenRegion_, IChunk chunk_) {
        BlockState bedrock = Blocks.BEDROCK.defaultBlockState();
        BlockState stone = Blocks.STONE.defaultBlockState();
        ChunkPos chunkpos = chunk_.getPos();

        BlockPos.Mutable pos = new BlockPos.Mutable();

        int x;
        int z;

        for (x = 0; x < 16; x++) {
            for (z = 0; z < 16; z++) {
                chunk_.setBlockState(pos.set(x, 0, z), bedrock, false);
            }
        }

        for (x = 0; x < 16; x++) {
            for (z = 0; z < 16; z++) {
                int realx = chunkpos.x * 16 + x;
                int realz = chunkpos.z * 16 + z;
                int height = (int) (65 + Math.sin(realx / 20.0f) * 10 + Math.cos(realz / 20.0f) * 10);
                for (int y = 1; y < height; y++) {
                    chunk_.setBlockState(pos.set(x, y, z), stone, false);
                }
            }
        }
    }

    @Override
    public void fillFromNoise(IWorld world_, StructureManager structureManager_, IChunk chunk_) {

    }

    @Override
    public int getBaseHeight(int x, int z, Heightmap.Type heightmapType) {
        return 0;
    }

    @Override
    public IBlockReader getBaseColumn(int int_, int int1_) {
        return null;
    }
}
