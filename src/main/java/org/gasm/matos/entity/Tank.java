package org.gasm.matos.entity;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.gasm.matos.dao.TankDao;
import org.gasm.matos.entity.enums.Capacity;
import org.gasm.matos.entity.visitor.EquipmentVisitor;
import org.gasm.persistance.dao.AbstractDao;

import com.googlecode.objectify.annotation.EntitySubclass;

@EntitySubclass(index = true)
public class Tank extends Equipment {

	// Brand is an equipment property (Nom du fabricant)

	/**
	 * Numéro de série
	 */
	private String serialNumber;

	/**
	 * Matière : acier ou aluminium
	 */
	private String material;

	/**
	 * Désignation du gaz contenu : air, oxygène, mélange, etc.
	 */
	private String gazType;

	/**
	 * Type de pas de vis
	 */
	private String screwType;

	/**
	 * Poids à vide en kg
	 */
	private BigDecimal weight;

	/**
	 * Volume intérieur dit volume en eau (capacité) The Tank capacity
	 */
	private Capacity blockCapacity;

	/**
	 * Date de fabrication
	 */
	private Date buildDate;

	/**
	 * Pression de chargement ou pression de service exprimée en - bar
	 */
	private BigInteger operatingPressure;

	/**
	 * Pression de ré-épreuve à 15° (exprimée en bar)
	 */
	private BigInteger testPressure;

	/**
	 * Date de ré-épreuve
	 */
	private Date testDate;

	/**
	 * poinçon de l'organisme vérificateur.
	 */
	private String punch;

	/**
	 * Date dernier TIV
	 */
	private Date lastTIVDate;

	/**
	 * Etat : dispo à la location, à ne pas louer (pb sur le bloc), déjà loué
	 */
	private String status;

	@Override
	public String getType() {
		return "Tank";
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getGazType() {
		return gazType;
	}

	public void setGazType(String gazType) {
		this.gazType = gazType;
	}

	public String getScrewType() {
		return screwType;
	}

	public void setScrewType(String screwType) {
		this.screwType = screwType;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public Date getBuildDate() {
		return buildDate;
	}

	public void setBuildDate(Date buildDate) {
		this.buildDate = buildDate;
	}

	public BigInteger getOperatingPressure() {
		return operatingPressure;
	}

	public void setOperatingPressure(BigInteger operatingPressure) {
		this.operatingPressure = operatingPressure;
	}

	public BigInteger getTestPressure() {
		return testPressure;
	}

	public void setTestPressure(BigInteger testPressure) {
		this.testPressure = testPressure;
	}

	public Date getTestDate() {
		return testDate;
	}

	public void setTestDate(Date testDate) {
		this.testDate = testDate;
	}

	public String getPunch() {
		return punch;
	}

	public void setPunch(String punch) {
		this.punch = punch;
	}

	public Date getLastTIVDate() {
		return lastTIVDate;
	}

	public void setLastTIVDate(Date lastTIVDate) {
		this.lastTIVDate = lastTIVDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@JsonIgnore
	@Override
	public AbstractDao getDao() {
		return new TankDao();
	}

	@Override
	public void accept(EquipmentVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public double getPrice() {
		return getBlockCapacity() == Capacity.Litre_15 ? 6 : 4.5;
	}

	public Capacity getBlockCapacity() {
		return blockCapacity;
	}

	public void setBlockCapacity(Capacity blockCapacity) {
		this.blockCapacity = blockCapacity;
	}
}
