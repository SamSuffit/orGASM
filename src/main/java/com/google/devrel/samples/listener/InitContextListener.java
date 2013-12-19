package com.google.devrel.samples.listener;

import com.googlecode.objectify.ObjectifyService;
import org.gasm.matos.entity.*;
import org.gasm.matos.entity.rental.DivingEvent;
import org.gasm.matos.entity.rental.RentalRecord;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class InitContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {

		ObjectifyService.register(Adherent.class);

		ObjectifyService.register(Jacket.class);
		ObjectifyService.register(Regulator.class);
		ObjectifyService.register(Suit.class);
		ObjectifyService.register(Tank.class);

		ObjectifyService.register(DivingEvent.class);
		ObjectifyService.register(RentalRecord.class);

        ObjectifyService.register(RentHistory.class);
	}

}
