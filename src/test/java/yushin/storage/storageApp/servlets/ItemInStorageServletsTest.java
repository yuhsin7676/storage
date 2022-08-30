/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package yushin.storage.storageApp.servlets;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author ilya
 */
public class ItemInStorageServletsTest {

    HttpServletRequest request;
    HttpServletResponse response;
    PrintWriter printWriter;
    
    @BeforeEach
    public void init() throws Exception{
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        printWriter = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(printWriter);
    }
    
    /**
     * Проверим, что FindAllItemInStorage не рухнет.
     */
    @Test
    public void testFindAllItemInStorage() throws Exception {
        new FindAllItemInStorage().doPost(request, response);
        verify(printWriter).println(anyString());
    }
    
    /**
     * Проверим, что FindAllItemInStorage c фильтрами не рухнет.
     */
    @Test
    public void testFindAllItemInStorageFilter() throws Exception {
        when(request.getParameter("filters")).thenReturn("[1, 2]");
        new FindAllItemInStorage().doPost(request, response);
        verify(printWriter).println(anyString());
    }
    
}
