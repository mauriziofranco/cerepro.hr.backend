///**
// * 
// */
//package centauri.academy.cerepro;
//
//import java.io.IOException;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * @author maurizio
// *
// */
//public class CorsFilter implements Filter {
//
//	    @Override
//
//	    public void init(FilterConfig filterConfig) throws ServletException {
//
//	        // TODO Auto-generated method stub
//	    }
//
//	    @Override
//	    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//	    		throws IOException, ServletException {	   
//	        HttpServletRequest req = (HttpServletRequest) request;	  
//	        // Autorizza tutti i domini a consumare il servizio
//	        ((HttpServletResponse) response).addHeader("Access-Control-Allow-Origin", "*");
//	        ((HttpServletResponse) response).addHeader("Access-Control-Allow-Methods", "GET, OPTIONS, HEAD, PUT, POST, DELETE");
//	        ((HttpServletResponse) response).addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
//	        HttpServletResponse resp = (HttpServletResponse) response;
//	        if (req.getMethod().equals("OPTIONS")) {
//	            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
//	            return;
//	        }
//        chain.doFilter(request, response);
//
//	    }
//	    
//	    @Override
//	    public void destroy() {
//	        // TODO Auto-generated method stub
//	    }
//	}