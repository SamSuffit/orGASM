package org.gasm.security;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.gasm.matos.dao.AdherentDao;
import org.gasm.matos.entity.Adherent;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: Samuel
 * Date: 02/09/14
 * Time: 11:56
 * To change this template use File | Settings | File Templates.
 */
public class SecurityManagerHashMapImpl implements SecurityManager {


    private static SecurityManagerHashMapImpl instance;
    private final AdherentDao adherentDao;

    private SecurityManagerHashMapImpl() {
        reset();
        adherentDao = new AdherentDao();
    }

    public static final SecurityManagerHashMapImpl getInstance() {
        if(instance == null ) {
            instance = new SecurityManagerHashMapImpl();
        }
        return  instance;
    }

    /**
     * Reset all security Keys
     */
    public void reset() {
        loginVault = new HashMap<>();
        uuidVault = new HashMap<>();
    }

    private Map<String,SecurityInfo> loginVault;
    private Map<String,SecurityInfo> uuidVault;

    @Override
    public String getSecurityKey(String login, String password) {
        if(isAllowed(login, password))  {

            SecurityInfo securityInfo = loginVault.get(login) ;
            if(!isValid(securityInfo)) {
                securityInfo = new SecurityInfo(login);
            }

            String key = securityInfo.getUuid().toString();
            loginVault.put(login, securityInfo);
            uuidVault.put(key,securityInfo);
            return key ;
        }
        return null;
    }

    @Override
    public boolean isValid(String securityKey) {
        return isValid(uuidVault.get(securityKey));
    }

    private boolean isValid(SecurityInfo securityInfo) {
        if(securityInfo != null && DateUtils.isSameDay(Calendar.getInstance(), securityInfo.getGeneratedCalendar())) {
            return true;
        }
        else {
            return false;
        }
    }

    private boolean isAllowed(String login, String password) {
        if(StringUtils.equals("admin", login) && StringUtils.equals("plongée", password) ) {
            return true;
        }
        else {
            for(Adherent ad : adherentDao.findAll()) {
                if(StringUtils.equals(ad.getLogin(), login) && StringUtils.equals(ad.getPassword(), password) ) {
                    return true;
                }
            }
        }
        return false;
    }


    private class SecurityInfo {

        private final UUID uuid;

        private final String login;

        public UUID getUuid() {
            return uuid;
        }

        public Calendar getGeneratedCalendar() {
            return generatedCalendar;
        }

        public String getLogin() {
            return login;
        }

        private final Calendar generatedCalendar;

        /**
         *
         * @param login
         */
        public SecurityInfo(String login) {
            this.login = login;
            this.uuid  = UUID.randomUUID();
            this.generatedCalendar = Calendar.getInstance();
        }


    }

}
