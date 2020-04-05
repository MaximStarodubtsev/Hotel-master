package Configuration;

import Service.HotelService;
import com.sun.istack.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    private static String DISPATCHER_SERVLET_PATH = "/";

    @Autowired
    private HotelService hotelService;

    @Nullable
    @Override
    protected Class<?> [] getRootConfigClasses(){ return new Class[0]; }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {WebConfiguration.class, Configuration.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{DISPATCHER_SERVLET_PATH};
    }
}
