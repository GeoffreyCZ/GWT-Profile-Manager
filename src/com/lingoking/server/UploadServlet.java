package com.lingoking.server;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

public class UploadServlet extends HttpServlet {

    static final String INPUT_FILE_NAME = "image.jpg";
    public static final String TEMP_DIRECTORY = "tmp/";

    private String filePath;
    private int maxFileSize;
    private int maxMemSize;

    public void init( ){
        // Get the file location where it would be stored.
        filePath = getServletContext().getInitParameter("file-upload");

        LoadConfiguration loadConfiguration = new LoadConfiguration(new File ("config/config.xml"));
        maxFileSize = Integer.parseInt(loadConfiguration.findSymbol("maxFileSize"));
        maxMemSize = Integer.parseInt(loadConfiguration.findSymbol("maxMemSize"));

    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check that we have a file upload request
        boolean isMultipart;
        System.out.println("jsem tuuuuuuu");

        File file;

        isMultipart = ServletFileUpload.isMultipartContent(request);
        response.setContentType("text/html");
        PrintWriter out = response.getWriter( );
        if( !isMultipart ){
            System.out.println("No file uploaded");
            return;
        }
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // Maximum size that will be stored in memory
        factory.setSizeThreshold(maxMemSize);
        // Location to save data that is larger than maxMemSize.
        factory.setRepository(new File(TEMP_DIRECTORY));

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);
        // Maximum file size to be uploaded.
        upload.setSizeMax(maxFileSize);

        try {
            // Parse the request to get file items.
            System.out.println("try");
            List fileItems = upload.parseRequest(request);
            for (Iterator i = fileItems.iterator(); i.hasNext();) {
                FileItem fi = (FileItem)i.next();
                // Checks if there is uploaded file or form field in input
                if (!fi.isFormField()) {
                    file = new File(filePath + INPUT_FILE_NAME);
                    fi.write(file);
                    System.out.println("Your file has been uploaded.");
                }
            }

        } catch (Exception ex) {
            System.out.println("An error occurred" + ex);
        }
    }
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {

        throw new ServletException("GET method used with " +
                getClass().getName()+": POST method required.");
    }
}