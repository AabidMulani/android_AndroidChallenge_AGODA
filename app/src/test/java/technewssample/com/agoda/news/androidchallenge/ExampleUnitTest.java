package technewssample.com.agoda.news.androidchallenge;

import android.content.Context;
import android.test.suitebuilder.annotation.SmallTest;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;

import news.agoda.com.sample.R;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */

@SmallTest
@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {


    @Mock
    Context mMockContext;

    @Test
    public void checkAllEntityValues() throws Exception {
        Assert.assertNotNull(mMockContext);
        String loadedString = loadResource(mMockContext);
        Assert.assertNotNull("Loading Local String Successful", loadedString);

    }

    private String loadResource(Context context) {
        InputStream inputStream = context.getResources().openRawResource(R.raw.news_list);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];

        try {
            InputStreamReader inputReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferReader = new BufferedReader(inputReader);
            int n;
            while ((n = bufferReader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
            inputStream.close();
        } catch (IOException ioException) {
            return null;
        }

        return writer.toString();
    }

}