package com.qingyii.hxtz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.qingyii.hxtz.http.HttpUrlConfig;
import com.qingyii.hxtz.pojo.BooksParameter;

import es.voghdev.pdfviewpager.library.PDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.BasePDFPagerAdapter;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;

public class PDFBookActivity extends AppCompatActivity {
    PDFViewPager pdfViewPager;
    BasePDFPagerAdapter adapter;

    private BooksParameter.DataBean book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfbook);
        book = getIntent().getParcelableExtra("book");

        pdfViewPager = (PDFViewPager) findViewById(R.id.pdfViewPager);
        adapter = new PDFPagerAdapter(this, HttpUrlConfig.cacheDir + "/" + book.getSdCardUrl());
        pdfViewPager.setAdapter(adapter);
    }
}
