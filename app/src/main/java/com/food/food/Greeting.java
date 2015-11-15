package com.food.food;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;

import com.food.fragments.CatalogFragment;
import com.food.fragments.GreetingFragment;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;

public class Greeting extends AppCompatActivity {

    private static final String link = "http://ufa.farfor.ru/getyml/?key=ukAXxeJYZN";
    private Drawer.Result drawerResult = null;
    private HashMap<Integer, ArrayList<Dish>> dishes;
    private ArrayList<CatalogItems> catalogItems;
    private CatalogFragment catalogFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greeting);

        GreetingFragment gf = new GreetingFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.catalog, gf)
                .commit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        parseXMLfromURL();


        drawerResult = new Drawer()
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withHeader(R.layout.drawer_header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_catalog).withIcon(FontAwesome.Icon.faw_navicon),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_contact).withIcon(FontAwesome.Icon.faw_map_marker)

                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {
                        if (iDrawerItem instanceof Nameable) {
                            {
                                if ((Greeting.this.getString(((Nameable) iDrawerItem).getNameRes()).equals("Каталог"))) {
                                    FragmentManager fm = getSupportFragmentManager();
                                    for (int j = 0; j < fm.getBackStackEntryCount(); j++) {
                                        fm.popBackStack();
                                    }
                                    Bundle bundle = new Bundle();
                                    bundle.putParcelableArrayList("catalogNames", catalogItems);
                                    catalogFragment = new CatalogFragment();
                                    catalogFragment.setArguments(bundle);
                                    getSupportFragmentManager().beginTransaction()
                                            .replace(R.id.catalog, catalogFragment)
                                            .addToBackStack(null)
                                            .commit();
                                } else if (Greeting.this.getString(((Nameable) iDrawerItem).getNameRes()).equals("Контакты")) {
                                    Intent intent = new Intent(Greeting.this, MapActivity.class);
                                    startActivity(intent);
                                }
                            }
                        }
                    }
                })
                .build();

    }

    public CatalogFragment getCatalogFragment() {
        return catalogFragment;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_greeting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        if (drawerResult.isDrawerOpen()) {
            drawerResult.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    private void parseXMLfromURL() {

        class GetXML extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Greeting.this, "Загрузка каталога...", null, true, true);

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

            }

            @Override
            protected String doInBackground(String... params) {
                catalogItems = new ArrayList<>();
                dishes = new HashMap<>();
                InputStream is = null;
                StringBuilder output = new StringBuilder();
                try{
                    URL url = new URL(link);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.setReadTimeout(20000);
                    connection.setConnectTimeout(20000);
                    connection.setRequestMethod("GET");
                    connection.connect();
                    int response = connection.getResponseCode();
                    if (response == HttpURLConnection.HTTP_OK){
                        is = connection.getInputStream();
                    }
                    BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("windows-1251")));
                    String temp;
                    while((temp = br.readLine()) != null)
                        output.append(temp);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                XMLParserHandler parserHandler = new XMLParserHandler();
                if(is != null) {
                    InputStream inputStream = new ByteArrayInputStream(output.toString().getBytes());
                    catalogItems = XMLParser.parse(inputStream);
                    dishes = XMLParser.getDishHashMap();
                }
                return null;
            }
        }
        GetXML gj = new GetXML();
        gj.execute(link);
    }

    public HashMap<Integer, ArrayList<Dish>> getDishes() {
        return dishes;
    }

    public void setDishes(HashMap<Integer, ArrayList<Dish>> dishes) {
        this.dishes = dishes;
    }


}
