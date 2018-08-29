# Yolo-Android
Real Time Object Detection By Using YOLO to online shopping

## Training Custom Dataset By Using Darknet

To training your custom dataset by using Darknet,

1- You must first tag your dataset. You can download draw boxing program from this url https://github.com/puzzledqs/BBox-Label-Tool.

  - After download draw boxing program, create new file like as 002 into folders of "Examples" , "Images" and "Labels". 
  - You copy your images in the daatset to a your new file in "Images" file.For this tutorial, path is /Images/002/.
  - If extension of your images are different from "jpeg" ,change all the "jpeg" values in the main.py file to your extension. For example,
  making "jpeg" to "jpg".
  
  - Open cmd or terminal and run "python main.py"  
  ![ekran alintisi0000](https://user-images.githubusercontent.com/38051809/40126494-1d917fda-5936-11e8-9ff7-68311bbc85f2.PNG)

  - You see interface of program and than write images folder name in Image Dir textbox .(For this tutorial , it is 002) Click Load button.Then draw box  all of your images.
  
  ![ekran alintisi_lab](https://user-images.githubusercontent.com/38051809/40127198-8660337a-5937-11e8-9c54-a585a0e8af53.PNG)
  
  - You can see text files of your label coordinates.
  
  - Note: You have to create files each of class!!
  - You have to convert to float value your coordinates values.So, you download convert.py file and place it in the BBox-Label-Tool project folder.
  
  - Change some values:
       1- line of 15 : classes= ["002"]     
       2- line of 34 : yourPath="your path" (Example my path: "C:/BBox-Label-Tool-Python3.x/Labels/002")     
       3- line of 35 : ypurOutputPath="your output path"     
       4- line of 37 : cls="002"
     
  - Than run "python convert.py"  at terminal.

  
 2- Download darknet project from https://github.com/AlexeyAB/darknet .
    To use on Windows you follow darknet->build->darknet->x64
    
    Note-1: You must use cudnn 9.0 version. So, you change "9.1" to "9.0" in darknet.sln to build your project. 
    
    Note-2: You must use opencv 3.4.0. and you put "opencv_ffmpeg340_64.dll" and "opencv_world340.dll" files next to darknet.exe file.
    
  - Create  custom dataset folder into path of /x64/data/ . Put your images , label texts and process.py file into this new folder from   BBox-Label-Tool project.
  
  - To create test and trainig dataset : run process.py ( Like as a C:\darknet\build\darknet\x64\data\custom_dataset>python process.py )
  
 3- We prepare dataset to training. Now, we have to create ".data", ".names" and ".cfg" files for custom dataset. 
    Note: There is many yolo architecture. But, I suggest you to use tiny yolo architecture for android. Because of, expecially  weights file size of  Yolov2 or Yolov3 are too large for android.
     
     1- Create custom.data from any one of "--.data" file and edit according to yourself like this:

 ![ekran alintisi22](https://user-images.githubusercontent.com/38051809/40129848-08e15d32-593e-11e8-8fca-4c8d99eff362.PNG)
    
     
     2- Create custom.names from one of "--.names"  file and edit according to your class names.
     3- Create "custom_yolov2_tiny.cfg" from one of "yolov2-tiny.cfg" file. And change this file like this:
         1- Lines of 3 and 6 : batch=64
         2- Lines of 4 and 7 : subdivisions=32
         3- Line of 237 : filters = 30  ---(filters= (count of class + 5)*5)
         4- Line of 244 : classes = 1 --- classes= count of class
         
 4- Finally, put "yolov2-tiny.conv.13" file  next to  darknet.exe file. 
 
 5- Start Training: darknet.exe detector train data/custom.data cfg/custom_yolov2_tiny.cfg yolov2-tiny.conv.13
 
(C:\darknet\build\darknet\x64>darknet.exe detector train data/custom.data cfg/custom_yolov2_tiny.cfg yolov2-tiny.conv.13)
  You can see the avarage loss value for each iterations and the program save weights file into backup folder for every 100 iterasyon automatically.
  
 6- You can stop trainig when the average loss value is between 0.8 and 1.0.
 7- To use weights file for android, you have to transformation to protobuf file. 
     
     1- Download darkflow from https://github.com/thtrieu/darkflow.
     2- Create bin folder into darkflow project.
     3- Put your weights file into bin forder.
     4  Put your cfg file into darkflow/cfg folder. 
     5- Convert pb file like this:
        C:\darkflow> python flow --model cfg/custom_yolov2_tiny.cfg --load bin/custom_yolov2_tiny_3500.weights --savepb
 
 8- Now you have probuf file.(darkflow/built_graph/)
 9- You can add your own protobuf file to the asset file in android and you can run the program.
           
      

![parfume_det](https://user-images.githubusercontent.com/38051809/40131661-409c0cd6-5943-11e8-9dee-f4388a52bc3e.jpg)
![toy](https://user-images.githubusercontent.com/38051809/44819004-c24c8f00-abf3-11e8-94f5-14ac86a68e21.jpg)
![book](https://user-images.githubusercontent.com/38051809/44819006-c4aee900-abf3-11e8-86cf-7351b9a1d743.jpg)
  
       
         
        
  



  
