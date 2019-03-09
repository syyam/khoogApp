package com.android.khoog.partner;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.khoog.Constants.Constants;
import com.android.khoog.R;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import com.nispok.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class pdf extends AppCompatActivity {
    Button fbtn;
    String f1,f2,sharedid;
    TextView tv,tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9,tv10,tv11,tv12,tv13,tv14,tv15,tv16,tv17,tv18,tv19,tv20,
            tv21,tv22,tv23,tv24,tv25,tv26,tv27,tv28,tv29,tv30,tv31,tv32,tv33,tv34,tv35;
    RadioButton rbtn;

    ProgressDialog pgDialog;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        tv = (TextView)findViewById(R.id.testTv);
        tv1 = (TextView)findViewById(R.id.testTv1);
        tv2 = (TextView)findViewById(R.id.testTv2);
        tv3 = (TextView)findViewById(R.id.testTv3);
        tv4 = (TextView)findViewById(R.id.testTv4);
        tv5 = (TextView)findViewById(R.id.testTv5);
        tv6= (TextView)findViewById(R.id.testTv6);
        tv7= (TextView)findViewById(R.id.testTv7);
        tv8 = (TextView)findViewById(R.id.testTv8);
        tv9 = (TextView)findViewById(R.id.testTv9);
        tv10 = (TextView)findViewById(R.id.testTv10);
        tv11 = (TextView)findViewById(R.id.testTv11);
        tv12 = (TextView)findViewById(R.id.testTv12);
        tv13 = (TextView)findViewById(R.id.testTv13);
        tv14 = (TextView)findViewById(R.id.testTv14);
        tv15 = (TextView)findViewById(R.id.testTv15);
        tv16 = (TextView)findViewById(R.id.testTv16);
        tv17 = (TextView)findViewById(R.id.testTv17);
        tv18 = (TextView)findViewById(R.id.testTv18);
        tv19 = (TextView)findViewById(R.id.testTv19);
        tv20 = (TextView)findViewById(R.id.testTv20);
        tv21 = (TextView)findViewById(R.id.testTv21);
        tv22 = (TextView)findViewById(R.id.testTv22);
        tv23 = (TextView)findViewById(R.id.testTv23);
        tv24 = (TextView)findViewById(R.id.testTv24);
        tv25 = (TextView)findViewById(R.id.testTv25);
        tv26 = (TextView)findViewById(R.id.testTv26);
        tv27 = (TextView)findViewById(R.id.testTv27);
        tv28 = (TextView)findViewById(R.id.testTv28);
        tv29 = (TextView)findViewById(R.id.testTv29);
        tv30 = (TextView)findViewById(R.id.testTv30);
        tv31 = (TextView)findViewById(R.id.testTv31);
        tv32 = (TextView)findViewById(R.id.testTv32);
        tv33 = (TextView)findViewById(R.id.testTv33);
        tv34 = (TextView)findViewById(R.id.testTv34);
        tv35 = (TextView)findViewById(R.id.testTv35);

        SharedPreferences result = getSharedPreferences("userinfo",Context.MODE_PRIVATE);
        sharedid = result.getString("userid","");
        f1 = result.getString("fn","");
        f2 =  result.getString("ln","");

        tv9.setText(f1+" "+f2+" "+tv9.getText().toString());
        tv14.setText(f1+" "+f2+" "+tv14.getText().toString());
        tv15.setText(f1+" "+f2+" "+tv15.getText().toString());
        tv20.setText("2. "+f1+" "+f2+" "+tv20.getText().toString());
        tv22.setText("4. khooG will transfer the amount after deduction of its share to the "+f1+" "+f2+" within four working days after the booking is complete and no complaints are received.");
        tv23.setText("5. khooG will be able to hold or deduct money from the share of "+f1+" "+f2+" if it fails to provide complete and satisfactory services to travelers according to the terms agreed before the tour.");
        tv24.setText("6. khooG will be able to ask for refund if "+f1+" "+f2+" fails to provide the mentioned services to the traveler during the tour.");

        tv25.setText("7. "+f1+" "+f2+" "+tv25.getText().toString());
        tv26.setText("8. Incase of emergency during the tour or an act of God, "+f1+" "+f2+" would be solely responsible to cover medical expenses and/or reschedule the tour.");
        tv27.setText("9. "+f1+" "+f2+" "+tv27.getText().toString());
        tv33.setText(currentDate);
        tv34.setText(f1+" "+f2);
        tv35.setText(currentDate);

        rbtn = (RadioButton)findViewById(R.id.radiobtn);
        fbtn = (Button)findViewById(R.id.finshBtn);
        fbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if  (!rbtn.isChecked()){
                    rbtn.requestFocus();
                    Snackbar.with(getApplicationContext()) // context
                            .text("Please accept the agreement") // text to display
                            .color(Color.RED)
                            .textColor(Color.WHITE)
                            .show(pdf.this);
                }
                else {

/*                    Intent in = new Intent(pdf.this, finishRegistration.class);
                    startActivity(in);
                    finish();*/

                    SharedPreferences sp = getSharedPreferences("userinfo",Context.MODE_PRIVATE);
                    String userid = sp.getString("userid","");

                    Constants constants = new Constants();
                    pgDialog = new ProgressDialog(pdf.this);
                    pgDialog = new ProgressDialog(pdf.this);
                    pgDialog.setMessage("Please wait...");
                    pgDialog.show();
                    pgDialog.setCanceledOnTouchOutside(false);

                    AndroidNetworking.post(constants.getUrl())
                            .addBodyParameter("action", "companyregistrationend")
                            .addBodyParameter("compid", userid)
                            .setTag("test")
                            .setPriority(Priority.MEDIUM)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {

                                    String result = null;
                                    try {
                                        result = response.getString("result");


                                        if (result.equals("success"))
                                        {


                                            pgDialog.dismiss();



                                            Intent intent = new Intent(pdf.this, finishRegistration.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                            finish();



                                        }else{
                                            pgDialog.dismiss();
                                            Snackbar.with(getApplicationContext()) // context
                                                    .text("Some error occred ") // text to display
                                                    .color(Color.RED)
                                                    .textColor(Color.WHITE)
                                                    .show(pdf.this);
                                        }


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                }
                                @Override
                                public void onError(ANError error) {
                                    // handle error
                                }
                            });







                }
            }
        });



    }
    public void createPDF(View view) {
//reference to EditText
        //EditText et = (EditText) findViewById(R.id.txt_input);



//create document object
        Document doc = new Document();
//output file path
        String outpath = Environment.getExternalStorageDirectory() + "/mypdf.pdf";
        try {
//create pdf writer instance
            PdfWriter.getInstance(doc, new FileOutputStream(outpath));
//open the document for writing
            doc.open();
//add paragraph to the document

            Font font1 = new Font(Font.FontFamily.TIMES_ROMAN, 25, Font.BOLD);
            Chunk hello = new Chunk(tv.getText().toString(), font1);
            Paragraph p1 = new Paragraph(hello);
            p1.setAlignment(Paragraph.ALIGN_CENTER);
            doc.add(p1);
            Paragraph p2 = new Paragraph(tv1.getText().toString()+"\n"+tv2.getText().toString());
            p2.setPaddingTop(10);
            doc.add(p2);
            Font font2 = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
            Chunk heading1 = new Chunk(tv3.getText().toString(), font2);
            Paragraph h1 = new Paragraph(heading1);
            doc.add(h1);
            Paragraph p3 = new Paragraph(tv4.getText().toString());
            doc.add(p3);
            Chunk heading2 = new Chunk(tv5.getText().toString(), font2);
            Paragraph h2 = new Paragraph(heading2);
            doc.add(h2);
            Paragraph p4 = new Paragraph(tv6.getText().toString());
            doc.add(p4);
            Chunk heading3 = new Chunk(tv7.getText().toString(), font2);
            Paragraph h3 = new Paragraph(heading3);
            doc.add(h3);
            Paragraph p5 = new Paragraph(tv8.getText().toString());
            doc.add(p5);
            Chunk heading4 = new Chunk(tv9.getText().toString(), font2);
            Paragraph h4 = new Paragraph(heading4);
            doc.add(h4);
            Paragraph p6 = new Paragraph(tv10.getText().toString());
            doc.add(p6);
            Chunk heading5 = new Chunk(tv11.getText().toString(), font2);
            Paragraph h5 = new Paragraph(heading5);
            doc.add(h5);
            Paragraph p7 = new Paragraph(tv12.getText().toString()+"\n"+tv13.getText().toString());
            doc.add(p7);
            Chunk heading6 = new Chunk(tv14.getText().toString(), font2);
            Paragraph h6 = new Paragraph(heading6);
            doc.add(h6);
            Paragraph p8 = new Paragraph(f1+" "+f2+" "+tv15.getText().toString()+"\n"+tv16.getText().toString());
            doc.add(p8);
            Chunk heading7 = new Chunk(tv17.getText().toString(), font2);
            Paragraph h7 = new Paragraph(heading7);
            doc.add(h7);
            Paragraph p9 = new Paragraph(tv18.getText().toString()+"\n"+tv19.getText().toString()+"\n"+tv20.getText().toString()
                    +"\n"+tv21.getText().toString()+"\n"+tv22.getText().toString()+"\n"+tv23.getText().toString()+"\n"+tv24.getText().toString()
                    +"\n"+tv25.getText().toString()+"\n"+tv26.getText().toString()+"\n"+tv27.getText().toString()+"\n"+tv28.getText().toString()+"\n"+tv29.getText().toString());
            doc.add(p9);
            Chunk heading8 = new Chunk(tv30.getText().toString(), font2);
            Paragraph h8 = new Paragraph(heading8);
            doc.add(h8);
            Paragraph p10 = new Paragraph(tv31.getText().toString());
            doc.add(p10);


            Chunk glue = new Chunk(new VerticalPositionMark());
            Paragraph p11 = new Paragraph(tv32.getText().toString(),font2);
            p11.add(new Chunk(glue));
            p11.add(tv34.getText().toString());
            doc.add(p11);

            Chunk glue1 = new Chunk(new VerticalPositionMark());
            Paragraph p12 = new Paragraph(tv33.getText().toString(),font2);
            p12.add(new Chunk(glue1));
            p12.add(tv35.getText().toString());
            doc.add(p12);


//close the document
            doc.close();
            Toast.makeText(this, "downloaded", Toast.LENGTH_SHORT).show();

        } catch (FileNotFoundException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        } catch (DocumentException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
