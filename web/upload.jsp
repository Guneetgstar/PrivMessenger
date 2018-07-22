<%@ page import="java.io.InputStream" %>
<%@ page import="java.io.File" %>
<%@ page import="java.io.FileOutputStream" %>
<%
    InputStream content = request.getPart("file").getInputStream();
    System.out.println(content.available());
    Cookie []cookies=request.getCookies();
    String uname="";
    for(Cookie cookie: cookies){
        if(cookie.getName().equals("email")) {
            uname = cookie.getValue();
            break;
        }
    }
    System.out.println(uname+("thats all"));
    if(!uname.equals("")) {
        try {
            if (content.available() > 0) {
                int a, b = 0;
                byte[] buffer = new byte[content.available()];
                while ((content.available()) > 0) {
                    buffer[b] = (byte) content.read();
                    b++;
                }
                File file = new File(application.getRealPath("/")+"//user//" + uname + ".jpg");
                System.out.println(file.getAbsolutePath());
                file.getParentFile().mkdirs();
                FileOutputStream fo = new FileOutputStream(file);
                fo.write(buffer);
                fo.flush();
                out.println(1);
                response.setStatus(HttpServletResponse.SC_OK);
            } else
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
%>