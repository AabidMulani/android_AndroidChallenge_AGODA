package technewssample.com.agoda.news.androidchallenge.sample;

import android.content.Context;
import android.test.InstrumentationTestCase;

import junit.framework.Assert;

import java.util.List;

public class MainActivityTest extends InstrumentationTestCase {

    MainActivity mainActivityTest;
    private Context context;

    public void setUp() throws Exception {
        super.setUp();
        context = getInstrumentation().getTargetContext();
        mainActivityTest = new MainActivity();
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }


    public void testCheckAllEntityValues() throws Exception {
        String loadedString = mainActivityTest.loadResource(context);
        Assert.assertNotNull("Loading Local Data", loadedString);

        List<NewsEntity> newsItemList = mainActivityTest.extractJsonDataFromString(loadedString);
        Assert.assertNotNull("NewsList Is Not Null", newsItemList);
        Assert.assertTrue(newsItemList.size() > 0);

        Assert.assertTrue(newsItemList.size() == 24);

        for (NewsEntity newsEntity : newsItemList) {
            // you need to add all the NotNull cases here to avoid unexpected null pointers

            List<MediaEntity> mediaEntity = newsEntity.getMediaEntity();

            if (mediaEntity != null && mediaEntity.size() > 0) {
                // Test Cases for Mandatory Fields inside MediaEntity

                Assert.assertNotNull(mediaEntity.get(0).getUrl());

            }

        }

    }


}