package com.unithon.cafee;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.RequestParams;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by Youngdo on 2016-07-31.
 */
public class LocationAcivity extends AppCompatActivity{
    String query = "";
    RequestParams params = new RequestParams();
    EditText inputlocation;
    List<SearchItem> list = new ArrayList<>();
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        inputlocation = (EditText) findViewById(R.id.inputlocation);
        Button select = (Button) findViewById(R.id.select);
        recyclerView = (RecyclerView) findViewById(R.id.search_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(LocationAcivity.this);
        recyclerView.setLayoutManager(layoutManager);
        final Intent intent = getIntent();
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    list.clear();
                    query = URLEncoder.encode(inputlocation.getText().toString(), "UTF8");
                    //new GetData().execute();
/*
                    params.put("query",query);
                    restClient.get("local.xml", params, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            Log.e("g",String.valueOf(statusCode));
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            Log.e("1234rwgdg",headers[0].toString());
                            Log.e("1234rwgdg",headers[1].toString());
                            Log.e("1234rwgdg",headers[2].toString());
                            Log.e("1234rwgdg",headers[3].toString());
                        }
                    });*/
                    new GetData().execute();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(LocationAcivity.this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                intent.putExtra("Latitude", list.get(position).getLatitude());
                intent.putExtra("Longtitude",list.get(position).getLongtitude());
                intent.putExtra("Location",list.get(position).getLocation());
                intent.putExtra("title",list.get(position).getTitle());
                setResult(RESULT_OK, intent);
                finish();
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));



    }

    public class GetData extends AsyncTask<Void, Void, Void> {
        // 뉴스의 타이틀을 저장해 주는 객체 생성
        Vector<String> titlevec = new Vector<>();
        // 뉴스의 기사내용을 저장해 주는 객체 생성
        Vector<String> descvec = new Vector<>();
        Vector<Double> mapx = new Vector<>();
        Vector<Double> mapy = new Vector<>();
        // 웹사이트에 접속할 주소
        String uri;
        // 웹사이트에 접속을 도와주는 클래스
        URL url;
        // XML문서의 내용을 임시로 저장할 변수
        String tagname = "", title = "", desc = "";
        String mapx1, mapy1;
        // 데이터의 내용을 모두 읽어드렸는지에 대한 정보를 저장
        Boolean flag = null;

        // 네트워크(url)에 접속해서 xml문서를 가져오는 메소드
        @Override
        protected Void doInBackground(Void... params) {
            try {
                //xml문서를 읽고 해석해줄 수 있는 객체를 선언
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                //네임스페이스 사용여부
                factory.setNamespaceAware(true);
                //실제 xml문서를 읽어 드리면서 데이터를 추출해주는 객체 선언
                XmlPullParser xpp = factory.newPullParser();

                // 웹사이트에 접속

                url = new URL(uri);
                HttpURLConnection con =(HttpURLConnection) url.openConnection();
                con.setRequestProperty("X-Naver-Client-Id","QdPFoUUgHHqO9FlJwK8h");
                con.setRequestProperty("X-Naver-Client-Secret","NhqI_6tOYg");
                con.setRequestMethod("GET");
                con.setRequestProperty("Content-Type", "application/xml");
                //con.setRequestProperty("Accept", "application/xml");
                // 사이트 접속후에 xml 문서를 읽어서 가져옴
                InputStream in = con.getInputStream();
                // 웹사이트로부터 받아온 xml문서를 읽어드리면서 데이터를 추출해 주는 XmlPullParser객체로 넘겨줌
                xpp.setInput(in, null);

                // 이벤트 내용을 사용하기 위해서 변수 선언
                int eventType = xpp.getEventType();
                // 반복문을 사용하여 문서의 끝까지 읽어 들이면서 데이터를 추출하여 각각의 벡터에 저장
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {
                        // 태그의 이름을 알아야 텍스트를 저장하기에 태그이름을 읽어서 변수에 저장

                            tagname = xpp.getName();



                    } else if (eventType == XmlPullParser.TEXT) {
                        // 태그 이름이 title과 같다면 변수에 title 저장
                        if (tagname.equals("title")) {
                                title += xpp.getText();

                        }
                        // 태그 이름이 description과 같다면 desc변수에 저장
                        else if (tagname.equals("address")) {
                            desc += xpp.getText();
                        }else if(tagname.equals("mapx")){
                            mapx1 += xpp.getText();
                        }else if(tagname.equals("mapy")){
                            mapy1 += xpp.getText();
                        }
                    } else if (eventType == XmlPullParser.END_TAG) {
                        // end tag 이름을 얻어옴
                        tagname = xpp.getName();
                        // end tag 이름이 item이라면 저장한 변수 title과 desc를 벡터에 저장
                        if (tagname.equals("item")) {
                            titlevec.add(title);
                            descvec.add(desc);
                            // 변수 초기화
                            SearchItem searchItem = new SearchItem(title, desc, mapx1,mapy1);
                            list.add(searchItem);
                            title = "";
                            desc = "";
                            mapy1 = "";
                            mapx1 = "";
                        }
                    }
                    // 다음 이벤트를 넘김
                    eventType = xpp.next();
                    if (isCancelled()) {
                        break;
                    }
                }
                // 모든 xml문서를 읽어드렸다면
                flag = true;


            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            uri = "https://openapi.naver.com/v1/search/local.xml?query="+query+"&display=20&start=1";
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            SearchAdapter searchAdapter = new SearchAdapter(LocationAcivity.this,list);
            recyclerView.setAdapter(searchAdapter);
        }
    }
}
