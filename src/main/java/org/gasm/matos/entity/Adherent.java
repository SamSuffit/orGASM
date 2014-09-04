/**
 * 
 */
package org.gasm.matos.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.gasm.matos.dao.AdherentDao;
import org.gasm.persistance.dao.AbstractDao;

/**
 *
 */
@Entity
public class Adherent extends AbstractLongEntity {

    @Override
    @JsonIgnore
    public AbstractDao getDao() {
        return new AdherentDao();
    }

    public Adherent() {}
    public Adherent(Long id) {
        this.id = id;
    }


	@Id
	private Long id;

    private String firstName;
	
	private String lastName;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

    @JsonIgnore
    public String getFullName() {
        StringBuilder sb = new StringBuilder();
        if(StringUtils.isNotBlank(getFirstName())) {
            sb.append(getFirstName()).append(" ");
        }
        if(StringUtils.isNotBlank(getLastName())) {
            sb.append(getLastName());
        }
      return sb.toString();
    }

}
