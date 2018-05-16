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
     
     1- Create custom.data from any one of "--.data" file and edit like this:
     
       
         
        
  



  
