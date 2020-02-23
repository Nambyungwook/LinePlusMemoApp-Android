package com.nbw.lineplusmemoapp.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.nbw.lineplusmemoapp.R;
import com.nbw.lineplusmemoapp.list.ImgListAdapter;
import com.nbw.lineplusmemoapp.list.ImgListDecoration;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ImageAddDialogActivity extends AppCompatActivity {

    private int REQUEST_IMAGE_CAPTURE = 0;//사진촬영
    private int REQUEST_GALLERY = 1;//갤러리

    private int REQUEST;

    EditText et_url;
    RecyclerView img_recycle_view_img_dialog;

    Bitmap bitmap;

    private String imageFilePath;

    private Uri photoUri;

    String strUrl;
    //사용자가 입력한 이미지 주소들
    ArrayList<String> imgArray = new ArrayList<String>();
    //다른 액티비티로 전달할 이미지 주소들
    ArrayList<String> sendImgArray = new ArrayList<String>();

    ImgListAdapter imgListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_add_dialog);

        //상태 바 제거(전체화면 모드)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        et_url = (EditText) findViewById(R.id.et_url);
        img_recycle_view_img_dialog = (RecyclerView) findViewById(R.id.img_recycle_view_img_dialog);
    }

    //사진 촬영
    public void onClickPhoto(View view) {
        takePhoto();
    }

    //갤러리
    public void onClickGallery(View view) {
        gallery();
    }

    //URL입력 - url주소를 입력 받아 리사이클뷰에 적용 및 어레이리스트에 저장
    public void onClickURL(View view) {
        strUrl = et_url.getText().toString();

        if (strUrl.equals("")) {
            Toast.makeText(this, "url을 입력해 주세요.", Toast.LENGTH_SHORT).show();
        } else {
            imgArray.add(strUrl);
            setImgRecycleView(imgArray);
        }

    }

    //확인
    public void onClickOK(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("LinePlusMemoApp", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //set을 사용해 저장하기 때문에 같은 이미지 저장이 안되는걸 고려하여 앞에 번호를 붙인다.
        for (int i =0; i < imgArray.size(); i++) {
            String tmpImgStr = i + imgArray.get(i);
            sendImgArray.add(tmpImgStr);

        }

        Set<String> set = new HashSet<String>();

        set.addAll(sendImgArray);

        editor.putStringSet("sendImgArray", set);
        //이미지가 추가 되었는지 아닌지 판단하기위한 변수
        editor.putInt("strImgChecker",1);
        editor.commit();

        finish();
    }

    //사진촬영 메소드
    private void takePhoto() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);

        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }

            if (photoFile != null) {
                photoUri = FileProvider.getUriForFile(this, getPackageName(), photoFile);

                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
            }
        }


    }

    //갤러리 접근 메소드
    private void gallery() {
        //앨범에서 사진 선택
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
        startActivityForResult(galleryIntent, REQUEST_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_GALLERY && resultCode == RESULT_OK && data != null && data.getData() != null) {

            //갤러리 사진이 존재하는 상대적 위치
            Uri selectedImageUri = data.getData();
            String strUri = selectedImageUri.toString();
            imgArray.add(strUri);

            setImgRecycleView(imgArray);

        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            //촬영한 이미지 Uri
            Uri captureImageUri = photoUri;
            String strUri = captureImageUri.toString();
            imgArray.add(strUri);

            setImgRecycleView(imgArray);

        }
    }

    //리사이클뷰에 다수의 이미지 보여주는 메소드
    private void setImgRecycleView(ArrayList<String> imgArray) {

        //리사이클뷰 화면 설정
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        img_recycle_view_img_dialog.setLayoutManager(layoutManager);

        //이미지 리스트 어댑터 생성 및 설정
        imgListAdapter = new ImgListAdapter();

        imgListAdapter.setImgItemList(imgArray);

        img_recycle_view_img_dialog.setAdapter(imgListAdapter);

        ImgListDecoration imgListDecoration = new ImgListDecoration();
        img_recycle_view_img_dialog.addItemDecoration(imgListDecoration);
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "TEST_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,      /* prefix */
                ".jpg",         /* suffix */
                storageDir          /* directory */
        );
        imageFilePath = image.getAbsolutePath();
        return image;
    }

}
