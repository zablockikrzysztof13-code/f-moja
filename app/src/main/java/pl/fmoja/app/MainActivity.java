package pl.fmoja.app;
import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.webkit.WebView;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.net.Uri;
public class MainActivity extends Activity {
  private WebView web; private ValueCallback<Uri[]> chooser; private static final int FILE_PICKER=101;
  @Override public void onCreate(Bundle b){super.onCreate(b);web=new WebView(this);setContentView(web);
    WebSettings s=web.getSettings();s.setJavaScriptEnabled(true);s.setDomStorageEnabled(true);s.setAllowFileAccess(true);s.setAllowFileAccessFromFileURLs(true);
    web.setWebViewClient(new WebViewClient());web.setWebChromeClient(new WebChromeClient(){
      @Override public boolean onShowFileChooser(WebView w,ValueCallback<Uri[]> cb,FileChooserParams params){if(chooser!=null)chooser.onReceiveValue(null);chooser=cb;try{startActivityForResult(params.createIntent(),FILE_PICKER);return true;}catch(Exception e){chooser=null;return false;}}
    });web.loadUrl("file:///android_asset/index.html");
  }
  @Override protected void onActivityResult(int req,int result,Intent data){super.onActivityResult(req,result,data);if(req==FILE_PICKER&&chooser!=null){chooser.onReceiveValue(result==RESULT_OK?WebChromeClient.FileChooserParams.parseResult(result,data):null);chooser=null;}}
  @Override public void onBackPressed(){if(web.canGoBack())web.goBack();else super.onBackPressed();}
}