package org.mnemosyne;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.srplab.www.starcore.*;

public class MnemosyneActivity extends Activity {

	static TextView MyEdit1;
	
	private void mergeApkFile(Activity c, ArrayList<String> partFileList, String dst) throws IOException {  
	    if (!new File(partFileList.get(0)).exists()) {  
	        //OutputStream out = new FileOutputStream(dst);    
	    	OutputStream out = openFileOutput(dst, MODE_WORLD_READABLE );  
	        byte[] buffer = new byte[1024];  
	        InputStream in;  
	        int readLen = 0;  
	        for(int i=0;i<partFileList.size();i++){  
	            in = c.getAssets().open(partFileList.get(i));  
	            while((readLen = in.read(buffer)) != -1){  
	                out.write(buffer, 0, readLen);  
	            }  
	            out.flush();  
	            in.close();  
	        }  
	        out.close();  
	    }  
	} 
	private void copyFile(Activity c, String Name,String desPath) throws IOException {  
		File outfile = new File("/data/data/"+getPackageName()+"/files/"+desPath+Name); 
	    if (!outfile.exists()) {
	    	outfile.createNewFile();
        	FileOutputStream out = new FileOutputStream(outfile);        	
	        byte[] buffer = new byte[1024];  
	        InputStream in;  
	        int readLen = 0;  
            in = c.getAssets().open(desPath+Name);  
            while((readLen = in.read(buffer)) != -1){  
                out.write(buffer, 0, readLen);  
            }  
            out.flush();  
            in.close();  
	        out.close();  
	    }  
	} 
	
	private boolean CreatePath(String Path){
		File destCardDir = new File(Path);
        if(!destCardDir.exists()){
        	int Index = Path.lastIndexOf(File.separator.charAt(0));
        	if( Index < 0 ){
        		if( destCardDir.mkdirs() == false )
        			return false;
        	}else{
        		String ParentPath = Path.substring(0, Index);
        		if( CreatePath(ParentPath) == false )
        			return false;
        		if( destCardDir.mkdirs() == false )
        			return false;        		
        	}
        }
        return true;
    }
	
    private boolean unzip(InputStream zipFileName, String outputDirectory,Boolean OverWriteFlag ) {
        try {
            ZipInputStream in = new ZipInputStream(zipFileName);
            ZipEntry entry = in.getNextEntry();
            byte[] buffer = new byte[1024];
            while (entry != null) {
                File file = new File(outputDirectory);
                file.mkdir();
                if (entry.isDirectory()) {
                    String name = entry.getName();
                    name = name.substring(0, name.length() - 1);
                    if( CreatePath(outputDirectory + File.separator + name) == false )
                    	return false;
                } else {
                	String name = outputDirectory + File.separator + entry.getName();
                	int Index = name.lastIndexOf(File.separator.charAt(0));
                	if( Index < 0 ){
                		file = new File(outputDirectory + File.separator + entry.getName());
                	}else{
                		String ParentPath = name.substring(0, Index);
                		if( CreatePath(ParentPath) == false )
                			return false;             
                		file = new File(outputDirectory + File.separator + entry.getName());
                	}
                    if( !file.exists() || OverWriteFlag == true){
                    	file.createNewFile();
                    	FileOutputStream out = new FileOutputStream(file);
                    	int readLen = 0;  
        	            while((readLen = in.read(buffer)) != -1){  
        	                out.write(buffer, 0, readLen);  
        	            }                      	
                    	out.close();
                    }
                }
                entry = in.getNextEntry();
            }
            in.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }  

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        MyEdit1 = (TextView) this.findViewById(R.id.widget61);
        
        java.io.File python_extras_r14File = new java.io.File("/data/data/" + getPackageName() + "/files/python_extras_r14.zip");
        if( !python_extras_r14File.exists() ){        
	        ArrayList<String> StarCoreFiles =  new ArrayList<String>();
    	    StarCoreFiles.add("python_extras_r14_aa");
        	StarCoreFiles.add("python_extras_r14_ab");
	        StarCoreFiles.add("python_extras_r14_ac");
    	    StarCoreFiles.add("python_extras_r14_ad");
	        try{
    	       mergeApkFile(this,StarCoreFiles,"python_extras_r14.zip");
	        }
    	    catch(Exception e){
        	}
        }
        
        File destDir = new File("/data/data/"+getPackageName()+"/files/lib-dynload");
        if(!destDir.exists())
        	destDir.mkdirs();
        try{
        	copyFile(this,"_bisect.so","lib-dynload/");
        	copyFile(this,"_bytesio.so","lib-dynload/");
        	copyFile(this,"_codecs_cn.so","lib-dynload/");
        	copyFile(this,"_codecs_hk.so","lib-dynload/");
        	copyFile(this,"_codecs_iso2022.so","lib-dynload/");
        	copyFile(this,"_codecs_jp.so","lib-dynload/");
        	copyFile(this,"_codecs_kr.so","lib-dynload/");
        	copyFile(this,"_codecs_tw.so","lib-dynload/");
        	copyFile(this,"_collections.so","lib-dynload/");
        	copyFile(this,"_ctypes.so","lib-dynload/");
        	copyFile(this,"_ctypes_test.so","lib-dynload/");
        	copyFile(this,"_elementtree.so","lib-dynload/");
        	copyFile(this,"_fileio.so","lib-dynload/");
        	copyFile(this,"_functools.so","lib-dynload/");
        	copyFile(this,"_heapq.so","lib-dynload/");
        	copyFile(this,"_hotshot.so","lib-dynload/");
        	copyFile(this,"_json.so","lib-dynload/");
        	copyFile(this,"_lsprof.so","lib-dynload/");
        	copyFile(this,"_md5.so","lib-dynload/");
        	copyFile(this,"_multibytecodec.so","lib-dynload/");
        	copyFile(this,"_multiprocessing.so","lib-dynload/");
        	copyFile(this,"_random.so","lib-dynload/");
        	copyFile(this,"_sha256.so","lib-dynload/");
        	copyFile(this,"_sha512.so","lib-dynload/");
        	copyFile(this,"_sha.so","lib-dynload/");
        	copyFile(this,"_socket.so","lib-dynload/");
        	copyFile(this,"_sqlite3.so","lib-dynload/");
        	copyFile(this,"_ssl.so","lib-dynload/");
        	copyFile(this,"_struct.so","lib-dynload/");
        	copyFile(this,"_testcapi.so","lib-dynload/");
        	copyFile(this,"_weakref.so","lib-dynload/");
        	copyFile(this,"array.so","lib-dynload/");
        	copyFile(this,"audioop.so","lib-dynload/");
        	copyFile(this,"binascii.so","lib-dynload/");
        	copyFile(this,"bz2.so","lib-dynload/");
        	copyFile(this,"cmath.so","lib-dynload/");
        	copyFile(this,"cPickle.so","lib-dynload/");
        	copyFile(this,"crypt.so","lib-dynload/");
        	copyFile(this,"cStringIO.so","lib-dynload/");
        	copyFile(this,"datetime.so","lib-dynload/");
        	copyFile(this,"fcntl.so","lib-dynload/");
        	copyFile(this,"future_builtins.so","lib-dynload/");
        	copyFile(this,"imageop.so","lib-dynload/");
        	copyFile(this,"itertools.so","lib-dynload/");
        	copyFile(this,"math.so","lib-dynload/");
        	copyFile(this,"mmap.so","lib-dynload/");
        	copyFile(this,"operator.so","lib-dynload/");
        	copyFile(this,"parser.so","lib-dynload/");
        	copyFile(this,"pyexpat.so","lib-dynload/");
        	copyFile(this,"resource.so","lib-dynload/");
        	copyFile(this,"select.so","lib-dynload/");
        	copyFile(this,"strop.so","lib-dynload/");
        	copyFile(this,"syslog.so","lib-dynload/");
        	copyFile(this,"termios.so","lib-dynload/");
        	copyFile(this,"time.so","lib-dynload/");
        	copyFile(this,"unicodedata.so","lib-dynload/");
        	copyFile(this,"zlib.so","lib-dynload/");
        }
        catch(Exception e){
        	System.out.println(e);        	
        }
        
        try {
        	AssetManager assetManager = getAssets();
        	//InputStream dataSource = assetManager.open("testpy.zip");
        	InputStream dataSource = assetManager.open("mnemosyne.zip");
        	StarCoreFactoryPath.CreatePath(Runtime.getRuntime(), 
        			"/data/data/" + getPackageName() + "/files");
        	unzip(dataSource, "/data/data/" + getPackageName() + "/files", true);
        }
        catch (IOException e) {
        	printStr(e); 
        }        	        	
        
        StarCoreFactoryPath.StarCoreCoreLibraryPath = "/data/data/" + getPackageName() + "/lib";
        StarCoreFactoryPath.StarCoreShareLibraryPath = "/data/data/" + getPackageName() + "/lib";
        StarCoreFactoryPath.StarCoreOperationPath = "/data/data/" + getPackageName() + "/files";
        StarCoreFactory starcore = StarCoreFactory.GetFactory();
        
        StarSrvGroupClass SrvGroup = starcore._GetSrvGroup(0); 
        StarServiceClass Service = SrvGroup._GetService("test", "123");
        StarObjectClass python = null;
        if ( Service == null ) {  // The service has not been initialized.
          Service = starcore._InitSimple("test", "123", 0, 0);
          Service._CheckPassword(false);
          SrvGroup = (StarSrvGroupClass) Service._Get("_ServiceGroup");
          SrvGroup._InitRaw("python", Service);
  		  python = Service._ImportRawContext("python", "", false, ""); 
  		
  		  // Set up extra paths.
          python._Call("import", "sys");
          StarObjectClass pythonSys = python._GetObject("sys");
          StarObjectClass pythonPath = (StarObjectClass) pythonSys._Get("path");
          pythonPath._Call("insert", 0, "/data/data/" + getPackageName() + "/files/python_extras_r14.zip");
          pythonPath._Call("insert", 0, "/data/data/" + getPackageName() + "/lib");
          pythonPath._Call("insert", 0, "/data/data/" + getPackageName() + "/files/lib-dynload");	
          pythonPath._Call("insert", 0, "/data/data/" + getPackageName() + "/files");	
          
          // Register simple call back.
          
          SrvGroup._LoadRawModule("python", "", "/data/data/" + getPackageName() + "/files/mnemosyne/cle/callback.py", false);
  		  StarObjectClass pythonCallback = (StarObjectClass) python._Get("callback");
  		  
          StarObjectClass javascriptCallback = Service._New()._Assign(new StarObjectClass() {
          	
          	public void makeToast(StarObjectClass self, StarParaPkgClass input) {
          		Toast.makeText(getApplicationContext(), input._GetStr(0), Toast.LENGTH_LONG).show();
            };
          });
     
          StarObjectClass proxy = Service._NewRawProxy("python", javascriptCallback, "makeToast", "makeToast", 0);
          pythonCallback._Call("set_callback", proxy);
          proxy._Free();
          
          // Run Mnemosyne itself.
          
          SrvGroup._DoFile("python", "/data/data/" + getPackageName() + "/files/mnemosyne/cle/mnemosyne.py");
          SrvGroup._LoadRawModule("python", "", "/data/data/" + getPackageName() + "/files/mnemosyne/cle/mnemosyne.py", false);
          //SrvGroup._LoadRawModule("python", "", "/data/data/" + getPackageName() + "/files/testpy.py", false);
          //SrvGroup._LoadRawModule("python", "", "/data/data/" + getPackageName() + "/files/callback.py", false);
          //Service._DoFile("python", "/data/data/"+getPackageName()+"/files/callback.py", "");
        }
		
        if (false) {
		//--all Python function tt, the return contains two integer, which will be packed into parapkg
		StarParaPkgClass ParaPkg = (StarParaPkgClass) python._Call("tt","hello ","world");
		printStr("ret from python :  "+ParaPkg._Get(0)+"   "+ParaPkg._Get(1));
		//--get global int value g1
		printStr("python value g1 :  "+python._Get("g1"));

		//--call Python function yy, the return is dict, which will be mapped to cle object
		StarObjectClass yy = (StarObjectClass) python._Call("yy","hello ","world",123);
		//--call dict __len__ function to get dict length
		printStr("python value dict length :  "+yy._Call("__len__"));

		//--get global class Multiply
		StarObjectClass Multiply = Service._ImportRawContext("python", "Multiply", true, null);
		StarObjectClass multiply = Multiply._Callobject("_StarCall",33,44);
		//--call instance method multiply
		printStr("instance multiply = " + multiply._Call("multiply",11,22));
		
		
        //--attach object to testpy.Class1 ---*/
        StarObjectClass TestCallBack = Service._ImportRawContext("python", "Class1", true, ""); 
        //--create an instance of TestCallBack-----*/
        //StarObjectClass inst = TestCallBack._Callobject("_StarCall");
	
		StarObjectClass inst = (StarObjectClass) python._Get("class1");
        
        // Create object and functions for proxy----*/
		
        StarObjectClass object = Service._New()._Assign(new StarObjectClass() {
        	
        	public void postExec(StarObjectClass self) {
        		printStr("Callback in Java from Python.");
            };
                    
             public float getNum(StarObjectClass self, StarParaPkgClass input) {
               	printStr("Callback [getNum] in Java from Python : " + input._Get(0) + "    " + input._Get(1));
                return (float)(input._Getdouble(0) + input._Getdouble(1));
             };
             
        });
       
        // Create proxy for interface testcallback/ICallBack ---*/
        StarObjectClass proxy1 = Service._NewRawProxy("python", object, "postExec", "_name_does_not_seem_to_matter_Class1.postExec", 0);
        StarObjectClass proxy2 = Service._NewRawProxy("python", object, "getNum", "Class1.getNum", 0);
        //--set the proxy to TestCallBack instance ---*/
        inst._Call("setCallBack", proxy1, proxy2);
        //--now proxy can be freed----*/
        proxy1._Free();
        proxy2._Free();

        //--call inst function postExec----*/
        inst._Call("postExec");
        //--call inst function getNum----*/
        printStr(inst._Call("getNum", SrvGroup._NewParaPkg(123.0,456.0)));    
        }
    }
    
    private void printStr(Object str)
    {
    	String in_Str;
    	
    	in_Str = MyEdit1.getText().toString();
    	in_Str = in_Str + "\n" + str;
    	MyEdit1.setText(in_Str);    	
    }
}