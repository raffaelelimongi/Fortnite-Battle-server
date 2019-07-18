package util;

import java.util.logging.Logger;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class ApplicationBinder extends AbstractBinder {

	@Override
	protected void configure() {
		// TODO Auto-generated method stub
		bind(SimpleKeyGenerator.class).to(KeyGenerator.class);
	}

}
