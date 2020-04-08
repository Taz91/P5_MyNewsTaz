package com.syc;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import static org.junit.Assert.assertEquals;
import static  org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MockNYTTestService {

    @Before
    public void setUp() {
        //Retrofit mRetrofit = RetrofitInstance.getRetrofitInstance();
    }

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

}
