package my.snapshot.ignite;

import org.apache.ignite.Ignite;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class IgniteInitation implements ApplicationContextAware{

	private Ignite ignite;
	
	public void InitIgniteCache() {
		
	}

	@Override
	public void setApplicationContext(ApplicationContext cnt){
		// TODO Auto-generated method stub
//		this.ignite = cnt.getBean(Ignite.class);
		this.ignite = cnt.getBean(Ignite.class);
	}
	
	public Ignite getIgniteInstance() {
		return this.ignite;
	}
}
