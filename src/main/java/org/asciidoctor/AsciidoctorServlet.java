package org.asciidoctor;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = "/asciidoctor")
public class AsciidoctorServlet  extends HttpServlet {

    private
    Asciidoctor asciidoctor = Asciidoctor.Factory.create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Map<String, Object> attributes = new HashMap<String, Object>();

        for (Map.Entry<String, String[]> parameter : req.getParameterMap().entrySet()) {

            attributes.put(parameter.getKey(), parameter.getValue()[0]);

        }

        try (Reader in = new InputStreamReader(getServletContext().getResourceAsStream("/test.adoc"));
            Writer out = new PrintWriter(resp.getOutputStream())){
            Options options = OptionsBuilder.options().attributes(attributes).get();

            asciidoctor.convert(in, out, options);
        } catch (Exception e) {
            e.printStackTrace(new PrintStream(resp.getOutputStream()));
        }
    }
}
