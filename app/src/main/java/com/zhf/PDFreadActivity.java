package com.zhf;

import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.qingyii.hxt.PDFBookActivity;
import com.qingyii.hxt.R;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PDFreadActivity extends AppCompatActivity implements OnPageChangeListener ,OnLoadCompleteListener{
     private Unbinder  unbinder;
     PDFView pdfView;
     private String path;
     @BindView(R.id.toolbar_back)
     Button back;
    @BindView(R.id.toolbar_title)
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfread);
        unbinder= ButterKnife.bind(this);
        pdfView= (PDFView) findViewById(R.id.pdfView);
        back.setVisibility(View.GONE);
        path=getIntent().getExtras().getString("path");
        initpdfview();
    }

    private void initpdfview() {
        pdfView.fromFile(new File(path))
                .enableSwipe(true)
                .defaultPage(0)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .enableDoubletap(true)
                .scrollHandle(new DefaultScrollHandle(this))
                .load();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unbinder.unbind();
        pdfView=null;
    }

    @Override
    public void onPageChanged(int page, int pageCount) {

    }

    @Override
    public void loadComplete(int nbPages) {
        tv.setText(pdfView.getDocumentMeta().getTitle());
    }


}
