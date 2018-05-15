package org.tensorflow.online_shopping_demo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.view.View.INVISIBLE;

public class ListProductsActivity extends Activity {

    private ListView listView;
    public ArrayList list = new ArrayList();

    private List<String> imageUrlList = new ArrayList<>();
    private List<String> productNamesList = new ArrayList<>();
    private List<String> productPriceList = new ArrayList<>();
    private List<Integer> shoppingWebSiteNo = new ArrayList<>();

    private ProgressDialog progressDialog;

    private static String[] webSiteUrlList ={"https://www.gittigidiyor.com/arama/?k=", "https://www.hepsiburada.com/ara?q="};

    private String label;
    private String web_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_products);

        Bundle extras = getIntent().getExtras();
        label = extras.getString("PRODUCT_NAME");

        new getData().execute();

    }
    class ListProductActivityAdapter extends ArrayAdapter<String>
    {
        Context context;
        List<String> images;
        List<String> decriprions;
        List<Integer> iconNo;
        List<String> price;

        ListProductActivityAdapter(Context context, List<String> productNames, List<String> imageList, List<Integer> iconNumber, List<String>productPrices) {
            super(context, R.layout.single_row,R.id.product_description,productNames);
            this.context=context;
            this.images = imageList;
            this.decriprions = productNames;
            this.iconNo = iconNumber;
            this.price = productPrices;
        }

        class CustomViewHolder {
            ImageView imageView;
            TextView productName;
            TextView productPrice;
            ImageView imageViewLogo;


            CustomViewHolder(View view) {

                imageView = (ImageView) view.findViewById(R.id.imageView);
                imageViewLogo = (ImageView) view.findViewById(R.id.imageview_logo);
                productName = (TextView) view.findViewById(R.id.product_description);
                productPrice = (TextView) view.findViewById(R.id.product_price);





            }

        }
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            View row = convertView;
            CustomViewHolder holder = null;

            if(row == null)
            {
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = layoutInflater.inflate(R.layout.single_row,parent,false);
                holder = new CustomViewHolder(row);
                row.setTag(holder);

            }
            else
            {
                holder = (CustomViewHolder) row.getTag();
            }

                Picasso.with(getApplicationContext()).load(images.get(position)).into(holder.imageView);
                holder.productName.setText(decriprions.get(position));
                holder.productPrice.setText(price.get(position));
                if(iconNo.get(position)==1)
                {
                    holder.imageViewLogo.setImageResource(R.drawable.gittigidiyor);

                }

                if(iconNo.get(position)== 2)
                {
                    holder.imageViewLogo.setImageResource(R.drawable.hepsi_burada);
                }




            return row;
        }
    }

    private class getData extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(ListProductsActivity.this);
            progressDialog.setTitle("YÜKLENİYOR");
            progressDialog.setMessage("Lütfen Bekleyiniz...");
            progressDialog.setIndeterminate(false);
            progressDialog.show();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            String url = imageUrlList.get(0);
            listView = (ListView) findViewById(R.id.listView);
            ListProductActivityAdapter adapter = new ListProductActivityAdapter(getApplicationContext(),productNamesList,imageUrlList,shoppingWebSiteNo,productPriceList);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    //Toast.makeText( ListProductsActivity.this,productNamesList.get(i),Toast.LENGTH_SHORT).show();

                    Intent webPage = new Intent(Intent.ACTION_VIEW, Uri.parse(web_url));
                    startActivity(webPage);
                }
            });

            progressDialog.dismiss();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {

                if (label.equals("giyim")) {
                    label = "gömlek";
                }

                web_url = webSiteUrlList[0] + label;
                //System.out.println("************** " + web_url);

                Document docGittigidiyor = Jsoup.connect(web_url).timeout(30 * 1000).get();

                Elements productImagesGittiGidiyor = docGittigidiyor.getElementsByTag("img");
                int i = 0;

                for (Element el : productImagesGittiGidiyor) {
                    i++;
                    String imageUrl = el.absUrl("data-original");
                    imageUrlList.add(imageUrl);
                    shoppingWebSiteNo.add(1);
                    String productNames = el.absUrl("alt");

                    String[] parts = productNames.split(" ");
                    String name = parts[1];

                    for (int m = 2; m < parts.length; m++) {
                        name = name + " " + parts[m];
                    }
                    productNamesList.add(name);

                    if (i > 15)
                        break;

                }

                Elements productPriceGittiGidiyor = docGittigidiyor.select("p[itemprop = price]");
                i = 0;

                for (Element el : productPriceGittiGidiyor) {
                    productPriceList.add(el.text());
                    i++;
                    if (i > 15)
                        break;
                }


             web_url = null;
                web_url = webSiteUrlList[1]+label;

                System.out.println("************** "+web_url);

                Document docHepsiBurada = Jsoup.connect(web_url).timeout(30*1000).get();

                Elements productImagesHepsiBurada = docHepsiBurada.getElementsByTag("img");

                    for (Element el : productImagesHepsiBurada) {

                        Boolean control = true;
                        String imageUrl = el.absUrl("src");
                        //System.out.println("###################URL " + imageUrl);


                        String productNames = el.absUrl("title");
                        //System.out.println("############## " + productNames);

                        if (imageUrl != ""  ) {

                            String control_png = imageUrl.substring(imageUrl.length()-3);
                            if(control_png.equals("png"))
                                control = false;
                            if(control == true) {
                                imageUrlList.add(imageUrl);
                                shoppingWebSiteNo.add(2);
                                if (productNames != "") {
                                    String newName = productNames.substring(28);
                                    productNamesList.add(newName);
                                }

                            }

                        }

                    }

                Elements productPriceHepsiBurada = docHepsiBurada.select("div[class=product-detail]");
                i = 0;

                for (Element el : productPriceHepsiBurada) {
                    Elements priceValuesType_1 = el.select("span[class=price product-price]");

                    if(priceValuesType_1.size()>0)
                    {
                        //System.out.println("**************PRICE_TYPE_1 :  " +priceValuesType_1.text());
                        productPriceList.add(priceValuesType_1.text());
                    }

                    else
                    {
                        Elements priceValuesType_2 = el.select("div[class=price-value");

                        if(priceValuesType_2.size()>0)
                        {
                            //System.out.println("**************PRICE_TYPE_2 :  " +priceValuesType_2.text());
                            productPriceList.add("*DISCOUNT* "+priceValuesType_2.text());

                        }

                    }

                }




            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }


    }



}
