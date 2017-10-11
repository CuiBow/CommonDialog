# CommonDialog
公用弹窗

#### 使用方式
1.在你的项目的 build.gradle 添加以下代码 Add it in your root build.gradle at the end of repositories:
```
allprojects {

  repositories {
  	...
	  	maven { url 'https://jitpack.io' }
    }
}
```
2.在你的 Library 中添加如下代码
```
dependencies { 

	    compile 'com.github.CuiBow:CommonDialog:v1.3.1'
     
} 
```
3.创建弹窗
```
 进度条弹窗
 ProgressDialog dialogs=new ProgressDialog(MainActivity.this,true);
 dialogs.show();
 dialogs.setProgress(100);
 dialogs.setProgressListener(new ProgressDialog.OnProgressListener() {
     @Override
     public void onProgress(int progress) {
          if (progress==100){
              dialogs.dismiss();
             }
           }
       });
       
 列表弹窗
 ListDialog dialog=new ListDialog(MainActivity.this);
                dialog.show();
                dialog.setListAdapter(list);
                dialog.setClickListener(new DialogClickListener() {
                    @Override
                    public void doCommit() {
                        dialog.dismiss();
                    }

                    @Override
                    public void cancal() {
                        dialog.dismiss();
                    }
                });        
```
