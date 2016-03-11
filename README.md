# AndroidChallenge - AGODA
#####1) Fix crashes in News List page
#####2) Fix crashes in News Detail page
#####3) Basic unit test
#####4) Improvements

-------

##1) Fix crashes in News List page:


######POINT 1

- Update line no 26 from MediaEntity.class to the following

caption = jsonObject.getString("caption");

- Please note there was a typo for the label "caption"

######POINT 2

#####Current Code Snippet:
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NewsEntity newsEntity = (NewsEntity) getItem(position);
        List<MediaEntity> mediaEntityList = newsEntity.getMediaEntity();
        String thumbnailURL = "";
        MediaEntity mediaEntity = mediaEntityList.get(0);
        thumbnailURL = mediaEntity.getUrl();

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item_news, parent, false);
            viewHolder.newsTitle = (TextView) convertView.findViewById(R.id.news_title);
            viewHolder.imageView = (DraweeView) convertView.findViewById(R.id.news_item_image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.newsTitle.setText(newsEntity.getTitle());
        DraweeController draweeController = Fresco.newDraweeControllerBuilder().setImageRequest(ImageRequest.fromUri
                (Uri.parse(thumbnailURL))).setOldController(viewHolder.imageView.getController()).build();
        viewHolder.imageView.setController(draweeController);
        return convertView;
    }

#####Corrected Code Snippet (Add null checks for imageURL):
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NewsEntity newsEntity = (NewsEntity) getItem(position);
        List<MediaEntity> mediaEntityList = newsEntity.getMediaEntity();
        String thumbnailURL = "";

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item_news, parent, false);
            viewHolder.newsTitle = (TextView) convertView.findViewById(R.id.news_title);
            viewHolder.imageView = (DraweeView) convertView.findViewById(R.id.news_item_image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.newsTitle.setText(newsEntity.getTitle());

        // add a null check and size check
        if (mediaEntityList != null && mediaEntityList.size() > 0) {
            MediaEntity mediaEntity = mediaEntityList.get(0);
            thumbnailURL = mediaEntity.getUrl();

            viewHolder.imageView.setVisibility(View.VISIBLE);
            DraweeController draweeController = Fresco.newDraweeControllerBuilder().setImageRequest(ImageRequest.fromUri
                    (Uri.parse(thumbnailURL))).setOldController(viewHolder.imageView.getController()).build();
            viewHolder.imageView.setController(draweeController);
        } else {
            //this means that the mediaEntityList is empty so HIDE the image view
            viewHolder.imageView.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }


##2) Fix crashes in News Detail page

######POINT 1

- The Intent was not holding the extra paramater when called from the MainActivity.java

- Add the following code on line no: 67 in MainActivity.java


		Intent intent = new Intent(MainActivity.this, DetailViewActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("storyURL", newsEntity.getArticleUrl());
        intent.putExtra("summary", newsEntity.getSummary());
        if(newsEntity.getMediaEntity() != null && newsEntity.getMediaEntity().size() > 0) {
            intent.putExtra("imageURL", newsEntity.getMediaEntity().get(0).getUrl());
        }
        startActivity(intent);


######POINT 2
- Please add a null check to the imageURL on the DetailViewActivity.java
- Update the code as follows on line no: 41 in DetailViewActivity.java

        if (imageURL != null && imageURL.length() > 0) {
            DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                    .setImageRequest(ImageRequest.fromUri(Uri.parse(imageURL)))
                    .setOldController(imageView.getController()).build();
            imageView.setController(draweeController);
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.INVISIBLE);
        }


##3)Basic unit test:

Below is the small snippet for Basic Unit testing in the app:

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


##4)Improvements:

1) The main logic is written in MainActivity, which is not a very clean way to construct an app. Can you help to improve it?
Yes, please find attached a fresh new project for a cleaner and maintable source code.

Download Link: 
Github Link:

- Use of Fragments
- Project Structure
- Usage of 3rd party libraries
- Dependency Injections


Details:




2) The way of parsing JSON data is not very nice. For example, if one of the name/value is missing, it can cause the app to crash. Can you help to make it better?

- Using Gson Library
- Using JsonDeserializer
- Please refer to the ** for exact implementation
