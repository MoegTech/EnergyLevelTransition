package com.github.zi_jing.cuckoolib.material;

import com.github.zi_jing.cuckoolib.CuckooLib;
import com.github.zi_jing.cuckoolib.material.type.Material;
import com.github.zi_jing.cuckoolib.util.data.UnorderedRegistry;
import net.minecraft.client.resources.I18n;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class SolidShape {
	public static final UnorderedRegistry<String, SolidShape> REGISTRY = new UnorderedRegistry<String, SolidShape>();

	private String name;
	private int unit;
	private Predicate<Material> materialPredicate;
	private Set<Material> ignoredMaterials, generatedMaterials;

	public SolidShape(String name, int unit, Predicate<Material> materialPredicate) {
		if (REGISTRY.containsKey(name)) {
			throw new IllegalStateException("Solid shape [ " + name + " ] has registered");
		}
		this.name = name;
		this.unit = unit;
		this.materialPredicate = materialPredicate;
		this.ignoredMaterials = new HashSet<Material>();
		this.generatedMaterials = new HashSet<Material>();
		this.register();
	}

	public static SolidShape getShapeByName(String name) {
		if (REGISTRY.containsKey(name)) {
			return REGISTRY.getValue(name);
		}
		return null;
	}

	public String getName() {
		return this.name;
	}

	public int getUnit() {
		return this.unit;
	}

	public void addGeneratedMaterial(Material... material) {
		this.generatedMaterials.addAll(Arrays.asList(material));
	}

	public void addIgnoredMaterial(Material... material) {
		this.ignoredMaterials.addAll(Arrays.asList(material));
	}

	public boolean generateMaterial(Material material) {
		return this.materialPredicate.test(material) && !this.ignoredMaterials.contains(material);
	}

	public String getUnlocalizedName() {
		return CuckooLib.MODID + ".shape." + this.name + ".name";
	}

	public String getLocalizedname(Material material) {
		return I18n.get(this.getUnlocalizedName(), material.getLocalizedName());
	}

	@Override
	public String toString() {
		return this.name;
	}

	public void register() {
		REGISTRY.register(this.name, this);
	}
}