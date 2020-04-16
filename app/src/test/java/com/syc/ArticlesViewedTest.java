package com.syc;
import com.syc.utils.Utils;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class ArticlesViewedTest {
    //private final Context context = Mockito.mock(Context.class);
    //private SharedPreferences sharedPref;

    @Before
    public void before() throws Exception {
        //sharedPref = Mockito.mock(SharedPreferences.class);
    }

    @Test
    public void articlesViewedTest(){
        // ======================================= List articles viewed: add one item and test, 2 item and test
        String articles="";
        String articlesRef="" ;
        String articlesViewedTab[] = new String [30];
        for (int i=0; i< 30; i++){
            articlesViewedTab[i] = "str" + i;
        }
        //invalidate test
        assertNotEquals(articlesViewedTab[0],"srtxx");
        articles = Utils.addSharedArticlesViewed(articlesViewedTab[0],articles,30);
        //first add test
        assertEquals(articles,articlesViewedTab[0]);
        //second add test
        articles = Utils.addSharedArticlesViewed(articlesViewedTab[1],articles,30);
        assertEquals(articles, "str0:str1");

        // ======================================= List articles viewed: add over size (add 10, size 5)
        // add 10 item with size max = 5,
        articles = "";
        for (int i=0; i< 10; i++){
            articles = Utils.addSharedArticlesViewed(articlesViewedTab[i],articles,5);
        }
        // add result must this :
        articlesRef = "str5:str6:str7:str8:str9";
        assertEquals(articles,articlesRef);
        // ======================================= Max articles respected
        //Explode in list, to evaluate size
        List<String> listArticlesViewed = Arrays.asList(articles.split(":"));
        assertTrue(listArticlesViewed.size() == 5);
    }

    @Test
    public void isArticleViewed(){
        String article="";
        String articles="";
        //add all list to test article exist
        for (int i=0; i< 7; i++){
            article = "str" + i;
            articles = Utils.addSharedArticlesViewed(article,articles,30);
        }
        assertTrue(Utils.isArticleViewed("str3",articles));
        assertFalse(Utils.isArticleViewed("strxx",articles));
    }

}
