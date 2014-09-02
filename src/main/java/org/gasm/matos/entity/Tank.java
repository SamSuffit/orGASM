package org.gasm.matos.entity;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.gasm.matos.dao.TankDao;
import org.gasm.matos.entity.enums.Capacity;
import org.gasm.matos.entity.enums.Gaz;
import org.gasm.matos.entity.enums.Material;
import org.gasm.matos.entity.enums.Screw;
import org.gasm.matos.entity.visitor.EquipmentVisitor;
import org.gasm.persistance.dao.AbstractDao;

import com.googlecode.objectify.annotation.EntitySubclass;

@EntitySubclass(index = true)
public class Tank extends Equipment {

	// Brand is an equipment property

	/**
	 * Serial number of this item
     *
	 */
	private String serialNumber;

	/**
	 * Material (enum)
     *
	 */
	private Material material;

	/**
	 * Gaz type contain in the tank (enum)
     *
	 */
	private Gaz gaz;

	/**
	 * Screw type (enum)
     *
	 */
	private Screw screw;

    /**
     * Weight of the empty tank
     *
     */
	private double weight;

	/**
	 * The Tank capacity (liquide capacity)
     *
	 */
	private Capacity capacity;

	/**
	 * Build date
	 */
	private Date buildDate;

    /**
     * Usage tank pressure
     */
	private double operatingPressure;

    /**
     * Test tank pressure
     */
	private double testPressure;

    /**
     * Date of the tank test
     */
	private Date testDate;

	/**
	 * Punch of the certification organism (is the punch on the tank?).
	 */
	private boolean punch;

	/**
	 * Date of the last TIV
	 */
	private Date lastDateOfTIV;

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

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public Gaz getGaz() {
		return gaz;
	}

	public void setGaz(Gaz gaz) {
		this.gaz = gaz;
	}

	public Screw getScrew() {
		return screw;
	}

	public void setScrew(Screw screw) {
		this.screw = screw;
	}

	public Capacity getCapacity() {
		return capacity;
	}

	public void setCapacity(Capacity capacity) {
		this.capacity = capacity;
	}

	public Date getBuildDate() {
		return buildDate;
	}

	public void setBuildDate(Date buildDate) {
		this.buildDate = buildDate;
	}

	/**
	 * Date of the last test
	 */
    public Date getTestDate() {
		return testDate;
	}

	public void setTestDate(Date testDate) {
		this.testDate = testDate;
	}

	public boolean isPunch() {
		return punch;
	}

	public void setPunch(boolean punch) {
		this.punch = punch;
	}

	public Date getLastDateOfTIV() {
		return lastDateOfTIV;
	}

	public void setLastDateOfTIV(Date lastDateOfTIV) {
		this.lastDateOfTIV = lastDateOfTIV;
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
		return getCapacity() == Capacity.Litre_12 ? 4.5 : 6;
	}

    /**
     * Weight (empty) unit is kg
     */
    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * Test pressure (at 15° , unit in bar)
     */
    public double getTestPressure() {
        return testPressure;
    }

    public void setTestPressure(double testPressure) {
        this.testPressure = testPressure;
    }

    /**
     * Operating pressure (unit in bar)
     */
    public double getOperatingPressure() {
        return operatingPressure;
    }

    public void setOperatingPressure(double operatingPressure) {
        this.operatingPressure = operatingPressure;
    }
}
